package br.com.xpg.narutosenjuu.sudokuandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
//import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class BoardActivity extends Activity {

	private int[][] board; // board of sudoku
	private ArrayList<PointOnBoard> sudokuPoints;
	public final static int BOARD_SIZE = 9;
	
	private Game game; //test if is end of game and display message

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.board);

		Intent intent = getIntent();

		// get the data on the received intent
		@SuppressWarnings("unchecked")
		HashMap<String, Integer> data = (HashMap<String, Integer>) intent
				.getSerializableExtra(LoadSudokuService.DATA_KEY);

		board = new int[BOARD_SIZE][BOARD_SIZE];

		sudokuPoints = new ArrayList<PointOnBoard>();
		
		//for debugging
		System.out.println("Receiving Data : \n");
		
		// get the data and set the ArrayList of points
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				
				board[row][col] = data.get("" + row + "" + col);
				
				//debug
				
				if(col != 8)
				{
					//Log.d("sending : " , "" + sudokuBoard[row][col]);
					System.err.print("" + board[row][col]);
				}
				else
				{
					System.err.print("" + board[row][col] + "\n");
				}
				//end debug
				
				//get the random display method (a random number 1 ... 3 )
				int display = getDisplayMethod();
				
				if(display == PointOnBoard.SHOW)
				{
					display = PointOnBoard.HIDDEN;
					sudokuPoints.add(new PointOnBoard(row, col, display , 0));
				}
				else if(display == PointOnBoard.HIDDEN)
					sudokuPoints.add(new PointOnBoard(row, col, display , 0));
				else
					sudokuPoints.add(new PointOnBoard(row, col, display , board[row][col]));
			}
		} // end of loop for

		final BoardView boardView = (BoardView) findViewById(R.id.board);

		boardView.setBoard(board);

		onTouchScreen(boardView); //set the listeners

		// set the array list on BoardView for display the numbers on board
		boardView.setPoints(sudokuPoints);
		
		game = new Game(this); //for test the game

	} // end onCreate
	
	public int getDisplayMethod()
	{
		return new Random().nextInt(3);
	}
	
	//set the listener for touch on screen
	public void onTouchScreen(final BoardView boardView)
	{
		boardView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				Dialog dialog = new Dialog(BoardActivity.this);
				dialog.setTitle("Choice one option : ");
				dialog.setContentView(R.layout.game_menu);
				
				int x = (int)event.getX();
				int y = (int)event.getY();

				if (isModifiableRectangle(sudokuPoints, x, y , boardView)) {
					dialog.show();

					setListener(dialog , sudokuPoints , x , y , boardView);
				}

				return false;
			}

		});
	}
	
	// return true if the rectangle is modifiable
	public boolean isModifiableRectangle(ArrayList<PointOnBoard> list, int x,
			int y, BoardView view) {
		for (PointOnBoard point : list) {
			// get the rect on array list
			Rect rect = view.getRect(point.getCol(), point.getRow());

			// if it is on click area and are modifiable
			if (containsInRect(rect, x, y)
					&& point.getDisplay() != PointOnBoard.BUSY)

				return true;
		}

		return false;
	}

	// return true if rect contains x and y
	public boolean containsInRect(Rect rect, int x, int y) {
		if (rect.contains(x, y))
			return true;
		else
			return false;
	}
	
	public PointOnBoard getPoint(ArrayList<PointOnBoard> list ,int x , int y , BoardView view)
	{
		for(PointOnBoard point : list)
		{
			Rect rect = view.getRect(point.getCol(), point.getRow());
			
			if(containsInRect(rect , x , y))
				return point;
		}
		
		return null;
	}

	public void setListener(Dialog dialog ,ArrayList<PointOnBoard> list ,int x , int y , BoardView view) 
	{
		//listener for buttons
		ButtonListener listener = new ButtonListener(dialog , list , x , y , view);

		Button one = (Button) dialog.findViewById(R.id.button_one);
		Button two = (Button) dialog.findViewById(R.id.button_two);
		Button three = (Button) dialog.findViewById(R.id.button_three);
		Button four = (Button) dialog.findViewById(R.id.button_four);
		Button five = (Button) dialog.findViewById(R.id.button_five);
		Button six = (Button) dialog.findViewById(R.id.button_six);
		Button seven = (Button) dialog.findViewById(R.id.button_seven);
		Button eight = (Button) dialog.findViewById(R.id.button_eight);
		Button nine = (Button) dialog.findViewById(R.id.button_nine);
		Button cancel = (Button) dialog.findViewById(R.id.button_cancel);
		Button reset = (Button) dialog.findViewById(R.id.button_reset);

		one.setOnClickListener(listener);
		two.setOnClickListener(listener);
		three.setOnClickListener(listener);
		four.setOnClickListener(listener);
		five.setOnClickListener(listener);
		six.setOnClickListener(listener);
		seven.setOnClickListener(listener);
		eight.setOnClickListener(listener);
		nine.setOnClickListener(listener);
		cancel.setOnClickListener(listener);
		reset.setOnClickListener(listener);
	}
	
	public void onClickMenu(View v)
	{
		switch(v.getId())
		{
			case R.id.button_one:
				break;
			case R.id.button_two:
				break;
			case R.id.button_three:
				break;
			case R.id.button_four:
				break;
			case R.id.button_five:
				break;
			case R.id.button_six:
				break;
			case R.id.button_seven:
				break;
			case R.id.button_eight:
				break;
			case R.id.button_nine:
				break;
			case R.id.button_cancel:
				break;
			case R.id.button_reset:
				break;
		}
	}
	
	

	/**
	 * Listener for Buttons on Dialog
	 * get the action on buttons pressed
	 * example : cancel action and set the
	 * sudoku with the numbers
	 * @author paulo
	 *
	 */
	private class ButtonListener implements View.OnClickListener {
		
		private Dialog dialog;
		private ArrayList<PointOnBoard> list;
		private int x;
		private int y;
		private BoardView view;

		public ButtonListener(Dialog dialog ,ArrayList<PointOnBoard> list ,int x , int y , BoardView view) {
			this.dialog = dialog;
			this.list = list;
			this.x = x;
			this.y = y;
			this.view = view;
		}
		
		

		@Override
		public void onClick(View v) {

			String cancel = getResources().getString(R.string.button_cancel);
			String reset = getResources().getString(R.string.reset_button);

			Button button = (Button) v;

			String text = button.getText().toString();

			if (text.equals(cancel)) {
				dialog.dismiss();
			}
			else if (text.equals(reset)) {
				
				PointOnBoard point = getPoint(list , x , y , view);
				
				//redraw the Display
				if(point.getDisplay() != PointOnBoard.BUSY)
				{
					point.setDisplay(PointOnBoard.HIDDEN);
					view.setPoints(sudokuPoints);
					view.invalidate();
					dialog.dismiss();
				}
			}
			else
			{
				PointOnBoard point = getPoint(list , x , y , view);
				
			    
				if(point.getDisplay() != PointOnBoard.BUSY)
				{
					String textNumber = button.getText().toString();
					int number = Integer.parseInt(textNumber);
					point.setDisplay(PointOnBoard.SHOW);
					point.setNumber(number);
					view.setPoints(sudokuPoints);
					view.invalidate();
					dialog.dismiss();
					
					//if game is ended
					if(game.isEndGame(sudokuPoints, board))
					{
						game.showDialog();
					}
				}
			}
		}//end of method onClick
	} //end of class ButtonListener

}
