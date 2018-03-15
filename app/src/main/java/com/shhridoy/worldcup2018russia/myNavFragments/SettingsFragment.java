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
import android.widget.TextView;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myUtilities.Settings;
import com.shhridoy.worldcup2018russia.myUtilities.SharedPreference;

/**
 * Created by Dream Land on 2/21/2018.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    Switch switchNotification, switchSound, switchVibration;
    RadioButton rbInternational, rbBangladeshi;
    RadioGroup radioGroup;
    RelativeLayout rlTeamSelection;

    SharedPreference sp;

    // Views of Team Selection Dialog
    RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7, rl8, rl9, rl10, rl11, rl12, rl13, rl14, rl15, rl16;
    RelativeLayout rl17, rl18, rl19, rl20, rl21, rl22, rl23, rl24, rl25, rl26, rl27, rl28, rl29, rl30, rl31, rl32;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11;
    CheckBox checkBox12, checkBox13, checkBox14, checkBox15, checkBox16, checkBox17, checkBox18, checkBox19, checkBox20, checkBox21;
    CheckBox checkBox22, checkBox23, checkBox24, checkBox25, checkBox26,checkBox27, checkBox28, checkBox29, checkBox30,checkBox31,checkBox32;
    TextView tvTeam1,tvTeam2;

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

        sp = new SharedPreference();

        switchNotification.setOnCheckedChangeListener(this);
        switchSound.setOnCheckedChangeListener(this);
        switchVibration.setOnCheckedChangeListener(this);

        rbInternational.setOnClickListener(this);
        rbBangladeshi.setOnClickListener(this);

        rlTeamSelection.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {

            // SWITCH CHECK HANDLER
            case R.id.switchNotifications:
                if (switchNotification.isChecked()) {
                    Settings.setSettings(getContext(), "Notification", true);
                } else {
                    Settings.setSettings(getContext(), "Notification", false);
                }
                break;

            case R.id.switchSound:
                if (switchSound.isChecked()) {
                    Settings.setSettings(getContext(), "Sound", true);
                } else {
                    Settings.setSettings(getContext(), "Sound", false);
                }
                break;

            case R.id.switchVibration:
                if (switchVibration.isChecked()) {
                    Settings.setSettings(getContext(), "Vibration", true);
                } else {
                    Settings.setSettings(getContext(), "Vibration", false);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // RADIO BUTTONS HANDLER
            case R.id.RadioButtonInternational:
                Settings.setSettings(getContext(), "International Zone", true);
                break;
            case R.id.RadioButtonBangladesh:
                Settings.setSettings(getContext(), "International Zone", false);
                break;

            // TEAM SELECTION DIALOG OPEN HANDLER
            case R.id.RLTeamSelection:
                teamSelectionDialog();
                break;

            // DIALOG RELATIVE LAYOUTS CLICKS HANDLER
            case R.id.DialogRL1:
                if (checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                    sp.removeFavorite(getContext(), tvTeam1.getText().toString());
                } else {
                    checkBox1.setChecked(true);
                    sp.addFavorite(getContext(), tvTeam1.getText().toString());
                }
                Toast.makeText(getContext(), "CheckBox 1 is "+checkBox1.isChecked(), Toast.LENGTH_LONG).show();
                break;

            case R.id.DialogRL2:
                if (checkBox2.isChecked()) {
                    checkBox2.setChecked(false);
                    sp.removeFavorite(getContext(), tvTeam2.getText().toString());
                } else {
                    checkBox2.setChecked(true);
                    sp.addFavorite(getContext(), tvTeam2.getText().toString());
                }
                Toast.makeText(getContext(), "CheckBox 2 is "+checkBox2.isChecked(), Toast.LENGTH_LONG).show();
                break;
        }
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

    private void teamSelectionDialog() {
        Dialog myDialog = new Dialog(getContext());
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.team_selection_dialog);

        rl1 = myDialog.findViewById(R.id.DialogRL1);
        rl2 = myDialog.findViewById(R.id.DialogRL2);
        rl3 = myDialog.findViewById(R.id.DialogRL3);
        rl4 = myDialog.findViewById(R.id.DialogRL4);
        rl5 = myDialog.findViewById(R.id.DialogRL5);
        rl6 = myDialog.findViewById(R.id.DialogRL6);
        rl7 = myDialog.findViewById(R.id.DialogRL7);
        rl8 = myDialog.findViewById(R.id.DialogRL8);
        rl9 = myDialog.findViewById(R.id.DialogRL9);
        rl10 = myDialog.findViewById(R.id.DialogRL10);
        rl11 = myDialog.findViewById(R.id.DialogRL11);
        rl12 = myDialog.findViewById(R.id.DialogRL12);
        rl13 = myDialog.findViewById(R.id.DialogRL13);
        rl14 = myDialog.findViewById(R.id.DialogRL14);
        rl15 = myDialog.findViewById(R.id.DialogRL15);
        rl16 = myDialog.findViewById(R.id.DialogRL16);
        rl17 = myDialog.findViewById(R.id.DialogRL17);
        rl18 = myDialog.findViewById(R.id.DialogRL18);
        rl19 = myDialog.findViewById(R.id.DialogRL19);
        rl20 = myDialog.findViewById(R.id.DialogRL20);
        rl21 = myDialog.findViewById(R.id.DialogRL21);
        rl22 = myDialog.findViewById(R.id.DialogRL22);
        rl23 = myDialog.findViewById(R.id.DialogRL23);
        rl24 = myDialog.findViewById(R.id.DialogRL24);
        rl25 = myDialog.findViewById(R.id.DialogRL25);
        rl26 = myDialog.findViewById(R.id.DialogRL26);
        rl27 = myDialog.findViewById(R.id.DialogRL27);
        rl28 = myDialog.findViewById(R.id.DialogRL28);
        rl29 = myDialog.findViewById(R.id.DialogRL29);
        rl30 = myDialog.findViewById(R.id.DialogRL30);
        rl31 = myDialog.findViewById(R.id.DialogRL31);
        rl32 = myDialog.findViewById(R.id.DialogRL32);

        checkBox1 = myDialog.findViewById(R.id.Checkbox1);
        checkBox2 = myDialog.findViewById(R.id.Checkbox2);
        checkBox3 = myDialog.findViewById(R.id.Checkbox3);
        checkBox4 = myDialog.findViewById(R.id.Checkbox4);
        checkBox5 = myDialog.findViewById(R.id.Checkbox5);
        checkBox6 = myDialog.findViewById(R.id.Checkbox6);
        checkBox7 = myDialog.findViewById(R.id.Checkbox7);
        checkBox8 = myDialog.findViewById(R.id.Checkbox8);
        checkBox9 = myDialog.findViewById(R.id.Checkbox9);
        checkBox10 = myDialog.findViewById(R.id.Checkbox10);
        checkBox11 = myDialog.findViewById(R.id.Checkbox11);
        checkBox12 = myDialog.findViewById(R.id.Checkbox12);
        checkBox13 = myDialog.findViewById(R.id.Checkbox13);
        checkBox14 = myDialog.findViewById(R.id.Checkbox14);
        checkBox15 = myDialog.findViewById(R.id.Checkbox15);
        checkBox16 = myDialog.findViewById(R.id.Checkbox16);
        checkBox17 = myDialog.findViewById(R.id.Checkbox17);
        checkBox18 = myDialog.findViewById(R.id.Checkbox18);
        checkBox19 = myDialog.findViewById(R.id.Checkbox19);
        checkBox20 = myDialog.findViewById(R.id.Checkbox20);
        checkBox21 = myDialog.findViewById(R.id.Checkbox21);
        checkBox22 = myDialog.findViewById(R.id.Checkbox22);
        checkBox23 = myDialog.findViewById(R.id.Checkbox23);
        checkBox24 = myDialog.findViewById(R.id.Checkbox24);
        checkBox25 = myDialog.findViewById(R.id.Checkbox25);
        checkBox26 = myDialog.findViewById(R.id.Checkbox26);
        checkBox27 = myDialog.findViewById(R.id.Checkbox27);
        checkBox28 = myDialog.findViewById(R.id.Checkbox28);
        checkBox29 = myDialog.findViewById(R.id.Checkbox29);
        checkBox30 = myDialog.findViewById(R.id.Checkbox30);
        checkBox31 = myDialog.findViewById(R.id.Checkbox31);
        checkBox32 = myDialog.findViewById(R.id.Checkbox32);

        tvTeam1 = myDialog.findViewById(R.id.TVTeam1);
        tvTeam2 = myDialog.findViewById(R.id.TVTeam2);

        checkBox1.setChecked(true);
        checkBox2.setChecked(true);

        if (checkBox1.isChecked()) {
            sp.addFavorite(getContext(), tvTeam1.getText().toString());
        }

        if (checkBox2.isChecked()) {
            sp.addFavorite(getContext(), tvTeam2.getText().toString());
        }

        checkBox1.setClickable(false);
        checkBox2.setClickable(false);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);



        myDialog.show();
    }
}
