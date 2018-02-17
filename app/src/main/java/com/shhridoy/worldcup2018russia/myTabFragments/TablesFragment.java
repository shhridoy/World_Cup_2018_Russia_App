package com.shhridoy.worldcup2018russia.myTabFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
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
import com.shhridoy.worldcup2018russia.myRetrofitApi.Api;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.TablesListItems;

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

public class TablesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<TablesListItems> listItems;
    boolean isDataSynced = false;
    boolean isLinkFailed;

    DatabaseHelper dbHelper;
    static boolean noData;

    // FIRST URL FOR JSON PURSING USING VOLLEY
    static String TABLES_LINK = "https://shhridoy.github.io/json/worldcup2018/tables.json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tables_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listItems = new ArrayList<>();
        isLinkFailed = false;

        //dbHelper = new DatabaseHelper(getContext());
        //noData = dbHelper.retrievePointsData().getCount() == 0;

        //populateRecyclerViewFromDB();

        if (isInternetOn()) {
            retrieveDataFromJson();
        } else {
            Toast.makeText(getContext(), "Please Check Internet Connection!!", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void populateRecyclerViewFromDB() {
        Cursor cursor = dbHelper.retrievePointsData();
        listItems.clear();
        noData = cursor.getCount() == 0;

        if (noData) {
            Toast.makeText(getContext(), "Data doesn't sync yet!!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String group = cursor.getString(1);
                String teamNo = cursor.getString(2);
                String teamName = cursor.getString(3);
                String status = cursor.getString(4);
                TablesListItems tablesListItems = new TablesListItems(id, group, teamNo, teamName, status);
                listItems.add(tablesListItems);
            }
            adapter = new RecyclerViewAdapter(getContext(), listItems, "Tables");
            recyclerView.setAdapter(adapter);
        }
    }

    private void saveTablesData (String group, String teamNo, String teamName, String status) {
        boolean added = dbHelper.insertPointsData(group, teamNo, teamName, status);
        if (!added) {
            Toast.makeText(getContext(), "Data can't be added!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatesTablesData(int id, String group, String teamNo, String teamName, String status) {
        boolean updated = dbHelper.updatePointsData(id, group, teamNo, teamName, status);
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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, TABLES_LINK,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        listItems.clear();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = (JSONObject) jsonArray.get(i);
                                String group = object.getString("group");
                                String teamNo = object.getString("teamNo");
                                String teamName = object.getString("teamName");
                                String status = object.getString("status");

                                TablesListItems tablesListItems = new TablesListItems(i+1, group, teamNo, teamName, status);
                                listItems.add(tablesListItems);

                                /*if (noData) {
                                    saveTablesData(group, teamNo, teamName, status);
                                } else {
                                    updatesTablesData(i+1, group, teamNo, teamName, status);
                                }*/
                            }

                            //populateRecyclerViewFromDB();
                            adapter = new RecyclerViewAdapter(getContext(), listItems, "Tables");
                            recyclerView.setAdapter(adapter);

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
                        TABLES_LINK = "https://jsonblob.com/api/e2aa1d0a-115d-11e8-8318-b311bf439c9f";
                        retrieveDataFromJson();
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
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

    private void retrieveJsonData() {
        Retrofit retrofit;
        Api api;
        Call<List<TablesListItems>> call;
        if (isLinkFailed) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(Api.class);

            call = api.getTables2();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(Api.class);

            call = api.getTables1();
        }

        call.enqueue(new Callback<List<TablesListItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<TablesListItems>> call, @NonNull retrofit2.Response<List<TablesListItems>> response) {

                List<TablesListItems> tables = response.body();

                listItems.clear();

                if (tables != null) {
                    for (TablesListItems table : tables) {
                        TablesListItems list = new TablesListItems(
                                table.getGroup(), table.getTeamNo(),
                                table.getTeamName(), table.getFlagLink(),
                                table.getStatus()
                        );
                        listItems.add(list);
                    }
                }
                adapter = new RecyclerViewAdapter(getContext(), listItems, "Tables");
                recyclerView.setAdapter(adapter);
                isDataSynced = true;
            }

            @Override
            public void onFailure(@NonNull Call<List<TablesListItems>> call, @NonNull Throwable t) {
                Log.i("ERROR", t.getMessage());

                Toast.makeText(getContext(), "Server access failed!", Toast.LENGTH_SHORT).show();

                isDataSynced = false;
                isLinkFailed = true;
                retrieveJsonData();
            }
        });

    }
}
