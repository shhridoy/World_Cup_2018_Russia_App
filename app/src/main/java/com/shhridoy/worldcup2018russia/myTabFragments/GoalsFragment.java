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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.shhridoy.worldcup2018russia.myRecyclerViewData.GoalsListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class GoalsFragment extends Fragment implements View.OnClickListener{

    RadioGroup radioGroup;
    RadioButton rb1, rb2;
    TextView tvNameTitle, tvGoalTitle;
    LinearLayout ll1, ll2;

    TextView tvTip, tvNotice;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<GoalsListItems> listItemsTeams, listItemsPlayers;

    DatabaseHelper dbHelper;
    static boolean noData;

    boolean isSecondLinkFailed = false;

    // FIRST URL FOR JSON PURSING USING VOLLEY
    static String GOALS_LINK = "https://shhridoy.github.io/json/worldcup2018/goals.json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.goals_fragment, container, false);

        iniViews(rootView);

        listItemsTeams = new ArrayList<>();
        listItemsPlayers = new ArrayList<>();

        dbHelper = new DatabaseHelper(getContext());
        noData = dbHelper.retrieveGoalsData().getCount() == 0;

        populateRecyclerViewFromDB();

        if (isInternetOn()) {
            retrieveDataFromJson();
            tvTip.setVisibility(View.INVISIBLE);
        } else {
            tvTip.setVisibility(View.VISIBLE);
            //Toast.makeText(getContext(), "Please Check Internet Connection!!", Toast.LENGTH_SHORT).show();
        }

        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        rb1.setChecked(true);

        if (rb1.isChecked()) {
            tvNameTitle.setText("Teams Name");
            adapter = new RecyclerViewAdapter("Goals", listItemsTeams, getContext());
            rb1.setChecked(true);
            rb2.setChecked(false);
        } else {
            tvNameTitle.setText("Players Name");
            adapter = new RecyclerViewAdapter("Goals", listItemsPlayers, getContext());
            rb1.setChecked(false);
            rb2.setChecked(true);
        }
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton1:
            case R.id.LL1:
                tvNameTitle.setText("Teams Name");
                rb1.setChecked(true);
                rb2.setChecked(false);
                adapter = new RecyclerViewAdapter("Goals", listItemsTeams, getContext());
                recyclerView.setAdapter(adapter);
                break;
            case R.id.radioButton2:
            case R.id.LL2:
                tvNameTitle.setText("Players Name");
                rb1.setChecked(false);
                rb2.setChecked(true);
                adapter = new RecyclerViewAdapter("Goals", listItemsPlayers, getContext());
                recyclerView.setAdapter(adapter);
                break;
        }
    }

    private void iniViews(View v) {
        radioGroup = v.findViewById(R.id.radioGroup);
        rb1 = v.findViewById(R.id.radioButton1);
        rb2 = v.findViewById(R.id.radioButton2);
        ll1 = v.findViewById(R.id.LL1);
        ll2 = v.findViewById(R.id.LL2);
        tvNameTitle = v.findViewById(R.id.tv_name_title);
        tvGoalTitle = v.findViewById(R.id.tv_goals_title);

        tvTip = v.findViewById(R.id.TVTip);
        tvNotice = v.findViewById(R.id.TVNotice);

        recyclerView = v.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void populateRecyclerViewFromDB() {
        Cursor cursor = dbHelper.retrieveGoalsData();
        listItemsTeams.clear();
        listItemsPlayers.clear();
        noData = cursor.getCount() == 0;

        if (noData) {
            tvNotice.setVisibility(View.VISIBLE);
        } else {
            tvNotice.setVisibility(View.INVISIBLE);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String goal = cursor.getString(2);
                String tag = cursor.getString(3);
                GoalsListItems goalsListItems = new GoalsListItems(id, name, goal, tag);
                String[] tagSplit = tag.split(" ");
                if (tagSplit[0].equals("P")) {
                    listItemsPlayers.add(goalsListItems);
                } else {
                    listItemsTeams.add(goalsListItems);
                }
            }
            adapter = new RecyclerViewAdapter("Goals", listItemsTeams, getContext());
            recyclerView.setAdapter(adapter);
        }
    }

    private void saveGoalsData (String name, String goal, String tag) {
        boolean added = dbHelper.insertGoalsData(name, goal, tag);
        if (!added) {
            Toast.makeText(getContext(), "Data can't be added!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGoalsData(int id, String name, String goal, String tag) {
        boolean updated = dbHelper.updateGoalsData(id, name, goal, tag);
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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GOALS_LINK,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = (JSONObject) jsonArray.get(i);
                                String name = object.getString("name");
                                String goal = object.getString("goal");
                                String tag = object.getString("tag");
                                if (noData) {
                                    saveGoalsData(name, goal, tag);
                                } else {
                                    if (dbHelper.isExistsInGoals(name)) {
                                        updateGoalsData(getId(name), name, goal, tag);
                                    } else {
                                        saveGoalsData(name, goal, tag);
                                    }
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
                        GOALS_LINK = "https://jsonblob.com/api/9460269c-115d-11e8-8318-811b17a0bdd7";
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

    private int getId(String name) {
        Cursor cursor = dbHelper.retrieveGoalsData();
        int id = 0;
        while (cursor.moveToNext()) {
            if (name.equals(cursor.getString(1))) {
                id = cursor.getInt(0);
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
}
