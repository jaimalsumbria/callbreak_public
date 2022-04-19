package com.waterbird.callbreak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.waterbird.callbreak.R;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class LudoVsCompActivity extends Activity {

	protected static Boolean play;
    protected static ArrayBlockingQueue<StepDoneMsg> msgQueueForGameKill = new ArrayBlockingQueue<StepDoneMsg>(1);
    protected static ArrayBlockingQueue<StepDoneMsg> msgQueue = new ArrayBlockingQueue<StepDoneMsg>(1);
    protected static ArrayBlockingQueue<StepDoneMsg> msgQueueCardSelected = new ArrayBlockingQueue<StepDoneMsg>(1);
    protected static ArrayBlockingQueue<StepDoneMsg> msgQueuePopupButtonClicked = new ArrayBlockingQueue<StepDoneMsg>(1);

    protected static Handler mHandler;
    protected static Handler exitHandler;
    private Button nextRoundPopupBtn;
    private Button newGamePopupBtn;
    private Button exitPopupBtn;
    private Button closePopupBtn;
    private PopupWindow popupWindow;
    private RelativeLayout linearLayout1;
	private InterstitialAd mInterstitialAd;
    protected Activity activity;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		clearMsgQueues();
		// init sound pool
		StaticVariables.initSoundPool(getApplicationContext());
        activity = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ludo_activity_vs_comp);
		play = true;
		Tools.init(this);
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-8796345110437201/6723586648");
		mInterstitialAd = Tools.decoratePf(this, mInterstitialAd);
		//Tools.decorateRnb(this, adView);

        linearLayout1 = findViewById(R.id.compActLayout);

        try {
            ThemeFactory.getThemeFactory().themes.get(WaterBirdActivity.APP_THEME).setTheme(linearLayout1);
        } catch (Exception e){

        }
        exitHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                GameCompleteMsg msg = (GameCompleteMsg) inputMessage.obj;
                doAllOnExit();
            }
        };
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                GameCompleteMsg msg = (GameCompleteMsg) inputMessage.obj;
                if(msg.isGameExit()){
                    openExitGamePopup(msg);
                } else if (msg.isBidSelectionPopup()) {
                    openBidSelectionPopup(msg);
                } else {
                    if(msg.getLeader() == -1) return;
                    openEndGamePopup(msg);
                }

            }
        };

	}//end onCreate

    private void clearMsgQueues() {

        LudoVsCompActivity.msgQueueForGameKill.clear();
        LudoVsCompActivity.msgQueue.clear();
        LudoVsCompActivity.msgQueuePopupButtonClicked.clear();
        LudoVsCompActivity.msgQueueCardSelected.clear();



        if(LudoVsCompView.cardDeck52Bkp != null) LudoVsCompView.cardDeck52Bkp.clear();
        if(LudoVsCompView.cardDeck52 != null) LudoVsCompView.cardDeck52.clear();
        if(LudoVsCompView.cardDeckP1 != null) LudoVsCompView.cardDeckP1.clear();
        if(LudoVsCompView.cardDeckP2 != null) LudoVsCompView.cardDeckP2.clear();
        if(LudoVsCompView.cardDeckP3 != null) LudoVsCompView.cardDeckP3.clear();
        if(LudoVsCompView.cardDeckP4 != null) LudoVsCompView.cardDeckP4.clear();
        if(LudoVsCompView.pointTable != null) LudoVsCompView.pointTable.clear();
    }

    private void openEndGamePopup(GameCompleteMsg msg) {
        if(msg.isOpenPopupMidGame()){
            if (popupWindow != null && popupWindow.isShowing()) {
                return;
            }
        } else{
            popupWindow.dismiss();
        }

        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) LudoVsCompActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.dialog_game_points,null);
        nextRoundPopupBtn = customView.findViewById(R.id.nextRoundPopupBtn);
        newGamePopupBtn = customView.findViewById(R.id.newGamePopupBtn);
        exitPopupBtn = customView.findViewById(R.id.exitPopupBtn);
        closePopupBtn = customView.findViewById(R.id.closePopupBtn);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if(!activity.isFinishing()) popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
        //create table
        TableLayout score_table = customView.findViewById(R.id.score_table);
        TableRow table_row_exit = customView.findViewById(R.id.table_row_exit);

        int padding_in_cell = 15;
        ArrayList<Player> playerList = msg.getPlayerList();

        TableRow tableHead = new TableRow(popupWindow.getContentView().getContext());
        tableHead.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        TextView headerRnd = new TextView(popupWindow.getContentView().getContext());
        headerRnd.setText("Round");
        headerRnd.setTextColor(Color.WHITE);          // part2
        headerRnd.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
        headerRnd.setBackgroundResource(R.drawable.cell_shape_head);
        tableHead.addView(headerRnd);

        for(Player player:playerList){
            TextView header = new TextView(popupWindow.getContentView().getContext());
            header.setText(player.getPlayerName()+"");
            header.setTextColor(Color.WHITE);          // part2
            header.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
            header.setBackgroundResource(R.drawable.cell_shape_head);
            tableHead.addView(header);
        }
        score_table.addView(tableHead);

        ArrayList<PointsPerGame> pointTable = msg.getPointTable();
        int countRound = 0;
        for(PointsPerGame pointsPerGame:pointTable){
            TableRow tableRow = new TableRow(popupWindow.getContentView().getContext());

            countRound ++;
            tableRow.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            TextView rnd = new TextView(popupWindow.getContentView().getContext());
            rnd.setText(countRound+"/"+5);
            rnd.setTextColor(Color.WHITE);          // part2
            rnd.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
            rnd.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(rnd);

            TextView pointsP1 = new TextView(popupWindow.getContentView().getContext());
            pointsP1.setText(pointsPerGame.getPointsPlayer0()+"");
            pointsP1.setTextColor(Color.WHITE);          // part2
            pointsP1.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
            pointsP1.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(pointsP1);

            TextView pointsP2 = new TextView(popupWindow.getContentView().getContext());
            pointsP2.setText(pointsPerGame.getPointsPlayer1()+"");
            pointsP2.setTextColor(Color.WHITE);          // part2
            pointsP2.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
            pointsP2.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(pointsP2);

            TextView pointsP3 = new TextView(popupWindow.getContentView().getContext());
            pointsP3.setText(pointsPerGame.getPointsPlayer2()+"");
            pointsP3.setTextColor(Color.WHITE);          // part2
            pointsP3.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
            pointsP3.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(pointsP3);

            TextView pointsP4 = new TextView(popupWindow.getContentView().getContext());
            pointsP4.setText(pointsPerGame.getPointsPlayer3()+"");
            pointsP4.setTextColor(Color.WHITE);          // part2
            pointsP4.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
            pointsP4.setBackgroundResource(R.drawable.cell_shape);
            tableRow.addView(pointsP4);

            score_table.addView(tableRow);
            //display the popup window
        }

        TableRow tableFooter = new TableRow(popupWindow.getContentView().getContext());
        tableFooter.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        TextView footerRnd = new TextView(popupWindow.getContentView().getContext());
        footerRnd.setText("Total");
        footerRnd.setTextColor(Color.WHITE);          // part2
        footerRnd.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
        footerRnd.setBackgroundResource(R.drawable.cell_shape_footer);
        tableFooter.addView(footerRnd);

        for(Player player:playerList){
            TextView footer = new TextView(popupWindow.getContentView().getContext());
            footer.setText(player.getTotalScore().getMajor()+"."+player.getTotalScore().getMinor());
            footer.setTextColor(Color.WHITE);          // part2
            footer.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
            footer.setBackgroundResource(R.drawable.cell_shape_footer);
            tableFooter.addView(footer);
        }
        score_table.addView(tableFooter);

        TableRow tableFooter2 = new TableRow(popupWindow.getContentView().getContext());
        tableFooter2.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        TextView footerLeader = new TextView(popupWindow.getContentView().getContext());
        footerLeader.setTextColor(Color.WHITE);          // part2
        footerLeader.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
        footerLeader.setBackgroundResource(R.drawable.cell_shape_footer);
        tableFooter2.addView(footerLeader);

        for(int i=0;i<4;i++){
            TextView footer = new TextView(popupWindow.getContentView().getContext());
            if(msg.getLeader()==i) {
                if(pointTable.size() < 5) {
                    footer.setText("Leader");
                } else {
                    footer.setText("Winner");
                }
            }
            footer.setTextColor(Color.WHITE);          // part2
            footer.setPadding(padding_in_cell, padding_in_cell, padding_in_cell, padding_in_cell);
            footer.setBackgroundResource(R.drawable.cell_shape_footer);
            tableFooter2.addView(footer);
        }
        score_table.addView(tableFooter2);

        if(msg.isOpenPopupMidGame()){
            table_row_exit.removeView(newGamePopupBtn);
            table_row_exit.removeView(exitPopupBtn);
            table_row_exit.removeView(nextRoundPopupBtn);
            closePopupBtn.setVisibility(View.VISIBLE);
            closePopupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        } else {

            if (pointTable.size() < 5) {
                table_row_exit.removeView(newGamePopupBtn);
                table_row_exit.removeView(exitPopupBtn);
                table_row_exit.removeView(closePopupBtn);
                nextRoundPopupBtn.setVisibility(View.VISIBLE);
                nextRoundPopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StepDoneMsg msg = new StepDoneMsg();
                        while (!LudoVsCompActivity.msgQueuePopupButtonClicked.offer(new StepDoneMsg())) ;
                        popupWindow.dismiss();
                    }
                });
            } else {
                table_row_exit.removeView(nextRoundPopupBtn);
                table_row_exit.removeView(closePopupBtn);
                newGamePopupBtn.setVisibility(View.VISIBLE);
                newGamePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StepDoneMsg msg = new StepDoneMsg();
                        msg.setExitTrueNewGameFalse(false);
                        while (!LudoVsCompActivity.msgQueuePopupButtonClicked.offer(msg)) ;
                        popupWindow.dismiss();
                    }
                });
                exitPopupBtn.setVisibility(View.VISIBLE);
                exitPopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StepDoneMsg msg = new StepDoneMsg();
                        msg.setExitTrueNewGameFalse(true);
                        while (!LudoVsCompActivity.msgQueuePopupButtonClicked.offer(msg)) ;
                        popupWindow.dismiss();
                    }
                });
            }
        }//end if
    }

    private void openExitGamePopup(GameCompleteMsg msg) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) LudoVsCompActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.dialog_game_exit, null);
        ArrayList<Button> btnList = new ArrayList<>();
        btnList.add((Button) customView.findViewById(R.id.noPopupBtn));
        btnList.add((Button) customView.findViewById(R.id.yesPopupBtn));
        //instantiate popup window
        popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if(!activity.isFinishing()) popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);


        btnList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        btnList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAllOnExit();
                popupWindow.dismiss();
            }
        });
    }

    private void openBidSelectionPopup(GameCompleteMsg msg) {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) LudoVsCompActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.dialog_bid_selection, null);
        ArrayList<Button> btnList = new ArrayList<>();
        btnList.add((Button) customView.findViewById(R.id.oneBtn));
        btnList.add((Button) customView.findViewById(R.id.twoBtn));
        btnList.add((Button) customView.findViewById(R.id.threeBtn));
        btnList.add((Button) customView.findViewById(R.id.fourBtn));
        btnList.add((Button) customView.findViewById(R.id.fiveBtn));
        btnList.add((Button) customView.findViewById(R.id.sixBtn));
        btnList.add((Button) customView.findViewById(R.id.sevenBtn));
        btnList.add((Button) customView.findViewById(R.id.eightBtn));
        //instantiate popup window
        popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if(!activity.isFinishing()) popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);

        for (final Button bidBtn : btnList) {
            bidBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StepDoneMsg msg = new StepDoneMsg();
                    msg.setBidAmount(Integer.parseInt(bidBtn.getText().toString()));
                    while (!LudoVsCompActivity.msgQueuePopupButtonClicked.offer(msg)) ;
                    popupWindow.dismiss();
                }
            });
        }
    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        //exitByBackKey();
            GameCompleteMsg gameCompleteMsg = new GameCompleteMsg();
            gameCompleteMsg.setGameExit(true);
            Message completeMessage =
                    mHandler.obtainMessage(0, gameCompleteMsg);
            completeMessage.sendToTarget();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}


    private void doAllOnExit() {
        LudoVsCompActivity.msgQueueForGameKill.offer(new StepDoneMsg());
        LudoVsCompActivity.msgQueuePopupButtonClicked.offer(new StepDoneMsg());
        StepDoneMsg exitMsg = new StepDoneMsg();
        exitMsg.setFinalExit(true);
        LudoVsCompActivity.msgQueueCardSelected.offer(exitMsg);

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        StaticVariables.soundUtil.playSound(StaticVariables.BTN_CLICK, 1, false);
        finish();
	    		if (mInterstitialAd.isLoaded()) {
	                mInterstitialAd.show();
	            }
    }

}
