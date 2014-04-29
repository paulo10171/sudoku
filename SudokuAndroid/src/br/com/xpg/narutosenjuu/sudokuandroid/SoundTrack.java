package br.com.xpg.narutosenjuu.sudokuandroid;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundTrack {
	
	private SoundPool sound;
	
	//sounds id's
	public int idBubbleSound; //bubble sound
	
	public SoundTrack(Context context)
	{
		sound = new SoundPool(5,AudioManager.STREAM_MUSIC ,0);
		
		idBubbleSound = sound.load(context, R.raw.bubble, 0);
		
	}
	
	public void playSound(int id , int loop)
	{
		sound.play(id, 1, 1, 0, loop, 1);
	}
	
	public void stopSound(int id)
	{
		sound.stop(id);
	}
	

}
