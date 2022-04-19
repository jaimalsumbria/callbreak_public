package com.waterbird.callbreak;

import java.util.ArrayList;

public class GridBox {


	float x; //can have value 0-9
	float y; //can have value 0-9
	Integer   tag; // can have value 1-100

	Boolean snake = false;
	Boolean ladder = false;

	Integer moveTo;
	
	ArrayList<Integer>  snakePath = null;
	
	ArrayList<Integer>  ladderPath = null;



	public GridBox(float x, float y, int value) {
		super();
		this.x = x;
		this.y = y;
		this.tag = value;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public Boolean getSnake() {
		return snake;
	}

	public void setSnake(Boolean snake) {
		this.snake = snake;
	}

	public Boolean getLadder() {
		return ladder;
	}

	public void setLadder(Boolean ladder) {
		this.ladder = ladder;
	}


	public Integer getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(Integer moveTo) {
		this.moveTo = moveTo;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public ArrayList<Integer> getSnakePath() {
		return snakePath;
	}

	public void setSnakePath(ArrayList<Integer> snakePath) {
		this.snakePath = snakePath;
	}

	public ArrayList<Integer> getLadderPath() {
		return ladderPath;
	}

	public void setLadderPath(ArrayList<Integer> ladderPath) {
		this.ladderPath = ladderPath;
	}

}
