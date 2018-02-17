package com.shhridoy.worldcup2018russia.myRecyclerViewData;

/**
 * Created by Dream Land on 2/1/2018.
 */

public class TablesListItems {

    private int id;
    private String group;
    private String teamNo;
    private String teamName;
    private String status;

    public TablesListItems(String group, String teamNo, String teamName, String status) {
        this.group = group;
        this.teamNo = teamNo;
        this.teamName = teamName;
        this.status = status;
    }


    public TablesListItems(int id, String group, String teamNo, String teamName, String status) {
        this.id = id;
        this.group = group;
        this.teamNo = teamNo;
        this.teamName = teamName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getGroup() {
        return group;
    }

    public String getTeamNo() {
        return teamNo;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getStatus() {
        return status;
    }
}
