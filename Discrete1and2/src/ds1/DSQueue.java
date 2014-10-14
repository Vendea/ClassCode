package ds1;

/**
 * DSQueue is really acting like a "priority queue"
 * where we always return the element with the least value
 * 
 * @author KatherineMJB
 *
 * @param <K> the items in the queue
 * @param <V> the values associated with the items - sort based on these
 */
public class DSQueue<K, V> {

	QueueNode first;
	QueueNode last;

	public DSQueue(){
		first = null;
		last = null;
	}

	/**
	 * 
	 * @usage: DSQueue q;
	 * @usage: q.push("My name", "its value");
	 * @param item the item to be pushed
	 * @param value item's initial value
	 */
	public void push(K item, V value){
		QueueNode newNode = new QueueNode(item, value, null);
		if(last == null){
			first = newNode;
			last = newNode;
		}
		else{
			last.next = newNode;
			last = newNode;
		}
	}

	/**
	 * Removes the first item from the queue
	 * @return the first item
	 */
	public K pop(){
		if(this.isEmpty())
			return null;
		if(first.next == null){
			K rv = first.item;
			first = null;
			last = null;
			return rv;				
		}
		else{
			K rv = first.item;
			first = first.next;
			return rv;
		}
	}

	public void sort(){

	}

	/**
	 * 
	 * @param item to update
	 * @param value new value to give it
	 * @return true if item's value was updated
	 */
	public boolean update(K item, V value){
		return true;
	}

	public boolean isEmpty(){
		return(first == null);
	}

	public void print(){
		QueueNode n = first;
		if(n == null){
			System.out.println("Queue is empty.");
			return;
		}

		do{
			System.out.print(n.item +",");
			n = n.next;
		}while(n != null);
		System.out.println("");
	}
	/**
	 * Inner class to hold the items and references to next
	 * @author KatherineMJB
	 *
	 */
	public class QueueNode{
		K item;
		V value;
		QueueNode next;

		public QueueNode(K i, V v, QueueNode qn){
			item = i;
			value = v;
			next = qn;			
		}
	}
}
