package com.shhridoy.worldcup2018russia.myNavFragments;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myDataBase.DatabaseHelper;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MatchesListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dream Land on 3/10/2018.
 */

public class YourTeamsFragment extends Fragment {

    LinearLayout ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.your_teams_fragment, container, false);

        ll = rootView.findViewById(R.id.LLYourTeams);

        String[] arr1 = {"Brazil", "Argentina", "Germany"};
        String[] arr2 = {"1","2","3","4","5", "6", "7", "8", "9"};

        for (String s : arr1) {
            retrieveData(s);
        }

        return rootView;
    }

    private void retrieveData(String name) {
        TextView textView = new TextView(getContext());
        textView.setText(name);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(17);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(5, 5, 5, 5);
        textView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        ll.addView(textView);
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            Cursor cursor = databaseHelper.retrieveMatchesData();
            while (cursor.moveToNext()) {
                String team1 = cursor.getString(3);
                String team2 = cursor.getString(4);
                if (name.equals(team1) || name.equals(team2)) {
                    String id = cursor.getString(0);
                    String date = cursor.getString(1);
                    String round = cursor.getString(2);
                    String score = cursor.getString(5);
                    String details = cursor.getString(6);
                    /*TextView tv = new TextView(getContext());
                    tv.setText(round+"\n"+date);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setPadding(5, 5, 5, 5);
                    ll.addView(tv);*/
                    RecyclerView recyclerView = new RecyclerView(getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    MatchesListItems list = new MatchesListItems(id, date, round, team1, team2, score, details);
                    List<MatchesListItems> matchesListItems = new ArrayList<>();
                    matchesListItems.add(list);
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
                    recyclerView.setAdapter(adapter);
                    ll.addView(recyclerView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
