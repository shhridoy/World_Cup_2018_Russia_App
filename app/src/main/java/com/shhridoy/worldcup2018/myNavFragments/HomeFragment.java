package com.shhridoy.worldcup2018.myNavFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shhridoy.worldcup2018.R;
import com.shhridoy.worldcup2018.myPagerClasses.PagerAdapter;
import com.shhridoy.worldcup2018.myUtilities.Settings;

/**
 * Created by Dream Land on 2/21/2018.
 */

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Matches"));
        tabLayout.addTab(tabLayout.newTab().setText("Tables"));
        tabLayout.addTab(tabLayout.newTab().setText("Goals"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        if (Settings.getTheme(getContext()).equals("Red")) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
        } else if (Settings.getTheme(getContext()).equals("Purple")) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.md_purple_500));
        }

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return rootView;
    }
}
