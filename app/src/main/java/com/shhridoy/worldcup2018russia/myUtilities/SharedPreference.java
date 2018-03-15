package com.shhridoy.worldcup2018russia.myUtilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dream Land on 3/15/2018.
 */

public class SharedPreference {

    public static final String PREFS_NAME = "RUSSIA_CUP";
    public static final String MY_TEAMS = "My_teams";

    public SharedPreference () {
        super();
    }

    public void storeMyTeams(Context context, List teams) {

        // used for store arrayList in json format
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonTeams = gson.toJson(teams);
        editor.putString(MY_TEAMS, jsonTeams);
        editor.apply();
    }

    public ArrayList loadMyTeams(Context context) {
        // used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        List teams;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(MY_TEAMS)) {
            String jsonFavorites = settings.getString(MY_TEAMS, null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);
            teams = Arrays.asList(favoriteItems);
            teams = new ArrayList(teams);
        } else {
            return null;
        }
        return (ArrayList) teams;
    }

    public void addMyTeams(Context context, String str) {
        List teams = loadMyTeams(context);
        if (teams == null) {
            teams = new ArrayList();
        }
        if (!teams.contains(str)) {
            teams.add(str);
        }
        storeMyTeams(context, teams);
    }

    public void removeMyTeams(Context context, String str) {
        ArrayList teams = loadMyTeams(context);
        if (teams != null) {
            teams.remove(str);
            storeMyTeams(context, teams);
        }
    }
}
