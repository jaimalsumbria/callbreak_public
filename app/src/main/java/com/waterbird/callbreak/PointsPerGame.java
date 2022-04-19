package com.waterbird.callbreak;

public class PointsPerGame {

	private float pointsPlayer0;
	private float pointsPlayer1;
	private float pointsPlayer2;
	private float pointsPlayer3;


	public float getPointsPlayer0() {
		return pointsPlayer0;
	}


	public void setPointsPlayer0(float pointsPlayer0) {
		this.pointsPlayer0 = pointsPlayer0;
	}


	public float getPointsPlayer1() {
		return pointsPlayer1;
	}


	public void setPointsPlayer1(float pointsPlayer1) {
		this.pointsPlayer1 = pointsPlayer1;
	}


	public float getPointsPlayer2() {
		return pointsPlayer2;
	}


	public void setPointsPlayer2(float pointsPlayer2) {
		this.pointsPlayer2 = pointsPlayer2;
	}


	public float getPointsPlayer3() {
		return pointsPlayer3;
	}


	public void setPointsPlayer3(float pointsPlayer3) {
		this.pointsPlayer3 = pointsPlayer3;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return pointsPlayer0 + ":" + pointsPlayer1 + ":" + pointsPlayer2 + ":" + pointsPlayer3;
	}
}
