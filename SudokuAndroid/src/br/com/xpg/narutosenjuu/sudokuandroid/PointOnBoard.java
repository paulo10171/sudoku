package br.com.xpg.narutosenjuu.sudokuandroid;

/**
 * Store the position on board
 * @author paulo
 *
 */

public class PointOnBoard {
	
	private int row ;
	private int col;
	private int display;
	private int number;
	
	//constant to access the data
	public final static int HIDDEN = 0;
	public final static int SHOW = 1;
	public final static int BUSY = 2;
	
	public PointOnBoard(int row , int col , int display , int number)
	{
		this.row = row;
		this.col = col;
		this.display = display; // the visibility access
		this.number = number;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public int getDisplay()
	{
		return display;
	}
	
	public void setDisplay(int display)
	{
		this.display = display;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}

}
