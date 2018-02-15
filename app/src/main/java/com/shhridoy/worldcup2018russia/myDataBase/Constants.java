package com.shhridoy.worldcup2018russia.myDataBase;

/**
 * Created by Dream Land on 2/15/2018.
 */

public class Constants {

    public static final String DB_NAME = "russiaworldcup.db";
    public static final int DB_VERSION = 1;

    // MATCHES TABLE CONSTANTS
    public static final String MATCHES_TABLE = "matches";

    public static final String M_ID = "mid";
    public static final String DATE = "date";
    public static final String ROUND = "round";
    public static final String TEAM1 = "team1";
    public static final String TEAM2 = "team2";
    public static final String SCORE = "score";

    public static final String CREATE_MATCHES_TABLE = "CREATE TABLE "+ MATCHES_TABLE +
            "( " +
            M_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DATE +" TEXT, " +
            ROUND +" TEXT, " +
            TEAM1 + " TEXT, " +
            TEAM2 + " TEXT, " +
            SCORE + " TEXT);";

    public static final String DROP_MATCHES_TABLE = "DROP TABLE IF EXISTS " + MATCHES_TABLE;


    // POINT TABLE CONSTANTS
    public static final String POINT_TABLE = "points";

    public static final String P_ID = "pid";
    public static final String GROUP = "group";
    public static final String TEAM_NO = "team_no";
    public static final String TEAM_NAME = "team_name";
    public static final String STATUS = "status";

    public static final String CREATE_POINTS_TABLE = "CREATE TABLE " + POINT_TABLE +
            "( " +
            P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            GROUP + " TEXT, " +
            TEAM_NO + " TEXT, " +
            TEAM_NAME + " TEXT, " +
            STATUS + " TEXT);";

    public static final String DROP_POINTS_TABLE = "DROP TABLE IF EXISTS " + POINT_TABLE;


    // GOALS TABLE CONSTANTS
    public static final String GOAL_TABLE = "goals";
    public static final String G_ID = "gid";
    public static final String NAME = "name";
    public static final String GOALS = "goal";
    public static final String TAG = "tag";

    public static final String CREATE_GOAL_TABLE = "CREATE TABLE " + GOAL_TABLE +
            "( " +
            G_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " +
            GOALS + " TEXT, " +
            TAG + " TEXT);";

    public static final String DROP_GOAL_TABLE = "DROP TABLE IF EXISTS " + GOAL_TABLE;

}
