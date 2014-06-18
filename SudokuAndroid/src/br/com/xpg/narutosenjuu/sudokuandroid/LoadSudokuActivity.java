package br.com.xpg.narutosenjuu.sudokuandroid;


import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.widget.ProgressBar;

public class LoadSudokuActivity extends Activity {
	
	private ProgressBar progressBar;
	private Messenger messenger;
	private Intent service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.load_sudoku_screen);
		
		//get references to objects on layout
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		messenger = new Messenger(handler);
		
		//start service for background computation and create new sudoku
		service = LoadSudokuService.makeIntent(this, LoadSudokuService.class, 1, messenger);
		startService(service);
		
	}
	
	@Override
	protected void onDestroy() {
		
		//stop the service when start new activity
		stopService(service);
		
		super.onDestroy();
	}
	
	//handler for get messages from service LoadSudokuService
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			
			
			switch(msg.arg1)
			{
			    case LoadSudokuService.UPDATE :
			    	int value = bundle.getInt(LoadSudokuService.PROGRESS_UPDATE);
			    	progressBar.setProgress(value);
			    	break;
			    	
			    case LoadSudokuService.SEND_DATA:
			    	Intent intent = new Intent(LoadSudokuActivity.this,BoardActivity.class);
			    	
			    	@SuppressWarnings("unchecked")
					HashMap<String , Integer> data = (HashMap<String , Integer>)
			    			bundle.getSerializable(LoadSudokuService.DATA_KEY);
			    	intent.putExtra(LoadSudokuService.DATA_KEY, data);
			    	startActivity(intent);
			    	break;
			}
		}
	};

}	