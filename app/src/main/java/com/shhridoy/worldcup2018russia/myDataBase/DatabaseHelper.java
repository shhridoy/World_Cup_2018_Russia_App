package com.shhridoy.worldcup2018russia.myDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dream Land on 2/15/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constants.CREATE_MATCHES_TABLE);
        sqLiteDatabase.execSQL(Constants.CREATE_POINTS_TABLE);
        sqLiteDatabase.execSQL(Constants.CREATE_GOAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Constants.DROP_MATCHES_TABLE);
        sqLiteDatabase.execSQL(Constants.DROP_POINTS_TABLE);
        sqLiteDatabase.execSQL(Constants.DROP_GOAL_TABLE);
        onCreate(sqLiteDatabase);
    }

    // INSERT VALUES IN MATCHES TABLE
    public void insertMatchesData (String date, String round, String team1, String team2, String score) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.DATE, date);
        cv.put(Constants.ROUND, round);
        cv.put(Constants.TEAM1, team1);
        cv.put(Constants.TEAM2, team2);
        cv.put(Constants.SCORE, score);
        this.getWritableDatabase().insertOrThrow(Constants.MATCHES_TABLE, "", cv);
        this.getWritableDatabase().close();
    }

    // UPDATE VALUES IN MATCHES TABLE
    public boolean updateMatchesData (int id, String date, String round, String team1, String team2, String score) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.DATE, date);
        cv.put(Constants.ROUND, round);
        cv.put(Constants.TEAM1, team1);
        cv.put(Constants.TEAM2, team2);
        cv.put(Constants.SCORE, score);
        int result = this.getWritableDatabase()
                .update(Constants.MATCHES_TABLE, cv, Constants.M_ID+" =?", new String[]{String.valueOf(id)});
        this.getWritableDatabase().close();
        return result > 0;
    }

    // RETRIEVE DATA FROM MATCHES TABLE
    public Cursor retrieveMatchesData () {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + Constants.MATCHES_TABLE, null);
    }

    // INSERT VALUES IN POINTS TABLE
    public void insertPointsData (String group, String teamNo, String teamName, String status) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.GROUP, group);
        cv.put(Constants.TEAM_NO, teamNo);
        cv.put(Constants.TEAM_NAME, teamName);
        cv.put(Constants.STATUS, status);
        this.getWritableDatabase().insertOrThrow(Constants.POINT_TABLE, "", cv);
        this.getWritableDatabase().close();
    }

    // UPDATE VALUES IN POINTS TABLE
    public boolean updatePointsData(int id, String group, String teamNo, String teamName, String status) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.GROUP, group);
        cv.put(Constants.TEAM_NO, teamNo);
        cv.put(Constants.TEAM_NAME, teamName);
        cv.put(Constants.STATUS, status);
        int result = this.getWritableDatabase()
                .update(Constants.POINT_TABLE, cv, Constants.P_ID+" =?", new String[]{String.valueOf(id)});
        this.getWritableDatabase().close();
        return result > 0;
    }

    // RETRIEVE DATA FROM POINTS TABLE
    public Cursor retrievePointsData() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + Constants.POINT_TABLE, null);
    }

    // INSERT OR ADD VALUES IN GOALS TABLE
    public void insertGoalsData(String name, String goal, String tag) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.NAME, name);
        cv.put(Constants.GOALS, goal);
        cv.put(Constants.TAG, tag);
        this.getWritableDatabase().insertOrThrow(Constants.GOAL_TABLE, "", cv);
        this.getWritableDatabase().close();
    }

    // UPDATE VALUES IN GOALS TABLE
    public boolean updateGoalsData(int id, String name, String goal, String tag) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.NAME, name);
        cv.put(Constants.GOALS, goal);
        cv.put(Constants.TAG, tag);
        int result = this.getWritableDatabase()
                .update(Constants.GOAL_TABLE, cv, Constants.G_ID+" =?", new String[]{String.valueOf(id)});
        this.getWritableDatabase().close();
        return result > 0;
    }

    // RETRIEVE ALL DATA FROM GOALS TABLE
    public Cursor retrieveGoalsData() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM "+Constants.GOAL_TABLE, null);
    }

}
