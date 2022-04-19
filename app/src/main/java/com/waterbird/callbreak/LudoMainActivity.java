package com.waterbird.callbreak;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.waterbird.callbreak.R;

import java.util.ArrayList;

public class LudoMainActivity extends Activity {

    private Button btnMultiplayer;
    private Button  playCallbreak;
    private ImageButton btnVsComputer;
    private ImageButton rulesPopup;
    private ImageButton themeChange;
    private PopupWindow popupWindow;
    private RelativeLayout layoutLudoMain;
    private InterstitialAd mInterstitialAd;
    protected Activity activity;
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// init sound pool 
		StaticVariables.initSoundPool(getApplicationContext());
        activity = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ludo_activity_main);
        layoutLudoMain = findViewById(R.id.layoutLudoMain);

        RelativeLayout  linearLayout = findViewById(R.id.layoutLudoMain);
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

        Tools.init(this);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8796345110437201/6723586648");
        mInterstitialAd = Tools.decoratePf(this, mInterstitialAd);

        AdView adView = this.findViewById(R.id.adView);
        Tools.decorateRnb(this, adView);

        playCallbreak = findViewById(R.id.playCallbreak);
        playCallbreak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(LudoMainActivity.this, LudoVsCompActivity.class);
				StaticVariables.soundUtil.playSound(StaticVariables.BTN_CLICK, 1, false);
				startActivity(i);

			}
		});

        btnMultiplayer = findViewById(R.id.multiplayerBtn);
        btnMultiplayer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(LudoMainActivity.this, LudoVsCompActivity.class);
                StaticVariables.soundUtil.playSound(StaticVariables.BTN_CLICK, 1, false);
                startActivity(i);

            }
        });

       	btnVsComputer = findViewById(R.id.settingsMain);
		btnVsComputer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                StaticVariables.soundUtil.playSound(StaticVariables.BTN_CLICK, 1, false);

                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) LudoMainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.dialog_settings, null);
                ArrayList<ImageButton> btnList = new ArrayList<>();
                btnList.add((ImageButton) customView.findViewById(R.id.settingsCloseBtn));
                Button btnVisitSite = customView.findViewById(R.id.btnVisitHomeSite);
                Button btnPrivacyPolicy = customView.findViewById(R.id.btnPrivacyPolicy);
                //instantiate popup window
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if(!activity.isFinishing()) popupWindow.showAtLocation(layoutLudoMain, Gravity.CENTER, 0, 0);

                btnVisitSite.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("http://www.yutani.in/#next"));
                        startActivity(intent);

                    }
                });

                btnPrivacyPolicy.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("http://www.yutani.in/privacy_policy_callbreak.html"));
                        startActivity(intent);


                    }
                });
                btnList.get(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });



            }
        });

        themeChange = findViewById(R.id.themeChange);
        themeChange.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               try {
                   WaterBirdActivity.APP_THEME = (WaterBirdActivity.APP_THEME + 1) % ThemeFactory.getThemeFactory().themes.size();
                   ThemeFactory.getThemeFactory().themes.get(WaterBirdActivity.APP_THEME).setTheme(layoutLudoMain);
                   spEditor.putInt("APP_THEME", WaterBirdActivity.APP_THEME);
                   spEditor.apply();
               } catch (Exception e){

               }
            }
        });

		rulesPopup = findViewById(R.id.rulesPopup);
		rulesPopup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
				StaticVariables.soundUtil.playSound(StaticVariables.BTN_CLICK, 1, false);

                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) LudoMainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.dialog_rules, null);
                ArrayList<ImageButton> btnList = new ArrayList<>();
                btnList.add((ImageButton) customView.findViewById(R.id.rulesCloseBtn));

                //RelativeLayout layout = (RelativeLayout) findViewById(R.id.dialog_rules_layout);
                //ThemeFactory.themeFactory.themes.get(WaterBirdActivity.APP_THEME).setTheme(layout);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if(!activity.isFinishing()) popupWindow.showAtLocation(layoutLudoMain, Gravity.CENTER, 0, 0);


                btnList.get(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });



			}
		});
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
