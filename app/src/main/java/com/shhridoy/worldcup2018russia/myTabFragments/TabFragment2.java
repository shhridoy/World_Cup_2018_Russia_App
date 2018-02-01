package com.shhridoy.worldcup2018russia.myTabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.MyAdapter;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.TablesListItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class TabFragment2 extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<TablesListItems> listItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_2, container, false);

        recyclerView = rootView.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listItems = new ArrayList<>();

        TablesListItems item1 = new TablesListItems(
                "Group A", "1. 2. 3. 4.", "Russia, Saudi Arabia, Egypt, Uruguay",
                "1 0 2.1, 1 2 0.5, 1 -2 0.5, 1 3 +0.1"
        );
        listItems.add(item1);

        TablesListItems item2 = new TablesListItems(
                "Group B", "1. 2. 3. 4.", "Portugal, Spain, Morocco, Iran",
                "1 0 -2.1, 1 2 0.5, 0 0 0, 0 0 0"
        );
        listItems.add(item2);

        TablesListItems item3 = new TablesListItems(
                "Group C", "1. 2. 3. 4.", "France, Australia, Peru, Denmark",
                "1 0 -2.1, 1 2 0.5, 1 0 -.5, 1 2 +1.0"
        );
        listItems.add(item3);

        adapter = new MyAdapter(getContext(), listItems, "Tables");
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
