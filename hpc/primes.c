#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<pthread.h>
#include<semaphore.h>

/*
 * Queue structure
 */
struct queue{
  int count;            /* # items in the queue */
  int locOfFirst;        /* index of first item  */
  sem_t spaceSem;        /* # remaining spaces   */
  sem_t itemsSem;        /* # items on queue     */
  pthread_mutex_t mutex;    /* protect count        */
  int* items;            /* array of queue ints  */
};
typedef struct queue queue;

/*
 * Forward declarations of some functions 
 */
int minNumSquares(int N);
int isPrime(int N);
void* addInts(void* N);
void* findPrimes(void* s);
void* findFourSquares(void* s);
void addItem(queue* q, int x);
int getItem(queue* q);

/*
 * Global variables
 */
int queueSize;
queue queue0, queue1, queue2;

/*
 * Prints out all prime numbers within a range of 1000 numbers
 *   that require four squares. The user inputs a number 
 *   representing "where to start," and the program checks the
 *   block of 10000 integers starting there.
 *
 * Two commandline parameters:
 *   1:  Where to start
 *   2:  Size of queues
 */
int main(int c, char* argv[]){
  // Read the commandline arguments
  int N = atoi(argv[1]);    /* Where to start */
  queueSize = atoi(argv[2]);
  printf("N = %d, qs = %d\n", N, queueSize);

  // Allocate memory for the items arrays within the queues
  // Your work here...
  queue0.items = (int*) malloc(queueSize * sizeof(int));
  queue1.items = (int*) malloc(queueSize * sizeof(int));
  queue2.items = (int*) malloc(queueSize * sizeof(int));
  printf("Allocated memory\n");

  // Initialize the counts and locOfFirsts of the queues
  queue0.count = queue0.locOfFirst = 0;
  queue1.count = queue1.locOfFirst = 0;
  queue2.count = queue2.locOfFirst = 0;
  printf("Initialized count and loc\n");

  // Initialize the queue semaphores
  // Your work here...
  sem_init(&queue0.spaceSem, 0, queueSize); 
  sem_init(&queue0.itemsSem, 0, 0);
  sem_init(&queue1.spaceSem, 0, queueSize); 
  sem_init(&queue1.itemsSem, 0, 0);
  sem_init(&queue2.spaceSem, 0, queueSize);
  sem_init(&queue2.itemsSem, 0, 0);
  printf("Initialized semaphores\n");

  // Initialize the queue mutexes
  // Your work here...
  pthread_mutex_init(&queue0.mutex, NULL);
  pthread_mutex_init(&queue1.mutex, NULL);
  pthread_mutex_init(&queue2.mutex, NULL);
  printf("Initialized mutexes\n");

  // Launch the threads
  pthread_t intsT, primesT, foursT;
  pthread_create(&intsT, NULL, addInts, (void*)&N);
  pthread_create(&primesT, NULL, findPrimes, NULL);
  pthread_create(&foursT, NULL, findFourSquares, NULL);
  printf("Launched Threads\n");

   while(1){
    int x = getItem(&queue2);
    if(x == -1)
      break;
    printf("%d is prime, and needs 4 squares\n", x);
  } 

  // Join the child threads
  // Your work here...
  pthread_join(intsT, NULL);
  pthread_join(primesT, NULL);
  pthread_join(foursT, NULL); 

  return 0;
}


/*
 * Thread to simply add items from N to N+999 to queue0
 */
void* addInts(void* N){
  int i;
  int startValue = *((int*)N);
  for(i = startValue; i < startValue + 1000; i++){
    addItem(&queue0, i);
  }
  addItem(&queue0, -1);
  printf("addInts done\n");
  return NULL;
}


/*
 * Thread to take items from the queue0, and place those
 * that are prime onto queue1.
 */
void* findPrimes(void* s){
  int x;
  do {
    x = getItem(&queue0); 
    if(isPrime(x)){
      addItem(&queue1, x);
    }
  } while(x != -1);
  addItem(&queue1, -1);
  printf("findPrimes done\n");
}



/*
 * Thread to take primes off of queue1, and place those
 * that require four squares onto queue2
 */
void* findFourSquares(void* s){
  int x;
  do{
    x = getItem(&queue1);
    if(minNumSquares(x) == 4){
      addItem(&queue2, x);
    }
  } while(x != -1);
  addItem(&queue2, -1);
}



/*
 * This function adds an item x to the given queue, q.
 *
 * It uses the queue's mutex to protect access to the count
 *   and to the items in the queue.
 *
 * Before taking an item, it waits on the space semaphore to make
 *   sure there is room to add the item. And then it posts to the
 *   items semaphore to announce that there is another in the queue.
 */
void addItem(queue* q, int x){
  sem_wait(&q->spaceSem);
  pthread_mutex_lock(&q->mutex);
  q->items[q->count] = x;
  q->count = (q->count + 1)%queueSize;
  pthread_mutex_unlock(&q->mutex);
  sem_post(&q->itemsSem);
}



/*
 * This function removes and returns an item from the given queue, q.
 *
 * It uses the queue's mutex to protect access to the count and locOfFirst,
 *   and to the items in the queue.
 *
 * Before adding an item, it waits on the items semaphore to make
 *   sure there is an item to return. And then it posts to the
 *   spaces semaphore to announce that there is another open space.
 */
int getItem(queue* q){
  int x;
  sem_wait(&q->itemsSem);
  pthread_mutex_lock(&q->mutex);
  x = q->items[q->locOfFirst];
  q->locOfFirst = (q->locOfFirst+1)%queueSize;
  q->count = (q->count-1)%queueSize;
  pthread_mutex_unlock(&q->mutex);
  sem_post(&q->spaceSem);
  return x;
}



/*
 * Returns the minimum number of square numbers (including 0)
 * needed to represent the input number N as the sum of those squares.
 *
 * By Lagrange's four squares theorem, the answer will always be <= 4.
 */
int minNumSquares(int N){
  int sq = sqrt(N);
  int a, b, c, d;        /* counters */
  int numNotZero = 0;

  for(a = 0; a <= sq; a++){
    for(b = a; b <= sq; b++){
      for(c = b; c <= sq; c++){
    for(d = c; d <= sq; d++){
      if(N == a*a + b*b + c*c + d*d)
        goto found;
    }
      }
    }
  }
 found:
  if(a > 0) numNotZero++;
  if(b > 0) numNotZero++;
  if(c > 0) numNotZero++;
  if(d > 0) numNotZero++;

  return numNotZero;
}


/*
 * Returns true if the number is a prime >= 2
 * and false otherwise.
 *
 * In C, "0" means "false," "not 0" means "true".
 */
int isPrime(int N){
  if(N < 2 || (N % 2) == 0)
    return 0;
  if(N == 2)
    return 1;

  int i;            /* counter */
  for(i = 3; i <= sqrt(N); i = i + 2){
    if(N % i == 0)
      return 0;
  }

  return 1;            /* If we make it here, N has no divisors */
}
