package com.shhridoy.worldcup2018russia.myRecyclerViewData;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class MatchesListItems {

    private int id;
    private String date;
    private String round;
    private String team1;
    private String flagTeam1;
    private String team2;
    private String flagTeam2;
    private String score;

    public MatchesListItems(int id, String date, String round, String team1, String team2, String score) {
        this.id = id;
        this.date = date;
        this.round = round;
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
    }

    public MatchesListItems(String date, String round, String team1, String team2, String score) {
        this.date = date;
        this.round = round;
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
    }

    public MatchesListItems(String date, String round, String team1, String flagTeam1, String team2, String flagTeam2, String score) {
        this.date = date;
        this.round = round;
        this.team1 = team1;
        this.flagTeam1 = flagTeam1;
        this.team2 = team2;
        this.flagTeam2 = flagTeam2;
        this.score = score;
    }

    public int getId() {
        return id;
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

    public String getFlagTeam1() {
        return flagTeam1;
    }

    public String getFlagTeam2() {
        return flagTeam2;
    }
}
