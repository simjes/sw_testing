import java.util.Random;

class MineField{

	private boolean[][] mines,visible;
	private boolean boom;
	private final int rowMax = 5;
	private final int colMax = 10;
	
	// constructor. Generates a minefield with random values
	MineField(){
		
		mines=new boolean[rowMax][colMax];
		visible=new boolean[rowMax][colMax];
		boom=false;
		
		initMap();		

		int counter2=15;
		int randomRow,randomCol;
		Random RGenerator=new Random();
		
		while(counter2>0){
			
			randomRow=Math.abs(RGenerator.nextInt()%rowMax);
			randomCol=Math.abs(RGenerator.nextInt()%colMax);
			
			if(trymove(randomRow,randomCol)){
				counter2--;
			}
		}
	}

	// Initialize the map for new game
	private void initMap(){
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				mines[row][col]=false;
				visible[row][col]=false;
			}
		}
		
	}

	//check if it the move is valid
	// @param randomRow random row
	//@param randomCol random col
	// @return returns false if it is. Sets the move too true and returns true if it not taken
	private boolean trymove(int randomRow, int randomCol) {
		if(mines[randomRow][randomCol]){
			return false;
		}
		else{
			mines[randomRow][randomCol]=true;
			return true;
		}
	}
	
	// iterates the field and sets visible 2d array to true if there is a mine on current position. sets boom to true when done and calls show()
	private void boom() {
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				if(mines[row][col]){
					visible[row][col]=true;
				}
			}
		}
		boom=true;
		show();
		
		
	}

	//show the current content of position given by row col
	// @param row
	//@param col
	//@return returns what should be located under the postion in form of a char
	private char drawChar(int row, int col) {
		int count=0;
		if(visible[row][col]){
			if(mines[row][col]) return '*';
			count = increaseCount(row, col);
		}else{
			if(boom){
				return '-';
			}
			return '?';
		}
		if(count < 9) return "" + count;
		
		return 'X';
	}

	/** 
	*Check number of mines that is surrounding the current cell 
	*@param row the row number
	*@param col the coloum number
	*@return int the number of bombs surrounding current cell
	*/
	private int increaseCount(int row, int col) {
		int count = 0;		
		for(int irow=row-1;irow<=row+1;irow++){
			for(int icol=col-1;icol<=col+1;icol++){
				if(icol>=0&&icol<colMax&&irow>=0&&irow<rowMax){
					if(mines[irow][icol]) count++;
				}
			}
		}
		return count;
	}

	// returns current boom status
	public boolean getBoom(){
		
		return boom;
	}

	// parses the string input and decide if it is a legal move. calles legalmoveValue if it is
	//@param input
	//@return true if it is a legal move. False else
	public boolean legalMoveString(String input) {
		String[] separated=input.split(" ");
		int row;
		int col;
		try{
			row=Integer.parseInt(separated[0]);
			col=Integer.parseInt(separated[1]);
			if(row<0||col<0||row>=rowMax||col>=colMax){
				throw new java.io.IOException();
			}
		}catch(Exception e){
			System.out.println("\nInvalid Input!");
			return false;
		}
		
		return legalMoveValue(row, col);
		
	}

	// handles the values given by the user. Sets the position to visible and checks for mines
	// @param row
	// @param col
	// @return returns true if it is legal move. False if it is not
	private boolean legalMoveValue(int row, int col) {
		
		if(visible[row][col]){
			System.out.println("You stepped in allready revealed area!");
			return false;
		}
		else{
			visible[row][col]=true;
		}
		
		if(mines[row][col]){
			boom();
			return false;
		}
		
		return true;
	}
	
	// prints the minefield
	public void show() {
		System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
		System.out.println("   ---------------------");
		for(int row=0;row<rowMax;row++){
			System.out.print(row+" |");
			for(int col=0;col<colMax;col++){
				System.out.print(" "+drawChar(row,col));
				
			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}
	
}
