package com.waterbird.callbreak;

import android.graphics.drawable.Drawable;

public class DrawableMarker {

	Drawable drawableMarker;
	int      colMarker ;
	
	public DrawableMarker(Drawable drawableMarker, int colMarker) {
		super();
		this.drawableMarker = drawableMarker;
		this.colMarker = colMarker;
	}
	public Drawable getDrawableMarker() {
		return drawableMarker;
	}
	public void setDrawableMarker(Drawable drawableMarker) {
		this.drawableMarker = drawableMarker;
	}
	public int getColMarker() {
		return colMarker;
	}
	public void setColMarker(int colMarker) {
		this.colMarker = colMarker;
	}

	
}
