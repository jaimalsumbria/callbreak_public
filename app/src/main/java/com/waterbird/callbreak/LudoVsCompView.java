package com.waterbird.callbreak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.waterbird.callbreak.R;

import org.w3c.dom.Text;

public class LudoVsCompView extends View{

    private int CARD_THROW_TIME_LAG = 500;

    private static Handler invalidateHandler;

	HashMap<Integer, GridBox> boxHashMap = new HashMap<Integer, GridBox>();

	Boolean cardSelected = true;

    Boolean bidSelected = true;

    Drawable show_player_score;
    Rect rectShowPlayerScore;

    int leader_now = -1;

	ArrayList<Paint> colorList = new ArrayList<Paint>();

	float gridBoxDpHeight = 0;
	float gridBoxDpWidth = 0;
	Paint gridLinesPaint ;

	Paint paintRed; 
	Paint paintGreen;
	Paint paintBlue; 
	Paint paintYellow;
	Paint textColorPaint ;
	Paint playerPositionPaint;
	Paint compPositionPaint;

    volatile Drawable ACE_CLUBS;
    volatile Drawable ACE_DIAMONDS;
    volatile Drawable ACE_HEARTS;

	int   rankNoSize = 0;

	float gameOverStrokeWidth = 0;
	int   gameOverSize = 0;

    volatile HashMap <String, Drawable> cardsDrawMap;

	private Context context;
    private RectF[] rectNames = new RectF[4];
    private Paint textColorPaint2;

    public LudoVsCompView(Context context){
		super(context);
		this.context = context;
		init();
	}

	public LudoVsCompView(Context context, AttributeSet attrs){
		super(context, attrs);
		this.context = context;
		init();
	}

	public LudoVsCompView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}


    float centerdpHeight;
    float centerdpWidth;

    float cardHeight;
    float cardWidth;

    float dpHeight;
    float dpWidth;

    Rect[] rectPlayer = new Rect[4];
    RectF[] rectPoints = new RectF[4];

    public void init() {

        invalidateHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                StepDoneMsg msg = (StepDoneMsg) inputMessage.obj;
                if (msg.getInvalidateCurrentHandRect() == true){
                    for (Card card : currentHand.getCardsPlayed()) {
                        Rect rect = card.getCardPic().getBounds();
                        invalidate(rect);
                    }
                } else {
                    LudoVsCompView.this.invalidate();
                }//end if
            }
        };

		pointTable = new ArrayList<PointsPerGame>();

		Resources res = getResources();
		rankNoSize = res.getDimensionPixelSize(R.dimen.ludo_rank_size);

		gameOverStrokeWidth = res.getDimension(R.dimen.ludo_game_over_width);
		gameOverSize = res.getDimensionPixelSize(R.dimen.ludo_game_over_size);


		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();    
		dpHeight = displayMetrics.heightPixels; // displayMetrics.density;
		dpWidth  = displayMetrics.widthPixels ; // displayMetrics.density;

        centerdpHeight  = dpHeight/2;
        centerdpWidth   = dpWidth/2;
        cardHeight      = dpHeight/4;
        cardWidth       = dpWidth/10;

        rectPlayer[0] = new Rect((int) (centerdpWidth - cardWidth/2), (int)(centerdpHeight - cardHeight/4),
                                 (int)(centerdpWidth + cardWidth/2), (int)(centerdpHeight + 3*cardHeight/4));

        rectPlayer[1] = new Rect((int) (centerdpWidth), (int)(centerdpHeight - cardHeight/2),
                                 (int)(centerdpWidth + cardWidth), (int)(centerdpHeight+cardHeight/2));

        rectPlayer[2] = new Rect((int) (centerdpWidth - cardWidth/2), (int)(centerdpHeight - 3*cardHeight/4),
                                 (int)(centerdpWidth + cardWidth/2), (int)(centerdpHeight+cardHeight/4));

        rectPlayer[3]= new Rect((int) (centerdpWidth - cardWidth), (int)(centerdpHeight - cardHeight/2),
                                (int)(centerdpWidth), (int)(centerdpHeight + cardHeight/2));

        rectPoints[0] = new RectF(centerdpWidth-cardWidth/4,dpHeight-cardHeight/2, centerdpWidth+cardWidth/4, dpHeight-cardHeight/8);
        rectPoints[1] = new RectF(dpWidth-cardWidth/2 - cardWidth/4,centerdpHeight-cardHeight/2, dpWidth-cardWidth/4, centerdpHeight-cardHeight/8);
        rectPoints[2] = new RectF(centerdpWidth-cardWidth/4,0+cardHeight/8, centerdpWidth+cardWidth/4, 0+cardHeight/2);
        rectPoints[3] = new RectF(0+cardWidth/4,centerdpHeight-cardHeight/2, cardWidth/4+cardWidth/2, centerdpHeight-cardHeight/8);

        rectNames[0] = new RectF(centerdpWidth-cardWidth/4,dpHeight-cardHeight - cardHeight/8 , centerdpWidth+cardWidth/4, dpHeight-cardHeight);
        rectNames[1] = new RectF(dpWidth-cardWidth/2 - cardWidth/4,centerdpHeight-cardHeight, dpWidth-cardWidth/4, centerdpHeight-cardHeight/6);
        rectNames[2] = new RectF(centerdpWidth-cardWidth/4,0+cardHeight-cardHeight/25, centerdpWidth+cardWidth/4, 0+cardHeight+cardHeight/3);
        rectNames[3] = new RectF(0+cardWidth/4,centerdpHeight-cardHeight, cardWidth/4+cardWidth/2, centerdpHeight-cardHeight/6);

        gridBoxDpHeight = dpHeight/17;
		gridBoxDpWidth  = dpWidth/(float) 19.35;

		float offset = (float) 0.5;  // need to be modified to accommodate all screen sizes

		cardsDrawMap = new HashMap<>();

        ACE_CLUBS = context.getResources().getDrawable(R.drawable.ac);
        ACE_DIAMONDS = context.getResources().getDrawable(R.drawable.ad);
        ACE_HEARTS = context.getResources().getDrawable(R.drawable.ah);

        cardsDrawMap.put("ACE:CLUBS", ACE_CLUBS);
        cardsDrawMap.put("ACE:DIAMONDS", ACE_DIAMONDS);
        cardsDrawMap.put("ACE:HEARTS", ACE_HEARTS);
        cardsDrawMap.put("ACE:SPADES", context.getResources().getDrawable(R.drawable.as));

        cardsDrawMap.put("TWO:CLUBS", context.getResources().getDrawable(R.drawable.c2));
        cardsDrawMap.put("TWO:DIAMONDS", context.getResources().getDrawable(R.drawable.d2));
        cardsDrawMap.put("TWO:HEARTS", context.getResources().getDrawable(R.drawable.h2));
        cardsDrawMap.put("TWO:SPADES", context.getResources().getDrawable(R.drawable.s2));

        cardsDrawMap.put("THREE:CLUBS", context.getResources().getDrawable(R.drawable.c3));
        cardsDrawMap.put("THREE:DIAMONDS", context.getResources().getDrawable(R.drawable.d3));
        cardsDrawMap.put("THREE:HEARTS", context.getResources().getDrawable(R.drawable.h3));
        cardsDrawMap.put("THREE:SPADES", context.getResources().getDrawable(R.drawable.s3));

        cardsDrawMap.put("FOUR:CLUBS", context.getResources().getDrawable(R.drawable.c4));
        cardsDrawMap.put("FOUR:DIAMONDS", context.getResources().getDrawable(R.drawable.d4));
        cardsDrawMap.put("FOUR:HEARTS", context.getResources().getDrawable(R.drawable.h4));
        cardsDrawMap.put("FOUR:SPADES", context.getResources().getDrawable(R.drawable.s4));


        cardsDrawMap.put("FIVE:CLUBS", context.getResources().getDrawable(R.drawable.c5));
        cardsDrawMap.put("FIVE:DIAMONDS", context.getResources().getDrawable(R.drawable.d5));
        cardsDrawMap.put("FIVE:HEARTS", context.getResources().getDrawable(R.drawable.h5));
        cardsDrawMap.put("FIVE:SPADES", context.getResources().getDrawable(R.drawable.s5));

        cardsDrawMap.put("SIX:CLUBS", context.getResources().getDrawable(R.drawable.c6));
        cardsDrawMap.put("SIX:DIAMONDS", context.getResources().getDrawable(R.drawable.d6));
        cardsDrawMap.put("SIX:HEARTS", context.getResources().getDrawable(R.drawable.h6));
        cardsDrawMap.put("SIX:SPADES", context.getResources().getDrawable(R.drawable.s6));

        cardsDrawMap.put("SEVEN:CLUBS", context.getResources().getDrawable(R.drawable.c7));
        cardsDrawMap.put("SEVEN:DIAMONDS", context.getResources().getDrawable(R.drawable.d7));
        cardsDrawMap.put("SEVEN:HEARTS", context.getResources().getDrawable(R.drawable.h7));
        cardsDrawMap.put("SEVEN:SPADES", context.getResources().getDrawable(R.drawable.s7));


        cardsDrawMap.put("EIGHT:CLUBS", context.getResources().getDrawable(R.drawable.c8));
        cardsDrawMap.put("EIGHT:DIAMONDS", context.getResources().getDrawable(R.drawable.d8));
        cardsDrawMap.put("EIGHT:HEARTS", context.getResources().getDrawable(R.drawable.h8));
        cardsDrawMap.put("EIGHT:SPADES", context.getResources().getDrawable(R.drawable.s8));

        cardsDrawMap.put("NINE:CLUBS", context.getResources().getDrawable(R.drawable.c9));
        cardsDrawMap.put("NINE:DIAMONDS", context.getResources().getDrawable(R.drawable.d9));
        cardsDrawMap.put("NINE:HEARTS", context.getResources().getDrawable(R.drawable.h9));
        cardsDrawMap.put("NINE:SPADES", context.getResources().getDrawable(R.drawable.s9));

        cardsDrawMap.put("TEN:CLUBS", context.getResources().getDrawable(R.drawable.c10));
        cardsDrawMap.put("TEN:DIAMONDS", context.getResources().getDrawable(R.drawable.d10));
        cardsDrawMap.put("TEN:HEARTS", context.getResources().getDrawable(R.drawable.h10));
        cardsDrawMap.put("TEN:SPADES", context.getResources().getDrawable(R.drawable.s10));

        cardsDrawMap.put("JACK:CLUBS", context.getResources().getDrawable(R.drawable.jc));
        cardsDrawMap.put("JACK:DIAMONDS", context.getResources().getDrawable(R.drawable.jd));
        cardsDrawMap.put("JACK:HEARTS", context.getResources().getDrawable(R.drawable.jh));
        cardsDrawMap.put("JACK:SPADES", context.getResources().getDrawable(R.drawable.js));

        cardsDrawMap.put("QUEEN:CLUBS", context.getResources().getDrawable(R.drawable.qc));
        cardsDrawMap.put("QUEEN:DIAMONDS", context.getResources().getDrawable(R.drawable.qd));
        cardsDrawMap.put("QUEEN:HEARTS", context.getResources().getDrawable(R.drawable.qh));
        cardsDrawMap.put("QUEEN:SPADES", context.getResources().getDrawable(R.drawable.qs));

        cardsDrawMap.put("KING:CLUBS", context.getResources().getDrawable(R.drawable.kc));
        cardsDrawMap.put("KING:DIAMONDS", context.getResources().getDrawable(R.drawable.kd));
        cardsDrawMap.put("KING:HEARTS", context.getResources().getDrawable(R.drawable.kh));
        cardsDrawMap.put("KING:SPADES", context.getResources().getDrawable(R.drawable.ks));


        show_player_score = context.getResources().getDrawable(R.drawable.show_player_score);
        rectShowPlayerScore = new Rect((int)(dpWidth-cardWidth),(int)(0+cardHeight/32),
                                       (int)(dpWidth - cardWidth/16), (int)(0+cardHeight/2+cardHeight/6));
        show_player_score.setBounds(rectShowPlayerScore);
		initMuteBoxes();
		definePaintColors();
		initializePlayers();

        new Thread(new Runnable() {
            public void run() {
                try {
                    for(int i=0;i<5;i++) {
                        i = game(i);

                    }
                    System.out.println("EXIT MAIN GAME");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }

        }).start();



	}

    ArrayList<Player> playerList = new ArrayList<Player>();
    private void initializePlayers() {
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();

        player1.setPlayerName("You");
        player2.setPlayerName("Amar");
        player3.setPlayerName("Akba");
        player4.setPlayerName("Anth");

        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
    }

	RectF       muteBoxRectF ;
	Boolean     mute = false;
    Drawable    muteImage;
    Drawable    notMuteImage;
	Paint       muteBoxPaint;
    private void initMuteBoxes(){

        muteBoxPaint = new Paint();
        muteBoxPaint.setColor(Color.parseColor("#FBE9E7"));
        muteBoxPaint.setAlpha(160);
        muteBoxPaint.setStyle(Paint.Style.FILL);
        muteBoxPaint.setTextSize(24);
        muteBoxPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        muteBoxRectF = new RectF(0+cardWidth/4,0+cardWidth/4,
                                cardWidth/2, cardWidth/2);


        muteImage = context.getResources().getDrawable(R.drawable.mute);
        notMuteImage = context.getResources().getDrawable(R.drawable.notmute);

        muteImage.setBounds((int) (muteBoxRectF.left),
                (int) (muteBoxRectF.top),
                (int) (muteBoxRectF.right),
                (int) (muteBoxRectF.bottom));
        notMuteImage.setBounds((int) (muteBoxRectF.left),
                (int) (muteBoxRectF.top),
                (int) (muteBoxRectF.right),
                (int) (muteBoxRectF.bottom));
    }

	private void definePaintColors() {

		gridLinesPaint = new Paint();
		gridLinesPaint.setStrokeWidth(4);
		gridLinesPaint.setStyle(Paint.Style.STROKE);
		gridLinesPaint.setColor(Color.parseColor("#757575"));
		gridLinesPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		paintYellow = new Paint();
		paintYellow.setStrokeWidth(6);
		paintYellow.setStyle(Paint.Style.FILL);
		paintYellow.setColor(Color.parseColor("#fbbc05"));
		paintYellow.setFlags(Paint.ANTI_ALIAS_FLAG);

		paintGreen = new Paint();
		paintGreen.setStrokeWidth(6);
		paintGreen.setStyle(Paint.Style.FILL);
		paintGreen.setColor(Color.parseColor("#34a853"));
		paintGreen.setFlags(Paint.ANTI_ALIAS_FLAG);

		paintBlue= new Paint();
		paintBlue.setStrokeWidth(6);
		paintBlue.setStyle(Paint.Style.FILL);
		paintBlue.setColor(Color.parseColor("#4285f4"));
		paintBlue.setFlags(Paint.ANTI_ALIAS_FLAG);

		paintRed= new Paint();
		paintRed.setStrokeWidth(6);
		paintRed.setStyle(Paint.Style.FILL);
		paintRed.setColor(Color.parseColor("#ea4335"));
		paintRed.setFlags(Paint.ANTI_ALIAS_FLAG);

		colorList.add(paintYellow);
		colorList.add(paintGreen);
		colorList.add(paintRed);
		colorList.add(paintBlue);

		playerPositionPaint = new Paint();
		playerPositionPaint.setStrokeWidth(4);
		playerPositionPaint.setColor(Color.YELLOW);
		playerPositionPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		playerPositionPaint.setTextSize(24);
		playerPositionPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		compPositionPaint = new Paint();
		compPositionPaint.setStrokeWidth(4);
		compPositionPaint.setColor(Color.RED);
		compPositionPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		compPositionPaint.setTextSize(24);
		compPositionPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		textColorPaint = new Paint();
		textColorPaint.setStrokeWidth(4);
		textColorPaint.setColor(Color.parseColor("#fbbc05"));
		textColorPaint.setStyle(Paint.Style.STROKE);
		textColorPaint.setTextSize(cardWidth/4);
		textColorPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        textColorPaint2 = new Paint();
        textColorPaint2.setStrokeWidth(4);
        textColorPaint2.setColor(Color.parseColor("#4285f4"));
        textColorPaint2.setStyle(Paint.Style.STROKE);
        textColorPaint2.setTextSize(cardWidth/5);
        textColorPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);
        Typeface plain =   textColorPaint2.getTypeface();
        Typeface italic = Typeface.create(plain, Typeface.ITALIC);
        textColorPaint2.setTypeface(italic);
	}



    public static ArrayList<PointsPerGame> pointTable;
    public static ArrayList<Card> cardDeck52 = new ArrayList<Card>();
    public static ArrayList<Card> cardDeck52Bkp = new ArrayList<Card>();

    public static ArrayList<Card> cardDeckP1 ;
    public static ArrayList<Card> cardDeckP2 ;
    public static ArrayList<Card> cardDeckP3 ;
    public static ArrayList<Card> cardDeckP4 ;
    public ArrayList<ArrayList<Card>> cardDeckList ;

    protected volatile CurrentHand currentHand;

    private Integer game(Integer round) {

        for(int cardType= 1; cardType< 5; cardType++){

            for(int cardNumber= 2; cardNumber< 15; cardNumber++){
                cardDeck52.add(new Card(cardNumber, CardName.cardName.get(cardNumber),
                                        cardType, CardType.cardType.get(cardType),
                                         cardsDrawMap.get(CardName.cardName.get(cardNumber)+ ":"+CardType.cardType.get(cardType)),
                                          context.getResources().getDrawable(R.drawable.cardback)));
            }
        }

        cardDeck52Bkp.addAll(cardDeck52);
        cardDeckP1 = new ArrayList<Card>();
        cardDeckP2 = new ArrayList<Card>();
        cardDeckP3 = new ArrayList<Card>();
        cardDeckP4 = new ArrayList<Card>();

        for(int i = 0 ; i<13; i++) {

            Random rand = new Random();

            cardDeckP1.add(cardDeck52.remove(rand.nextInt(cardDeck52.size())));
            cardDeckP2.add(cardDeck52.remove(rand.nextInt(cardDeck52.size())));
            cardDeckP3.add(cardDeck52.remove(rand.nextInt(cardDeck52.size())));
            cardDeckP4.add(cardDeck52.remove(rand.nextInt(cardDeck52.size())));
        }

        cardDeckList = new ArrayList<ArrayList<Card>>();
        cardDeckList.add(cardDeckP1);
        cardDeckList.add(cardDeckP2);
        cardDeckList.add(cardDeckP3);
        cardDeckList.add(cardDeckP4);
        Integer endRound = play (round);
        System.out.println("STARTING EXIT MAIN GAME ");
        return endRound;

    }

    private Integer play(Integer round) {

        currentHand =  new CurrentHand();

        Integer playerToLeadTrick = pointTable.size()%4;//0,1,2,3,0
        Integer cardDeckList_i=0;
        for(Player player:playerList){
            player.setCardDeckP(cardDeckList.get(cardDeckList_i));
            cardDeckList_i++;
            player.setCurrentHand(currentHand);
            player.organizeDeck();
            player.calculateBid();
            player.setWinCount(0);
        }

        int deck1Offset = 0;
        int deckSize1 = cardDeckP1.size();
        deck1Offset  = (int) dpWidth/2 - (int)(cardWidth*2/3)*(deckSize1/2) - (int) cardWidth*1/3;
        for(Card card: cardDeckP1){
            Rect rect = new Rect(deck1Offset,(int)(dpHeight - cardHeight),(int) cardWidth+deck1Offset,(int)dpHeight);
            card.setCardRect(rect);
            cardsDrawMap.get(card.getName() + ":" + card.getTypeName())
                    .setBounds(rect);
            deck1Offset = deck1Offset+(int)cardWidth*2/3;
        }

        invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();

        deck1Offset = 0;
        for(Card card: cardDeckP2){
            int deckSize = cardDeckP2.size();

            cardsDrawMap.get(card.getName() + ":" + card.getTypeName())
                    .setBounds((int) (dpWidth-2*cardWidth) + deck1Offset,(int)(centerdpHeight - cardHeight/2),
                               (int) (dpWidth-cardWidth)+ deck1Offset,(int)(centerdpHeight+cardHeight/2));
            card.getCardBackPic().setBounds((int) (dpWidth-2*cardWidth) + deck1Offset,(int)(centerdpHeight - cardHeight/2),
                    (int) (dpWidth-cardWidth)+ deck1Offset,(int)(centerdpHeight+cardHeight/2));
            deck1Offset = deck1Offset+(int)cardWidth*1/13;
        }

        invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();

        deck1Offset = 0;
        for(Card card: cardDeckP3){
            int deckSize = cardDeckP3.size();

            cardsDrawMap.get(card.getName() + ":" + card.getTypeName())
                    .setBounds((int) (centerdpWidth-cardWidth) + deck1Offset,0,
                            (int) (centerdpWidth)+ deck1Offset,(int)(cardHeight));
            card.getCardBackPic().setBounds((int) (centerdpWidth-cardWidth) + deck1Offset,0,
                    (int) (centerdpWidth)+ deck1Offset,(int)(cardHeight));
            deck1Offset = deck1Offset+(int)cardWidth*1/13;
        }

        invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();

        deck1Offset = 0;
        for(Card card: cardDeckP4){

            int deckSize = cardDeckP4.size();

            cardsDrawMap.get(card.getName() + ":" + card.getTypeName())
                    .setBounds(0 + deck1Offset ,(int)(centerdpHeight - cardHeight/2),
                            (int) cardWidth + deck1Offset,(int)(centerdpHeight+cardHeight/2));
            card.getCardBackPic().setBounds(0 + deck1Offset ,(int)(centerdpHeight - cardHeight/2),
                    (int) cardWidth + deck1Offset,(int)(centerdpHeight+cardHeight/2));
            deck1Offset = deck1Offset+(int)cardWidth*1/13;
        }

        invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();

        waitForBidSelection();

        System.out.println("GAME STARTED");
        Boolean firstHand = false;

        for(int i = 0 ; i<13; i++) {
            try {
                if(!(LudoVsCompActivity.msgQueueForGameKill.poll()==null)) {
                    System.out.println("EXIT GAME");
                    cardDeck52Bkp.clear();
                    currentHand.reset();
                    return 5;
                }

                currentHand.reset();
                invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
                Thread.sleep(CARD_THROW_TIME_LAG);

                setCardBoundsPlayerOne();

                invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
                Thread.sleep(CARD_THROW_TIME_LAG);

                // first throw
                if(playerToLeadTrick == 0) {
                    playerList.get(0).setClickableCards(1);
                    StepDoneMsg msg = waitForCardSelection();
                    if(msg.getFinalExit()) return 5;

                }
                playerList.get(playerToLeadTrick).playCard(firstHand, 1,playerToLeadTrick);
                for(Card cardPlayed : currentHand.getCardsPlayed()){
                    if(cardPlayed.getCurrentPlayedBy()== playerToLeadTrick) {
                        throwCard(rectPlayer[playerToLeadTrick], cardPlayed);
                    }
                }

                invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
                System.out.println(currentHand.getCardsPlayed().toString());
                Thread.sleep(CARD_THROW_TIME_LAG);

                // second throw
                if((playerToLeadTrick+1)%4 == 0) {
                    playerList.get((playerToLeadTrick+1)%4).setClickableCards(2);
                    setCardBoundsPlayerOneWithClickables();
                    invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
                    StepDoneMsg msg = waitForCardSelection();
                    if(msg.getFinalExit()) return 5;
                }
                playerList.get((playerToLeadTrick+1)%4).playCard(firstHand, 2,(playerToLeadTrick+1)%4);
                System.out.println(currentHand.getCardsPlayed().toString());
                for(Card cardPlayed : currentHand.getCardsPlayed()){
                    if(cardPlayed.getCurrentPlayedBy()== (playerToLeadTrick+1)%4)
                        throwCard(rectPlayer[(playerToLeadTrick+1)%4], cardPlayed);
                }
                invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
                Thread.sleep(CARD_THROW_TIME_LAG);

                // third throw
                if((playerToLeadTrick+2)%4 == 0) {
                    playerList.get((playerToLeadTrick+2)%4).setClickableCards(3);
                    setCardBoundsPlayerOneWithClickables();
                    invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
                    StepDoneMsg msg = waitForCardSelection();
                    if(msg.getFinalExit()) return 5;
                }
                playerList.get((playerToLeadTrick+2)%4).playCard(firstHand, 3,(playerToLeadTrick+2)%4);
                System.out.println(currentHand.getCardsPlayed().toString());
                for(Card cardPlayed : currentHand.getCardsPlayed()){
                    if(cardPlayed.getCurrentPlayedBy()==(playerToLeadTrick+2)%4)
                        throwCard(rectPlayer[(playerToLeadTrick+2)%4], cardPlayed);
                }
                invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
                Thread.sleep(CARD_THROW_TIME_LAG);

                // fourth throw
                if((playerToLeadTrick+3)%4 == 0) {
                    playerList.get((playerToLeadTrick+3)%4).setClickableCards(4);
                    setCardBoundsPlayerOneWithClickables();
                    invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
                    StepDoneMsg msg = waitForCardSelection();
                    if(msg.getFinalExit()) return 5;
                }
                playerList.get((playerToLeadTrick+3)%4).playCard(firstHand, 4,(playerToLeadTrick+3)%4);
                System.out.println(currentHand.getCardsPlayed().toString());
                for(Card cardPlayed : currentHand.getCardsPlayed()){
                    if(cardPlayed.getCurrentPlayedBy()== (playerToLeadTrick+3)%4)
                        throwCard(rectPlayer[(playerToLeadTrick+3)%4], cardPlayed);
                }
               invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
               Thread.sleep(CARD_THROW_TIME_LAG);

                playerToLeadTrick = currentHand.getWinner();

                moveCardsToWinner(playerToLeadTrick);

                Player winnerOfHand = playerList.get(playerToLeadTrick);
                winnerOfHand.setWinCount(winnerOfHand.getWinCount()+1);

                for(int h=0; h<4;h++) {
                    System.out.println(h+ ":" + playerList.get(h).getWinCount() + "; ");
                }

                firstHand = false;

            } catch (InterruptedException e) {
                e.printStackTrace();
                return 5;
            } finally {
            }
        }

        final PointsPerGame pointsThisGame = new PointsPerGame();
        pointsThisGame.setPointsPlayer0(playerList.get(0).calculatePoints());
        pointsThisGame.setPointsPlayer1(playerList.get(1).calculatePoints());
        pointsThisGame.setPointsPlayer2(playerList.get(2).calculatePoints());
        pointsThisGame.setPointsPlayer3(playerList.get(3).calculatePoints());

        //check leader
        int leader = 0;
        for(int i=1; i<4; i++) {
            if(playerList.get(leader).getTotalScore().getMajor()<playerList.get(i).getTotalScore().getMajor()) {
                leader = i;
            } else if (playerList.get(leader).getTotalScore().getMajor()== playerList.get(i).getTotalScore().getMajor()) {
                if(playerList.get(leader).getTotalScore().getMinor() < playerList.get(i).getTotalScore().getMinor()){
                    leader = i;
                }
            }
        }

        leader_now = leader;

        pointTable.add(pointsThisGame);
        cardDeck52Bkp.clear();
        cardDeck52.clear();
        cardDeckP1.clear();
        cardDeckP2.clear();
        cardDeckP3.clear();
        cardDeckP4.clear();
        cardDeckList.clear();
        currentHand.reset();

        invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();

		GameCompleteMsg gameCompleteMsg = new GameCompleteMsg();
		gameCompleteMsg.setPointTable(pointTable);
		gameCompleteMsg.setPlayerList(playerList);
		gameCompleteMsg.setLeader(leader);
        gameCompleteMsg.setBidSelectionPopup(false);

        Message completeMessageOne =
                LudoVsCompActivity.mHandler.obtainMessage(0, gameCompleteMsg);
        completeMessageOne.sendToTarget();

        try {
           StepDoneMsg stepDoneMsg = LudoVsCompActivity.msgQueuePopupButtonClicked.take();
            if(pointTable.size() >= 5) {
                if(stepDoneMsg.getExitTrueNewGameFalse() == false) {
                    new Thread(new Runnable() {
                        public void run() {
                            pointTable.clear();
                            leader_now = -1;
                            for (Player player : playerList) {
                                 player.getTotalScore().clear();
                            }
                            try {
                                for(int i=0;i<5;i++) {
                                    i=game(i);
                                }
                                System.out.println("EXIT MAIN GAME");
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {

                            }
                        }

                    }).start();

                } else {

                    Message completeMessage =
                            LudoVsCompActivity.exitHandler.obtainMessage(0, new GameCompleteMsg());
                    completeMessage.sendToTarget();
                    System.out.println("EXIT GAME");
                    return 5;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 5;
        }
        return round;
    }

    private void throwCard(Rect finalBounds, Card cardPlayed) {
        StaticVariables.soundUtil.playSound(StaticVariables.TICK, 1, mute);
        Rect initialBounds = cardPlayed.getCardPic().getBounds();
        double distance = Math.sqrt((Math.abs(finalBounds.left-initialBounds.left))^2+Math.abs((finalBounds.top-initialBounds.top))^2);
        for (int i = 6; i >= 1; i--) {
            double smallD = distance/i;
            double left = initialBounds.left+smallD/distance*(finalBounds.left-initialBounds.left);
            double top = initialBounds.top+smallD/distance*(finalBounds.top-initialBounds.top);
            double right = initialBounds.right+smallD/distance*(finalBounds.right-initialBounds.right);
            double bottom = initialBounds.bottom+smallD/distance*(finalBounds.bottom-initialBounds.bottom);

            //System.out.println("MOVE: "+left+","+top+","+right+","+bottom);

            cardPlayed.getCardPic().setBounds((int) Math.abs(left),(int)Math.abs(top),
                    (int)Math.abs(right),(int)Math.abs(bottom));

            StepDoneMsg msg = new StepDoneMsg();
            msg.setInvalidateCurrentHandRect(true);
            msg.setCurrentHand(currentHand);

            invalidateHandler.obtainMessage(0, msg).sendToTarget();

            try {
                Thread.sleep(CARD_THROW_TIME_LAG / 16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//end for
    }

    private void moveCardsToWinner(Integer playerToLeadTrick) {
        for (int i = 0; i < 6; i++) {
            for (Card card : currentHand.getCardsPlayed()) {
                Rect rect = card.getCardPic().getBounds();
                if (playerToLeadTrick == 0) {
                    card.getCardPic().setBounds(rect.left, rect.top + (int) cardHeight / 4, rect.right, rect.bottom + (int) cardHeight / 4);
                   if(i==0) {
                       rect = card.getCardPic().getBounds();
                       card.getCardPic().setBounds(rect.left + (int) (cardWidth / 4), rect.top + (int) (cardHeight / 4),
                            rect.right - (int) (cardWidth / 8), rect.bottom - (int) (cardHeight / 8));
                   }
                }
                if (playerToLeadTrick == 1) {
                    card.getCardPic().setBounds(rect.left + (int) cardWidth/2, rect.top, rect.right + (int) cardWidth/2, rect.bottom);
                    if(i==0) {
                        rect = card.getCardPic().getBounds();
                        card.getCardPic().setBounds(rect.left + (int) (cardWidth / 4), rect.top + (int) (cardHeight / 4),
                                rect.right - (int) (cardWidth / 8), rect.bottom - (int) (cardHeight / 8));
                    }
                }
                if (playerToLeadTrick == 2) {
                    card.getCardPic().setBounds(rect.left, rect.top - (int) cardHeight / 4, rect.right, rect.bottom - (int) cardHeight / 4);
                    if(i==0) {
                        rect = card.getCardPic().getBounds();
                        card.getCardPic().setBounds(rect.left + (int) (cardWidth / 4), rect.top + (int) (cardHeight / 4),
                                rect.right - (int) (cardWidth / 8), rect.bottom - (int) (cardHeight / 8));
                    }
                }
                if (playerToLeadTrick == 3) {
                    card.getCardPic().setBounds(rect.left - (int) cardWidth/2, rect.top, rect.right - (int) cardWidth/2, rect.bottom);
                    if(i==0) {
                        rect = card.getCardPic().getBounds();
                        card.getCardPic().setBounds(rect.left + (int) (cardWidth / 4), rect.top + (int) (cardHeight / 4),
                                rect.right - (int) (cardWidth / 8), rect.bottom - (int) (cardHeight / 8));
                    }
                }
            }


            StepDoneMsg msg = new StepDoneMsg();
            msg.setInvalidateCurrentHandRect(true);
            msg.setCurrentHand(currentHand);

            invalidateHandler.obtainMessage(0, msg).sendToTarget();

            try {
                Thread.sleep(CARD_THROW_TIME_LAG/16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//end for
    }

    private void setCardBoundsPlayerOne() {
        int deckSize1;
        int deck1Offset;
        deckSize1 = cardDeckP1.size();
        deck1Offset  = (int) dpWidth/2 - (int)(cardWidth*2/3)*(deckSize1/2) - (int) cardWidth*1/3;

        for(Card card: cardDeckP1){
            Rect rect = new Rect(deck1Offset,(int)(dpHeight - cardHeight),(int) cardWidth+deck1Offset,(int)dpHeight);
            card.setCardRect(rect);
            cardsDrawMap.get(card.getName() + ":" + card.getTypeName()).setBounds(rect);
            deck1Offset = deck1Offset+(int)cardWidth*2/3;
        }
    }

    private void setCardBoundsPlayerOneWithClickables() {
        int deckSize1;
        int deck1Offset;
        deckSize1 = cardDeckP1.size();
        deck1Offset  = (int) dpWidth/2 - (int)(cardWidth*2/3)*(deckSize1/2) - (int) cardWidth*1/3;

        for(Card card: cardDeckP1){
            float sinkBy = 0;
            if(!card.isClickable()){
                sinkBy = cardHeight/3;
            }
            Rect rect = new Rect(deck1Offset,(int)(dpHeight - cardHeight + sinkBy),(int) cardWidth+deck1Offset,(int) (dpHeight + sinkBy));
            card.setCardRect(rect);
            cardsDrawMap.get(card.getName() + ":" + card.getTypeName()).setBounds(rect);
            deck1Offset = deck1Offset+(int)cardWidth*2/3;
        }
    }

    private void waitForBidSelection() {
        //if(auto) return;
        bidSelected = false;
        GameCompleteMsg gameCompleteMsg = new GameCompleteMsg();
        gameCompleteMsg.setBidSelectionPopup(true);

        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(LudoVsCompActivity.mHandler == null) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Message completeMessageTwo =
                LudoVsCompActivity.mHandler.obtainMessage(0, gameCompleteMsg);
        completeMessageTwo.sendToTarget();

        try {
            StepDoneMsg msg = LudoVsCompActivity.msgQueuePopupButtonClicked.take();
            playerList.get(0).setPlayerBid(msg.getBidAmount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private StepDoneMsg waitForCardSelection() {

        StepDoneMsg exitMsg = new StepDoneMsg();
        exitMsg.setFinalExit(false);

    	if(StaticVariables.auto)return exitMsg;
        cardSelected = false;

        try {
            exitMsg = LudoVsCompActivity.msgQueueCardSelected.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return  exitMsg;
    }// end waitForCardSelection

    @Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        try {
            for (Card card : currentHand.getCardsPlayed()) {
                cardsDrawMap.get(card.getName() + ":" + card.getTypeName()).draw(canvas);
            }

            for (Card card : cardDeckP1) {
                if (card.isVisible()) {
                    card.getCardPic().draw(canvas);
                }
            }
            for (Card card : cardDeckP2) {
                if (card.isVisible()) {
                    card.getCardBackPic().draw(canvas);
                }
            }
            for (Card card : cardDeckP3) {
                if (card.isVisible()) {
                    card.getCardBackPic().draw(canvas);
                }
            }
            for (Card card : cardDeckP4) {
                if (card.isVisible()) {
                    card.getCardBackPic().draw(canvas);
                }
            }

            for (int i = 0; i < 4; i++) {
                canvas.drawRoundRect(rectPoints[i], 5, 5, paintRed);
                canvas.drawRoundRect(rectPoints[i], 5, 5, gridLinesPaint);
            }

            int rect_i = 0;
            for (Player player : playerList) {
                canvas.drawText(player.getWinCount() + "/" + player.getPlayerBid(),
                        rectPoints[rect_i].centerX() - cardWidth / 5, rectPoints[rect_i].centerY() + cardHeight / 20, textColorPaint);
                rect_i++;
            }

            int rect_j = 0;
            for (Player player : playerList) {
                canvas.drawText(player.getPlayerName(),
                        rectNames[rect_j].centerX() - cardWidth / 4, rectNames[rect_j].centerY(), textColorPaint2);
                rect_j++;
            }

            show_player_score.draw(canvas);

            //mute or unmute
            canvas.drawCircle(muteBoxRectF.centerX(), muteBoxRectF.centerY(), cardWidth / 4, muteBoxPaint);
            canvas.drawCircle(muteBoxRectF.centerX(), muteBoxRectF.centerY(), cardWidth / 4, gridLinesPaint);

            if (mute) {
                muteImage.draw(canvas);
            } else {
                notMuteImage.draw(canvas);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
	}//end onDraw

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();

		if (muteBoxRectF.contains(x, y)) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
                StaticVariables.soundUtil.playSound(StaticVariables.BTN_CLICK2, 1, false);
                mute = !mute;
                invalidateHandler.obtainMessage(0, new StepDoneMsg()).sendToTarget();
				break;
			}

		}// end if

        if(rectShowPlayerScore.contains((int) x, (int) y)){

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    GameCompleteMsg gameCompleteMsg = new GameCompleteMsg();
                    gameCompleteMsg.setPointTable(pointTable);
                    gameCompleteMsg.setPlayerList(playerList);
                    gameCompleteMsg.setLeader(leader_now);
                    gameCompleteMsg.setOpenPopupMidGame(true);
                    gameCompleteMsg.setBidSelectionPopup(false);
                    StaticVariables.soundUtil.playSound(StaticVariables.BTN_CLICK2, 1, mute);
                    Message completeMessageOne =
                            LudoVsCompActivity.mHandler.obtainMessage(0, gameCompleteMsg);
                    completeMessageOne.sendToTarget();
                    break;
            }
        }

		if(cardSelected == false){

            for (int j = cardDeckP1.size() - 1; j >= 0; j--) {
                Card card = cardDeckP1.get(j);
                if(card.getCardRect().contains((int) x, (int) y) && card.isClickable()){
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            synchronized (cardSelected) {
                                cardSelected = true;
                                card.setSelected(true);
                            }
                            break;
                    }//switch
                    break;
                }//if
            }
            if(cardSelected == true){
                for(Card card: cardDeckP1){
                    card.setClickable(false);
                }
                StepDoneMsg msg = new StepDoneMsg();
                msg.setFinalExit(false);
                LudoVsCompActivity.msgQueueCardSelected.offer(msg);
            }
		}

		return true;
	}
}
