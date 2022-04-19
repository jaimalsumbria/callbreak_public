package com.waterbird.callbreak;

import java.util.ArrayList;

public class StepDoneMsg {

    private Boolean youWon;
    private Integer bidAmount;
    private Boolean exitTrueNewGameFalse;
    private Boolean invalidateCurrentHandRect;
    private CurrentHand currentHand;
    private Boolean finalExit;
    private ArrayList<String> playerNameList;

    StepDoneMsg(){
        this.bidAmount = 1;
        invalidateCurrentHandRect = false;
        finalExit=false;
    }

    public Boolean getFinalExit() {
        return finalExit;
    }

    public void setFinalExit(Boolean finalExit) {
        this.finalExit = finalExit;
    }

    public Boolean getYouWon() {
        return youWon;
    }

    public void setYouWon(Boolean youWon) {
        this.youWon = youWon;
    }

    public Integer getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Integer bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Boolean getExitTrueNewGameFalse() {
        return exitTrueNewGameFalse;
    }

    public void setExitTrueNewGameFalse(Boolean exitTrueNewGameFalse) {
        this.exitTrueNewGameFalse = exitTrueNewGameFalse;
    }

    public Boolean getInvalidateCurrentHandRect() {
        return invalidateCurrentHandRect;
    }

    public void setInvalidateCurrentHandRect(Boolean invalidateCurrentHandRect) {
        this.invalidateCurrentHandRect = invalidateCurrentHandRect;
    }

    public CurrentHand getCurrentHand() {
        return currentHand;
    }

    public void setCurrentHand(CurrentHand currentHand) {
        this.currentHand = currentHand;
    }

    public ArrayList<String> getPlayerNameList() {
        return playerNameList;
    }

    public void setPlayerNameList(ArrayList<String> playerNameList) {
        this.playerNameList = playerNameList;
    }
}
