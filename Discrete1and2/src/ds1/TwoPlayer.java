package ds1;
public abstract class TwoPlayer extends Game{
	/**
     * Fields used in all TwoPlayer Games to keep track of game status
     * @since 02/27/2014
     * @version 0.1
     */
    /**
     * Constant representing a win for player 1.
     * @see TwoPlayer#PLAYER2WIN
     * @see TwoPlayer#CONTINUE
     * @see TwoPlayer#DRAW
     */
	public static final int PLAYER1WIN = 1;
	 /**
     * Constant representing a win for player 2.
     * @see TwoPlayer#PLAYER1WIN
     * @see TwoPlayer#CONTINUE
     * @see TwoPlayer#DRAW
     */
	public static final int PLAYER2WIN = 2;
	/**
     * Constant representing a drawn game.
     * @see TwoPlayer#PLAYER1WIN
     * @see TwoPlayer#PLAYER2WIN
     * @see TwoPlayer#CONTINUE
     */
	public static final int DRAW = -1;
	/**
     * Constant representing a "continue" state.
     * @see TwoPlayer#PLAYER1WIN
     * @see TwoPlayer#PLAYER2WIN
     * @see TwoPlayer#DRAW
     */
	public static final int CONTINUE = 0;
	 /**
     * this variable gives the player whose turn it is
     * 1 is player 1's turn
     * 2 is player 2's turn
     */
	int whoseTurn;
	
	public void computerPlaySelf(){
		int whoseTurn = 1;
		int e = CONTINUE;
		while(e == CONTINUE){
			computerRandomMove(whoseTurn);
			e = endCheck();
			whoseTurn = 3-whoseTurn;
		}
		endstate = e;
		System.out.print(endstate);
	}

	/**
     * Plays the game.
     * Gets each player's human or computer state. Draws the board.
     * While the game is unfinished, loops through
     * the player's move, draws a new board, checks 
     * the state of the game, and changes the player's turn.
     * Checks the endstate and prints out who won or draw.
     *
     * @see TwoPlayer getHumanOrComputer()
     * @see Game isHuman
     * @see Game move(int turn)
     */
	public void play(){
		whoseTurn = 1;
		int e = CONTINUE;
		drawBoard();
		
		getHumanOrComputer();
		while (e == CONTINUE){	
			move(whoseTurn);
			drawBoard();
			e = endCheck();
			whoseTurn = 3-whoseTurn;
		}
		
		endstate = e;
		if(endstate == PLAYER1WIN)
			System.out.println("Player1 is the winner");
		if(endstate == PLAYER2WIN)
			System.out.println("Player2 is the winner");
		if(endstate == DRAW)
			System.out.println("Game is a draw");
		//System.out.println("End state = " + endstate);
	}
	 /**
     * Used to determine which players are human.
     * Initializes indices 1 and 2 of the
     * {@link Game#isHuman isHuman} array.
     * @see Game#computerMove(int)
     * @see Game#humanMove(int)
     */
	private void getHumanOrComputer(){ 
		isHuman[1] = true;
		isHuman[2] = false;
	}
}
