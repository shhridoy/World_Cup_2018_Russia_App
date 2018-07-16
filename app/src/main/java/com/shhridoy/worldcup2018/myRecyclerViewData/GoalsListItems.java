package com.shhridoy.worldcup2018.myRecyclerViewData;

/**
 * Created by Dream Land on 2/1/2018.
 */

public class GoalsListItems {

    private int id;
    private String name;
    private int goal;
    private String tag;

    public GoalsListItems(String name, int goal, String tag) {
        this.name = name;
        this.goal = goal;
        this.tag = tag;
    }

    public GoalsListItems(int id, String name, int goal, String tag) {
        this.id = id;
        this.name = name;
        this.goal = goal;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGoal() {
        return goal;
    }

    public String getTag() {
        return tag;
    }
}
