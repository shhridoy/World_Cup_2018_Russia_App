package com.shhridoy.worldcup2018russia.myNavFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
    RelativeLayout rlTeamSelection;
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

        rlTeamSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog myDialog = new Dialog(getContext());
                myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                myDialog.setContentView(R.layout.team_selection_dialog);

                RelativeLayout rl1 = myDialog.findViewById(R.id.DialogRL1);
                RelativeLayout rl2 = myDialog.findViewById(R.id.DialogRL2);
                final CheckBox checkBox1 = myDialog.findViewById(R.id.Checkbox1);
                final CheckBox checkBox2 = myDialog.findViewById(R.id.Checkbox2);

                checkBox1.setChecked(true);
                checkBox2.setChecked(true);

                rl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkBox1.isChecked()) {
                            checkBox1.setChecked(false);
                        } else {
                            checkBox1.setChecked(true);
                        }
                        Toast.makeText(getContext(), "CheckBox 1 is "+checkBox1.isChecked(), Toast.LENGTH_LONG).show();
                    }
                });

                rl2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkBox2.isChecked()) {
                            checkBox2.setChecked(false);
                        } else {
                            checkBox2.setChecked(true);
                        }
                        Toast.makeText(getContext(), "CheckBox 2 is "+checkBox2.isChecked(), Toast.LENGTH_LONG).show();
                    }
                });

                myDialog.show();
            }
        });

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
        rlTeamSelection = v.findViewById(R.id.RLTeamSelection);
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
