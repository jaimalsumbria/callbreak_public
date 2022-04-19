package com.waterbird.callbreak;

import android.content.Context;

import java.util.ArrayList;

public class GameCompleteMsg {

    Boolean youWon;
    Boolean exitPopUpThread;
    Context context;
    ArrayList<PointsPerGame> pointTable;
    ArrayList<Player> playerList;
    boolean isBidSelectionPopup;
    boolean gameExit;
    Integer leader;
    boolean openPopupMidGame;
    boolean dismissBidSelectionPopUp = false;

    GameCompleteMsg(){
        exitPopUpThread = false;
        gameExit=false;
        openPopupMidGame = false;
    }

    public boolean getDismissBidSelectionPopUp() {
        return dismissBidSelectionPopUp;
    }

    public void setDismissBidSelectionPopUp(boolean dismissBidSelectionPopUp) {
        this.dismissBidSelectionPopUp = dismissBidSelectionPopUp;
    }

    public Boolean getYouWon() {
        return youWon;
    }

    public void setYouWon(Boolean youWon) {
        this.youWon = youWon;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<PointsPerGame> getPointTable() {
        return pointTable;
    }

    public void setPointTable(ArrayList<PointsPerGame> pointTable) {
        this.pointTable = pointTable;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public boolean isBidSelectionPopup() {
        return isBidSelectionPopup;
    }

    public void setBidSelectionPopup(boolean bidSelectionPopup) {
        isBidSelectionPopup = bidSelectionPopup;
    }

    public Boolean getExitPopUpThread() {
        return exitPopUpThread;
    }

    public void setExitPopUpThread(Boolean exitPopUpThread) {
        this.exitPopUpThread = exitPopUpThread;
    }

    public boolean isGameExit() {
        return gameExit;
    }

    public void setGameExit(boolean gameExit) {
        this.gameExit = gameExit;
    }

    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }

    public boolean isOpenPopupMidGame() {
        return openPopupMidGame;
    }

    public void setOpenPopupMidGame(boolean openPopupMidGame) {
        this.openPopupMidGame = openPopupMidGame;
    }
}
