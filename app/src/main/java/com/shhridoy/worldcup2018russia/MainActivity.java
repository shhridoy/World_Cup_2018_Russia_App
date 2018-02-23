package com.shhridoy.worldcup2018russia;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shhridoy.worldcup2018russia.myNavFragments.SettingsFragment;
import com.shhridoy.worldcup2018russia.myNavFragments.HomeFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    // PERMISSION CODE
    public static final int MULTIPLE_PERMISSIONS = 10;

    // PERMISSION LIST
    String[] permissionsList = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.SET_ALARM,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fab.hide();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //select first items from navigation menu while view created
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
        navigationView.setCheckedItem(R.id.nav_home);

        // action bar back arrow button click handler
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            checkPermissions();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragmentManager.findFragmentByTag("Settings") != null && fragmentManager.findFragmentByTag("Settings").isVisible()) {

                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Settings")).commit();

                if(fragmentManager.findFragmentByTag("Home") != null){
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Home")).commit();
                    enableHamburgerAndDisableBackArrow(R.id.nav_home);
                    navigationView.setCheckedItem(R.id.nav_home);
                }

            } else if (fragmentManager.findFragmentByTag("MatchDetails") != null && fragmentManager.findFragmentByTag("MatchDetails").isVisible()) {

                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();

                if(fragmentManager.findFragmentByTag("Home") != null){
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Home")).commit();
                    enableHamburgerAndDisableBackArrow(R.id.nav_home);
                    navigationView.setCheckedItem(R.id.nav_home);
                }

            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (getSupportFragmentManager().findFragmentByTag("Home") != null && getSupportFragmentManager().findFragmentByTag("Home").isVisible()) {
                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("Home")).commit();
                getSupportFragmentManager().beginTransaction().add(R.id.content_frame, new HomeFragment(), "Home").commit();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_home) {

            if(fragmentManager.findFragmentByTag("Home") != null) {
                //if the fragment exists, show it.
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Home")).commit();
            } else {
                //if the fragment does not exist, add it to fragment manager.
                fragmentManager.beginTransaction().add(R.id.content_frame, new HomeFragment(), "Home").commit();
            }

            if(fragmentManager.findFragmentByTag("Settings") != null){
                //if the other fragment is visible, hide it.
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Settings")).commit();
            }

            if (fragmentManager.findFragmentByTag("MatchDetails") != null) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();
            }

            enableHamburgerAndDisableBackArrow(R.id.nav_home);

        } else if (id == R.id.nav_settings) {

            if(fragmentManager.findFragmentByTag("Settings") != null) {
                //if the fragment exists, show it.
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Settings")).commit();
            } else {
                //if the fragment does not exist, add it to fragment manager.
                fragmentManager.beginTransaction().add(R.id.content_frame, new SettingsFragment(), "Settings").commit();
            }
            if(fragmentManager.findFragmentByTag("Home") != null){
                //if the other fragment is visible, hide it.
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Home")).commit();
            }
            if (fragmentManager.findFragmentByTag("MatchDetails") != null) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();
            }
            enableHamburgerAndDisableBackArrow(R.id.nav_settings);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void enableHamburgerAndDisableBackArrow(int id) {
        if (id == R.id.nav_home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setTitle("WC 2018 Russia");
        } else {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            if (id == R.id.nav_settings) {
                getSupportActionBar().setTitle("Settings");
            } else {
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            }
        }
    }

    private void setNotification () {
        boolean alarmActive = (PendingIntent.getBroadcast(
                this,
                100,
                new Intent(this, NotificationReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmActive) {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, 0);
            //calendar.set(Calendar.SECOND, 30);

            Intent intent = new Intent(this, NotificationReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    100,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private  void checkPermissions() {

        /*if (checkPermissions()) {
            //  permissions  granted.
        }*/

        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissionsList) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            //return false;
        }
        //return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permissions granted.
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    String permissionss = "";
                    for (String per : permissionsList) {
                        permissionss += "\n" + per;
                    }
                    // permissions list of don't granted permission
                    Toast.makeText(this, "Permission doesn't granted.", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

}
