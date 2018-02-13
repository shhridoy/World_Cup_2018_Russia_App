package com.shhridoy.worldcup2018russia.myRecyclerViewData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dream Land on 2/13/2018.
 */

public interface Api {

    //String BASE_URL = "https://shhridoy.github.io/json/";
    String BASE_URL = "https://api.myjson.com/bins/";

    //@GET("worldcup2018.js")
    @GET("hmc3h")
    Call<List<MatchesListItems>> getMatches();

}
