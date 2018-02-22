package com.shhridoy.worldcup2018russia.myTabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shhridoy.worldcup2018russia.R;

/**
 * Created by Dream Land on 2/23/2018.
 */

public class MatchDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.match_details_fragment, container, false);

        return rootView;
    }
}
