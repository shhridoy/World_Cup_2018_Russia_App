package com.shhridoy.worldcup2018russia.myRecyclerViewData;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class ListItems {
    private String date;
    private String round;
    private String team1;
    private String team2;
    private String score;

    public ListItems(String date, String round, String team1, String team2, String score) {
        this.date = date;
        this.round = round;
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
    }

    public ListItems (String date, String round, String team1) {
        this.date = date;
        this.round = round;
        this.team1 = team1;
    }

    public String getDate() {
        return date;
    }

    public String getRound() {
        return round;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getScore() {
        return score;
    }
}
