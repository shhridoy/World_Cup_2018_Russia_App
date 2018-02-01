package com.shhridoy.worldcup2018russia.myRecyclerViewData;

/**
 * Created by Dream Land on 2/1/2018.
 */

public class GoalsListItems {

    private String flagLink;
    private String name;
    private String goal;

    public GoalsListItems(String flagLink, String name, String goal) {
        this.flagLink = flagLink;
        this.name = name;
        this.goal = goal;
    }

    public GoalsListItems(String name, String goal) {
        this.name = name;
        this.goal = goal;
    }

    public String getFlagLink() {
        return flagLink;
    }

    public String getName() {
        return name;
    }

    public String getGoal() {
        return goal;
    }
}
