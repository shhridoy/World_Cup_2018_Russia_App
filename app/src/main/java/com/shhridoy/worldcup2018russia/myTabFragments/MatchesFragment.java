package com.shhridoy.worldcup2018russia.myTabFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.Api;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MatchesListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class MatchesFragment extends Fragment {

    Spinner chooserSpinner;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<MatchesListItems> matchesListItems;
    ArrayAdapter<String> spinnerAdapter;
    String[] spinnerItems;
    static final String MY_DATA = "https://shhridoy.github.io/json/worldcup2018.js";
    static final String TEMP_DATA = "https://api.myjson.com/bins/196pel";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.matches_fragment, container, false);

        chooserSpinner = rootView.findViewById(R.id.RoundChooserSpiner);
        recyclerView = rootView.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesListItems = new ArrayList<>();

        spinnerItems = new String[]{"Round 1", "Round 2", "Round 3", "Round of 16", "Quarter-finals", "Semi-finals", "3rd Place Playoff", "Final"};
        spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.round_chooser_item, R.id.RoundChooserTV, spinnerItems);
        chooserSpinner.setAdapter(spinnerAdapter);
        chooserSpinner.setSelection(0);
        chooserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getContext(), "Choose "+spinnerItems[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //spinnerFontSize.setSelection(1);
            }
        });

        //loadRecyclerViewFromJson();
        RetrofitFunc();

        return rootView;
    }

    private void RetrofitFunc() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<MatchesListItems>> call = api.getMatches();

        call.enqueue(new Callback<List<MatchesListItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<MatchesListItems>> call, @NonNull retrofit2.Response<List<MatchesListItems>> response) {

                List<MatchesListItems> matches = response.body();

                matchesListItems.clear();

                for (MatchesListItems mat : matches) {
                    MatchesListItems list = new MatchesListItems(
                            mat.getDate(), mat.getRound(), mat.getTeam1(), mat.getTeam2(), mat.getScore()
                    );
                    matchesListItems.add(list);
                }
                adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(@NonNull Call<List<MatchesListItems>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("ERROR", t.getMessage());
            }
        });

    }

    private void loadRecyclerViewFromJson() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, TEMP_DATA,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Responses", Toast.LENGTH_LONG).show();

                        try {
                            matchesListItems.clear();

                            JSONObject jsonObject = new JSONObject("Matches");
                            JSONArray jsonArray = jsonObject.getJSONArray("Round1");
                            int LENGTH = jsonArray.length();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = (JSONObject) jsonArray.get(i);

                                String date = object.getString("date");
                                String round = object.getString("round");
                                String team1 = object.getString("team1");
                                String flagTeam1 = object.getString("flagTeam1");
                                String team2 = object.getString("team2");
                                String flagTeam2 = object.getString("flagTeam2");
                                String score = object.getString("score");

                                MatchesListItems list = new MatchesListItems(date, round, team1, team2, score);

                                matchesListItems.add(list);
                                adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
                                recyclerView.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.i("EROR", error.getMessage());
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
