package com.shhridoy.worldcup2018russia.myNavFragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.R;

/**
 * Created by Dream Land on 2/21/2018.
 */

public class SettingsFragment extends Fragment {

    Switch switchNotification, switchSound, switchVibration;
    boolean isNotificationOn, isSoundOn, isVibrationOn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        initializeViews(rootView);

        isNotificationOn = getSettings("Notification");
        isSoundOn = getSettings("Sound");
        isVibrationOn = getSettings("Vibration");

        setSwitchesCheck();

        switchOnCheckChangeListeners();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeViews(View v) {
        switchNotification = v.findViewById(R.id.switchNotifications);
        switchSound = v.findViewById(R.id.switchSound);
        switchVibration = v.findViewById(R.id.switchVibration);
    }

    private void setSwitchesCheck(){
        if (isNotificationOn) {
            switchNotification.setChecked(true);
        } else {
            switchNotification.setChecked(false);
        }

        if (isSoundOn) {
            switchSound.setChecked(true);
        } else {
            switchSound.setChecked(false);
        }

        if (isVibrationOn) {
            switchVibration.setChecked(true);
        } else {
            switchVibration.setChecked(false);
        }
    }

    private void switchOnCheckChangeListeners() {
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchNotification.isChecked()) {
                    setSettings("Notification", true);
                } else {
                    setSettings("Notification", false);
                }
            }
        });

        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchSound.isChecked()) {
                    setSettings("Sound", true);
                } else {
                    setSettings("Sound", false);
                }
            }
        });

        switchVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchVibration.isChecked()) {
                    setSettings("Vibration", true);
                } else {
                    setSettings("Vibration", false);
                }
            }
        });
    }

    private void setSettings(String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    private boolean getSettings(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(key, true);
    }
}
