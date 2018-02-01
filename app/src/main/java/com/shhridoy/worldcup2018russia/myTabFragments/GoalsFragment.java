package com.shhridoy.worldcup2018russia.myTabFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.GoalsListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;

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

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<GoalsListItems> listItemsTeams, listItemsPlayers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.goals_fragment, container, false);

        iniViews(rootView);

        listItemsTeams = new ArrayList<>();
        listItemsPlayers = new ArrayList<>();

        GoalsListItems itemT1 = new GoalsListItems("Brazil", "4");
        listItemsTeams.add(itemT1);
        GoalsListItems itemT2 = new GoalsListItems("Argentina", "4");
        listItemsTeams.add(itemT2);
        GoalsListItems itemT3 = new GoalsListItems("Germany", "3");
        listItemsTeams.add(itemT3);
        GoalsListItems itemT4 = new GoalsListItems("Spain", "1");
        listItemsTeams.add(itemT4);
        GoalsListItems itemT5 = new GoalsListItems("France", "1");
        listItemsTeams.add(itemT5);
        GoalsListItems itemT6 = new GoalsListItems("Russia", "1");
        listItemsTeams.add(itemT6);
        GoalsListItems itemT7 = new GoalsListItems("Portugal", "1");
        listItemsTeams.add(itemT7);

        GoalsListItems itemP1 = new GoalsListItems("Neymar", "3");
        listItemsPlayers.add(itemP1);
        GoalsListItems itemP2 = new GoalsListItems("Messi", "3");
        listItemsPlayers.add(itemP2);
        GoalsListItems itemP3 = new GoalsListItems("C. Ronaldo", "2");
        listItemsPlayers.add(itemP3);
        GoalsListItems itemP4 = new GoalsListItems("Aguaro", "1");
        listItemsPlayers.add(itemP4);

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
