package com.shhridoy.worldcup2018russia.myNavFragments;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myDataBase.DatabaseHelper;

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

        /*for (int i=0; i<arr1.length; i++){
            TextView textView = new TextView(getContext());
            textView.setText(arr1[i]);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);
            ll.addView(textView);
            for (int j=i; j<arr2.length; j=j+2) {
                TextView tv = new TextView(getContext());
                tv.setText(arr2[j]);
                tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                ll.addView(tv);
            }
        }*/

        return rootView;
    }

    private void retrieveData(String name) {
        TextView textView = new TextView(getContext());
        textView.setText(name);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(17);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(5, 5, 5, 5);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(getContext().getResources().getDrawable(R.drawable.shape1));
        } else {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
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
                    TextView tv = new TextView(getContext());
                    tv.setText(round+"\n"+date);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setPadding(5, 5, 5, 5);
                    ll.addView(tv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
