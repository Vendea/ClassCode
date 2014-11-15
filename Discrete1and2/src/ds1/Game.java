package ds1;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game{
	 /**
     * The number of nodes in a tree
     * @see #buildTree(Object)
     * @see ds1.TicTacToe#TicTacToe() // constructor
     */
	int numNodes;
	int endstate;
	int numMoves;

	//public int whoWon;
	//public int numWins;

	//int n; //was numMoves

	Object board;

	final void move(int turn){
		if(isHuman[turn])
			humanMove(turn);
		else 
			computerMove(turn);
	}

	//array of Booleans: true = human, false = computer
	//isHuman 1 for player 1 isHuman 2 for player 2
	boolean[] isHuman = new boolean[3];

	abstract void humanMove(int turn);
	abstract void computerMove(int turn);
	abstract void computerRandomMove(int turn);

	abstract void drawBoard();

	abstract int endCheck();

	public abstract void computerPlaySelf();
	public abstract void play();

	public class DSGameNode<E> extends DSNode<E>{
		int whoWins;
		public DSGameNode(E theThing, DSGameNode<E> p){
			super(theThing, p);
		}
	}

	/**
	 * HashMap that returns a tree node, if we 
	 * already have one that contains the same board
	 * @since 3/27/2014
	 */
	HashMap<String, DSGameNode<Object>> boardNodes = new HashMap<String,DSGameNode<Object>>();

	 /**
     * @param b -the object from which a tree will be built
     * @return DSGameNode<Object> - a tree of nodes of object b and it's children nodes
     */
	DSGameNode<Object> buildTree(Object b){
		DSGameNode<Object>	root = new DSGameNode<Object>(b, null);
		String bh = boardHash(b);
		if(boardNodes.containsKey(bh))
			return boardNodes.get(bh);
		numNodes++;

		//now get the children of this board
		//in the base case there will be no children
		DSArrayList<Object> children = getChildren(b);
		for(int i = 0; i < children.size(); i++){
			DSGameNode<Object> n = buildTree(children.get(i));
			root.addChild(n);
		}
		boardNodes.put(bh, root);
		return root;
	}

	/**
	 * The actual game needs to be able to produce a list of all "child" boards of a given board b.
	 * @param b the game board
	 * @return DSArrayList of board objects
	 */
	abstract DSArrayList<Object> getChildren(Object b);

	//Given a DSGameNode, returns the winner of this game board.	
	HashMap<String, Integer> boardValues = new HashMap<String, Integer>();
	
	/**
	 * @param b the game board to hash
	 * @return string representation of the hash
	 * @since 3/27/2014
	 */
	abstract String boardHash(Object b);
	
	/**
     * @see TwoPlayer.PLAYER1WIN
     * @see TwoPlayer.PLAYER2WIN
     * @see TwoPlayer.CONTINUE
     * @see TwoPlayer.DRAW
     * @see ds1.DSArrayList
     * @see ds1.DSNode
     * @see ds1.TwoPlayer
     * @see Game.DSGameNode
     * @param n -the node to be evaluated
     * @return -an int representing the winner of the given game board 
     */
	int evaluateNode(DSGameNode<Object> n){
		Object localBoard = n.returnThing();
		int rv = -99; //-99 is code for "uninitialized"
		//hash the board
		String bh = boardHash(localBoard);
		//see if we've seen this board already
		//if(boardValues.containsKey(bh))
			//return boardValues.get(bh);

		int val = evaluateBoard(n.returnThing());
		if(val != TwoPlayer.CONTINUE)
			rv = val;
		else{
			ArrayList<DSNode<Object>> children = n.returnChildren();

			boolean drawIsPossible = false;
			int whoseTurn = whoseTurn(n.returnThing());

			for(int i = 0; i < children.size(); i++){
				DSGameNode<Object> c = (DSGameNode<Object>)children.get(i);
				int childVal = evaluateNode(c);
				if(childVal == TwoPlayer.PLAYER1WIN &&  whoseTurn == 1){
					rv = TwoPlayer.PLAYER1WIN;
					break;
				}
				if(childVal == TwoPlayer.PLAYER2WIN &&  whoseTurn == 2){
					rv = TwoPlayer.PLAYER2WIN;
					break;
				}

				if(childVal == TwoPlayer.DRAW)
					drawIsPossible = true; //flag
			}

			if(rv == -99){
				if(drawIsPossible)
					rv = TwoPlayer.DRAW;

				else if(whoseTurn == 1)
					rv = TwoPlayer.PLAYER2WIN;

				else	
					rv = TwoPlayer.PLAYER1WIN;
			}
		}
		//System.out.printf("Board:([%d],  %d, %d, %d)
		//before returning, store result
		boardValues.put(bh, rv);
		return rv;

	}

	abstract int evaluateBoard(Object b);
	abstract int whoseTurn(Object b);
}
