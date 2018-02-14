package com.shhridoy.worldcup2018russia.myRecyclerViewData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dream Land on 2/13/2018.
 */

public interface Api {

    //String BASE_URL = "https://shhridoy.github.io/json/worldcup2018/";
    //String BASE_URL = "https://api.myjson.com/bins/";
    String BASE_URL = "https://jsonblob.com/api/";

    //@GET("matches.json")
    //@GET("68z7p")
    @GET("270a9124-115b-11e8-8318-97970fcd3530")
    Call<List<MatchesListItems>> getMatches();

    //@GET("tables.json")
    //@GET("zd2p1")
    @GET("e2aa1d0a-115d-11e8-8318-b311bf439c9f")
    Call<List<TablesListItems>> getTables();

    //@GET("goals.json")
    //@GET("1eucf9")
    @GET("9460269c-115d-11e8-8318-811b17a0bdd7")
    Call<List<GoalsListItems>> getGoals();

}
