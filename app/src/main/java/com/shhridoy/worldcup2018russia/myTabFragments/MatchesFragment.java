package com.shhridoy.worldcup2018russia.myTabFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MatchesListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    String ROUND;
    DatabaseHelper dbHelper;
    static boolean noData;

    boolean isSecondLinkFailed = false;

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
        dateItems = new String[]{"14 Jun - 19 Jun", "20 Jun - 24 Jun", "25 Jun - 29 Jun", "30 Jun - 04 Jul", "06 Jul - 08 Jul", "11 Jul - 12 Jul", "14 Jul", "15 Jul"};

        spinnerAdapter = new SpinnerAdapter(roundItems, dateItems, getContext());
        chooserSpinner.setAdapter(spinnerAdapter);

        setRoundSpinnerSelection();

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

        return rootView;
    }

    // INNER ADAPTER CLASS FOR SPINNER
    class SpinnerAdapter extends BaseAdapter {

        String[] rounds;
        String[] dates;
        Context context;

        SpinnerAdapter(String[] rounds, String[] dates, Context context) {
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

        if (!noData) {
            while (cursor.moveToNext()) {
                String round1 = cursor.getString(2);
                if (ROUND.equals(round1)) {
                    String id = cursor.getString(0);
                    String date = cursor.getString(1);
                    String round = cursor.getString(2);
                    String team1 = cursor.getString(3);
                    String team2 = cursor.getString(4);
                    String score = cursor.getString(5);
                    String details = cursor.getString(6);
                    MatchesListItems list = new MatchesListItems(id, date, round, team1, team2, score, details);
                    matchesListItems.add(list);
                }
            }
            adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
            recyclerView.setAdapter(adapter);
        }
    }

    private void saveMatchesData (String id, String date, String round, String team1, String team2, String score, String details) {
        boolean added = dbHelper.insertMatchesData(id, date, round, team1, team2, score, details);
        if (!added) {
            Toast.makeText(getContext(), "Data can't be added!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMatchesData(String id, String date, String round, String team1, String team2, String score, String details) {
        boolean updated = dbHelper.updateMatchesData(id, date, round, team1, team2, score, details);
        if (!updated) {
            Toast.makeText(getContext(), "Doesn't updated!", Toast.LENGTH_SHORT).show();
        }
    }

    // FUNCTION FOR RETRIEVE JSON DATA USING VOLLEY
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
                                String id = object.getString("id");
                                String date = object.getString("date");
                                String round = object.getString("round");
                                String team1 = object.getString("team1");
                                String team2 = object.getString("team2");
                                String score = object.getString("score");
                                String details = object.getString("details");
                                if (noData) {
                                    saveMatchesData(id, date, round, team1, team2, score, details);
                                } else {
                                    updateMatchesData(id, date, round, team1, team2, score, details);
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
                        Log.i("ERROR", error.getMessage());
                        //Toast.makeText(getContext(), "Error Occurs!!", Toast.LENGTH_LONG).show();
                        // SECOND URL FOR JSON PURSING USING VOLLEY
                        MATCHES_LINK = "https://jsonblob.com/api/270a9124-115b-11e8-8318-97970fcd3530";
                        if (!isSecondLinkFailed) {
                            retrieveDataFromJson();
                        }
                        isSecondLinkFailed = true;
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void setRoundSpinnerSelection() {
        Calendar c = Calendar.getInstance();
        int currYear = c.get(Calendar.YEAR); // like 2018
        int currMonth = c.get(Calendar.MONTH)+1; // count month from 0 to 11 (1, 2, 3 and so on)
        int currDay = c.get(Calendar.DATE); // like 1, 2, 3 and so on

        if (currDay <= 19 && currMonth <= 6 && currYear == 2018) {
            chooserSpinner.setSelection(0);
            ROUND = roundItems[0];
        } else if ((currDay >= 20 && currDay <= 24) && currMonth == 6 && currYear == 2018) {
            chooserSpinner.setSelection(1);
            ROUND = roundItems[1];
        } else if ((currDay >= 25 && currDay <= 29) && currMonth == 6 && currYear == 2018) {
            chooserSpinner.setSelection(2);
            ROUND = roundItems[2];
        } else if ((currDay >= 30 && currDay <= 4) && currMonth == 7 && currYear == 2018) {
            chooserSpinner.setSelection(3);
            ROUND = roundItems[3];
        } else if ((currDay >= 5 && currDay <= 8) && currMonth == 7 && currYear == 2018) {
            chooserSpinner.setSelection(4);
            ROUND = roundItems[4];
        } else if ((currDay >= 9 && currDay <= 12) && currMonth == 7 && currYear == 2018) {
            chooserSpinner.setSelection(5);
            ROUND = roundItems[5];
        } else if ((currDay >= 13 && currDay <= 14) && currMonth == 7 && currYear == 2018) {
            chooserSpinner.setSelection(6);
            ROUND = roundItems[6];
        } else {
            chooserSpinner.setSelection(7);
            ROUND = roundItems[7];
        }
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
}
