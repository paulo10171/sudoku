package br.com.xpg.narutosenjuu.sudokuandroid;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import sudokiller.*;

public class LoadSudoku extends AsyncTask<Integer, Integer, Integer> {
	
	public final static int OK = 1;
	private ProgressBar dialog;
	private int[][] sudokuBoard;
	private final static String DATA_KEY = "br.com.xpg.narutosenjuu";
	private Context context;
	
	public LoadSudoku(Context context , ProgressBar bar)
	{
		dialog = bar;
		dialog.setIndeterminate(false);
		dialog.setMax(100);
		sudokuBoard = new int[9][9];
		this.context = context;
	}

	@Override
	protected Integer doInBackground(Integer... params) {
		
		SudokuMaker.makeSudoku(sudokuBoard);
		
		for(int i = 0 ; i < 10 ; i++)
		{
			try{
				Thread.sleep(200);
				publishProgress( ( i + 1 ) * 10 );
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return OK;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		
		dialog.setProgress(values[0]);
	}
	
	@Override
	protected void onPreExecute() {	
		//dialog.show();	
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		
		ArrayList<String> data = new ArrayList<String>();
		
		for(int row = 0; row < 9 ; row++)
			for(int col = 0; col < 9 ; col++)
				data.add("" + sudokuBoard[row][col]);
		
		Intent intent = new Intent(context , BoardView.class);
		intent.putStringArrayListExtra(DATA_KEY,data);
		context.startActivity(intent);
	}

}
