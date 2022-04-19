package com.waterbird.callbreak;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Player {

	private int playerBid = 1;
	private int maxBid    = 12;
	private int winCount=0;
	private String playerName;
	private Boolean isCurrentPlayer = false;
	private Boolean isOnline = true;
	private TotalScore totalScore = new TotalScore();

	private Card playedCard;

	private ArrayList<Card> cardDeckP;

	private CurrentHand currentHand;

	private Map<String, ArrayList<Card>> organizedCardDeckP = new HashMap<String, ArrayList<Card>>();

	public Player(ArrayList<Card> cardDeckP, CurrentHand currentHand) {
		super();
		this.cardDeckP = cardDeckP;
		this.currentHand = currentHand;
	}

    public Player() {

    }

	public Boolean isOnline() {
		return isOnline;
	}

	public void setOnline(Boolean online) {
		isOnline = online;
	}

	public Boolean isCurrentPlayer() {
		return isCurrentPlayer;
	}

	public void setIsCurrentPlayer(Boolean currentPlayer) {
		isCurrentPlayer = currentPlayer;
	}

	public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public TotalScore getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(TotalScore totalScore) {
        this.totalScore = totalScore;
    }

    public CurrentHand getCurrentHand() {
        return currentHand;
    }

    public void setCurrentHand(CurrentHand currentHand) {
        this.currentHand = currentHand;
    }

    public void organizeDeck () {

		organizedCardDeckP.put("CLUBS", new ArrayList<Card>());
		organizedCardDeckP.put("DIAMONDS", new ArrayList<Card>());
		organizedCardDeckP.put("HEARTS", new ArrayList<Card>());
		organizedCardDeckP.put("SPADES", new ArrayList<Card>());

		for(Card card: cardDeckP) {
			if(card.getTypeName().contains("CLUBS"))	{
				organizedCardDeckP.get("CLUBS").add(card);
			}
			if(card.getTypeName().contains("DIAMONDS"))	{
				organizedCardDeckP.get("DIAMONDS").add(card);
			}
			if(card.getTypeName().contains("HEARTS"))	{
				organizedCardDeckP.get("HEARTS").add(card);
			}
			if(card.getTypeName().contains("SPADES"))	{
				organizedCardDeckP.get("SPADES").add(card);
			}
		}

		cardDeckP.clear();

		for(Card card : organizedCardDeckP.get("HEARTS")) {
		    cardDeckP.add(card);
        }
        for(Card card : organizedCardDeckP.get("CLUBS")) {
            cardDeckP.add(card);
        }

        for(Card card : organizedCardDeckP.get("DIAMONDS")) {
            cardDeckP.add(card);
        }

        for(Card card : organizedCardDeckP.get("SPADES")) {
            cardDeckP.add(card);
        }
		
		//calculateBid();
	}

	public static Comparator<Card> weightageComparator = new Comparator<Card>() {

		@Override
		public int compare(Card c1, Card c2) {
			return c1.getWeightage() - c2.getWeightage();
		}
	};

	public void setClickableCards(int turn){

        for (Card card : cardDeckP) {
            card.setClickable(false);
        }// end for

	    Boolean clickableSet = false;

	    if(turn == 1) {
            for (Card card : cardDeckP) {
                card.setClickable(true);
            }// end for
            clickableSet = true;
        }

        if(!clickableSet) {
            if (cardDeckP.size() == 1) {
                cardDeckP.get(0).setClickable(true);
                clickableSet = true;
            }
        }
        if(!clickableSet) {
            for (Card card : cardDeckP) {
                if (card.getTypeName().contains(currentHand.getSuit())) {
                    card.setClickable(true);
                    clickableSet = true;
                }
            }// end for
        }

        if(!clickableSet) {
            for(Card card : cardDeckP) {
                if(card.getTypeName().contains("SPADES")) {
                    card.setClickable(true);
                    clickableSet = true;
                }
            }// end for
        }

        if(!clickableSet) {
            for(Card card : cardDeckP) {
                    card.setClickable(true);
                    clickableSet = true;
            }// end for
        }//end if
    }// end setClickableCards

	public void playCard(Boolean isFirstHand, Integer turn, Integer playerNumber) {

		if(playerNumber == 0 && !StaticVariables.auto) {
            for(Card card : cardDeckP) {
                if(card.isSelected()){
                    card.setWeightage(14);
                } else{
                    card.setWeightage(0);
                }
            }

            Collections.sort(cardDeckP);
            Card cardPlayed = cardDeckP.remove(cardDeckP.size()-1);
            cardPlayed.setCurrentPlayedBy(playerNumber);
            currentHand.getCardsPlayed().add(cardPlayed);
            if("".equalsIgnoreCase(currentHand.getSuit())) currentHand.setSuit(cardPlayed.getTypeName());
            currentHand.getCardsGone().put(cardPlayed.getTypeName()+":"+cardPlayed.getNumber(), cardPlayed.getTypeName()+":"+cardPlayed.getNumber());
		} else if(turn == 1) {

			for(Card card : cardDeckP) {
				if(card.getTypeName().contains("SPADES")) {
					Boolean biggerCardsGone = true;
					for(int cardNumber = card.getNumber()+1; cardNumber<=14; cardNumber++) {
						if(!currentHand.getCardsGone().containsKey("SPADES"+":"+cardNumber)) {
							biggerCardsGone = false;
						}
                    }
					
					if(biggerCardsGone) {
						card.setWeightage(14 + 14 - card.getNumber());
					} else {
						card.setWeightage(14 - card.getNumber());
					}
				} 

				if(!card.getTypeName().contains("SPADES")) {
					
					Boolean biggerCardsGone = true;
					for(int cardNumber = card.getNumber()+1; cardNumber<=14; cardNumber++) {
						if(!currentHand.getCardsGone().containsKey(currentHand.getSuit()+":"+cardNumber)) {
							biggerCardsGone = false;
						}
                    }
					
					if(biggerCardsGone) {
						card.setWeightage(14+ 14 + 14 + 14- card.getNumber());
					} else if (card.getNumber() == 14) {
						card.setWeightage(14+14+14);
					} else {
						card.setWeightage(14+14+14 - card.getNumber());
					}
				}

			}// end for

			Collections.sort(cardDeckP);
			Card cardPlayed = cardDeckP.remove(cardDeckP.size()-1);
			cardPlayed.setCurrentPlayedBy(playerNumber);
			currentHand.getCardsPlayed().add(cardPlayed);
			currentHand.setSuit(cardPlayed.getTypeName());
			currentHand.getCardsGone().put(cardPlayed.getTypeName()+":"+cardPlayed.getNumber(), cardPlayed.getTypeName()+":"+cardPlayed.getNumber());
		}// end if

		else if(turn == 2) {

			ArrayList<Card> copyCurrentHand = new ArrayList<Card>();
			copyCurrentHand.addAll(currentHand.getCardsPlayed());	

			Boolean suitNotEmpty = false;
			Boolean spadesNotEmpty = false;

			for(Card card : cardDeckP) {
				if(card.getTypeName().contains(currentHand.getSuit())) {
					suitNotEmpty = true;
				}

				if(card.getTypeName().contains("SPADES")) {
					spadesNotEmpty = true;
				} 

			}// end for


			if(suitNotEmpty) {
				for(Card card : cardDeckP) {
					if(!card.getTypeName().contains(currentHand.getSuit())) {
						card.setWeightage(-1);
					}else {
						Boolean currentCardIsWinner = true;
						//check if winner
						for(Card thrownCard: copyCurrentHand) {
							if(card.getNumber() < thrownCard.getNumber() || thrownCard.getTypeName().contains("SPADES")) {
								currentCardIsWinner = false;
								break;
							}
						}

						Boolean biggerCardsGone = true;
						for(int cardNumber = card.getNumber()+1; cardNumber<=14; cardNumber++) {
							if(!currentHand.getCardsGone().containsKey(currentHand.getSuit()+":"+cardNumber)) {
								biggerCardsGone = false;
							}
                        }
						if(currentCardIsWinner && biggerCardsGone) {
							//throw smallest suit winner
							card.setWeightage(14 + 14 - card.getNumber());
						} else {
							//throw smallest suit
							card.setWeightage(14 - card.getNumber());
						}

					}

				}// end for
			} else if (spadesNotEmpty) {
				for(Card card : cardDeckP) {
					if(!card.getTypeName().contains("SPADES")) {
						card.setWeightage(-1);
					}else {
						Boolean currentCardIsWinner = true;
						//check if winner
						for(Card thrownCard: copyCurrentHand) {
							if(card.getNumber() < thrownCard.getNumber() && thrownCard.getTypeName().contains("SPADES")) {
								currentCardIsWinner = false;
								break;
							}
						}
						Boolean biggerCardsGone = true;
						for(int cardNumber = card.getNumber()+1; cardNumber<=14; cardNumber++) {
							if(!currentHand.getCardsGone().containsKey("SPADES"+":"+cardNumber)) {
								biggerCardsGone = false;
							}
                        }
						if(currentCardIsWinner && biggerCardsGone) {
							//throw smallest spade winner
							card.setWeightage(14 + 14 - card.getNumber());
						} else {
							//throw smallest spade
							card.setWeightage(14 - card.getNumber());
						}

					}

				}// end for
			} else {
				for(Card card : cardDeckP) {
					// throw any smallest card
					card.setWeightage(14 - card.getNumber());
				}// end for
			}

            Collections.sort(cardDeckP);
			Card cardPlayed = cardDeckP.remove(cardDeckP.size()-1);
			cardPlayed.setCurrentPlayedBy(playerNumber);
			currentHand.getCardsPlayed().add(cardPlayed);
			currentHand.getCardsGone().put(cardPlayed.getTypeName()+":"+cardPlayed.getNumber(), cardPlayed.getTypeName()+":"+cardPlayed.getNumber());
		}

		else if(turn == 3) {

			ArrayList<Card> copyCurrentHand = new ArrayList<Card>();
			copyCurrentHand.addAll(currentHand.getCardsPlayed());	

			Boolean suitNotEmpty = false;
			Boolean spadesNotEmpty = false;

			for(Card card : cardDeckP) {
				if(card.getTypeName().contains(currentHand.getSuit())) {
					suitNotEmpty = true;
				}

				if(card.getTypeName().contains("SPADES")) {
					spadesNotEmpty = true;
				} 

			}// end for


			if(suitNotEmpty) {
				for(Card card : cardDeckP) {
					if(!card.getTypeName().contains(currentHand.getSuit())) {
						card.setWeightage(-1);
					}else {
						Boolean cuerrentCardIsWinner = true;
						//check if winner
						for(Card thrownCard: copyCurrentHand) {
							if(card.getNumber() < thrownCard.getNumber() || thrownCard.getTypeName().contains("SPADES")) {
								cuerrentCardIsWinner = false;
								break;
							}
						}

						if(cuerrentCardIsWinner) {
							//throw biggest suit winner
							card.setWeightage(14 + card.getNumber());
						} else {
							//throw smallest suit
							card.setWeightage(14 - card.getNumber());
						}

					}

				}// end for
			} else if (spadesNotEmpty) {
				for(Card card : cardDeckP) {
					if(!card.getTypeName().contains("SPADES")) {
						card.setWeightage(-1);
					}else {
						Boolean cuerrentCardIsWinner = true;
						//check if winner
						for(Card thrownCard: copyCurrentHand) {
							if(card.getNumber() < thrownCard.getNumber() && thrownCard.getTypeName().contains("SPADES")) {
								cuerrentCardIsWinner = false;
								break;
							}
						}

						if(cuerrentCardIsWinner) {
							//throw biggest spade winner
							card.setWeightage(14 + card.getNumber());
						} else {
							//throw smallest spade
							card.setWeightage(14 - card.getNumber());
						}

					}

				}// end for
			} else {
				for(Card card : cardDeckP) {
					// throw any smallest card
					card.setWeightage(14 - card.getNumber());
				}// end for
			}

            Collections.sort(cardDeckP);
			Card cardPlayed = cardDeckP.remove(cardDeckP.size()-1);
			cardPlayed.setCurrentPlayedBy(playerNumber);
			currentHand.getCardsPlayed().add(cardPlayed);
			currentHand.getCardsGone().put(cardPlayed.getTypeName()+":"+cardPlayed.getNumber(), cardPlayed.getTypeName()+":"+cardPlayed.getNumber());
		}
		
		else if(turn == 4) {

			ArrayList<Card> copyCurrentHand = new ArrayList<Card>();
			copyCurrentHand.addAll(currentHand.getCardsPlayed());	

			Boolean suitNotEmpty = false;
			Boolean spadesNotEmpty = false;

			for(Card card : cardDeckP) {
				if(card.getTypeName().contains(currentHand.getSuit())) {
					suitNotEmpty = true;
				}

				if(card.getTypeName().contains("SPADES")) {
					spadesNotEmpty = true;
				} 

			}// end for


			if(suitNotEmpty) {
				for(Card card : cardDeckP) {
					if(!card.getTypeName().contains(currentHand.getSuit())) {
						card.setWeightage(-1);
					}else {
						Boolean cuerrentCardIsWinner = true;
						//check if winner
						for(Card thrownCard: copyCurrentHand) {
							if(card.getNumber() < thrownCard.getNumber() || thrownCard.getTypeName().contains("SPADES")) {
								cuerrentCardIsWinner = false;
								break;
							}
						}

						if(cuerrentCardIsWinner) {
							//throw smallest suit winner
							card.setWeightage(14 + 14 - card.getNumber());
						} else {
							//throw smallest suit
							card.setWeightage(14 - card.getNumber());
						}

					}

				}// end for
			} else if (spadesNotEmpty) {
				for(Card card : cardDeckP) {
					if(!card.getTypeName().contains("SPADES")) {
						card.setWeightage(-1);
					}else {
						Boolean cuerrentCardIsWinner = true;
						//check if winner
						for(Card thrownCard: copyCurrentHand) {
							if(card.getNumber() < thrownCard.getNumber() && thrownCard.getTypeName().contains("SPADES")) {
								cuerrentCardIsWinner = false;
								break;
							}
						}

						if(cuerrentCardIsWinner) {
							//throw smallest spade winner
							card.setWeightage(14 + 14 - card.getNumber());
						} else {
							//throw smallest spade
							card.setWeightage(14 - card.getNumber());
						}

					}

				}// end for
			} else {
				for(Card card : cardDeckP) {
					// throw any smallest card
					card.setWeightage(14 - card.getNumber());
				}// end for
			}

            Collections.sort(cardDeckP);
			Card cardPlayed = cardDeckP.remove(cardDeckP.size()-1);
			cardPlayed.setCurrentPlayedBy(playerNumber);
			currentHand.getCardsPlayed().add(cardPlayed);
			currentHand.getCardsGone().put(cardPlayed.getTypeName()+":"+cardPlayed.getNumber(), cardPlayed.getTypeName()+":"+cardPlayed.getNumber());
		}


	}

	public Integer calculateBid () {
		
		Integer totalPointsForBidCal = 4*14*(14+1)/2 - 1;
		Integer playerPoints = 0;
		for(Card card : cardDeckP) {
			playerPoints = playerPoints + card.getNumber();
		}// end for
		
		System.out.println(playerPoints + ":" + totalPointsForBidCal);
	   playerBid = (playerPoints*12)/totalPointsForBidCal;
       return playerBid;
	}


	public float calculatePoints() {

		if (winCount > playerBid) {
			totalScore.add(playerBid,winCount - playerBid);
			return playerBid + (float) (winCount - playerBid)/10;
			
		} else if(winCount == playerBid) {
			totalScore.add(playerBid,0);
			return playerBid;
		} else {
			totalScore.add(0-playerBid,0);
			return 0 - playerBid;
		}
	}
	
	public Map<String, ArrayList<Card>> getOrganizedCardDeckP() {
		return organizedCardDeckP;
	}

	public void setOrganizedCardDeckP(Map<String, ArrayList<Card>> organizedCardDeckP) {
		this.organizedCardDeckP = organizedCardDeckP;
	}

	public ArrayList<Card> getCardDeckP() {
		return cardDeckP;
	}

	public void setCardDeckP(ArrayList<Card> cardDeckP) {
		this.cardDeckP = cardDeckP;
	}

	public int getPlayerBid() {
		return playerBid;
	}

	public void setPlayerBid(int playerBid) {
		this.playerBid = playerBid;
	}

	public Card getPlayedCard() {
		return playedCard;
	}

	public void setPlayedCard(Card playedCard) {
		this.playedCard = playedCard;
	}


	public int getWinCount() {
		return winCount;
	}


	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

}
