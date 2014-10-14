package ds1;

/**
 * creates a Ship object for use in Battleship
 * @author KatherineMJB
 *
 */
class Ship{
	private boolean isShipPlaced;
	private int length;
	private int hitPoints1;
	private int hitPoints2;
	private String name;
	private int identifier;
	private int[] rowLocation;
	private int[] colLocation;
	private int [] rowLocationTwo;
	private int [] colLocationTwo;

	/**
	 * instantiates ship object with a name, length, and integer identifier
	 * sets rowLocation and colLocation equal to the length of the ship
	 * @author KatherineMJB
	 * @param length int length of ship
	 * @param name String name of ship
	 * @param id number to identify ship
	 */
	public Ship(int l, String n, int id){
		isShipPlaced = false;
		length = l;
		hitPoints1 = l;
		hitPoints2 = l;
		name = n;
		identifier = id;
		rowLocation = new int[length];
		colLocation = new int[length];
		rowLocationTwo = new int[length];
		colLocationTwo = new int[length];
	}

	/**
	 * 
	 * @return int the length of the ship
	 */
	public int getLength(){
		return length;
	}

	/**
	 * 
	 * @return string the name of the ship
	 */
	public String getName(){
		return name;
	}

	/**
	 * 
	 * @return the identifier of the ship
	 */
	public int whatAmI(){
		return identifier;
	}

	/**
	 * concatenates a string with "row,col;" location of the ship
	 * @return the String representation of the ship's location
	 */
	public String whereAmI(){
		String rv = "";
		for(int i = 0; i < rowLocation.length; i ++){
			rv += rowLocation[i];
			rv += ",";
			rv += colLocation[i];
			rv+= ";";
		}
		return rv;
	}

	/**
	 * concatenates every other member of rowLocation
	 * with every other member of colLocation
	 * @param l method will make location array in l
	 * @return int array of locations: row,col 
	 */
	public int[] whereAmI(int[] l){
		//int[] rv = new int[rowLocation.length*2];
		for(int i = 0; i < rowLocation.length; i ++){
			l[2*i] = rowLocation[i];
			l[2*i+1] = colLocation[i];
		}
		return l;	
	}

	/**
	 * 
	 * @param l the location to be returned
	 * @return int the ship's row location
	 */
	public int returnRow(int t, int l){
		if(t==1)
			return rowLocationTwo[l];
		else
			return rowLocation[l];
	}

	/**
	 * 
	 * @param l the location to be returned
	 * @return int the ship's column location
	 */
	public int returnCol(int t, int l){
		if(t == 1)
			return colLocationTwo[l];
		else
			return colLocation[l];
	}

	/**
	 * Given an id number, find the ship that the id belongs to
	 * @param ident the id number of the ship you want
	 * @return the ship corresponding to the id number
	 */
	public Ship getShip(int ident){
		if(this.whatAmI() == ident)
			return this;
		return null;
	}

	/**
	 * given startRow, startCol and endRow, endCol
	 * places starting and ending points as well as 
	 * locations inbetween into rowLocation and colLocation arrays
	 * if the ship is horizontal, makes all rowLocation equal
	 * to startRow and puts correct column into colLocation
	 * vice versa if ship is vertical, makes all colLocation same, etc.
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 */
	public void setLocation(int t, int startRow, int startCol, int endRow, int endCol){
		int[] columns;
		int [] rows;
		if(t == 1){
			columns = colLocation;
			rows = rowLocation;
		}
		else{
			columns = colLocationTwo;
			rows = rowLocationTwo;
		}
		for(int i = 0; i < length; i++){
			if(startRow == endRow){
				if(startCol<endCol){
					columns[i] = startCol + i;
					rows[i] = startRow;
				}
				else{
					columns[i] = startCol-i;
					rows[i]= startRow;
				}
			}
			if(startCol == endCol){
				if(startRow<endRow){
					rows[i] = startRow + i;
					columns[i] = startCol;
				}
				else{
					rows[i] = startRow-i;
					columns[i] = startCol;
				}
			}
		}
		isShipPlaced = true;
	}

	public void iAmHit(int t){
		if(t == 1)
			hitPoints1--;
		else
			hitPoints2--;
	}

	public int amIHit(int t){
		if(t == 1)
			return hitPoints1;
		else
			return hitPoints2;
	}

	public boolean isShipPlaced(){
		return isShipPlaced;
	}
}