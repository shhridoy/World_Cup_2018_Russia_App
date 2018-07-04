package com.shhridoy.worldcup2018russia.myNavFragments;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.R;
import com.shhridoy.worldcup2018russia.myDataBase.DatabaseHelper;
import com.shhridoy.worldcup2018russia.myUtilities.Settings;

import java.util.ArrayList;

/**
 * Created by Dream Land on 2/21/2018.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    Switch switchNotification, switchSound, switchVibration;
    RadioButton rbInternational, rbBangladeshi;
    RadioGroup radioGroup;
    RelativeLayout rlTeamSelection;

    ImageButton defBtn, redBtn, purpleBtn;

    DatabaseHelper databaseHelper;

    // Views of Team Selection Dialog
    RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7, rl8, rl9, rl10, rl11, rl12, rl13, rl14, rl15, rl16;
    RelativeLayout rl17, rl18, rl19, rl20, rl21, rl22, rl23, rl24, rl25, rl26, rl27, rl28, rl29, rl30, rl31, rl32;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11;
    CheckBox checkBox12, checkBox13, checkBox14, checkBox15, checkBox16, checkBox17, checkBox18, checkBox19, checkBox20, checkBox21;
    CheckBox checkBox22, checkBox23, checkBox24, checkBox25, checkBox26,checkBox27,checkBox28,checkBox29,checkBox30,checkBox31,checkBox32;
    TextView tvTeam1,tvTeam2, tvTeam3, tvTeam4, tvTeam5, tvTeam6, tvTeam7, tvTeam8, tvTeam9, tvTeam10, tvTeam11, tvTeam12, tvTeam13;
    TextView tvTeam14, tvTeam15, tvTeam16, tvTeam17, tvTeam18, tvTeam19, tvTeam20, tvTeam21, tvTeam22, tvTeam23, tvTeam24, tvTeam25;
    TextView tvTeam26, tvTeam27, tvTeam28, tvTeam29, tvTeam30, tvTeam31, tvTeam32;

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

        switchNotification.setOnCheckedChangeListener(this);
        switchSound.setOnCheckedChangeListener(this);
        switchVibration.setOnCheckedChangeListener(this);

        rbInternational.setOnClickListener(this);
        rbBangladeshi.setOnClickListener(this);

        defBtn.setOnClickListener(this);
        redBtn.setOnClickListener(this);
        purpleBtn.setOnClickListener(this);

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

            // THEME SELECTIONS
            case R.id.ThemeSelectionImgBtnDefault:
                Settings.setTheme(getContext(), "Default");
                defBtn.setImageResource(R.drawable.ic_action_check);
                redBtn.setImageResource(0);
                purpleBtn.setImageResource(0);
                break;

            case R.id.ThemeSelectionImgBtnRed:
                Settings.setTheme(getContext(), "Red");
                redBtn.setImageResource(R.drawable.ic_action_check);
                defBtn.setImageResource(0);
                purpleBtn.setImageResource(0);
                break;

            case R.id.ThemeSelectionImgBtnPurple:
                Settings.setTheme(getContext(), "Purple");
                purpleBtn.setImageResource(R.drawable.ic_action_check);
                defBtn.setImageResource(0);
                redBtn.setImageResource(0);
                break;

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
                    removeMyTeams(tvTeam1.getText().toString());
                } else {
                    checkBox1.setChecked(true);
                    addMyTeams(tvTeam1.getText().toString());
                }
                break;

            case R.id.DialogRL2:
                if (checkBox2.isChecked()) {
                    checkBox2.setChecked(false);
                    removeMyTeams(tvTeam2.getText().toString());
                } else {
                    checkBox2.setChecked(true);
                    addMyTeams(tvTeam2.getText().toString());
                }
                break;

            case R.id.DialogRL3:
                if (checkBox3.isChecked()) {
                    checkBox3.setChecked(false);
                    removeMyTeams(tvTeam3.getText().toString());
                } else {
                    checkBox3.setChecked(true);
                    addMyTeams(tvTeam3.getText().toString());
                }
                break;

            case R.id.DialogRL4:
                if (checkBox4.isChecked()) {
                    checkBox4.setChecked(false);
                    removeMyTeams(tvTeam4.getText().toString());
                } else {
                    checkBox4.setChecked(true);
                    addMyTeams(tvTeam4.getText().toString());
                }
                break;

            case R.id.DialogRL5:
                if (checkBox5.isChecked()) {
                    checkBox5.setChecked(false);
                    removeMyTeams(tvTeam5.getText().toString());
                } else {
                    checkBox5.setChecked(true);
                    addMyTeams(tvTeam5.getText().toString());
                }
                break;

            case R.id.DialogRL6:
                if (checkBox6.isChecked()) {
                    checkBox6.setChecked(false);
                    removeMyTeams(tvTeam6.getText().toString());
                } else {
                    checkBox6.setChecked(true);
                    addMyTeams(tvTeam6.getText().toString());
                }
                break;

            case R.id.DialogRL7:
                if (checkBox7.isChecked()) {
                    checkBox7.setChecked(false);
                    removeMyTeams(tvTeam7.getText().toString());
                } else {
                    checkBox7.setChecked(true);
                    addMyTeams(tvTeam7.getText().toString());
                }
                break;

            case R.id.DialogRL8:
                if (checkBox8.isChecked()) {
                    checkBox8.setChecked(false);
                    removeMyTeams(tvTeam8.getText().toString());
                } else {
                    checkBox8.setChecked(true);
                    addMyTeams(tvTeam8.getText().toString());
                }
                break;

            case R.id.DialogRL9:
                if (checkBox9.isChecked()) {
                    checkBox9.setChecked(false);
                    removeMyTeams(tvTeam9.getText().toString());
                } else {
                    checkBox9.setChecked(true);
                    addMyTeams(tvTeam9.getText().toString());
                }
                break;

            case R.id.DialogRL10:
                if (checkBox10.isChecked()) {
                    checkBox10.setChecked(false);
                    removeMyTeams(tvTeam10.getText().toString());
                } else {
                    checkBox10.setChecked(true);
                    addMyTeams(tvTeam10.getText().toString());
                }
                break;

            case R.id.DialogRL11:
                if (checkBox11.isChecked()) {
                    checkBox11.setChecked(false);
                    removeMyTeams(tvTeam11.getText().toString());
                } else {
                    checkBox11.setChecked(true);
                    addMyTeams(tvTeam11.getText().toString());
                }
                break;

            case R.id.DialogRL12:
                if (checkBox12.isChecked()) {
                    checkBox12.setChecked(false);
                    removeMyTeams(tvTeam12.getText().toString());
                } else {
                    checkBox12.setChecked(true);
                    addMyTeams(tvTeam12.getText().toString());
                }
                break;

            case R.id.DialogRL13:
                if (checkBox13.isChecked()) {
                    checkBox13.setChecked(false);
                    removeMyTeams(tvTeam13.getText().toString());
                } else {
                    checkBox13.setChecked(true);
                    addMyTeams(tvTeam13.getText().toString());
                }
                break;

            case R.id.DialogRL14:
                if (checkBox14.isChecked()) {
                    checkBox14.setChecked(false);
                    removeMyTeams(tvTeam14.getText().toString());
                } else {
                    checkBox14.setChecked(true);
                    addMyTeams(tvTeam14.getText().toString());
                }
                break;

            case R.id.DialogRL15:
                if (checkBox15.isChecked()) {
                    checkBox15.setChecked(false);
                    removeMyTeams(tvTeam15.getText().toString());
                } else {
                    checkBox15.setChecked(true);
                    addMyTeams(tvTeam15.getText().toString());
                }
                break;

            case R.id.DialogRL16:
                if (checkBox16.isChecked()) {
                    checkBox16.setChecked(false);
                    removeMyTeams(tvTeam16.getText().toString());
                } else {
                    checkBox16.setChecked(true);
                    addMyTeams(tvTeam16.getText().toString());
                }
                break;

            case R.id.DialogRL17:
                if (checkBox17.isChecked()) {
                    checkBox17.setChecked(false);
                    removeMyTeams(tvTeam17.getText().toString());
                } else {
                    checkBox17.setChecked(true);
                    addMyTeams(tvTeam17.getText().toString());
                }
                break;

            case R.id.DialogRL18:
                if (checkBox18.isChecked()) {
                    checkBox18.setChecked(false);
                    removeMyTeams(tvTeam18.getText().toString());
                } else {
                    checkBox18.setChecked(true);
                    addMyTeams(tvTeam18.getText().toString());
                }
                break;

            case R.id.DialogRL19:
                if (checkBox19.isChecked()) {
                    checkBox19.setChecked(false);
                    removeMyTeams(tvTeam19.getText().toString());
                } else {
                    checkBox19.setChecked(true);
                    addMyTeams(tvTeam19.getText().toString());
                }
                break;

            case R.id.DialogRL20:
                if (checkBox20.isChecked()) {
                    checkBox20.setChecked(false);
                    removeMyTeams(tvTeam20.getText().toString());
                } else {
                    checkBox20.setChecked(true);
                    addMyTeams(tvTeam20.getText().toString());
                }
                break;

            case R.id.DialogRL21:
                if (checkBox21.isChecked()) {
                    checkBox21.setChecked(false);
                    removeMyTeams(tvTeam21.getText().toString());
                } else {
                    checkBox21.setChecked(true);
                    addMyTeams(tvTeam21.getText().toString());
                }
                break;

            case R.id.DialogRL22:
                if (checkBox22.isChecked()) {
                    checkBox22.setChecked(false);
                    removeMyTeams(tvTeam22.getText().toString());
                } else {
                    checkBox22.setChecked(true);
                    addMyTeams(tvTeam22.getText().toString());
                }
                break;

            case R.id.DialogRL23:
                if (checkBox23.isChecked()) {
                    checkBox23.setChecked(false);
                    removeMyTeams(tvTeam23.getText().toString());
                } else {
                    checkBox23.setChecked(true);
                    addMyTeams(tvTeam23.getText().toString());
                }
                break;

            case R.id.DialogRL24:
                if (checkBox24.isChecked()) {
                    checkBox24.setChecked(false);
                    removeMyTeams(tvTeam24.getText().toString());
                } else {
                    checkBox24.setChecked(true);
                    addMyTeams(tvTeam24.getText().toString());
                }
                break;

            case R.id.DialogRL25:
                if (checkBox25.isChecked()) {
                    checkBox25.setChecked(false);
                    removeMyTeams(tvTeam25.getText().toString());
                } else {
                    checkBox25.setChecked(true);
                    addMyTeams(tvTeam25.getText().toString());
                }
                break;

            case R.id.DialogRL26:
                if (checkBox26.isChecked()) {
                    checkBox26.setChecked(false);
                    removeMyTeams(tvTeam26.getText().toString());
                } else {
                    checkBox26.setChecked(true);
                    addMyTeams(tvTeam26.getText().toString());
                }
                break;

            case R.id.DialogRL27:
                if (checkBox27.isChecked()) {
                    checkBox27.setChecked(false);
                    removeMyTeams(tvTeam27.getText().toString());
                } else {
                    checkBox27.setChecked(true);
                    addMyTeams(tvTeam27.getText().toString());
                }
                break;

            case R.id.DialogRL28:
                if (checkBox28.isChecked()) {
                    checkBox28.setChecked(false);
                    removeMyTeams(tvTeam28.getText().toString());
                } else {
                    checkBox28.setChecked(true);
                    addMyTeams(tvTeam28.getText().toString());
                }
                break;

            case R.id.DialogRL29:
                if (checkBox29.isChecked()) {
                    checkBox29.setChecked(false);
                    removeMyTeams(tvTeam29.getText().toString());
                } else {
                    checkBox29.setChecked(true);
                    addMyTeams(tvTeam29.getText().toString());
                }
                break;

            case R.id.DialogRL30:
                if (checkBox30.isChecked()) {
                    checkBox30.setChecked(false);
                    removeMyTeams(tvTeam30.getText().toString());
                } else {
                    checkBox30.setChecked(true);
                    addMyTeams(tvTeam30.getText().toString());
                }
                break;

            case R.id.DialogRL31:
                if (checkBox31.isChecked()) {
                    checkBox31.setChecked(false);
                    removeMyTeams(tvTeam31.getText().toString());
                } else {
                    checkBox31.setChecked(true);
                    addMyTeams(tvTeam31.getText().toString());
                }
                break;

            case R.id.DialogRL32:
                if (checkBox32.isChecked()) {
                    checkBox32.setChecked(false);
                    removeMyTeams(tvTeam32.getText().toString());
                } else {
                    checkBox32.setChecked(true);
                    addMyTeams(tvTeam32.getText().toString());
                }
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
        defBtn = v.findViewById(R.id.ThemeSelectionImgBtnDefault);
        redBtn = v.findViewById(R.id.ThemeSelectionImgBtnRed);
        purpleBtn = v.findViewById(R.id.ThemeSelectionImgBtnPurple);
    }

    private void setSwitchAndButtonsCheck(){

        if (Settings.getTheme(getContext()).equals("Red")) {
            defBtn.setImageResource(0);
            redBtn.setImageResource(R.drawable.ic_action_check);
            purpleBtn.setImageResource(0);
        } else if (Settings.getTheme(getContext()).equals("Purple")) {
            defBtn.setImageResource(0);
            redBtn.setImageResource(0);
            purpleBtn.setImageResource(R.drawable.ic_action_check);
        } else {
            defBtn.setImageResource(R.drawable.ic_action_check);
            redBtn.setImageResource(0);
            purpleBtn.setImageResource(0);
        }

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

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(checkBox1);
        checkBoxes.add(checkBox2);
        checkBoxes.add(checkBox3);
        checkBoxes.add(checkBox4);
        checkBoxes.add(checkBox5);
        checkBoxes.add(checkBox6);
        checkBoxes.add(checkBox7);
        checkBoxes.add(checkBox8);
        checkBoxes.add(checkBox9);
        checkBoxes.add(checkBox10);
        checkBoxes.add(checkBox11);
        checkBoxes.add(checkBox12);
        checkBoxes.add(checkBox13);
        checkBoxes.add(checkBox14);
        checkBoxes.add(checkBox15);
        checkBoxes.add(checkBox16);
        checkBoxes.add(checkBox17);
        checkBoxes.add(checkBox18);
        checkBoxes.add(checkBox19);
        checkBoxes.add(checkBox20);
        checkBoxes.add(checkBox21);
        checkBoxes.add(checkBox22);
        checkBoxes.add(checkBox23);
        checkBoxes.add(checkBox24);
        checkBoxes.add(checkBox25);
        checkBoxes.add(checkBox26);
        checkBoxes.add(checkBox27);
        checkBoxes.add(checkBox28);
        checkBoxes.add(checkBox29);
        checkBoxes.add(checkBox30);
        checkBoxes.add(checkBox31);
        checkBoxes.add(checkBox32);

        tvTeam1 = myDialog.findViewById(R.id.TVTeam1);
        tvTeam2 = myDialog.findViewById(R.id.TVTeam2);
        tvTeam3 = myDialog.findViewById(R.id.TVTeam3);
        tvTeam4 = myDialog.findViewById(R.id.TVTeam4);
        tvTeam5 = myDialog.findViewById(R.id.TVTeam5);
        tvTeam6 = myDialog.findViewById(R.id.TVTeam6);
        tvTeam7 = myDialog.findViewById(R.id.TVTeam7);
        tvTeam8 = myDialog.findViewById(R.id.TVTeam8);
        tvTeam9 = myDialog.findViewById(R.id.TVTeam9);
        tvTeam10 = myDialog.findViewById(R.id.TVTeam10);
        tvTeam11 = myDialog.findViewById(R.id.TVTeam11);
        tvTeam12 = myDialog.findViewById(R.id.TVTeam12);
        tvTeam13 = myDialog.findViewById(R.id.TVTeam13);
        tvTeam14 = myDialog.findViewById(R.id.TVTeam14);
        tvTeam15 = myDialog.findViewById(R.id.TVTeam15);
        tvTeam16 = myDialog.findViewById(R.id.TVTeam16);
        tvTeam17 = myDialog.findViewById(R.id.TVTeam17);
        tvTeam18 = myDialog.findViewById(R.id.TVTeam18);
        tvTeam19 = myDialog.findViewById(R.id.TVTeam19);
        tvTeam20 = myDialog.findViewById(R.id.TVTeam20);
        tvTeam21 = myDialog.findViewById(R.id.TVTeam21);
        tvTeam22 = myDialog.findViewById(R.id.TVTeam22);
        tvTeam23 = myDialog.findViewById(R.id.TVTeam23);
        tvTeam24 = myDialog.findViewById(R.id.TVTeam24);
        tvTeam25 = myDialog.findViewById(R.id.TVTeam25);
        tvTeam26 = myDialog.findViewById(R.id.TVTeam26);
        tvTeam27 = myDialog.findViewById(R.id.TVTeam27);
        tvTeam28 = myDialog.findViewById(R.id.TVTeam28);
        tvTeam29 = myDialog.findViewById(R.id.TVTeam29);
        tvTeam30 = myDialog.findViewById(R.id.TVTeam30);
        tvTeam31 = myDialog.findViewById(R.id.TVTeam31);
        tvTeam32 = myDialog.findViewById(R.id.TVTeam32);

        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add(tvTeam1);
        textViews.add(tvTeam2);
        textViews.add(tvTeam3);
        textViews.add(tvTeam4);
        textViews.add(tvTeam5);
        textViews.add(tvTeam6);
        textViews.add(tvTeam7);
        textViews.add(tvTeam8);
        textViews.add(tvTeam9);
        textViews.add(tvTeam10);
        textViews.add(tvTeam11);
        textViews.add(tvTeam12);
        textViews.add(tvTeam13);
        textViews.add(tvTeam14);
        textViews.add(tvTeam15);
        textViews.add(tvTeam16);
        textViews.add(tvTeam17);
        textViews.add(tvTeam18);
        textViews.add(tvTeam19);
        textViews.add(tvTeam20);
        textViews.add(tvTeam21);
        textViews.add(tvTeam22);
        textViews.add(tvTeam23);
        textViews.add(tvTeam24);
        textViews.add(tvTeam25);
        textViews.add(tvTeam26);
        textViews.add(tvTeam27);
        textViews.add(tvTeam28);
        textViews.add(tvTeam29);
        textViews.add(tvTeam30);
        textViews.add(tvTeam31);
        textViews.add(tvTeam32);

        try {
            databaseHelper = new DatabaseHelper(getContext());
            Cursor c = databaseHelper.getMyTeams();
            if (c.getCount() != 0) {
                while (c.moveToNext()) {
                    String name = c.getString(0);
                    for (int i=0; i<textViews.size(); i++) {
                        if (name.equalsIgnoreCase(textViews.get(i).getText().toString())) {
                            checkBoxes.get(i).setChecked(true);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkBox1.setClickable(false);
        checkBox2.setClickable(false);
        checkBox3.setClickable(false);
        checkBox4.setClickable(false);
        checkBox5.setClickable(false);
        checkBox6.setClickable(false);
        checkBox7.setClickable(false);
        checkBox8.setClickable(false);
        checkBox9.setClickable(false);
        checkBox10.setClickable(false);
        checkBox11.setClickable(false);
        checkBox12.setClickable(false);
        checkBox13.setClickable(false);
        checkBox14.setClickable(false);
        checkBox15.setClickable(false);
        checkBox16.setClickable(false);
        checkBox17.setClickable(false);
        checkBox18.setClickable(false);
        checkBox19.setClickable(false);
        checkBox20.setClickable(false);
        checkBox21.setClickable(false);
        checkBox22.setClickable(false);
        checkBox23.setClickable(false);
        checkBox24.setClickable(false);
        checkBox25.setClickable(false);
        checkBox26.setClickable(false);
        checkBox27.setClickable(false);
        checkBox28.setClickable(false);
        checkBox29.setClickable(false);
        checkBox30.setClickable(false);
        checkBox31.setClickable(false);
        checkBox32.setClickable(false);

        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        rl8.setOnClickListener(this);
        rl9.setOnClickListener(this);
        rl10.setOnClickListener(this);
        rl11.setOnClickListener(this);
        rl12.setOnClickListener(this);
        rl13.setOnClickListener(this);
        rl14.setOnClickListener(this);
        rl15.setOnClickListener(this);
        rl16.setOnClickListener(this);
        rl17.setOnClickListener(this);
        rl18.setOnClickListener(this);
        rl19.setOnClickListener(this);
        rl20.setOnClickListener(this);
        rl21.setOnClickListener(this);
        rl22.setOnClickListener(this);
        rl23.setOnClickListener(this);
        rl24.setOnClickListener(this);
        rl25.setOnClickListener(this);
        rl26.setOnClickListener(this);
        rl27.setOnClickListener(this);
        rl28.setOnClickListener(this);
        rl29.setOnClickListener(this);
        rl30.setOnClickListener(this);
        rl31.setOnClickListener(this);
        rl32.setOnClickListener(this);

        myDialog.show();
    }

    private void addMyTeams (String teamName) {
        boolean added = databaseHelper.insertMyTeamsData(teamName);
        if (!added) {
            Toast.makeText(getContext(), "Data can't be added!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeMyTeams(String teamName) {
        boolean removed = databaseHelper.removeFromMyTeams(teamName);
        if (!removed) {
            Toast.makeText(getContext(), "Doesn't updated!", Toast.LENGTH_SHORT).show();
        }
    }
}
