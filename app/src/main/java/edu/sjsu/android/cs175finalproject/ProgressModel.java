package edu.sjsu.android.cs175finalproject;

public class ProgressModel {
    String game;
    long round;
    long score;

    public ProgressModel(){}

    public ProgressModel(String game, long round, long score) {
        this.round = round;
        this.score = score;
        this.game = game;
    }

    public long getRound() {
        return round;
    }

    public long getScore() {
        return score;
    }

    public String getGame() {
        return game;
    }
}
