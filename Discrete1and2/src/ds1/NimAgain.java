package ds1;
import java.util.Scanner;

import ds1.Game.DSGameNode;

public class NimAgain extends TwoPlayer{
	int numPiles; //number of piles
	int[] board; //idx 0 is player, idx 1,2,3 are the piles
	Scanner scanner;

	public NimAgain(boolean human, int ... piles){
		scanner = new Scanner(System.in);
		numPiles = piles.length;
		board = new int[numPiles + 1]; //create the board
			board[0] = 1; //player number whose turn it is
			for(int i = 1; i <= numPiles; i++){ //Fill the board
				board[i] = piles[i-1];
			}
		numMoves = 0;
		if(human){
			play();
			System.out.println("End state = " + endstate);
		}
		else
			computerPlaySelf();

	}

	void drawBoard(){
		for(int i = 1; i <= numPiles; i++){
			System.out.println("Pile " + i + " has " + board[i] + " stones.");
		}
		System.out.println(" ");
	}

	void humanMove(int turn) {
		int pile, takeAway;
		int tempTakeAway = -1;

		/*int sumOfSizes = 0;
		for(int i = 1; i <= numPiles; i++){
			sumOfSizes+=board[i];
		}*/

		System.out.println("Player " + whoseTurn + ", how many?");
		takeAway = scanner.nextInt();

		System.out.println("From which pile?");
		pile = scanner.nextInt();
		if(takeAway > board[pile]){
			do{
				System.out.println("There are not that many stones. Please enter a smaller number.");
				tempTakeAway = scanner.nextInt();
			}while(tempTakeAway > board[pile]);
		}
		if(tempTakeAway > -1)
			takeAway = tempTakeAway;
		board[pile] -= takeAway;
		board[0] = 3-board[0];
	}


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

	public void computerRandomMove(int turn){
		//pick random pile, pick random number to leave
		int pileFrom;
		int numLeft;
		do{
			pileFrom = (int)(Math.floor(Math.random() * numPiles));
		}while(pileFrom == 0 || board[pileFrom] == 0);
		do{
			numLeft = (int)(Math.floor(Math.random() * board[pileFrom]));
		}while(numLeft == 0);
		board[pileFrom] = numLeft;

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

	int endCheck() {
		int sumOfSizes = 0;
		for(int i = 1; i <= numPiles; i++){
			sumOfSizes+=board[i];
		}
		if(sumOfSizes == 0 && board[0] == 1)
			return TwoPlayer.PLAYER2WIN;
		if(sumOfSizes == 0 && board[0] == 2)
			return TwoPlayer.PLAYER1WIN;
		else
			return CONTINUE;
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
		for(int i = 1; i < numPiles; i++){
			sumOfSizes += localBoard[i];
		}
		if(sumOfSizes != 0)
			rv = TwoPlayer.PLAYER1WIN;
		else{
			if(board[0] == 1)
				rv = TwoPlayer.PLAYER2WIN;
			else
				rv = TwoPlayer.CONTINUE;
		}
		//System.out.printf("Board:([%d], %d, %d, %d)->%d\n", localBoard[0], localBoard[1], localBoard[3])
		return rv;
	}

	
	int whoseTurn(Object b) {
		int[] brd = (int[])b;
		return brd[0];
	}

	String boardHash(Object b) {
		return null;
	}

}
