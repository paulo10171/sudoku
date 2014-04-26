package jframethread;

import java.awt.Color;
import java.util.Random;

public class Points {
	
	public int x , y;
	public int accelerationX = new Random().nextInt(20);
	public int accelerationY = new Random().nextInt(20);
	public boolean accelerationXnegative = new Random().nextBoolean();
	public boolean accelerationYnegative = new Random().nextBoolean();
	private Color color;

	public Points(int x  , int y , Color c)
	{
		this.x = x;
		this.y = y;
		this.color = c;
		randomDirection();
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Color getColor()
	{
		return color;
	}

	private void randomDirection()
	{
		accelerationX = (accelerationXnegative) ? (makeRandomNumber()) 
				: (- makeRandomNumber()) ;
		
		
		accelerationY = (accelerationYnegative) ? (makeRandomNumber()) 
				: (- makeRandomNumber()) ;
		
	}
	
	public void changeDirection()
	{
		accelerationX = -accelerationX;
		accelerationY = -accelerationY;
	}
	
	
	public void changeAcceleration()
	{
		accelerationX = (accelerationX < 0) ? (- makeRandomNumber()) :
			makeRandomNumber();
		
		accelerationY = (accelerationY < 0) ? (- makeRandomNumber() ) :
			makeRandomNumber();
	}
	
	public int makeRandomNumber()
	{
		return new Random().nextInt(20) + 10;
	}
	
}
