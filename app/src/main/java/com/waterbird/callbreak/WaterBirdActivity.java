package com.waterbird.callbreak;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.waterbird.callbreak.R;

public class WaterBirdActivity extends Activity {

	Button btnMultiplayer;
	Button btnTwoplayer;
	Button btnThreeplayer;
	Button btnVsCompTwoplayer;
	Button btnVsComputer;
	ImageButton btnSnkLad;
	ProgressBar progressBar;
	private int progressStatus = 0;
    private Handler handler = new Handler();
	private static int TIME_OUT = 2000; //Time to launch the another activity
	public static Context applicationContext;
	public static Integer APP_THEME = 0;
	private SharedPreferences sp;
	private SharedPreferences.Editor spEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.water_bird_activity);
		applicationContext = getApplicationContext();

		RelativeLayout linearLayout = findViewById(R.id.waterBirdLayout);
		// Drawable bgd = linearLayout.getBackground();
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		spEditor = sp.edit();

		if (!sp.contains("APP_THEME")) {
			spEditor.putInt("APP_THEME", WaterBirdActivity.APP_THEME);
			spEditor.apply();
		} else {
			WaterBirdActivity.APP_THEME = sp.getInt("APP_THEME", 0);
		}

		try {
			ThemeFactory.getThemeFactory().themes.get(WaterBirdActivity.APP_THEME).setTheme(linearLayout);
		} catch (Exception e){

		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(WaterBirdActivity.this, LudoMainActivity.class);
				startActivity(i);
				finish();
			}
		}, TIME_OUT);
		
		progressBar = findViewById(R.id.progressBar);
		new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 9;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
//                            textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
