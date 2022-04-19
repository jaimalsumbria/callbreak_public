package com.waterbird.callbreak;
import java.util.Comparator;

public class BiggerCardComparator implements Comparator<Card>{
	private String localSuit = ""; 
	
	public String getLocalSuit() {
		return localSuit;
	}

	public void setLocalSuit(String localSuit) {
		this.localSuit = localSuit;
	}

	@Override
	public int compare(Card c1, Card c2) {
		
		if(c1.getTypeName().equalsIgnoreCase("SPADES") && c2.getTypeName().equalsIgnoreCase("SPADES")) {
			
			if(c1.getNumber() == 1) {
				return 1; 
			}else if(c2.getNumber() == 1) {
				return -1;
			}
			
			return c1.getNumber() - c2.getNumber();
		}else if(c1.getTypeName().equalsIgnoreCase("SPADES") && !c2.getTypeName().equalsIgnoreCase("SPADES")) {
			return 1;
		}else if(!c1.getTypeName().equalsIgnoreCase("SPADES") && c2.getTypeName().equalsIgnoreCase("SPADES")) {
			return -1;
		}else if(c1.getTypeName().equalsIgnoreCase(localSuit) && !c2.getTypeName().equalsIgnoreCase(localSuit)) {
			return 1;
		}else if(!c1.getTypeName().equalsIgnoreCase(localSuit) && c2.getTypeName().equalsIgnoreCase(localSuit)) {
			return -1;
		}else {
			if(c1.getNumber() == 1) {
				return 1; 
			}else if(c2.getNumber() == 1) {
				return -1;
			}
			return c1.getNumber() - c2.getNumber();
		}
	}

}
