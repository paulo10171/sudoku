package br.com.xpg.narutosenjuu.sudokuandroid;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Game {
	
	private Context context;
	private Dialog dialog;
	
	
	public final static String END_GAME = "ENDGAME";
	
	public Game(Context context)
	{
		this.context = context;
		dialog = new Dialog(context);
		
	}
	
	public void showDialog()
	{
		dialog.setTitle(R.string.choice_one_option);
		dialog.setContentView(R.layout.end_game_dialog);
		Button newGame = (Button)dialog.findViewById(R.id.button_new_game_end_dialog);
		Button endGame = (Button)dialog.findViewById(R.id.button_end_game_end_dialog);
		
		//set the listeners
		newGame.setOnClickListener(listener);
		endGame.setOnClickListener(listener);
		
		dialog.show();
	}
	
	public boolean isEndGame(ArrayList<PointOnBoard> points , int[][] board)
	{
		int length = BoardActivity.BOARD_SIZE;
		int start = 0;
		
		for(PointOnBoard point : points)
		{
			//for debugging
			
			System.err.print("point.getNumber : " + point.getNumber());
			System.err.print(" board[" + start%length + "][" + start/length + "] : "
					+ board [start%length] [start/length]);
			System.err.print("ArrayList Size : " + points.size() + "\n");
			//end debugging
			
			if(point.getNumber() != board [start%length] [start/length] 
					|| point.getNumber() == 0)
			{
				return false;
			}
			start++;
		}
		
		return true;
	}
	
	

	private View.OnClickListener listener = new View.OnClickListener() {
		
		public void onClick(View v) {
			
			Button button = (Button)v;
			
			String text = button.getText().toString();
			
			String endGame = context.getResources().getString(R.string.end_game);
			String newGame = context.getResources().getString(R.string.new_game);
			
			//end or continue the game
			Intent intent = new Intent();
			
			if(text.equals(endGame))//end the game
			{
				intent.setClass(context, SudokuActivity.class);
				intent.putExtra(END_GAME, true); //message for end game
				context.startActivity(intent);
			}
			else if(text.equals(newGame))//new Game
			{
				intent.setClass(context, SudokuActivity.class);
				context.startActivity(intent);
			}
			
		}
	};

}
