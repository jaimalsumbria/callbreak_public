package com.waterbird.callbreak;

import java.util.ArrayList;
import java.util.Random;

public class Callbreak {

	public static ArrayList<PointsPerGame> pointTable;
	
	public static void main(String[] args) {
		pointTable = new ArrayList<PointsPerGame>();
		Callbreak callbreak = new Callbreak();
//		for(int i=0;i<1;i++) {
//			callbreak.game();
//		}
		
		for(int i=0;i<5;i++) {
			callbreak.game();
		}

		//System.out.println(pointTable);
	}

	public static ArrayList<Card> cardDeck52 = new ArrayList<Card>();

	public static ArrayList<Card> cardDeckP1 ;
	public static ArrayList<Card> cardDeckP2 ;
	public static ArrayList<Card> cardDeckP3 ;
	public static ArrayList<Card> cardDeckP4 ;

	CurrentHand currentHand;

	private void game() {


		for(int cardType= 1; cardType< 5; cardType++){

			for(int cardNumber= 2; cardNumber< 15; cardNumber++){
				//cardDeck52.add(new Card(cardNumber, CardName.cardName.get(cardNumber), cardType, CardType.cardType.get(cardType)));
			}
		}

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

		play ();

	}
	private void play() {

		currentHand =  new CurrentHand();
		Player player1 = new Player(cardDeckP1, currentHand);
		Player player2 = new Player(cardDeckP2, currentHand);
		Player player3 = new Player(cardDeckP3, currentHand);
		Player player4 = new Player(cardDeckP4, currentHand);

		Integer playerToLeadTrick = 0;

		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);

		player1.organizeDeck();
		player2.organizeDeck();
		player3.organizeDeck();
		player4.organizeDeck();

//		Random rand = new Random();
//		player1.setPlayerBid(rand.nextInt(5)+1);
//		player2.setPlayerBid(rand.nextInt(5)+1);
//		player3.setPlayerBid(rand.nextInt(5)+1);
//		player4.setPlayerBid(rand.nextInt(5)+1);

		Boolean firstHand = true;
		for(int i = 0 ; i<13; i++) {
			try { 
				currentHand.reset();
				playerList.get(playerToLeadTrick).playCard(firstHand, 1,playerToLeadTrick);
				System.out.println(currentHand.getCardsPlayed().toString());

				Thread.sleep(20);

				playerList.get((playerToLeadTrick+1)%4).playCard(firstHand, 2,(playerToLeadTrick+1)%4);
				System.out.println(currentHand.getCardsPlayed().toString());
				Thread.sleep(20);
				playerList.get((playerToLeadTrick+2)%4).playCard(firstHand, 3,(playerToLeadTrick+2)%4);
				System.out.println(currentHand.getCardsPlayed().toString());
				Thread.sleep(20);
				playerList.get((playerToLeadTrick+3)%4).playCard(firstHand, 4,(playerToLeadTrick+3)%4);
				System.out.println(currentHand.getCardsPlayed().toString());
				Thread.sleep(20);

				playerToLeadTrick = currentHand.getWinner();

				Player winnerOfHand = playerList.get(playerToLeadTrick);
				winnerOfHand.setWinCount(winnerOfHand.getWinCount()+1);

				for(int h=0; h<4;h++) {
					System.out.println(h+ ":" + playerList.get(h).getWinCount() + "; ");
				}

				firstHand = false;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		PointsPerGame pointsThisGame = new PointsPerGame();
		pointsThisGame.setPointsPlayer0(player1.calculatePoints());
		pointsThisGame.setPointsPlayer1(player2.calculatePoints());
		pointsThisGame.setPointsPlayer2(player3.calculatePoints());
		pointsThisGame.setPointsPlayer3(player4.calculatePoints());

		pointTable.add(pointsThisGame);
	}

}
