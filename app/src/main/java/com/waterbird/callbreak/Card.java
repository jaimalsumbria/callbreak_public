package com.waterbird.callbreak;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Card implements Comparable<Card>{
   
	private int number;
	private String name;
	private int type;
	private String typeName;
	private Integer weightage = 0;
	private Integer currentPlayedBy;
	private boolean visible;
	private Drawable cardPic;
	private Drawable cardBackPic;
	private Rect cardRect;
	private boolean isSelected;
	private boolean isClickable;

	public Card(int number, String name, int type, String typeName, Drawable cardPic, Drawable cardBackPic) {
		super();
		this.number = number;
		this.name = name;
		this.type = type;
		this.typeName = typeName;
		this.cardPic  = cardPic;
		this.visible  = true;
		this.isSelected = false;
		this.isClickable = true;
		this.cardBackPic =  cardBackPic;
	}
	
	public Integer getCurrentPlayedBy() {
		return currentPlayedBy;
	}

	public void setCurrentPlayedBy(Integer currentPlayedBy) {
		this.currentPlayedBy = currentPlayedBy;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Integer getWeightage() {
		return weightage;
	}

	public void setWeightage(Integer weightage) {
		this.weightage = weightage;
	}

	public int compareTo(Card o) {
        return this.getWeightage() - o.getWeightage();
	}

	@Override
	public String toString() {
		return name + ":" + typeName;
	}

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Drawable getCardPic() {
        return cardPic;
    }

    public void setCardPic(Drawable cardPic) {
        this.cardPic = cardPic;
    }

    public Rect getCardRect() {
        return cardRect;
    }

    public void setCardRect(Rect cardRect) {
        this.cardRect = cardRect;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    public Drawable getCardBackPic() {
        return cardBackPic;
    }

    public void setCardBackPic(Drawable cardBackPic) {
        this.cardBackPic = cardBackPic;
    }
}
