package com.shhridoy.worldcup2018russia.myNavFragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.NotificationReceiver;
import com.shhridoy.worldcup2018russia.R;

import java.util.Calendar;

/**
 * Created by Dream Land on 2/21/2018.
 */

public class SettingsFragment extends Fragment {

    Switch aSwitch;
    TextView tv;
    EditText et1, et2;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        aSwitch = rootView.findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (aSwitch.isChecked()) {
                    Toast.makeText(getContext(), "Is checked!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Isn't checked!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et1 = rootView.findViewById(R.id.ET1);
        et2 = rootView.findViewById(R.id.ET2);
        btn = rootView.findViewById(R.id.BTN);
        et1.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotification(Integer.parseInt(et2.getText().toString()));
            }
        });

        return rootView;
    }

    private void setNotification (int min) {
        boolean alarmActive = (PendingIntent.getBroadcast(
                getContext(),
                100,
                new Intent(getContext(), NotificationReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmActive) {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(System.currentTimeMillis());
            //calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            //calendar.set(Calendar.SECOND, 30);

            Intent intent = new Intent(getContext(), NotificationReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getContext(),
                    100,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
