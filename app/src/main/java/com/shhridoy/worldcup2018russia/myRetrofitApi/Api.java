package com.shhridoy.worldcup2018russia.myRetrofitApi;

import com.shhridoy.worldcup2018russia.myRecyclerViewData.GoalsListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MatchesListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.TablesListItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dream Land on 2/13/2018.
 */

public interface Api {

    String BASE_URL1 = "https://shhridoy.github.io/json/worldcup2018/";
    String BASE_URL2 = "https://jsonblob.com/api/";

    @GET("matches.json")
    Call<List<MatchesListItems>> getMatches1();

    @GET("270a9124-115b-11e8-8318-97970fcd3530")
    Call<List<MatchesListItems>> getMatches2();

    @GET("tables.json")
    Call<List<TablesListItems>> getTables1();

    @GET("e2aa1d0a-115d-11e8-8318-b311bf439c9f")
    Call<List<TablesListItems>> getTables2();

    @GET("goals.json")
    Call<List<GoalsListItems>> getGoals1();

    @GET("9460269c-115d-11e8-8318-811b17a0bdd7")
    Call<List<GoalsListItems>> getGoals2();

}
