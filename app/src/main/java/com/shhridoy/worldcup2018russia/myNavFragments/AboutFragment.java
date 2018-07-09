package com.shhridoy.worldcup2018russia.myNavFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myUtilities.Settings;

/**
 * Created by Dream Land on 3/7/2018.
 */

public class AboutFragment extends Fragment {

    TextView tvLink, tvDeveloperTitle, tvUserGuideTitle, tvAboutAppTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.about_fragment, container, false);

        iniViews(rootView);

        tvLink.setMovementMethod(LinkMovementMethod.getInstance());

        return rootView;
    }

    private void iniViews(View view) {
        tvLink = view.findViewById(R.id.TVDeveloperDetails);
        tvAboutAppTitle = view.findViewById(R.id.TVAboutAppTitle);
        tvUserGuideTitle = view.findViewById(R.id.TVUserGuideTitle);
        tvDeveloperTitle = view.findViewById(R.id.TVDeveloperTitle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (Settings.getTheme(getContext()).equals("Red")){
                tvDeveloperTitle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_red));
                tvUserGuideTitle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_red));
                tvAboutAppTitle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_red));
            } else if (Settings.getTheme(getContext()).equals("Purple")){
                tvDeveloperTitle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_purple));
                tvUserGuideTitle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_purple));
                tvAboutAppTitle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_purple));
            }
        }
    }
}
