package br.com.xpg.narutosenjuu.sudokuandroid;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class LoadSudoku extends AsyncTask<Integer, Integer, Integer> {
	
	public final static int OK = 1;
	private ProgressBar dialog;
	
	public LoadSudoku(ProgressBar bar)
	{
		dialog = bar;
		dialog.setIndeterminate(false);
		dialog.setMax(100);
	}

	@Override
	protected Integer doInBackground(Integer... params) {
		
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
		
		//dialog.dismiss(); //close dialog
	}

}
