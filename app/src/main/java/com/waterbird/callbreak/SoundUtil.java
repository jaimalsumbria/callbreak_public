package com.waterbird.callbreak;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundUtil {
	
	private SoundPool mSoundPool;
	
	private HashMap<String, Integer> mSoundMap;
	
	AudioManager mgr;


	public SoundUtil(SoundPool mSoundPool,
			HashMap<String, Integer> mSoundMap, AudioManager mgr) {
		super();
		this.mSoundPool = mSoundPool;
		this.mSoundMap = mSoundMap;
		this.mgr = mgr;
	}
	
	public void playSound(String sound, int loop, Boolean mute) {
		
		if (mute == true) return;
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    float volume = streamVolumeCurrent / streamVolumeMax;  

	    if(mSoundPool != null){
	        mSoundPool.play(mSoundMap.get(sound), volume, volume, 1, 0, 1.0f);
	    }
	}

}
