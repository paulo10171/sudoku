package br.com.xpg.narutosenjuu.sudokuandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import br.com.xpg.narutosenjuu.sudokuandroid.R;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SudokuActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//get the intent send by BoardActivity
		Intent intent = getIntent();
		boolean endGame = intent.getBooleanExtra(Game.END_GAME, false);
		if(endGame)
		{
			finish(); //end the activity
		}

		setContentView(R.layout.activity_sudoku);

		Button newGame = (Button)findViewById(R.id.newGame);
		
		newGame.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				//load the sudoku
				//SoundTrack sound = new SoundTrack(SudokuActivity.this);
				//sound.playSound(sound.idBubbleSound, 1);
				Intent intent = new Intent(SudokuActivity.this,LoadSudokuActivity.class);
				startActivity(intent);
			}
		});
		
	}//end of onCreate

}
