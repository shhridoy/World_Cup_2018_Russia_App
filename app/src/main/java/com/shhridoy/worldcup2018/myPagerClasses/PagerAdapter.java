package com.shhridoy.worldcup2018.myPagerClasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shhridoy.worldcup2018.myTabFragments.MatchesFragment;
import com.shhridoy.worldcup2018.myTabFragments.TablesFragment;
import com.shhridoy.worldcup2018.myTabFragments.GoalsFragment;

/**
 * Created by Dream Land on 1/12/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MatchesFragment tab1 = new MatchesFragment();
                return tab1;
            case 1:
                TablesFragment tab2 = new TablesFragment();
                return tab2;
            case 2:
                GoalsFragment tab3 = new GoalsFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}