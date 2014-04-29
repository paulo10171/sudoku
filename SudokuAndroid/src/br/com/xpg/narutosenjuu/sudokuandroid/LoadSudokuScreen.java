package br.com.xpg.narutosenjuu.sudokuandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class LoadSudokuScreen extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.load_sudoku_screen);
		
		ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
		
		new LoadSudoku(progressBar).execute(1);
	}

}
