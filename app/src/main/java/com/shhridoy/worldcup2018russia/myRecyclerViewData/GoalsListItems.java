package com.shhridoy.worldcup2018russia.myRecyclerViewData;

/**
 * Created by Dream Land on 2/1/2018.
 */

public class GoalsListItems {

    private String flagLink;
    private String name;
    private String goal;
    private String tag;

    public GoalsListItems(String flagLink, String name, String goal, String tag) {
        this.flagLink = flagLink;
        this.name = name;
        this.goal = goal;
        this.tag = tag;
    }

    public GoalsListItems(String name, String goal, String tag) {
        this.name = name;
        this.goal = goal;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }
}
