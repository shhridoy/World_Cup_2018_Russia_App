package com.shhridoy.worldcup2018russia.myTabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MatchesListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

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

        MatchesListItems item1 = new MatchesListItems("Thu 14.06.18 21:00", "Round 1", "Russia", "Saudi Arab", "1 : 1");
        matchesListItems.add(item1);

        MatchesListItems item2 = new MatchesListItems("Fri 15.06.18 18:00", "Round 1", "Egypt", "Uruguay", "0 : 2");
        matchesListItems.add(item2);

        MatchesListItems item3 = new MatchesListItems("Fri 15.06.18 21:00", "Round 1", "Morocco", "Iran", "1 : 0");
        matchesListItems.add(item3);

        MatchesListItems item4 = new MatchesListItems("Sat 16.06.18 00:00", "Round 1", "Portugal", "Spain", "2 : 2");
        matchesListItems.add(item4);

        MatchesListItems item5 = new MatchesListItems("Sat 16.06.18 16:00", "Round 1", "France", "Australia", "- : -");
        matchesListItems.add(item5);

        MatchesListItems item6 = new MatchesListItems("Sat 16.06.18 19:00", "Round 1", "Argentina", "Iceland", "- : -");
        matchesListItems.add(item6);

        MatchesListItems item7 = new MatchesListItems("Sat 16.06.18 22:00", "Round 1", "Peru", "Denmark", "- : -");
        matchesListItems.add(item7);

        adapter = new RecyclerViewAdapter(matchesListItems, getContext(), "Matches");
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
