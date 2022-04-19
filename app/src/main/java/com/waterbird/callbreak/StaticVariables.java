package com.waterbird.callbreak;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

import com.waterbird.callbreak.R;

public class StaticVariables {

	public static final int START=0;
	
	public static final int ROLL_DICE_PLAYER = 1;
	
	public static final int MOVE_TOKEN_PLAYER = 2;
	
	public static final int MOVE_IF_SNAKE_LADDER_PLAYER = 3;
	
	public static final int START_COMP = 4;
	
	public static final int ROLL_DICE_COMP = 5;
	
	public static final int MOVE_TOKEN_COMP = 6;
	
	public static final int MOVE_IF_SNAKE_LADDER_COMP = 7;

	public static final String TICK = "TICK";

	public static final String SUCCESS = "SUCCESS";

	public static final String BTN_CLICK = "BTN_CLICK";

	public static final String BTN_CLICK2 = "BTN_CLICK2";

	public static SoundUtil soundUtil;
	
	public static boolean loaded;

	public static Boolean auto = false;

	public static void initSoundPool(Context context) {
		
		SoundPool mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
		HashMap<String, Integer> mSoundMap = new HashMap<String, Integer>();
		AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

		mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				loaded = true;
			}
		});


		if(mSoundPool != null){
			mSoundMap.put(TICK, mSoundPool.load(context, R.raw.tick, 1));
			mSoundMap.put(SUCCESS, mSoundPool.load(context, R.raw.success_1, 1));
			mSoundMap.put(BTN_CLICK, mSoundPool.load(context, R.raw.btn_click, 1));
            mSoundMap.put(BTN_CLICK2, mSoundPool.load(context, R.raw.btn_clk2, 1));
		}

		StaticVariables.soundUtil = new SoundUtil(mSoundPool, mSoundMap, mgr);
	}

	
	
}
