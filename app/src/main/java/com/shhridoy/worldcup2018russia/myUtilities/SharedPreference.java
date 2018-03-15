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

    public static final String PREFS_NAME = "NKDROID_APP";
    public static final String FAVORITES = "Favorite";

    public SharedPreference () {
        super();
    }

    public void storeFavorites (Context context, List favorites) {

        // used for store arrayList in json format
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.apply();
    }

    public ArrayList loadFavorites(Context context) {
        // used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        List favorites;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList(favorites);
        } else
            return null;
        return (ArrayList) favorites;
    }

    public void addFavorite(Context context, String str) {
        List favorites = loadFavorites(context);
        if (favorites == null) {
            favorites = new ArrayList();
        }
        if (!favorites.contains(str)) {
            favorites.add(str);
        }
        storeFavorites(context, favorites);
    }

    public void removeFavorite(Context context, String str) {
        ArrayList favorites = loadFavorites(context);
        if (favorites != null) {
            favorites.remove(str);
            storeFavorites(context, favorites);
        }
    }
}
