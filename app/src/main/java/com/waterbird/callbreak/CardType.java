package com.waterbird.callbreak;

import java.util.HashMap;
import java.util.Map;

public class CardType {
	
	public static Map<Integer, String> cardType = new HashMap<Integer, String>();
	static {
		cardType.put(1, "CLUBS");
		cardType.put(2, "DIAMONDS");
		cardType.put(3, "HEARTS");
		cardType.put(4, "SPADES");
		
	}
	
}
