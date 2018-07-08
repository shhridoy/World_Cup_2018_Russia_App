package com.shhridoy.worldcup2018russia.myTabFragments;

import android.app.ActionBar;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myDataBase.DatabaseHelper;
import com.shhridoy.worldcup2018russia.myRecyclerViewData.Flags;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dream Land on 2/23/2018.
 */

public class MatchDetailsFragment extends Fragment {

    TextView tvDateTime, tvRound, tvTeam1, tvTeam2, tvScore, tvDetailsTitles;
    TextView tvTeam1Scorers, tvTeam2Scorers, tvTeam1Details, tvTeam2Details, tvStadiumTitle, tvStadium;
    CircleImageView imgTeam1, imgTeam2;
    Bundle bundle;
    String dateTime, round, team1, team2, score, details;
    String id;
    ActionBar actionBar;
    ActionBarDrawerToggle toggle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.match_details_fragment, container, false);

        iniViewsAndValues(rootView);
        getValues();
        retrieveDetailsFromDB();
        setValues();

        return rootView;
    }

    public void setFunc(ActionBar actionBar, ActionBarDrawerToggle toggle) {
        this.actionBar = actionBar;
        this.toggle = toggle;
        this.toggle.setDrawerIndicatorEnabled(false);
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        this.actionBar.setHomeButtonEnabled(true);
    }

    private void iniViewsAndValues (View v) {
        tvDateTime = v.findViewById(R.id.tv_date_time);
        tvRound = v.findViewById(R.id.tv_round);
        tvTeam1 = v.findViewById(R.id.TextViewTeam1MatchDetails);
        tvTeam2 = v.findViewById(R.id.TextViewTeam2MatchDetails);
        tvScore = v.findViewById(R.id.TextViewScoreMatchDetails);
        tvDetailsTitles = v.findViewById(R.id.TextViewMatchDetailsTittles);
        imgTeam1 = v.findViewById(R.id.ImageTeam1MatchDetails);
        imgTeam2 = v.findViewById(R.id.ImageTeam2MatchDetails);
        tvTeam1Scorers = v.findViewById(R.id.Team1Scorers);
        tvTeam2Scorers = v.findViewById(R.id.Team2Scorers);
        tvTeam1Details = v.findViewById(R.id.TextViewMatchDetailsTeam1);
        tvTeam2Details = v.findViewById(R.id.TextViewMatchDetailsTeam2);
        tvStadium = v.findViewById(R.id.TextViewMatchDetailsStadium);
        tvStadiumTitle = v.findViewById(R.id.TextViewMatchDetailsStadiumTittle);

        dateTime = null;
        round = null;
        team1 = null;
        team2 = null;
        score = null;
        details = null;
    }

    private void getValues() {
        bundle = getArguments();
        if (bundle != null) {
            dateTime = bundle.getString("DATE_TIME");
            round = bundle.getString("ROUND");
            team1 = bundle.getString("TEAM1");
            team2 = bundle.getString("TEAM2");
            score = bundle.getString("SCORE");
            id = bundle.getString("ID");
        }
    }

    private void setValues () {
        tvDateTime.setText(dateTime);
        tvRound.setText(round);
        tvTeam1.setText(team1);
        tvTeam2.setText(team2);
        tvScore.setText(score);
        imgTeam1.setImageResource(Flags.getFlag(team1));
        imgTeam2.setImageResource(Flags.getFlag(team2));

        //Toast.makeText(getContext(), details, Toast.LENGTH_LONG).show();

        if (!details.equals(" ") || !details.equals("  ")) {
            try {
                String[] splitDetails = details.split("@@");

                String[] goalScorers = splitDetails[0].split("@");
                String team1GoalScorers = goalScorers[0];
                String team2GoalScorers = goalScorers[1];

                tvTeam1Scorers.setText(team1GoalScorers);
                tvTeam2Scorers.setText(team2GoalScorers);

                String[] matchDetails = splitDetails[1].split("@");
                String team1MatchDetails = matchDetails[0];
                String team2MatchDetails = matchDetails[1];

                tvDetailsTitles.setText(getResources().getString(R.string.details));
                tvTeam1Details.setText(team1MatchDetails);
                tvTeam2Details.setText(team2MatchDetails);

                String stadium = splitDetails[2];
                tvStadiumTitle.setText("Stadium");
                tvStadium.setText(stadium);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void retrieveDetailsFromDB() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        Cursor cursor = databaseHelper.retrieveMatchesData();

        while (cursor.moveToNext()) {
            if (cursor.getString(0).equalsIgnoreCase(id)) {
                details = cursor.getString(6);
                break;
            }
        }
    }
}
