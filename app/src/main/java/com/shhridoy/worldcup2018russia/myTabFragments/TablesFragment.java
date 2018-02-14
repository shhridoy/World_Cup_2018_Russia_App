package com.shhridoy.worldcup2018russia.myTabFragments;

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

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.Api;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.GoalsListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.TablesListItems;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tables_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listItems = new ArrayList<>();

        if (!isDataSynced) {
            retrofitDataPopulationFunc();
        } else {
            adapter = new RecyclerViewAdapter(getContext(), listItems, "Tables");
            recyclerView.setAdapter(adapter);
        }

        return rootView;
    }

    private void retrofitDataPopulationFunc() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        isDataSynced = false;

        Api api = retrofit.create(Api.class);

        Call<List<TablesListItems>> call = api.getTables();

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
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("ERROR", t.getMessage());
                isDataSynced = false;
            }
        });

    }
}
