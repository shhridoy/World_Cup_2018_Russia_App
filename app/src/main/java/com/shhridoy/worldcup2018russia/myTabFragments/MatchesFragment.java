package com.shhridoy.worldcup2018russia.myTabFragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myRetrofitApi.Api;
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
    SpinnerAdapter spinnerAdapter;
    String[] roundItems, dateItems;

    // URL FOR JSON PURSING USING VOLLEY
    static final String MY_DATA = "https://shhridoy.github.io/json/worldcup2018.js";

    boolean isDataSynced = false;
    boolean isLinkFailed;
    String round;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.matches_fragment, container, false);

        chooserSpinner = rootView.findViewById(R.id.RoundChooserSpiner);
        recyclerView = rootView.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        matchesListItems = new ArrayList<>();

        isLinkFailed = false;

        roundItems = new String[]{"Round 1", "Round 2", "Round 3", "Round of 16", "Quarter-finals", "Semi-finals", "3rd Place Playoff", "Final"};
        dateItems = new String[]{"14 Jun - 19 Jun", "19 Jun - 24 Jun", "25 Jun - 28 Jun", "30 Jun - 03 Jul", "06 Jul - 07 Jul", "10 Jul - 11 Jul", "14 Jul", "15 Jul"};

        spinnerAdapter = new SpinnerAdapter(roundItems, dateItems, getContext());
        chooserSpinner.setAdapter(spinnerAdapter);
        chooserSpinner.setSelection(0);
        round = roundItems[0];

        chooserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                round = roundItems[position];
                retrieveJsonData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //spinnerFontSize.setSelection(1);
            }
        });

        if (!isDataSynced) {
            retrieveJsonData();
        } else {
            adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
            recyclerView.setAdapter(adapter);
        }

        return rootView;
    }

    // INNER ADAPTER CLASS FOR SPINNER
    class SpinnerAdapter extends BaseAdapter {

        String[] rounds;
        String[] dates;
        Context context;

        public SpinnerAdapter(String[] rounds, String[] dates, Context context) {
            this.rounds = rounds;
            this.dates = dates;
            this.context = context;
        }

        @Override
        public int getCount() {
            return rounds.length;
        }

        @Override
        public Object getItem(int i) {
            return rounds[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(R.layout.round_chooser_item, viewGroup, false);
            }

            TextView tvRound = view.findViewById(R.id.RoundChooserTV);
            TextView tvDate = view.findViewById(R.id.RoundChooserTVDate);

            tvRound.setText(rounds[i]);
            tvDate.setText(dates[i]);

            return view;
        }
    }

    // FUNCTION FOR RETRIEVE THE JSON DATA USING RETROFIT
    private void retrieveJsonData() {

        //ProgressBar progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);

        Retrofit retrofit;
        Api api;
        Call<List<MatchesListItems>> call;
        if (isLinkFailed) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(Api.class);

            call = api.getMatches2();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(Api.class);

            call = api.getMatches1();
        }

        isDataSynced = false;

        call.enqueue(new Callback<List<MatchesListItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<MatchesListItems>> call, @NonNull retrofit2.Response<List<MatchesListItems>> response) {

                List<MatchesListItems> matches = response.body();

                matchesListItems.clear();

                if (matches != null) {
                    for (MatchesListItems mat : matches) {
                        if (round.equals(mat.getRound())) {
                            MatchesListItems list = new MatchesListItems(
                                    mat.getDate(), mat.getRound(),
                                    mat.getTeam1(), mat.getFlagTeam1(),
                                    mat.getTeam2(), mat.getFlagTeam2(),
                                    mat.getScore()
                            );
                            matchesListItems.add(list);
                        }
                    }
                }
                adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
                recyclerView.setAdapter(adapter);
                isDataSynced = true;
            }

            @Override
            public void onFailure(@NonNull Call<List<MatchesListItems>> call, @NonNull Throwable t) {
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("ERROR", t.getMessage());

                Toast.makeText(getContext(), "Server access failed!", Toast.LENGTH_SHORT).show();

                isLinkFailed = true;
                retrieveJsonData();
            }
        });

    }

    // FUNCTION FOR RETRIEVE JSON DATA USING VOLLEY (NOT USED)
    private void loadRecyclerViewFromJson() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MY_DATA,

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
