package com.shhridoy.worldcup2018russia.myNavFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shhridoy.worldcup2018russia.R;

/**
 * Created by Dream Land on 3/7/2018.
 */

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.about_fragment, container, false);

        TextView tv = rootView.findViewById(R.id.TVDeveloperDetails);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        return rootView;
    }
}
