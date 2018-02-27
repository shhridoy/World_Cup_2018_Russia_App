package com.shhridoy.worldcup2018russia.myNavFragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myUtilities.Settings;

/**
 * Created by Dream Land on 2/21/2018.
 */

public class SettingsFragment extends Fragment {

    Switch switchNotification, switchSound, switchVibration;
    RadioButton rbInternational, rbBangladeshi;
    RadioGroup radioGroup;
    boolean isNotificationOn, isSoundOn, isVibrationOn, isInternational;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        initializeViews(rootView);

        isNotificationOn = Settings.getSettings(getContext(), "Notification");
        isSoundOn = Settings.getSettings(getContext(), "Sound");
        isVibrationOn = Settings.getSettings(getContext(), "Vibration");
        isInternational = Settings.getSettings(getContext(), "International Zone");

        setSwitchAndButtonsCheck();

        switchOnCheckChangeListeners();
        radioButtonsClicks();

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
        radioGroup = v.findViewById(R.id.radioGroup);
        rbInternational = v.findViewById(R.id.RadioButtonInternational);
        rbBangladeshi = v.findViewById(R.id.RadioButtonBangladesh);
    }

    private void setSwitchAndButtonsCheck(){
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

        if (isInternational) {
            rbInternational.setChecked(true);
        } else {
            rbBangladeshi.setChecked(true);
        }
    }

    private void switchOnCheckChangeListeners() {
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchNotification.isChecked()) {
                    Settings.setSettings(getContext(), "Notification", true);
                } else {
                    Settings.setSettings(getContext(), "Notification", false);
                }
            }
        });

        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchSound.isChecked()) {
                    Settings.setSettings(getContext(), "Sound", true);
                } else {
                    Settings.setSettings(getContext(), "Sound", false);
                }
            }
        });

        switchVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchVibration.isChecked()) {
                    Settings.setSettings(getContext(), "Vibration", true);
                } else {
                    Settings.setSettings(getContext(), "Vibration", false);
                }
            }
        });
    }

    private void radioButtonsClicks(){
        rbInternational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setSettings(getContext(), "International Zone", true);
            }
        });

        rbBangladeshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setSettings(getContext(), "International Zone", false);
            }
        });
    }

}
