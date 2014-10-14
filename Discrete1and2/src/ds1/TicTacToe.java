package ds1;
import java.util.Scanner;

/**
 * This class implements the game of Tic-Tac-Toe
 *
 * @author UD DS1, Spring 2014
 * @version 0.1
 * @see ds1.Nim
 */
public class TicTacToe extends TwoPlayer{

	char[][] board;
	Scanner scanner;

	public TicTacToe(){
		scanner = new Scanner(System.in);
		board = new char[3][3];
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				board[i][j] = ' ';
		numNodes = 0;
		//DSGameNode<Object> root = buildTree(board);
		//int ww = evaluateNode(root);

		numMoves = 0;
		play();
		//computerPlaySelf();
		System.out.println("End state = " + endstate);
	}

	/**
	 * This method overrides the abstract method and draws the TicTacToe Board
	 *
	 * @author UD DSI, Spring 2014
	 * @see ds1.Game#drawBoard()
	 * @version 0.1
	 * @since 02/27/2014
	 */
	void drawBoard(){
		System.out.println(" " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + "\t" + " " + charForPosition(0, 0) + " | " + charForPosition(0, 1) + " | " + charForPosition(0, 2));
		System.out.println("---+---+---\t---+---+---");
		System.out.println(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + "\t" + " " + charForPosition(1, 0) + " | " + charForPosition(1, 1) + " | " + charForPosition(1, 2));
		System.out.println("---+---+---\t---+---+---");
		System.out.println(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + "\t" + " " + charForPosition(2, 0) + " | " + charForPosition(2, 1) + " | " + charForPosition(2, 2));
		System.out.println("\n");
	}

	/**
	 * This gives the character that is in our input template
	 * based on what is in the board
	 * 
	 * @param row
	 * @param col
	 * @return number of space if available or blank if space is not available
	 */
	char charForPosition(int row, int col){
		int candidateValue = 3*(2-row) + col + 1;
		if(board[row][col] == ' '){
			//48 is the ASCII value for char 0
			return (char)(candidateValue + 48);
		} 
		else{
			return ' ';
		}
	}

	/**
	 * This method is used when it is the human player's move
	 * This method also keeps the player from making an illegal move
	 * @author UD DSI, Spring 2014
	 * @version 0.1
	 * @param turn
	 */
	void humanMove(int turn){
		int row, move, col;
		System.out.println("What is player " + whoseTurn + "'s move?");
		do{	
			String temp = scanner.next();
			boolean valid = temp.matches("^[1-9]$");
			while(!valid){
				System.out.println("Entry invalid.");
				temp = scanner.next();
				valid = temp.matches("^[1-9]$");

			}
			move = Integer.parseInt(temp);
			row = 2 - (move - 1)/3;
			col = (move - 1) % 3;
			if (board[row][col] != ' '){
				System.out.println("That space is already taken.  Please try again.");
			}
		} 
		while(board[row][col] != ' ');  

		char moveChar;
		if(whoseTurn == 1)
			moveChar = 'X';
		else
			moveChar = 'O';
		board[row][col] = moveChar;
		numMoves++;
	}

	/**
	 * This method is called when computerMove() can't compute a winning move
	 * It moves player 'turn' randomly
	 * 
	 * @param turn    - gives whose turn it is
	 */
	void computerRandomMove(int turn){
		DSArrayList<Integer> moves = new DSArrayList<Integer>();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(board[i][j] == ' ')
					moves.add(3*i + j);
			}
		}
		int numMovesAvailable = moves.size();
		int myMoveIndex = (int)(Math.floor(Math.random() * numMovesAvailable));
		int myMove = moves.get(myMoveIndex);
		int row = myMove/3;
		int col = myMove%3;

		char moveChar;
		if(turn == 1)
			moveChar = 'X';
		else
			moveChar = 'O';
		board[row][col] = moveChar;
		numMoves++;
	}

	//smart version.  
	void computerMove(int turn){
		char moveChar;
		if(turn == 1)
			moveChar = 'X';
		else
			moveChar = 'O';
		int moveRow = -1;
		int moveCol = -1;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(board[i][j] == ' '){
					char[][] newBoard = new char[3][3];
					for(int k = 0; k < 3; k++){
						for(int l = 0; l < 3; l++){
							newBoard[k][l] = board[k][l];
						}
					}
					newBoard[i][j] = moveChar;
					DSGameNode<Object> root = buildTree(newBoard);
					int ww = evaluateNode(root);
					if((ww == TwoPlayer.PLAYER1WIN && turn == 1) ||
							(ww == TwoPlayer.PLAYER2WIN && turn == 2)){
						board [i][j] = moveChar;
						numMoves++;
						return;
					}
					if(ww == TwoPlayer.DRAW){
						moveRow = i;
						moveCol = j;
					}
				}
			}
		}
		if(moveRow != -1){
			board[moveRow][moveCol] = moveChar;
			numMoves++;
			return;
		}

		computerRandomMove(turn);
	}

	/**
	 * This method overrides the abstract method in the Game class.
	 * Checks to see if the game has ended. If player 1 has won it
	 * returns TwoPlayer.PLAYER1WIN, if player 2 has won it returns
	 * TwoPlayer.PLAYER2Win, if a draw has occured it returns TwoPlayer.DRAW,
	 * and if the game is not over it returns TwoPlayer.CONTINUE.
	 *
	 * Overrides the abstract method in the Game class
	 * @see DS1.TicTacToe#endCheck()
	 * @return Returns an integer which is either -2, -1, 0, or 1.
	 */
	int endCheck(){
		//int winner;
		char winChar = ' ';
		for(int i = 0; i < 3; i++){
			if(board[i][0] == board [i][1] && board[i][1] == board [i][2] && board[i][0] != ' ')
				winChar = board[i][0];
		}
		for(int i = 0; i < 3; i++){
			if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ')
				winChar = board[0][i];
		}
		if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ')
			winChar = board[0][0];
		if(board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != ' ')
			winChar = board[2][0];

		if(winChar == 'X')
			return PLAYER1WIN;
		if(winChar == 'O')
			return PLAYER2WIN;
		if(numMoves == 9)
			return DRAW;
		else 
			return CONTINUE;
	}

	DSArrayList<Object> getChildren(Object b){
		char[][] localBoard = (char[][]) b;

		DSArrayList<Integer> moves = new DSArrayList<Integer>();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(localBoard[i][j] == ' ')
					moves.add(3*i + j);
			}
		}

		int whoseTurn = (moves.size() % 2 == 0) ? 2 : 1;
		//create move, create board, add board to return array
		DSArrayList<Object>  returnArray = new DSArrayList<Object>(moves.size());
		for(int i = 0; i < moves.size(); i++){
			int myMove = moves.get(i);
			int row = myMove/3;
			int col = myMove%3;

			char moveChar;
			if(whoseTurn == 1)
				moveChar = 'X';
			else
				moveChar = 'O';
			char[][] newBoard = new char [3][3];
			for(int k = 0; k < 3; k++){
				for(int j = 0; j < 3; j++){
					newBoard[k][j] = localBoard[k][j];
				}
			}
			newBoard[row][col] = moveChar;
			returnArray.add(newBoard);
		}
		return returnArray;
	}

	int evaluateBoard(Object b){
		char[][] localBoard = (char[][])b;
		char winChar = ' ';

		for(int i = 0; i < 3; i++){
			if(localBoard[i][0] == localBoard [i][1] && localBoard[i][1] == localBoard [i][2] && localBoard[i][0] != ' ')
				winChar = localBoard[i][0];
		}
		for(int i = 0; i < 3; i++){
			if(localBoard[0][i] == localBoard[1][i] && localBoard[1][i] == localBoard[2][i] && localBoard[0][i] != ' ')
				winChar = localBoard[0][i];
		}
		if(localBoard[0][0] == localBoard[1][1] && localBoard[1][1] == localBoard[2][2] && localBoard[0][0] != ' ')
			winChar = localBoard[0][0];

		if(localBoard[2][0] == localBoard[1][1] && localBoard[1][1] == localBoard[0][2] && localBoard[2][0] != ' ')
			winChar = localBoard[2][0];

		if(winChar == 'X')
			return TwoPlayer.PLAYER1WIN;
		if(winChar == 'O')
			return TwoPlayer.PLAYER2WIN;

		if(numMoves(localBoard) == 9)
			return TwoPlayer.DRAW;

		return TwoPlayer.CONTINUE;
	}

	int numMoves(char[][]b){
		int m = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(b[i][j]!=' ')
					m++;
			}
		}
		return m;
	}

	int whoseTurn(Object b){
		char[][] localBoard = (char[][])b;
		int m = numMoves(localBoard);
		if(m%2==0)
			return 1;
		else
			return 2;
	}

	/**
	 * Converts a game board into a string to be hashed
	 * @see ds1.Game#boardHash(Object)
	 * @param b the board to hash
	 * @return the String of the hashed board
	 */
	String boardHash(Object b) {
		String rv = "";
		char[][] localBoard = (char[][])b;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				rv = rv + localBoard[i][j] + "+";
			}
		}
		/*String rv = "" + hb[0][0] + hb[0][1]+hb[0][2]+
				hb[1][0] + hb[1][1]+hb[1][2]+
				hb[2][0] + hb[2][1]+hb[2][2];*/
		return rv;
	}
}
