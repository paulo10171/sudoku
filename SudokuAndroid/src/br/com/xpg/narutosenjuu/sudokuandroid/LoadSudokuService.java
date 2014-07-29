package br.com.xpg.narutosenjuu.sudokuandroid;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import sudokiller.*;
import java.util.HashMap;

public class LoadSudokuService extends Service {
	

	public final static int OK = 1;
	
	private int[][] sudokuBoard;
	public final static String DATA_KEY = "br.com.xpg.narutosenjuu";
	private HashMap<String , Integer> data; //number on board of sudoku
	private final static int SUDOKU_LENGTH = 9;
	
	public final static String MESSENGER = "MESSENGER";
	public final static String DIFFICULTY = "difficulty";
	public final static String PROGRESS_UPDATE = "PROGRESS_UPDATE";
	
	public final static int UPDATE = 1;
	public final static int SEND_DATA = 2;
	
	private Messenger messenger;
	
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		
		sudokuBoard = new int[SUDOKU_LENGTH][SUDOKU_LENGTH];
		data = new HashMap<String , Integer>();
	}
		
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		messenger = (Messenger)intent.getParcelableExtra(MESSENGER);
		
		int difficulty = intent.getIntExtra(DIFFICULTY, -1);
		
		new LoadSudokuTask().execute(difficulty);
		
		return START_NOT_STICKY;
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/**
	 * make a intent for start this service
	 * 
	 * @param context the context
	 * @param service this service class
	 * @param difficulty level of difficulty of game
	 * @param messenger for send message for handler
	 * @return
	 */
	public static Intent makeIntent(Context context, Class <?> service , int difficulty , Messenger messenger)
	{
		Intent intent = new Intent(context , service);
		intent.putExtra(LoadSudokuService.MESSENGER,messenger);
		intent.putExtra(LoadSudokuService.DIFFICULTY, difficulty);
		return intent;
	}
	
	/**
	 * 
	 * @author paulo
	 * 
	 * Load the Sudoku in background thread
	 *
	 */
	private class LoadSudokuTask extends AsyncTask<Integer, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Integer... params) {
			
			sudokuBoard = SudokuMaker.makeSudoku(sudokuBoard);
			
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
			
			sendMessage(values[0], false);
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			
			setData();
		
			sendMessage(data, true);
		}
		
		private void setData()
		{
			//data for sending
			
			System.out.println("Sendind Data : \n");
			
			for(int row = 0; row < 9 ; row++)
			{
				for(int col = 0; col < 9 ; col++)
				{
					data.put("" + row + "" + col , sudokuBoard[row][col]);
					
					//debug
					
					if(col != 8)
					{
						//Log.d("sending : " , "" + sudokuBoard[row][col]);
						System.err.print("" + sudokuBoard[row][col]);
					}
					else
					{
						System.err.print("" + sudokuBoard[row][col] + "\n");
					}
					//end debug
				}
			}
		}
		
		/**
		 * Send message for handler on LoadSudokuActivity Class
		 * 
		 * @param data the data for send
		 * @param isData if is SEND_DATA or UPDATE ProgressDialog on LoadSudokuActivity
		 */
		@SuppressWarnings("unchecked")
		private void sendMessage(Object data , boolean isData)
		{
			Bundle bundle = new Bundle();
			Message msg = Message.obtain();
			
			if(isData)
			{
				bundle.putSerializable(DATA_KEY, (HashMap<String , Integer>)data);
				msg.arg1 = SEND_DATA;
			}
			else
			{
				bundle.putInt(PROGRESS_UPDATE, (Integer)data);
				msg.arg1 = UPDATE;
			}
			
			
			msg.setData(bundle);
			
			try {
				messenger.send(msg);
			} catch (RemoteException e) {
				
				e.printStackTrace();
			}
		}
		
	}//end private class LoadSudokuTask


}
