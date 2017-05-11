package ds1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * shipArrayTwo goes underneath
 * shipButtonTwo and attackShipButtonOne
 * 
 * shipArrayOne goes underneath
 * shipButtonOne and attackShipButtonTwo
 */

public class BattleshipMineCopy implements ActionListener{
	JFrame myBoardGUIOne;
	JButton[][] shipButtonOne;

	JLabel[] rowLabel;
	JLabel[] columnLabel;

	JFrame attackBoardGUIOne;
	JButton[][] attackShipButtonOne;

	JLabel[] attackrowLabelOne;
	JLabel[] attackcolumnLabelOne;

	//set up the GUI for player two
	JFrame myBoardGUITwo;
	JButton[][] shipButtonTwo;

	JFrame attackBoardGUITwo;
	JButton[][] attackShipButtonTwo;

	JLabel[] rowLabelTwo;
	JLabel[] columnLabelTwo;

	JLabel[] attackrowLabelTwo;
	JLabel[] attackcolumnLabelTwo;

	int[][] shipArrayOne;
	int[][] shipArrayTwo;

	JFrame statusOne;
	JTextArea textOne;

	JFrame statusTwo;
	JTextArea textTwo;

	static final int HIT = 100;
	static final int MISS = 50;
	static final int SHIP = 25;
	static final int EMPTY = 17;
	int turn;

	boolean placingShips;
	boolean cplay;

	Semaphore holdForHuman;

	Ship patrolBoat;
	Ship submarine;
	Ship destroyer;
	Ship battleship;
	Ship carrier;

	int shipsPlaced;
	int numMoves;
	boolean allShipsPlaced;
	//int players;

	public BattleshipMineCopy() {

		myBoardGUIOne = new JFrame("Player One - My Ships");
		myBoardGUIOne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myBoardGUIOne.setSize(new Dimension(670,650));
		//myBoardGUIOne.setLocation(0, 0);

		attackBoardGUIOne = new JFrame("Player One - Enemy Ships - Attack Here!");
		attackBoardGUIOne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		attackBoardGUIOne.setSize(new Dimension(670,650));
		//attackBoardGUIOne.setLocation(0, 0);

		myBoardGUITwo = new JFrame("Player Two - My Ships");
		myBoardGUITwo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myBoardGUITwo.setSize(new Dimension(670,650));
		//myBoardGUITwo.setLocation(0, 0);

		attackBoardGUITwo = new JFrame("Player Two - Enemy Ships - Attack Here!");
		attackBoardGUITwo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		attackBoardGUITwo.setSize(new Dimension(670,650));
		//attackBoardGUITwo.setLocation(670, 0);

		shipArrayOne = new int[10][10];
		shipArrayTwo = new int[10][10];

		JLabel topLeftBlank = new JLabel("");
		columnLabel = new JLabel[10];
		rowLabel = new JLabel[10];
		shipButtonOne = new JButton[10][10];
		shipButtonTwo = new JButton[10][10];
		JPanel centerPanel = new JPanel();
		JPanel northPanel = new JPanel();

		JLabel attacktopLeftBlank = new JLabel("");
		attackcolumnLabelOne = new JLabel[10];
		attackrowLabelOne = new JLabel[10];
		attackShipButtonOne = new JButton[10][10];
		attackShipButtonTwo = new JButton[10][10];
		JPanel attackcenterPanel = new JPanel();
		JPanel attacknorthPanel = new JPanel();

		JLabel topLeftBlankTwo = new JLabel("");
		columnLabelTwo = new JLabel[10];
		rowLabelTwo = new JLabel[10];
		JPanel centerPanelTwo = new JPanel();
		JPanel northPanelTwo = new JPanel();

		JLabel attacktopLeftBlankTwo = new JLabel("");
		attackcolumnLabelTwo = new JLabel[10];
		attackrowLabelTwo = new JLabel[10];
		JPanel attackcenterPanelTwo = new JPanel();
		JPanel attacknorthPanelTwo = new JPanel();

		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				shipArrayOne[i][j]=EMPTY;
				shipArrayTwo[i][j]=EMPTY;

				shipButtonOne[i][j] = new JButton("");
				shipButtonTwo[i][j] = new JButton("");
				shipButtonOne[i][j].addActionListener(this);
				shipButtonTwo[i][j].addActionListener(this);

				attackShipButtonOne[i][j] = new JButton("");
				attackShipButtonOne[i][j].addActionListener(this);
				attackShipButtonTwo[i][j] = new JButton("");
				attackShipButtonTwo[i][j].addActionListener(this);

				attackrowLabelOne[i] = new JLabel(""+(char)(i+65), JLabel.CENTER);
				attackrowLabelTwo[i] = new JLabel(""+(char)(i+65), JLabel.CENTER);
				rowLabel[i] = new JLabel(""+(char)(i+65), JLabel.CENTER);
				rowLabelTwo[i] = new JLabel(""+(char)(i+65), JLabel.CENTER);

				if(j<9){
					columnLabel[j] = new JLabel(""+(char)(j+49), JLabel.CENTER);
					columnLabelTwo[j] = new JLabel(""+(char)(j+49), JLabel.CENTER);
					attackcolumnLabelOne[j] = new JLabel(""+(char)(j+49), JLabel.CENTER);
					attackcolumnLabelTwo[j] = new JLabel(""+(char)(j+49), JLabel.CENTER);
				}
				if(j == 9){
					columnLabel[j] = new JLabel("10", JLabel.CENTER);
					columnLabelTwo[j] = new JLabel("10", JLabel.CENTER);
					attackcolumnLabelOne[j] = new JLabel("10", JLabel.CENTER);
					attackcolumnLabelTwo[j] = new JLabel("10", JLabel.CENTER);
				}
				attackShipButtonOne[i][j].setText(rowLabel[i].getText()+columnLabel[j].getText());
				attackShipButtonTwo[i][j].setText(rowLabel[i].getText()+columnLabel[j].getText());
				shipButtonOne[i][j].setText(rowLabel[i].getText()+columnLabel[j].getText());
				shipButtonTwo[i][j].setText(rowLabel[i].getText()+columnLabel[j].getText());
			}			
		}

		centerPanel.setLayout(new GridLayout(11, 11));
		centerPanelTwo.setLayout(new GridLayout(11, 11));
		centerPanel.add(topLeftBlank);
		centerPanelTwo.add(topLeftBlankTwo);
		attackcenterPanel.setLayout(new GridLayout(11, 11));
		attackcenterPanelTwo.setLayout(new GridLayout(11,11));
		attackcenterPanel.add(attacktopLeftBlank);  
		attackcenterPanelTwo.add(attacktopLeftBlankTwo);

		for(int i = 0; i< 10; i++){
			centerPanel.add(columnLabel[i]);
			centerPanelTwo.add(columnLabelTwo[i]);
			attackcenterPanel.add(attackcolumnLabelOne[i]);	
			attackcenterPanelTwo.add(attackcolumnLabelTwo[i]);
		}

		for(int i = 1; i < 11; i++){
			for(int j = 1; j < 11; j++){
				if(j==1){
					centerPanel.add(rowLabel[i-1]);
					centerPanelTwo.add(rowLabelTwo[i-1]);
					attackcenterPanel.add(attackrowLabelOne[i-1]);
					attackcenterPanelTwo.add(attackrowLabelTwo[i-1]);
				}
				centerPanel.add(shipButtonOne[i-1][j-1]);
				centerPanelTwo.add(shipButtonTwo[i-1][j-1]);
				attackcenterPanel.add(attackShipButtonOne[i-1][j-1]);
				attackcenterPanelTwo.add(attackShipButtonTwo[i-1][j-1]);
			}
		}

		JButton newGame = new JButton("New Game");
		JButton newGame2 = new JButton("New Game");
		JButton newGame3 = new JButton("New Game");
		JButton newGame4 = new JButton("New Game");
		northPanel.add(newGame);
		northPanelTwo.add(newGame2);
		attacknorthPanel.add(newGame4);
		attacknorthPanelTwo.add(newGame3);
		newGame.addActionListener(this);
		newGame2.addActionListener(this);
		newGame3.addActionListener(this);
		newGame4.addActionListener(this);

		myBoardGUIOne.add(centerPanel, BorderLayout.CENTER);
		myBoardGUITwo.add(centerPanelTwo, BorderLayout.CENTER);
		myBoardGUIOne.add(northPanel, BorderLayout.NORTH);
		myBoardGUITwo.add(northPanelTwo, BorderLayout.NORTH);
		//myBoardGUIOne.setVisible(true);
		//myBoardGUITwo.setVisible(true);

		attackBoardGUIOne.add(attackcenterPanel, BorderLayout.CENTER);
		attackBoardGUITwo.add(attackcenterPanelTwo, BorderLayout.CENTER);
		attackBoardGUIOne.add(attacknorthPanel, BorderLayout.NORTH);
		attackBoardGUITwo.add(attacknorthPanelTwo, BorderLayout.NORTH);
		//attackBoardGUIOne.setVisible(true);
		//attackBoardGUITwo.setVisible(true);

		newGame();

	}

	public void newGame(){
		myBoardGUIOne.setVisible(false);
		myBoardGUITwo.setVisible(false);
		attackBoardGUIOne.setVisible(false);
		attackBoardGUITwo.setVisible(false);
		Object[] options = {"One Player", "Two Players"};
		int n = JOptionPane.showOptionDialog(null, "How many players?",
				"Start Game", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		patrolBoat = new Ship(2, "Patrol Boat", 0);
		submarine = new Ship(3, "Submarine", 1);
		destroyer = new Ship(3, "Destroyer", 2);
		battleship = new Ship(4, "Battleship", 3);
		carrier = new Ship(5, "Aircraft Carrier", 4);

		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++){
				shipArrayOne[i][j]=EMPTY;
				shipArrayTwo[i][j]=EMPTY;
				shipButtonOne[i][j].setBackground(null);
				shipButtonOne[i][j].setEnabled(true);
				attackShipButtonOne[i][j].setBackground(null);
				attackShipButtonOne[i][j].setEnabled(false);
				shipButtonTwo[i][j].setBackground(null);
				attackShipButtonTwo[i][j].setBackground(null);
				attackShipButtonTwo[i][j].setEnabled(false);
			}	

		if(n == JOptionPane.NO_OPTION){
			cplay = false;
			myBoardGUIOne.setLocation(0,0);
			myBoardGUITwo.setLocation(0,0);
			attackBoardGUIOne.setLocation(0,0);
			attackBoardGUITwo.setLocation(670,0);
			myBoardGUIOne.setVisible(true);
			turn = 1;
		}
		else{
			myBoardGUIOne.setLocation(0,0);
			attackBoardGUIOne.setLocation(670,0);
			myBoardGUIOne.setVisible(true);
			attackBoardGUIOne.setVisible(true);
			cplay = true;
			//cplayRandShips();
			double t = (Math.random()*2);
			if(t < 1){
				patrolBoat.setLocation(2, 0, 0, 0, 1);
				submarine.setLocation(2,2, 2, 4, 2);
				destroyer.setLocation(2,2, 3, 2, 5);
				battleship.setLocation(2,5, 5, 5, 8);
				carrier.setLocation(2,6, 8, 6, 4);
			}
			else{
				patrolBoat.setLocation(2, 2, 4, 3, 4);
				submarine.setLocation(2, 4, 5, 4, 7);
				destroyer.setLocation(2, 5, 8, 7, 8);
				battleship.setLocation(2, 9, 4, 9, 7);
				carrier.setLocation(2, 6, 0, 6, 4);
			}
			hardCodeShipArray(t);
		}

		allShipsPlaced = false;
		shipsPlaced = 0;
		numMoves = 0;
		turn = 1;
		JOptionPane.showMessageDialog(null,"Click a square to start placing your ships "
				+ "beginning with the smallest ship, the Patrol Boat.",
				"Begin battleship - place ships", JOptionPane.INFORMATION_MESSAGE);	

	}

	public void hardCodeShipArray(double t){
		if(t < 1){
			shipArrayTwo[0][0]=SHIP;
			shipArrayTwo[0][1]=SHIP;

			shipArrayTwo[2][2]=SHIP;
			shipArrayTwo[3][2]=SHIP;
			shipArrayTwo[4][2]=SHIP;

			shipArrayTwo[2][3]=SHIP;
			shipArrayTwo[2][4]=SHIP;
			shipArrayTwo[2][5]=SHIP;

			shipArrayTwo[5][5]=SHIP;
			shipArrayTwo[5][6]=SHIP;
			shipArrayTwo[5][7]=SHIP;
			shipArrayTwo[5][8]=SHIP;

			shipArrayTwo[6][8]=SHIP;
			shipArrayTwo[6][7]=SHIP;
			shipArrayTwo[6][6]=SHIP;
			shipArrayTwo[6][5]=SHIP;
			shipArrayTwo[6][4]=SHIP;
		}

		else{
			shipArrayTwo[2][4]=SHIP;
			shipArrayTwo[3][4]=SHIP;

			shipArrayTwo[4][5]=SHIP;
			shipArrayTwo[4][6]=SHIP;
			shipArrayTwo[4][7]=SHIP;

			shipArrayTwo[5][8]=SHIP;
			shipArrayTwo[6][8]=SHIP;
			shipArrayTwo[7][8]=SHIP;

			shipArrayTwo[9][4]=SHIP;
			shipArrayTwo[9][5]=SHIP;
			shipArrayTwo[9][6]=SHIP;
			shipArrayTwo[9][7]=SHIP;

			shipArrayTwo[6][0]=SHIP;
			shipArrayTwo[6][1]=SHIP;
			shipArrayTwo[6][2]=SHIP;
			shipArrayTwo[6][3]=SHIP;
			shipArrayTwo[6][4]=SHIP;
		}
	}

	/**
	 * checks the button the user clicked to make sure
	 * the current ship can go there
	 * if possible, highlights possibilities for user and
	 * disables illegal placement buttons, and returns true
	 * @see BattleshipMine#placeShip(Ship, int, int)
	 * if no possibilities for the current ship and square,
	 * returns false
	 * @param shipid the id of the ship we are placing
	 * @param temprow from the button user clicked
	 * @param tempcol from the button user clicked
	 * @return true if at least one orientation is possible otherwise false
	 */
	public Boolean checkShipPlace(int t, int shipid, int temprow, int tempcol){
		int place[][];
		Boolean rv = true;
		Boolean up = true;
		Boolean down = true;
		Boolean left = true;
		Boolean right = true;
		if(shipsPlaced > 0)
			returnVoid();

		Ship thing = whichShip(shipid);
		int tlength = thing.getLength()-1;
		if(t == 1)
			place = shipArrayOne;
		else
			place = shipArrayTwo;

		//check up:
		if(temprow-tlength < 0)
			up = false;
		if(up)
			for(int i = 1; i <= tlength; i++)
				if(place[temprow-i][tempcol] != EMPTY){
					up = false;
				}
		//check down:
		if(temprow + tlength > 9)
			down = false;
		if(down)
			for(int i = 1; i <= tlength; i++)
				if(place[temprow+i][tempcol] != EMPTY){
					down = false;
				}
		//check left:
		if(tempcol-tlength < 0)
			left = false;
		if(left)
			for(int i = 1; i <= tlength; i++)
				if(place[temprow][tempcol - i] != EMPTY){
					left = false;
				}
		//check right:
		if(tempcol + tlength > 9)
			right  = false;
		if(right)
			for(int i = 1; i <= tlength; i++)
				if(place[temprow][tempcol+i] != EMPTY){
					right = false;
				}

		//set rv:
		if(!up&&!down&&!left&&!right)
			rv = false;		
		else
			rv = true;

		if(rv){ //if the place has at least one valid option, set any and all possibilities enabled, disable all else
			JButton buttons[][];
			if(t == 1)
				buttons = shipButtonOne;
			else
				buttons = shipButtonTwo;
			placingShips = true;
			buttons[temprow][tempcol].setBackground(Color.BLACK);
			place[temprow][tempcol] = SHIP;
			for(int i = 0; i < 10; i++)
				for(int j = 0; j < 10; j++)
					buttons[i][j].setEnabled(false);
			//set legal options enabled
			if(down)
				buttons[temprow+tlength][tempcol].setEnabled(true);
			if(up)
				buttons[temprow-tlength][tempcol].setEnabled(true);
			if(right)
				buttons[temprow][tempcol+tlength].setEnabled(true);
			if(left)
				buttons[temprow][tempcol-tlength].setEnabled(true);
			System.out.println(thing.getName()+ " begun placing. placingShips is true now.");
			//System.out.println('s');
		}
		//if(cplay && turn == 2)
		//if(!up || !down || !left || !right)
		//return false;
		return rv;
	}

	/**
	 * Finishes the placement of the ship.
	 * @see Ship#setLocation(int, int, int, int)
	 * @see Ship#colLocation
	 * @see Ship#rowLocation
	 * @param shipid id of the ship currently being placed
	 * @param srow start row
	 * @param scol start column
	 * @param trow end row
	 * @param tcol end column
	 */
	public void finishShipPlacement(int t, int shipid, int srow, int scol, int trow, int tcol) {
		JButton[][] buttons;
		int place[][];
		Ship thing = whichShip(shipid);
		int tlength = thing.getLength();

		if(t == 1){
			buttons = shipButtonOne;
			place = shipArrayOne;
		}
		else{
			buttons = shipButtonTwo;
			place = shipArrayTwo;
		}

		if(srow == trow){
			if(tcol<scol)//go left
				for(int i = 0; i < tlength; i++){
					if(t==2 && !cplay)
						buttons[srow][scol-i].setBackground(Color.BLACK);
					place[srow][scol-i] = SHIP;
				}
			if(scol<tcol)//go right
				for(int i = 0; i < tlength; i++){
					if(t == 2 && !cplay)
						buttons[srow][scol+i].setBackground(Color.BLACK);
					place[srow][scol+i] = SHIP;
				}
		}
		if(scol == tcol){
			if(trow<srow)//go up
				for(int i = 0; i < tlength; i++){
					if(t == 2 && !cplay)
						buttons[srow-i][scol].setBackground(Color.BLACK);
					place[srow-i][scol] = SHIP;
				}
			if(srow<trow)//go down
				for(int i = 0; i < tlength; i++){
					if(t == 2 && !cplay)
						buttons[srow+i][scol].setBackground(Color.BLACK);
					place[srow+i][scol] = SHIP;
				}
		}
		shipsPlaced++;
		placingShips = false;

		thing.setLocation(t,srow, scol, trow, tcol);
		if(cplay){
			if(t==2){
				return;
			}
			if(shipsPlaced == 5){
				turn = 1;
				allShipsPlaced = true;
				shipsPlaced = 5;
				resetGridForShips(1);
				for(int i = 0; i <10; i++)
					for(int j = 0; j <10;j++)
						attackShipButtonOne[i][j].setEnabled(true);
				return;
			}
			else{
				turn = 1;
				resetGridForShips(1);
				displayBox(whichShip(shipid+1));
				return;
			}
		}

		else{
			System.out.println(thing.getName()+ " placed now.  Player"+ t +" placingShips is false."
					+'\n'+ shipsPlaced + " ships placed so far.");
			resetGridForShips(t);
			if(shipsPlaced<5)
				displayBox(whichShip(shipid+1));

			if(t == 1){
				if(shipsPlaced == 5){
					turn = 2;
					shipsPlaced = 0;
					allShipsPlaced = false;
					System.out.println("Playeronedone");
					myBoardGUIOne.setVisible(false);
					myBoardGUITwo.setVisible(true);
					return;
				}
				else
					return;			
			}
			if(t==2){
				if(shipsPlaced == 5){
					for(int i = 0; i < 10; i++)
						for(int j = 0; j < 10; j++){
							attackShipButtonOne[i][j].setEnabled(true);
							attackShipButtonTwo[i][j].setEnabled(true);
						}
					turn = 1;
					allShipsPlaced = true;
					System.out.println("Playertwodone");
					attackBoardGUITwo.setVisible(true);
					attackBoardGUIOne.setVisible(true);

					myBoardGUITwo.setVisible(false);
					System.out.println("Done placing ships.  Begin game play.");
					JOptionPane.showMessageDialog(null, "Begin attacking opponent's ships", 
							"Begin game play", JOptionPane.INFORMATION_MESSAGE);
				}
				else
					return;				
			}	
		}
		resetGridForShips(t);
	}

	/**
	 * 
	 * @param ident
	 * @return the Ship corresponding to the ident
	 */
	public Ship whichShip(int ident){
		if(ident==0)
			return patrolBoat;
		if(ident == 1)
			return submarine;
		if(ident == 2)
			return destroyer;
		if(ident == 3)
			return battleship;
		if(ident == 4)
			return carrier;
		return null;
	}

	int startrow = 0, startcol = 0, holdUntil = 17;
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Game")){
			int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new game?",
					"New Game", JOptionPane.OK_CANCEL_OPTION);
			if(n == JOptionPane.OK_OPTION)
				newGame();
			else
				return;
		}

		if(allShipsPlaced){
			find:
				for(int i=0;i<10;i++){
					for(int j=0;j<10;j++){
						if(shipButtonOne[i][j] == e.getSource())
							return;
						if(attackShipButtonOne[i][j] == e.getSource()){
							if(turn == 2)
								return;
							play(turn, i, j);
							if(numMoves >= holdUntil){
								int sw = endCheck();							
								if(sw!=TwoPlayer.PLAYER1WIN && sw!= TwoPlayer.PLAYER2WIN){
									holdUntil =-(sw)+holdUntil;
									return;
								}
								else{
									JOptionPane.showMessageDialog(null, "Player "+sw+" wins Battleship!",
											"Congratulations!", JOptionPane.INFORMATION_MESSAGE);
									int n = JOptionPane.showConfirmDialog(null, "Do you want to play again?", 
											"New Game", JOptionPane.YES_NO_OPTION);
									if(n == JOptionPane.YES_OPTION)
										newGame();
									else
										System.exit(0);
								}
							}

							if(attackShipButtonTwo[i][j] == e.getSource()){
								if(turn == 1)
									return;
								play(turn, i, j);
								if(numMoves >= 2*holdUntil){
									int sw = endCheck();
									if(sw!=TwoPlayer.PLAYER1WIN&& sw!= TwoPlayer.PLAYER2WIN){
										holdUntil = -(sw)+holdUntil;
										return;
									}
									else{
										JOptionPane.showMessageDialog(null, "Player "+sw+" wins Battleship!",
												"Congratulations!", JOptionPane.INFORMATION_MESSAGE);
										int n = JOptionPane.showConfirmDialog(null, "Do you want to play again?", 
												"New Game", JOptionPane.YES_NO_OPTION);
										if(n == JOptionPane.YES_OPTION)
											newGame();
										else
											System.exit(0);
									}
								}
							}
							break find;
						}
					}
				}
		}
		else{
			JButton[][] button;
			if(turn == 1)
				button = shipButtonOne;
			else
				button = shipButtonTwo;
			if(!placingShips){	
				find:
					for(int i=0;i<10;i++)
						for(int j=0;j<10;j++)
							if(button[i][j] == e.getSource()){
								startrow = i;
								startcol = j;
								if(!checkShipPlace(turn,shipsPlaced, i, j)){
									System.out.println("Ship can't go on that button.");
									JOptionPane.showMessageDialog(null, "Ship doesn't fit there.",
											"Error in Placement", JOptionPane.ERROR_MESSAGE);
									return;
								}	
								checkShipPlace(turn,shipsPlaced, i, j);
								break find;
							}
			}
			else{
				find:
					for(int i=0;i<10;i++)
						for(int j=0;j<10;j++)
							if(button[i][j] == e.getSource()){
								finishShipPlacement(turn, shipsPlaced, startrow, startcol, i, j);
								break find;
							}
			}
		}
	}

	public void play(int t, int srow, int scol){
		humanMove(t, srow, scol);
		if(cplay){
			computerRandomMove(2);
			turn = 1;
		}
		else
			turn = 3-t;
		/*if(!cplay)
			resetGridForAttacks(t);
		else{
		resetGridForShips(t);*/
		//}
		resetGrid();
	}

	void resetGrid(){
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++){
				if(cplay){
					if(shipArrayOne[i][j] == SHIP){
						shipButtonOne[i][j].setBackground(Color.BLACK);
						shipButtonOne[i][j].setEnabled(false);
					}
					if(shipArrayOne[i][j] == MISS)
						shipButtonOne[i][j].setBackground(Color.BLUE);
					if(shipArrayOne[i][j] == HIT)
						shipButtonOne[i][j].setBackground(Color.RED);
				}
				if(shipArrayOne[i][j] == HIT){
					attackShipButtonTwo[i][j].setBackground(Color.RED);
					attackShipButtonTwo[i][j].setEnabled(false);
				}
				if(shipArrayOne[i][j] == MISS){
					attackShipButtonTwo[i][j].setBackground(Color.BLUE);
					attackShipButtonTwo[i][j].setEnabled(false);
				}
				if(shipArrayTwo[i][j] == HIT){
					attackShipButtonOne[i][j].setBackground(Color.RED);
					attackShipButtonOne[i][j].setEnabled(false);
				}
				if(shipArrayTwo[i][j] == MISS){
					attackShipButtonOne[i][j].setBackground(Color.BLUE);
					attackShipButtonOne[i][j].setEnabled(false);
				}					
			}
	}
	void resetGridForShips(int t){
		int place [][];
		JButton button[][];
		if(t == 1){
			place = shipArrayOne;
			button = shipButtonOne;
		}
		else{
			place = shipArrayTwo;
			button = shipButtonTwo;
		}
		for(int i = 0; i < 10; i++)
			for(int j = 0; j <10; j++){
				if(place[i][j] == SHIP){
					button[i][j].setEnabled(false);
					button[i][j].setBackground(Color.BLACK);
				}
				else
					//if(placingShips)
					button[i][j].setEnabled(true);
				//else
				//return;
				if(place[i][j] == HIT)
					button[i][j].setBackground(Color.RED);
				if(place[i][j] == MISS)
					button[i][j].setBackground(Color.WHITE);

			}
	}

	/*void resetGridForAttacks(int t){
		int attack[][];
		JButton button[][];
		JButton sbutton[][];
		if(t ==1){
			attack = shipArrayTwo;
			button = attackShipButtonOne;
			sbutton = shipButtonTwo;
		}
		else{
			attack = shipArrayOne;
			button = attackShipButtonTwo;
			sbutton = shipButtonOne;
		}			
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++){
				if(attack[i][j] == HIT || attack[i][j] == MISS){
					button[i][j].setEnabled(false);
					if(attack[i][j]==HIT){
						button[i][j].setBackground(Color.RED);
						sbutton[i][j].setBackground(Color.RED);
					}
					else{
						button[i][j].setBackground(Color.BLUE);
						sbutton[i][j].setBackground(Color.BLUE);
					}
				}
				else{
					button[i][j].setEnabled(true);
				}
			}
	}*/

	void humanMove(int t, int srow, int scol){
		Ship thing = null;
		int[][] attack;
		if(t == 1)
			attack  = shipArrayTwo;
		else
			attack = shipArrayOne;

		if(attack[srow][scol] == SHIP){
			find:
				for(int i = 0; i < 5; i++){
					Ship s = whichShip(i);
					for(int j = 0; j < s.getLength(); j++){
						if(s.returnRow(t,j) == srow && s.returnCol(t,j) == scol){
							thing = s;
							break find;
						}	
					}
				}
		attack[srow][scol] = HIT;
		thing.iAmHit();
		if(thing.amIHit()>0)
			JOptionPane.showMessageDialog(null, "Opponent's "+thing.getName()+" hit!",
					"KA-BOOM", JOptionPane.INFORMATION_MESSAGE);

		if(thing.amIHit() == 0){
			JOptionPane.showMessageDialog(null, "You sunk the opponent's "
					+thing.getName()+ "!", "Victory!", JOptionPane.INFORMATION_MESSAGE);
		}
		}
		else{
			attack[srow][scol] = MISS;
		}
		if(cplay)
			numMoves+=2;
		else
			numMoves++;
	}

	ArrayList<String> nums = new ArrayList<String>();
	void computerRandomMove(int t) {
		int srow = 0, scol = 0;
		boolean thir = false;

		while(!thir){
			srow = (int)Math.floor(Math.random()*10);
			scol = (int)Math.floor(Math.random()*10);
			String thing = Integer.toString(srow)+Integer.toString(scol);
			if(nums.contains(thing))
				thir = false;
			else{
				nums.add(thing);
				thir = true;
			}
		}

		if(shipArrayOne[srow][scol] == SHIP)// || shipArrayOne[srow][scol] == HIT)
			shipArrayOne[srow][scol] = HIT;
		else
			shipArrayOne[srow][scol] = MISS;
		turn = 1;
	}

	public void disableBoard(JButton[][] b){
		for(int i = 0;i<10;i++)
			for(int j = 0; j < 10; j++)
				b[i][j].setEnabled(false);
	}

	public void drawBoard() {
		resetGridForShips(turn);
		//resetGridForAttacks(turn);
	}

	public void displayBox(Ship s) {
		JFrame parent;
		if(turn == 1)
			parent = myBoardGUIOne;
		else
			parent = myBoardGUITwo;
		JOptionPane.showMessageDialog(parent,"Place your "+s.getName(),
				s.getName(), JOptionPane.INFORMATION_MESSAGE);
	}

	public int endCheck() {
		int toWin = 17;
		int testOne = 0;
		int testTwo = 0;
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++)
				if(shipArrayOne[i][j] == HIT)
					testOne++;
		if(testOne<17){
			for(int i = 0; i < 10; i++)
				for(int j = 0; j < 10; j++)
					if(shipArrayTwo[i][j] == HIT)
						testTwo++;
		}

		if(testTwo>testOne)
			toWin = -(toWin - testTwo);
		if(testTwo<=testOne)
			toWin = -(toWin - testOne);
		if(testTwo == 17)
			toWin = TwoPlayer.PLAYER1WIN;
		if(testOne == 17)
			toWin = TwoPlayer.PLAYER2WIN;
		return toWin;
	}

	ArrayList<String> ssss = new ArrayList<String>();
	void cplayRandShips(){
		int horv = (int) Math.floor(Math.random()*4);
		int srow = 0, scol = 0;
		boolean thir = false;
		for(int i = 0; i < 5; i++){
			do{
				srow = (int)(Math.floor(Math.random()*5));
				scol = (int)(Math.floor(Math.random()*5));
				String thing = Integer.toString(srow)+Integer.toString(scol);
				if(ssss.contains(thing))
					thir = false;
				else{
					ssss.add(thing);
					thir = true;
				}
			}while(!thir);
			if(horv<1){		
				finishShipPlacement(2, i, srow, scol, srow, scol+whichShip(i).getLength()-1);
			}
			else{
				this.finishShipPlacement(2, i, srow, scol, srow+whichShip(i).getLength()-1, scol);
			}
		}
	}

	public void returnVoid(){
		return;
	}
}