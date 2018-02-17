package com.shhridoy.worldcup2018russia.myTabFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
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
import com.shhridoy.worldcup2018russia.myDataBase.DatabaseHelper;
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

    // FIRST URL FOR JSON PURSING USING VOLLEY
    static String MATCHES_LINK = "https://shhridoy.github.io/json/worldcup2018/matches.json";

    boolean isDataSynced;
    boolean isLinkFailed = false;
    String ROUND;
    DatabaseHelper dbHelper;
    static boolean noData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.matches_fragment, container, false);

        chooserSpinner = rootView.findViewById(R.id.RoundChooserSpiner);
        recyclerView = rootView.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        matchesListItems = new ArrayList<>();

        dbHelper = new DatabaseHelper(getContext());
        noData = dbHelper.retrieveMatchesData().getCount() == 0;

        roundItems = new String[]{"Round 1", "Round 2", "Round 3", "Round of 16", "Quarter-finals", "Semi-finals", "3rd Place Playoff", "Final"};
        dateItems = new String[]{"14 Jun - 19 Jun", "19 Jun - 24 Jun", "25 Jun - 28 Jun", "30 Jun - 03 Jul", "06 Jul - 07 Jul", "10 Jul - 11 Jul", "14 Jul", "15 Jul"};

        spinnerAdapter = new SpinnerAdapter(roundItems, dateItems, getContext());
        chooserSpinner.setAdapter(spinnerAdapter);
        chooserSpinner.setSelection(0);

        ROUND = roundItems[0];

        chooserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ROUND = roundItems[position];
                populateRecyclerViewFromDB();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //spinnerFontSize.setSelection(1);
            }
        });

        populateRecyclerViewFromDB();

        if (isInternetOn()) {
            retrieveDataFromJson();
        } else {
            Toast.makeText(getContext(), "Please Check Internet Connection!!", Toast.LENGTH_SHORT).show();
        }

        //retrieveJsonData();

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


    private void populateRecyclerViewFromDB() {
        Cursor cursor = dbHelper.retrieveMatchesData();
        matchesListItems.clear();
        noData = cursor.getCount() == 0;

        if (noData) {
            Toast.makeText(getContext(), "Data doesn't sync yet!!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String round = cursor.getString(2);
                if (ROUND.equals(round)) {
                    int id = cursor.getInt(0);
                    String date = cursor.getString(1);
                    String team1 = cursor.getString(3);
                    String team2 = cursor.getString(4);
                    String score = cursor.getString(5);
                    MatchesListItems list = new MatchesListItems(id, date, round, team1, team2, score);
                    matchesListItems.add(list);
                }
            }
            adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
            recyclerView.setAdapter(adapter);
        }
    }

    private void saveMatchesData (String date, String round, String team1, String team2, String score) {
        boolean added = dbHelper.insertMatchesData(date, round, team1, team2, score);
        if (!added) {
            Toast.makeText(getContext(), "Data can't be added!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMatchesData(int id, String date, String round, String team1, String team2, String score) {
        boolean updated = dbHelper.updateMatchesData(id, date, round, team1, team2, score);
        if (!updated) {
            Toast.makeText(getContext(), "Doesn't updated!", Toast.LENGTH_SHORT).show();
        }
    }

    // FUNCTION FOR RETRIEVE JSON DATA USING VOLLEY (NOT USED)
    private void retrieveDataFromJson() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data....");
        progressDialog.setCancelable(false);
        //progressDialog.show();
        //ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MATCHES_LINK,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = (JSONObject) jsonArray.get(i);
                                String date = object.getString("date");
                                String round = object.getString("round");
                                String team1 = object.getString("team1");
                                //String flagTeam1 = object.getString("flagTeam1");
                                String team2 = object.getString("team2");
                                //String flagTeam2 = object.getString("flagTeam2");
                                String score = object.getString("score");
                                if (noData) {
                                    saveMatchesData(date, round, team1, team2, score);
                                } else {
                                    updateMatchesData(i+1, date, round, team1, team2, score);
                                }
                            }

                            populateRecyclerViewFromDB();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Exception arises!!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.i("EROR", error.getMessage());
                        Toast.makeText(getContext(), "Error Occurs!!", Toast.LENGTH_LONG).show();
                        // SECOND URL FOR JSON PURSING USING VOLLEY
                        MATCHES_LINK = "https://jsonblob.com/api/270a9124-115b-11e8-8318-97970fcd3530";
                        retrieveDataFromJson();
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private int getId(String date, String round, String team1, String team2) {
        int id = 0;
        while (dbHelper.retrieveMatchesData().moveToNext()) {
            if (date.equals(dbHelper.retrieveMatchesData().getString(1)) &&
                    round.equals(dbHelper.retrieveMatchesData().getString(2)) &&
                    team1.equals(dbHelper.retrieveMatchesData().getString(3)) &&
                    team2.equals(dbHelper.retrieveMatchesData().getString(4))) {
                id = dbHelper.retrieveMatchesData().getInt(0);
                break;
            }
        }
        return id;
    }

    private boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        assert connec != null;
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            return false;
        }

        return false;
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

        call.enqueue(new Callback<List<MatchesListItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<MatchesListItems>> call, @NonNull retrofit2.Response<List<MatchesListItems>> response) {

                List<MatchesListItems> matches = response.body();
                matchesListItems.clear();

                if (matches != null) {
                    for (MatchesListItems mat : matches) {
                        if (ROUND.equals(mat.getRound())) {
                            MatchesListItems listItems = new MatchesListItems(
                                    mat.getDate(), mat.getRound(),
                                    mat.getTeam1(), mat.getTeam2(), mat.getScore()
                            );
                            matchesListItems.add(listItems);
                        }
                    }
                }

                adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<MatchesListItems>> call, @NonNull Throwable t) {
                Log.i("ERROR", t.getMessage());
                Toast.makeText(getContext(), "Server access failed!", Toast.LENGTH_SHORT).show();
                isLinkFailed = true;
                retrieveJsonData();
            }
        });

    }
}
