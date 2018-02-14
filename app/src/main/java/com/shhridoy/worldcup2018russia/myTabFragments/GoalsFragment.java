package com.shhridoy.worldcup2018russia.myTabFragments;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.Api;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.GoalsListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MatchesListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class GoalsFragment extends Fragment implements View.OnClickListener{

    RadioGroup radioGroup;
    RadioButton rb1, rb2;
    TextView tvNameTitle, tvGoalTitle;
    LinearLayout ll1, ll2;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<GoalsListItems> listItemsTeams, listItemsPlayers;
    boolean isDataSynced =false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.goals_fragment, container, false);

        iniViews(rootView);

        listItemsTeams = new ArrayList<>();
        listItemsPlayers = new ArrayList<>();


        if (!isDataSynced) {
            retrofitDataPopulationFunc();
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
                Toast.makeText(getContext(), rb1.getText().toString(), Toast.LENGTH_LONG).show();
                tvNameTitle.setText("Teams Name");
                rb1.setChecked(true);
                rb2.setChecked(false);
                adapter = new RecyclerViewAdapter("Goals", listItemsTeams, getContext());
                recyclerView.setAdapter(adapter);
                break;
            case R.id.radioButton2:
            case R.id.LL2:
                Toast.makeText(getContext(), rb2.getText().toString(), Toast.LENGTH_LONG).show();
                tvNameTitle.setText("Players Name");
                rb1.setChecked(false);
                rb2.setChecked(true);
                adapter = new RecyclerViewAdapter("Goals", listItemsPlayers, getContext());
                recyclerView.setAdapter(adapter);
                break;
        }
    }

    private void retrofitDataPopulationFunc() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        isDataSynced = false;
        Api api = retrofit.create(Api.class);

        Call<List<GoalsListItems>> call = api.getGoals();

        call.enqueue(new Callback<List<GoalsListItems>>() {
            @Override
            public void onResponse(@NonNull Call<List<GoalsListItems>> call, @NonNull retrofit2.Response<List<GoalsListItems>> response) {

                List<GoalsListItems> goals = response.body();

                listItemsTeams.clear();
                listItemsPlayers.clear();

                if (goals != null) {
                    for (GoalsListItems goal : goals) {
                        GoalsListItems list = new GoalsListItems(
                                goal.getFlagLink(),
                                goal.getName(),
                                goal.getGoal(),
                                goal.getTag()
                        );
                        if (goal.getTag().equals("P")) {
                            listItemsPlayers.add(list);
                        } else {
                            listItemsTeams.add(list);
                        }
                    }
                }
                adapter = new RecyclerViewAdapter("Goals", listItemsTeams, getContext());
                recyclerView.setAdapter(adapter);
                isDataSynced = true;
            }

            @Override
            public void onFailure(@NonNull Call<List<GoalsListItems>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("ERROR", t.getMessage());
            }
        });

    }

    private void iniViews(View v) {
        radioGroup = v.findViewById(R.id.radioGroup);
        rb1 = v.findViewById(R.id.radioButton1);
        rb2 = v.findViewById(R.id.radioButton2);
        ll1 = v.findViewById(R.id.LL1);
        ll2 = v.findViewById(R.id.LL2);
        tvNameTitle = v.findViewById(R.id.tv_name_title);
        tvGoalTitle = v.findViewById(R.id.tv_goals_title);

        recyclerView = v.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
