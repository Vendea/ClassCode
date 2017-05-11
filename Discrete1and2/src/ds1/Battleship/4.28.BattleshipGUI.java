package ds1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BattleshipGUI extends TwoPlayer implements ActionListener {
	JFrame MyBoardGUI;
	JButton[][] shipButton;
	JLabel playerLabel;
	JLabel statusLabel;
	JLabel winLabel;
	Semaphore holdForHuman;
	JLabel[] rowLabel;
	JLabel[] columnLabel;

	JFrame CPUBoardGUI;
	JButton[][] CPUshipButton;
	JLabel CPULabel;
	JLabel CPUstatusLabel;
	JLabel CPUwinLabel;
	JLabel[] CPUrowLabel;
	JLabel[] CPUcolumnLabel;

	boolean placingShips;    //the ships, used for placing ships
	int twoShip;
	int threeShipA;
	int threeShipB;
	int fourShip;
	int fiveShip;
	int[][] shipArray;

	int prevRow;
	int prevCol;
	int twoPastRow;
	int twoPastCol;
	int row;
	int column;
	boolean cont;

	int CPUShipArray[][];    // hard coded for now to get human shooting functionality.
	int LearningArray[][];
	int turn;

	public BattleshipGUI() {

		/////////////////////////////////   manually set cpu array to setup human functionality
		CPUShipArray = new int[10][10];
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				CPUShipArray[i][j]=1;
			}
		}

		CPUShipArray[0][0]=2;
		CPUShipArray[0][1]=2;

		CPUShipArray[2][2]=2;
		CPUShipArray[3][2]=2;
		CPUShipArray[4][2]=2;

		CPUShipArray[2][3]=2;
		CPUShipArray[2][4]=2;
		CPUShipArray[2][5]=2;

		CPUShipArray[5][5]=2;
		CPUShipArray[5][6]=2;
		CPUShipArray[5][7]=2;
		CPUShipArray[5][8]=2;

		CPUShipArray[6][8]=2;
		CPUShipArray[6][7]=2;
		CPUShipArray[6][6]=2;
		CPUShipArray[6][5]=2;
		CPUShipArray[6][4]=2;

		//////////////////////////////////





		shipArray = new int[10][10]; // 1 = empty, 2 = ship, 3 = hit, 4 = miss
		row=-1;
		column=-1;
		prevRow=-1;
		prevCol=-1;

		JLabel topLeftBlank = new JLabel("");
		columnLabel = new JLabel[10];
		rowLabel = new JLabel[10];
		shipButton = new JButton[10][10];    // ship buttons are on my board, I only need actionlisteners to place the ships then disable them.
		JPanel centerPanel = new JPanel();
		JPanel northPanel = new JPanel();

		JLabel CPUtopLeftBlank = new JLabel("");
		CPUcolumnLabel = new JLabel[10];
		CPUrowLabel = new JLabel[10];
		CPUshipButton = new JButton[10][10];
		JPanel CPUcenterPanel = new JPanel();
		JPanel CPUnorthPanel = new JPanel();


		MyBoardGUI = new JFrame("Human Player Board - My Ships");
		MyBoardGUI.setLayout(new BorderLayout());
		MyBoardGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyBoardGUI.setSize(new Dimension(600,600));
		MyBoardGUI.setLocation(0, 0);

		CPUBoardGUI = new JFrame("Enemy Board - Attack Here!");
		CPUBoardGUI.setLayout(new BorderLayout());
		CPUBoardGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CPUBoardGUI.setSize(new Dimension(600,600));
		CPUBoardGUI.setLocation(0, 0);


		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				shipArray[i][j]=1;
				shipButton[i][j] = new JButton(i+""+j);
				shipButton[i][j].addActionListener(this);     
				rowLabel[i] = new JLabel("A", JLabel.CENTER);
				columnLabel[i] = new JLabel("", JLabel.CENTER);

				CPUshipButton[i][j] = new JButton(i+""+j);
				CPUshipButton[i][j].addActionListener(this);
				CPUrowLabel[i] = new JLabel("A", JLabel.CENTER);
				CPUcolumnLabel[i] = new JLabel("", JLabel.CENTER);

			}

		}

		columnLabel[0].setText("A");
		columnLabel[1].setText("B");
		columnLabel[2].setText("C");
		columnLabel[3].setText("D");
		columnLabel[4].setText("E");
		columnLabel[5].setText("F");
		columnLabel[6].setText("G");
		columnLabel[7].setText("H");
		columnLabel[8].setText("I");
		columnLabel[9].setText("J");

		CPUcolumnLabel[0].setText("A");
		CPUcolumnLabel[1].setText("B");
		CPUcolumnLabel[2].setText("C");
		CPUcolumnLabel[3].setText("D");
		CPUcolumnLabel[4].setText("E");
		CPUcolumnLabel[5].setText("F");
		CPUcolumnLabel[6].setText("G");
		CPUcolumnLabel[7].setText("H");
		CPUcolumnLabel[8].setText("I");
		CPUcolumnLabel[9].setText("J");

		centerPanel.setLayout(new GridLayout(11, 11));
		centerPanel.add(topLeftBlank);     //add top left square for alignment
		//adds columnLabels to the first line of the center panel

		CPUcenterPanel.setLayout(new GridLayout(11, 11));
		CPUcenterPanel.add(CPUtopLeftBlank);   

		for(int i = 0; i< 10; i++){
			centerPanel.add(columnLabel[i]);
			CPUcenterPanel.add(CPUcolumnLabel[i]);
		}

		//add shipButtons to center panel along with the row buttons
		for(int i = 1; i < 11; i++){
			for(int j = 1; j < 11; j++){
				if(j==1){
					centerPanel.add(rowLabel[i-1]);
					CPUcenterPanel.add(CPUrowLabel[i-1]);
				}
				centerPanel.add(shipButton[i-1][j-1]);
				CPUcenterPanel.add(CPUshipButton[i-1][j-1]);
			}
		}

		JButton newGame = new JButton("New Game");
		northPanel.add(newGame);





		//holdForHuman = new Semaphore(0);

		MyBoardGUI.add(centerPanel, BorderLayout.CENTER);
		MyBoardGUI.add(northPanel, BorderLayout.NORTH);
		MyBoardGUI.setVisible(true);

		CPUBoardGUI.add(CPUcenterPanel, BorderLayout.CENTER);
		CPUBoardGUI.add(CPUnorthPanel, BorderLayout.NORTH);
		CPUBoardGUI.setVisible(true);
		//////above is for setting up the boards//////
		///////////////////////////////////////////////////////////////////////////////////////////////
		placingShips = true; 
		twoShip = 2;
		threeShipA = 3;
		threeShipB = 3;
		fourShip = 4;
		fiveShip = 5;

		twoPastCol = -1;
		twoPastRow = -1;


		newGame();
	}

	public void placeShips(){
		if(placingShips = true){



		}
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				shipButton[i][j].removeActionListener(this);
			}
		}
	}

	public void newGame(){
		if(placingShips == true)
			JOptionPane.showMessageDialog(null,"Click a square to place your ships starting "
					+ "with the smallest ship","Patrol Ship", JOptionPane.INFORMATION_MESSAGE);

		
	}
	/****
	 * 
	 * @param prevRow
	 * @param prevCol
	 * @param row
	 * @param col
	 * @param twoPastrow
	 * @param twoPastCol
	 * checks to make sure ship placement is valid
	 * @return
	 */
	public Boolean validPlace(int row, int col){


		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				shipButton[i][j].setEnabled(false);
			}
		}
		System.out.println(row+" " +col);
		if(row<9 && row>0 && col<9 && col>0){
			System.out.println(shipArray[row][col]+" "+shipArray[row+1][col]+shipArray[row-1][col]+shipArray[row][col+1]+shipArray[row][col-1]);

			if(shipArray[row+1][col]==1)
				shipButton[row+1][col].setEnabled(true);
			if(shipArray[row-1][col]==1)
				shipButton[row-1][col].setEnabled(true);
			if(shipArray[row][col+1]==1)
				shipButton[row][col+1].setEnabled(true);
			if(shipArray[row][col-1]==1)
				shipButton[row][col-1].setEnabled(true);
		}
		if(row==0&& col!=0 && col!=9){
			if(shipArray[row+1][col]==1)
				shipButton[row+1][col].setEnabled(true);
			if(shipArray[row][col+1]==1)
				shipButton[row][col+1].setEnabled(true);
			if(shipArray[row][col-1]==1)
				shipButton[row][col-1].setEnabled(true);
		}
		if(row==9&& col!=0 && col!=9){
			if(shipArray[row][col+1]==1)
				shipButton[row][col+1].setEnabled(true);
			if(shipArray[row][col-1]==1)
				shipButton[row][col-1].setEnabled(true);
			if(shipArray[row-1][col]==1)
				shipButton[row-1][col].setEnabled(true);
		}
		if(col==9&& row!=0 && row!=9){
			if(shipArray[row+1][col]==1)
				shipButton[row+1][col].setEnabled(true);
			if(shipArray[row-1][col]==1)
				shipButton[row-1][col].setEnabled(true);
			if(shipArray[row][col-1]==1)
				shipButton[row][col-1].setEnabled(true);
		}
		if(col==0 && row!=0 && row!=9){
			if(shipArray[row+1][col]==1)
				shipButton[row+1][col].setEnabled(true);
			if(shipArray[row-1][col]==1)				//top
				shipButton[row-1][col].setEnabled(true);
			if(shipArray[row][col+1]==1)				//right?
				shipButton[row][col+1].setEnabled(true);
		}
		if(row==0 && col==0){
			if(shipArray[row+1][col]==1)
				shipButton[row+1][col].setEnabled(true);
			if(shipArray[row][col+1]==1)
				shipButton[row][col+1].setEnabled(true);
		}
		if(row==9 && col==9){
			if(shipArray[row-1][col]==1)
				shipButton[row-1][col].setEnabled(true);
			if(shipArray[row][col-1]==1)
				shipButton[row][col-1].setEnabled(true);
		}
		if(row==0 && col==9){
			if(shipArray[row+1][col]==1)
				shipButton[row+1][col].setEnabled(true);
			if(shipArray[row][col-1]==1)
				shipButton[row][col-1].setEnabled(true);
		}
		if(row==9 && col==0){
			if(shipArray[row-1][col]==1)
				shipButton[row-1][col].setEnabled(true);
			if(shipArray[row][col+1]==1)
				shipButton[row][col+1].setEnabled(true);
		}


		if(prevRow==row && prevRow!=-1 && row>1 && row<9){
			shipButton[row-1][col].setEnabled(false);
			shipButton[row+1][col].setEnabled(false);
		}
		if(prevCol==col && prevCol!=-1 && col>1 && col<9){
			shipButton[row][col+1].setEnabled(false);
			shipButton[row][col-1].setEnabled(false);
		}
		if(prevCol==col && prevCol!=-1 && col==0)
			shipButton[row][col+1].setEnabled(false);
		if(prevCol==col && prevCol!=-1 && col==9)
			shipButton[row][col-1].setEnabled(false);
		if(prevRow==row && prevRow!=-1 && row==0)
			shipButton[row+1][col].setEnabled(false);
		if(prevRow==row && prevRow!=-1 && row==9)
			shipButton[row-1][col].setEnabled(false);


		System.out.println(""+prevRow+ " pC " + prevCol);
		if (prevRow>0 && prevCol>0)
			shipButton[prevRow][prevCol].setEnabled(false);

		shipArray[row][col]=2;
		prevRow=row;
		prevCol=col;
		twoPastRow=prevRow;
		twoPastCol=prevCol;



		return true;
	}
	public void place2(int row, int col){
		//System.out.println("Enter of twoship");
		cont = false;
		do{
			cont = validPlace(row,col);}
		while(cont=false);


		shipArray[row][col] = 2;
		shipButton[row][col].setBackground(Color.GRAY);
		shipButton[row][col].setEnabled(false);
		twoShip -= 1;
		System.out.println(twoShip+"  twoship val");
		if(twoShip ==0)
			System.out.println("return  out of twoship");
		return;

	}
	public void place3A(int row, int col){
		System.out.println("3shipA");
		cont = false;
		do{
			cont = validPlace(row,col);}
		while(cont=false);


		shipArray[row][col] = 2;
		shipButton[row][col].setBackground(Color.GRAY);
		shipButton[row][col].setEnabled(false);
		threeShipA -= 1;
		System.out.println(threeShipA+"  threeship val");
		if(threeShipA ==0)
			System.out.println("return  out of threeshipA");
		return;
	}
	public void place3B(int row, int col){
		System.out.println("3shipB");
		cont = false;
		do{
			cont = validPlace(row,col);}
		while(cont=false);


		shipArray[row][col] = 2;
		shipButton[row][col].setBackground(Color.GRAY);
		shipButton[row][col].setEnabled(false);
		threeShipB -= 1;
		System.out.println(threeShipB+"  threeship val");
		if(threeShipB ==0)
			System.out.println("return  out of threeshipB");
		return;
	}
	public void place4(int row, int col){
		System.out.println("4ship");
		cont = false;
		do{
			cont = validPlace(row,col);}
		while(cont=false);


		shipArray[row][col] = 2;
		shipButton[row][col].setBackground(Color.GRAY);
		shipButton[row][col].setEnabled(false);
		fourShip -= 1;
		System.out.println(fourShip+"  4ship val");
		if(fourShip ==0)
			System.out.println("return  out of 4ship");
	}
	public void place5(int row, int col){
		System.out.println("5ship");
		cont = false;
		do{
			cont = validPlace(row,col);}
		while(cont=false);


		shipArray[row][col] = 2;
		shipButton[row][col].setBackground(Color.GRAY);
		shipButton[row][col].setEnabled(false);
		fiveShip -= 1;
		System.out.println(fiveShip+"  5ship val");
		if(fiveShip ==0)
			System.out.println("return  out of 5ship");

	}
	public void resetBtns(){


		for(int k=0;k<10;k++){
			for(int l=0;l<10;l++){
				if(shipArray[k][l]==1){
					shipButton[k][l].setEnabled(true);
				}
			}
		}
		row=-1;
		column=-1;
		prevRow=-1;
		prevCol=-1;

	}
	/**
	 * sets all the ships on the board
	 * will terminate the ship setting process once all the ships have been set
	 * call methods @place2 @place3A @place3B @place4 @place5 to set each specific ship, and
	 * it resets the board buttons for placing the ships
	 * @
	 * @param i
	 * @param j
	 */
	public void shipPlacing(int i,int j){




		if (fiveShip!=0 && fourShip==0 && threeShipA==0 && threeShipB==0 && twoShip==0){
			place5(i,j);
			if(fiveShip==0)
				resetBtns();}
		if(fourShip!=0 && threeShipA==0 && threeShipB==0 && twoShip==0){
			place4(i,j);
			if(fourShip==0)
				resetBtns();}
		if(threeShipB!=0 && threeShipA==0 && twoShip==0){
			place3B(i,j);
			if(threeShipB==0)
				resetBtns();}
		if(threeShipA!=0 && twoShip==0){
			place3A(i,j);
			if(threeShipA==0)
				resetBtns();}
		if(twoShip!=0){
			place2(i,j);
			if(twoShip==0)
				resetBtns();
		}
		if(fiveShip==0 && fourShip==0 && threeShipA==0 && threeShipB==0 && twoShip==0){

			System.out.println("shipplacing is false now");
			placingShips=false;
			turn=1;
		}

	}
	public void checkMyShips(){
		String test = "";
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				test += ""+shipArray[i][j];
				if(j==9)
					test+= "\n";
			}
		}
		System.out.println(test);
	}

	public void humanMove(int turn,int row, int col){
		if(CPUShipArray[row][col]==2){
			CPUShipArray[row][col]=3;
			CPUshipButton[row][col].setBackground(Color.red);
			CPUshipButton[row][col].removeActionListener(this);

		}
		if(CPUShipArray[row][col]==1){
			CPUShipArray[row][col]=2;
			CPUshipButton[row][col].setBackground(Color.white);
			CPUshipButton[row][col].removeActionListener(this);
		}


		turn++;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(shipButton[i][j] == e.getSource()){
					if(placingShips == true)
						shipPlacing(i,j);
				}
			}
		}
		if(placingShips==false)
			checkMyShips();


		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){

				if(CPUshipButton[i][j] == e.getSource()){
					humanMove(turn, i, j);

				}

			}
		}


	}

	@Override
	void computerMove(int turn) {
		int rndRow = (int)(Math.random()*9); 
		int rndCol = (int)(Math.random()*9);
		System.out.println("row " + rndRow+ "  RndCol " + rndCol);
		if(shipArray[rndRow][rndCol]==2){
			shipButton[rndRow][rndCol].setBackground(Color.red);
		}
			if(shipArray[rndRow][rndCol]==1){
		shipButton[rndRow][rndCol].setBackground(Color.white);
			}
	}

	@Override
	void drawBoard() {
		// TODO Auto-generated method stub

	}

	@Override
	int endCheck() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	DSArrayList<Object> getChildren(Object b) { ////wont need because hits cannot be determined
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String boardHash(Object b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	int evaluateBoard(Object b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int whoseTurn(Object b) {
		turn= turn%2 +1;
		return 0;
	}

	@Override
	void humanMove(int turn) {
		// TODO Auto-generated method stub

	}

	@Override
	void computerRandomMove(int turn) {
		// TODO Auto-generated method stub
		
	}

}