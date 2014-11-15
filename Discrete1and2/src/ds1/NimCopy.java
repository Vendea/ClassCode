package ds1;
import java.util.Scanner;
//import ds1.Game.DSGameNode;

public class NimCopy extends TwoPlayer{
	int numPiles; //number of piles
	int[] board; //idx 0 is player, idx 1,2,3 are the piles
	Scanner scanner;

	//for different numbers of piles
	public NimCopy(int ... piles){
		scanner = new Scanner(System.in);
		numPiles = piles.length;
		board = new int[numPiles + 1]; //create the board
		for(int i = 1; i <= numPiles; i++){ //Fill the board
			board[i] = piles[i-1];
		}
		board[0] = 1;
		numMoves = 0;
		DSGameNode<Object> root = buildTree(board);
		System.out.println(root.leavesOfTree());
		play();
		//Analyze the tree
		//DSGameNode<Object> root = buildTree(board);
		//System.out.println(root.leavesOfTree());
		//computerPlaySelf();
		System.out.println("End state = " + endstate);		
	}

	void humanMove(int turn) {
		int finalRemove = 0, finalPile;
		int tempPile = 0, tempRemove;
		String pileIn, removeIn;
		System.out.println("Player " + whoseTurn + ", how many?");
		removeIn = scanner.next();
		while(!removeIn.matches("^[0-9]+$")){
			System.out.println("Invalid entry");
			removeIn = scanner.next();
		}
		tempRemove = Integer.parseInt(removeIn);

		System.out.println("From which pile?");
		pileIn = scanner.next();
		while(!pileIn.matches("^[0-9]+$")){
			System.out.println("Invalid entry");
			pileIn = scanner.next();
			if(pileIn.matches("^[0-9]+$")){
				tempPile = Integer.parseInt(pileIn);
				while(tempPile == 0){
					System.out.println("Invalid entry");
					pileIn = scanner.next();
					break;
				}
			}
			while(!legalPile(tempPile)){
				System.out.println("Invalid entry");
				pileIn = scanner.next();
			}
		}
		if(pileIn.matches("^[0-9]+$"))
			while(!legalPile(tempPile))
				pileIn = scanner.next();

		while(!legalRemove(tempRemove, tempPile)){
			removeIn = scanner.next();
			while(!removeIn.matches("^[0-9]+$")){
				System.out.println("Invalid entry");
				removeIn = scanner.next();
			}
			tempRemove = Integer.parseInt(removeIn);
		}
		finalRemove = tempRemove;
		finalPile = tempPile;
		board[finalPile] -= finalRemove;
		board[0] = 3-board[0];
	}

	boolean legalPile(int p){
		if(p == 0){
			System.out.println("Invalid entry");
			return false;
		}
		if(p>numPiles){
			System.out.println("Invalid entry");
			return false;
		}
		if(board[p] == 0){
			System.out.println("That pile has no stones left.");
			return false;
		}
		return true;		
	}

	boolean legalRemove(int t, int p){
		if(board[p]<t && board[p] != 0){
			System.out.println("There are not enough stones in the pile you chose. \n"
					+ "Please enter a smaller number of stones.");
			return false;
		}
		return true;
	}
	/*int testInput(String in){
		int rv = 0;
			in.codePointAt(1);
		return rv;
	}*/

	void computerMove(int turn) {
		for(int i = 1; i <= numPiles; i++){//loop over piles
			for(int j = 0; j < board[i]; j++){//loop over # stones to leave
				int[] newBoard = new int[numPiles + 1];
				for(int k = 1; k <= numPiles; k++){
					newBoard[k] = board[k];
				}
				newBoard[i] = j;
				newBoard[0] = 3-board[0];
				DSGameNode<Object> root = buildTree(newBoard);
				int ww = evaluateNode(root);
				if((ww == TwoPlayer.PLAYER1WIN && turn == 1) ||
						(ww == TwoPlayer.PLAYER2WIN && turn == 2)){
					board[i] = j;
					board[0] = 3-board[0];
					numMoves++;
					return;
				}
			}
		}
		computerRandomMove(turn);
	}

	void computerRandomMove(int turn){
		//pick random pile, pick random number to leave
		int pileFrom;
		int numLeft;
		do{
			pileFrom = (int)(Math.floor(Math.random() * numPiles));
		}while(pileFrom == 0 || board[pileFrom] == 0);

		if(board[pileFrom] == 1)
			numLeft = 0;
		else{
			do{
				numLeft = (int)(Math.floor(Math.random() * board[pileFrom]));
			}while(numLeft == 0);
		}
		board[pileFrom] = numLeft;
		board[0] = 3-board[0];

		//take one stone from largest pile
		/*int largestPileSize = 0;
		int largestPileIndex = 0;
		for(int  i = 1; i <= numPiles; i++){
			if(board[i] > largestPileSize){
				largestPileSize = board[i];
				largestPileIndex = i;
			}
			board[largestPileIndex] -= 1;
		}*/
	}

	void drawBoard(){
		for(int i = 1; i <= numPiles; i++){
			System.out.println("Pile " + i + " has " + board[i] + " stones.");
		}
		System.out.println("");
	}

	int endCheck() {
		int sumOfSizes = 0;
		for(int i = 1; i <= numPiles; i++){
			sumOfSizes+=board[i];
		}
		if(sumOfSizes != 0)
			return TwoPlayer.CONTINUE;
		if(board[0] == 1)
			return TwoPlayer.PLAYER2WIN;		
		else
			return TwoPlayer.PLAYER1WIN;
	}

	DSArrayList<Object> getChildren(Object b) {
		int[] localBoard = (int[]) b;
		int turn = localBoard[0];
		DSArrayList<Object> returnArray = new DSArrayList<Object>();

		//for each pile, see what new size it could be
		for(int pile = 1; pile <= numPiles; pile++){
			for(int newSize = 0; newSize < localBoard[pile]; newSize++){

				int[] newBoard = new int[numPiles+1];
				for(int k = 1; k <= numPiles; k++){
					newBoard[k] = localBoard[k];
				}
				newBoard[pile] = newSize;
				newBoard[0] = 3-turn;
				returnArray.add(newBoard);
			}
		}
		return returnArray;
	}


	int evaluateBoard(Object b) {
		int[] localBoard = (int[]) b;
		int rv = -1;
		int sumOfSizes = 0;
		for(int i = 1; i <= numPiles; i++){
			sumOfSizes += localBoard[i];
		}
		if(sumOfSizes != 0)
			rv = TwoPlayer.CONTINUE;
		else{
			if(localBoard[0] == 1)
				rv = TwoPlayer.PLAYER2WIN;		
			else
				rv = TwoPlayer.PLAYER1WIN;
		}
		//System.out.printf("Board:([%d], %d, %d, %d)->%d\n", localBoard[0], localBoard[1], localBoard[3])
		return rv;
	}


	int whoseTurn(Object b) {
		int[] brd = (int[])b;
		return brd[0];
	}

	String boardHash(Object b) {
		int[] localBoard = (int[])b;
		String bh = "";
		for(int i = 0; i <= numPiles; i++)
			bh = bh + localBoard[i] + "+";
		return bh;
	}

}
