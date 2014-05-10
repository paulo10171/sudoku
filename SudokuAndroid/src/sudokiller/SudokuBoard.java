package sudokiller;

import java.util.Random;

public class SudokuBoard {
    final int EMPTY = 0;      // Empty cells marker
    final int size;           // Size of the board (number of rows and columns)
    final int box_size;       // Size of the inner boxes

    private int[][] board;    // 2D array representing the game board

    /**
     * Creates an empty board.
     * @param <code>size</code> Number of rows and columns of the board.
     */
    public SudokuBoard(int size) {
        board = new int[size][size];
        this.size = size;
        this.box_size = (int) Math.sqrt(size);
    }
    
    /**
     * Creates and initializes the board.
     * @param board Array to initialize the board contents.
     */
    public SudokuBoard(int[][] board) {
        this(board.length);
        this.board = board;
    }
    
    /**
     * Puts a number into a specific cell.
     * @param num Number to put into the board cell.
     * @param row Cell's row.
     * @param col Cell's column.
     */
    public void setCell(int num, int row, int col) {
        board[row][col] = num;
    }

    /**
     * Returns the number contained in a specific cell.
     * @param row Cell's row.
     * @param col Cell's column.
     * @return The number contained in the cell.
     */
    public int getCell(int row, int col) {
        return board[row][col];
    }
    
    /**
     * Returns the array board to string
     *
     */
    @Override
    public String toString()
    {
    	String b = "";
    	
    	for(int row = 0 ; row < size ; row++)
    	{
    		for(int col = 0 ; col < size ; col++)
    		{
    			b += board[row][col] + " ";
    		}
    		
    		b+= "\n";
    	}
    	
    	return b;
    }
    
    /**
     * Return the sector on sudoku board
     * @param <code>row</code> the row on board
     * @param <code>col</code> the column on board
     * @return the sector on sudoku board
     */
    private int sector(int row , int col)
    {
    	switch(row)
    	{
    		case 0:
    		case 1:	
    		case 2:
    			if(col < 3)
    				return 0;
    			if(col >= 3 && col < 6)
    				return 1;
    			if(col >=6 && col <= 8)
    				return 2;
    			break;
    			
    		case 3:
    		case 4:
    		case 5:
    			if(col < 3)
    				return 3;
    			if(col >= 3 && col < 6)
    				return 4;
    			if(col >=6 && col <= 8)
    				return 5;
    			break;
    			
    		case 6:
    		case 7:
    		case 8:
    			if(col < 3)
    				return 6;
    			if(col >= 3 && col < 6)
    				return 7;
    			if(col >=6 && col <= 8)
    				return 8;
    			break;
    			
    		default : 
    			return -1;
    	}
    	
    	return -1; //error code
    } //end of sector();
    
    /**
     * return true if the value is on the sector
     * @param <code>value</code> the value on board
     * @param <code>sector</code> the sector on board
     * @return <code>true</code> if value is on sector
     */
    private boolean isOnSector(int value , int sector)
    {	
    	for(int row = 0 ; row < size ; row++)
    	{
    		for(int col = 0 ; col < size ; col++)
    		{
    			if(sector == sector(row , col))
    			{
    				if(value == board[row][col])
    					return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Detect if value on the row
     * @param <code>value</code>
     * @param <code>row</code>
     * @return <code>true</code> if this row contains that value
     */
    private boolean isOnRow(int value , int row)
    {
    	for(int i = 0 ; i < size ; i++)
    	{
    		if(board[row][i] == value)
    			return true;
    	}
    	
    	return false;
    }
    
    /**
     * Detect if value on the column
     * @param value
     * @param col
     * @return true if this column contains that value
     */
    private boolean isOnColumn(int value , int col)
    {
    	for(int i = 0 ; i < size ; i++)
    	{
    		if(board[i][col] == value)
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * fill board with number
     * @param level of game
     */
    public void makeRandomBoard(int level)
    {
    	Random random = new Random();
    	
    	int row , col , value;
    	
    	
    	int thisLevel = level;
    	
    	long currentTime = System.currentTimeMillis();
    	
    	while(thisLevel > 0 )
    	{
    		row = random.nextInt(size);
        	col = random.nextInt(size);
        	value = random.nextInt(size) + 1;//value of 1 ... 9
        	
        	//exit method on error long time thread
        	if( (System.currentTimeMillis() - currentTime) > 500)
        	{
        		System.err.println("Long time await ...");
        		break;
        	}
        	
        	if(!isOnColumn(value , col) && !isOnRow(value, row)
        			&& !isOnSector(value, sector(row,col)))
        	{
        		board[row][col] = value;
        		thisLevel--;
        	}
    	}
    }
}

