package com.waterbird.callbreak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CurrentHand {
	
	private String suit = "";// cardType
	private ArrayList<Card> cardsPlayed = new ArrayList<>();
	private Map<String, String> cardsGone;
	
	
	private BiggerCardComparator biggerCardComparator = new BiggerCardComparator();
	    
	public Integer getWinner() {
		
		biggerCardComparator.setLocalSuit(suit);
		Collections.sort(cardsPlayed, biggerCardComparator);
//		cardsPlayed.sort(biggerCardComparator);
		return cardsPlayed.get(cardsPlayed.size()-1).getCurrentPlayedBy();
	}
	
	
	public void reset() {
		suit        = "";
		for (Card card:cardsPlayed) {
			card.setVisible(false);
		}
		cardsPlayed = new ArrayList<Card>();
	    cardsGone   = new HashMap<String, String>();
	}
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	public ArrayList<Card> getCardsPlayed() {
		return cardsPlayed;
	}
	public void setCardsPlayed(ArrayList<Card> cardsPlayed) {
		this.cardsPlayed = cardsPlayed;
	}


	public Map<String, String> getCardsGone() {
		return cardsGone;
	}


	public void setCardsGone(Map<String, String> cardsGone) {
		this.cardsGone = cardsGone;
	}
	
	
}
