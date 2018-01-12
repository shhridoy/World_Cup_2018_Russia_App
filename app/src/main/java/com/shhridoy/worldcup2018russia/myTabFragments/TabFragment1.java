package com.shhridoy.worldcup2018russia.myTabFragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.ListItems;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class TabFragment1 extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ListItems> listItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_1, container, false);

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listItems = new ArrayList<>();

        ListItems item1 = new ListItems("Thu 14.06.18 21:00", "Round 1", "Russia", "Saudi Arab", "1 : 1");
        listItems.add(item1);

        ListItems item2 = new ListItems("Fri 15.06.18 18:00", "Round 1", "Egypt", "Uruguay", "0 : 2");
        listItems.add(item2);

        ListItems item3 = new ListItems("Fri 15.06.18 21:00", "Round 1", "Morocco", "Iran", "1 : 0");
        listItems.add(item3);

        ListItems item4 = new ListItems("Sat 16.06.18 00:00", "Round 1", "Portugal", "Spain", "2 : 2");
        listItems.add(item4);

        ListItems item5 = new ListItems("Sat 16.06.18 16:00", "Round 1", "France", "Australia", "- : -");
        listItems.add(item5);

        ListItems item6 = new ListItems("Sat 16.06.18 19:00", "Round 1", "Argentina", "Iceland", "- : -");
        listItems.add(item6);

        ListItems item7 = new ListItems("Sat 16.06.18 22:00", "Round 1", "Peru", "Denmark", "- : -");
        listItems.add(item7);

        adapter = new MyAdapter(listItems, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));

        return rootView;
    }
}
