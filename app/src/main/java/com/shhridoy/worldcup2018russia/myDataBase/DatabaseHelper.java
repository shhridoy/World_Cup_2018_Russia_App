package com.shhridoy.worldcup2018russia.myDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dream Land on 2/15/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(Constants.CREATE_MATCHES_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("MATCHES TABLE ERROR", e.getMessage());
        }
        try {
            sqLiteDatabase.execSQL(Constants.CREATE_POINTS_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("POINTS TABLE ERROR", e.getMessage());
        }
        try {
            sqLiteDatabase.execSQL(Constants.CREATE_GOAL_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("GOAL TABLE ERROR", e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Constants.DROP_MATCHES_TABLE);
        sqLiteDatabase.execSQL(Constants.DROP_POINTS_TABLE);
        sqLiteDatabase.execSQL(Constants.DROP_GOAL_TABLE);
        onCreate(sqLiteDatabase);
    }

    // INSERT VALUES IN MATCHES TABLE
    public boolean insertMatchesData (String date, String round, String team1, String team2, String score) {

        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.DATE, date);
            cv.put(Constants.ROUND, round);
            cv.put(Constants.TEAM1, team1);
            cv.put(Constants.TEAM2, team2);
            cv.put(Constants.SCORE, score);
            long result  = this.getWritableDatabase().insert(Constants.MATCHES_TABLE, Constants.M_ID, cv);
            this.getWritableDatabase().close();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE VALUES IN MATCHES TABLE
    public boolean updateMatchesData (int id, String date, String round, String team1, String team2, String score) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.DATE, date);
            cv.put(Constants.ROUND, round);
            cv.put(Constants.TEAM1, team1);
            cv.put(Constants.TEAM2, team2);
            cv.put(Constants.SCORE, score);
            int result = this.getWritableDatabase()
                    .update(Constants.MATCHES_TABLE, cv, Constants.M_ID+" =?", new String[]{String.valueOf(id)});
            this.getWritableDatabase().close();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // RETRIEVE DATA FROM MATCHES TABLE
    public Cursor retrieveMatchesData () {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + Constants.MATCHES_TABLE, null);
    }

    // INSERT VALUES IN POINTS TABLE
    public boolean insertPointsData (String group, String teamNo, String teamName, String status) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.GROUP_NO, group);
            cv.put(Constants.TEAM_NO, teamNo);
            cv.put(Constants.TEAM_NAME, teamName);
            cv.put(Constants.STATUS, status);
            long result = this.getWritableDatabase().insert(Constants.POINT_TABLE, Constants.P_ID, cv);
            this.getWritableDatabase().close();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE VALUES IN POINTS TABLE
    public boolean updatePointsData(int id, String group, String teamNo, String teamName, String status) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.GROUP_NO, group);
            cv.put(Constants.TEAM_NO, teamNo);
            cv.put(Constants.TEAM_NAME, teamName);
            cv.put(Constants.STATUS, status);
            int result = this.getWritableDatabase()
                    .update(Constants.POINT_TABLE, cv, Constants.P_ID+" =?", new String[]{String.valueOf(id)});
            this.getWritableDatabase().close();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // RETRIEVE DATA FROM POINTS TABLE
    public Cursor retrievePointsData() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + Constants.POINT_TABLE, null);
    }

    // INSERT OR ADD VALUES IN GOALS TABLE
    public boolean insertGoalsData(String name, String goal, String tag) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, name);
            cv.put(Constants.GOALS, goal);
            cv.put(Constants.TAG, tag);
            long result = this.getWritableDatabase().insert(Constants.GOAL_TABLE, Constants.G_ID, cv);
            this.getWritableDatabase().close();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE VALUES IN GOALS TABLE
    public boolean updateGoalsData(int id, String name, String goal, String tag) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, name);
            cv.put(Constants.GOALS, goal);
            cv.put(Constants.TAG, tag);
            int result = this.getWritableDatabase()
                    .update(Constants.GOAL_TABLE, cv, Constants.G_ID+" =?", new String[]{String.valueOf(id)});
            this.getWritableDatabase().close();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // RETRIEVE ALL DATA FROM GOALS TABLE
    public Cursor retrieveGoalsData() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM "+Constants.GOAL_TABLE, null);
    }

}
