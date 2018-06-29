package com.shhridoy.worldcup2018russia;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.shhridoy.worldcup2018russia.myNavFragments.AboutFragment;
import com.shhridoy.worldcup2018russia.myNavFragments.FeedbackFragment;
import com.shhridoy.worldcup2018russia.myNavFragments.SettingsFragment;
import com.shhridoy.worldcup2018russia.myNavFragments.HomeFragment;
import com.shhridoy.worldcup2018russia.myNavFragments.YourTeamsFragment;
import com.shhridoy.worldcup2018russia.myUtilities.NotificationReceiver;
import com.shhridoy.worldcup2018russia.myUtilities.Settings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    AdView mAdView; // BANNER AD VIEW
    InterstitialAd mInterstitialAd; // INTERSTITIAL AD VIEW

    public static boolean isYourTeam = false;

    // PERMISSION CODE
    public static final int MULTIPLE_PERMISSIONS = 28;

    // GOOGLE DEVELOPER PAGE ID
    final String DEV_ID = "6869327098906954532";

    // PERMISSION LIST
    String[] permissionsList = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.SET_ALARM,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Settings.getTheme(this).equalsIgnoreCase("Red")){
            setTheme(R.style.AppThemeRed);
        } else if (Settings.getTheme(this).equalsIgnoreCase("Purple")){
            setTheme(R.style.AppThemePurple);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
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

        setNotification();
        loadBannerAd();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadInterstitialAd();
            }
        }, 6000);
    }

    boolean backButtonPressedOnce = false;

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

                isYourTeam = false;

            } else if (fragmentManager.findFragmentByTag("Your Teams") != null && fragmentManager.findFragmentByTag("Your Teams").isVisible()) {

                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Your Teams")).commit();

                if(fragmentManager.findFragmentByTag("Home") != null){
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Home")).commit();
                    enableHamburgerAndDisableBackArrow(R.id.nav_home);
                    navigationView.setCheckedItem(R.id.nav_home);
                }

                isYourTeam = false;

            } else if (fragmentManager.findFragmentByTag("MatchDetails") != null && fragmentManager.findFragmentByTag("MatchDetails").isVisible()) {

                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();

                if (isYourTeam) {
                    if (fragmentManager.findFragmentByTag("Your Teams") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Your Teams")).commit();
                        enableHamburgerAndDisableBackArrow(R.id.nav_your_teams);
                        navigationView.setCheckedItem(R.id.nav_your_teams);
                    }
                } else {
                    if(fragmentManager.findFragmentByTag("Home") != null){
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Home")).commit();
                        enableHamburgerAndDisableBackArrow(R.id.nav_home);
                        navigationView.setCheckedItem(R.id.nav_home);
                    }

                    isYourTeam = false;
                }

            } else  if (fragmentManager.findFragmentByTag("About") != null && fragmentManager.findFragmentByTag("About").isVisible()) {

                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("About")).commit();

                if (fragmentManager.findFragmentByTag("Home") != null) {
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Home")).commit();
                    enableHamburgerAndDisableBackArrow(R.id.nav_home);
                    navigationView.setCheckedItem(R.id.nav_home);
                }

                isYourTeam = false;

            } else if (fragmentManager.findFragmentByTag("Feedback") != null && fragmentManager.findFragmentByTag("Feedback").isVisible()) {

                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Feedback")).commit();

                if (fragmentManager.findFragmentByTag("Home") != null) {
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Home")).commit();
                    enableHamburgerAndDisableBackArrow(R.id.nav_home);
                    navigationView.setCheckedItem(R.id.nav_home);
                }

                isYourTeam = false;

            } else {

                if(backButtonPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.backButtonPressedOnce = true;
                Snackbar snackbar = Snackbar.make(findViewById(R.id.COR), "Please Press Back Again to Exit", Snackbar.LENGTH_SHORT);
                snackbar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backButtonPressedOnce = false;
                    }
                }, 2000);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {
            if (isInternetOn()) {
                if (getSupportFragmentManager().findFragmentByTag("Home") != null
                        && getSupportFragmentManager().findFragmentByTag("Home").isVisible()) {

                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("Home")).commit();
                    getSupportFragmentManager().beginTransaction().add(R.id.content_frame, new HomeFragment(), "Home").commit();
                }
            } else {
                Toast.makeText(this, "Please Check Internet Connection!!", Toast.LENGTH_SHORT).show();
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

            if (fragmentManager.findFragmentByTag("Home") != null) {
                isYourTeam = false;
                //if the fragment exists, show it.
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Home")).commit();
            } else {
                isYourTeam = false;
                //if the fragment does not exist, add it to fragment manager.
                fragmentManager.beginTransaction().add(R.id.content_frame, new HomeFragment(), "Home").commit();
            }

            if (fragmentManager.findFragmentByTag("Settings") != null && fragmentManager.findFragmentByTag("Settings").isVisible()){
                //if the other fragment is visible, hide it.
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Settings")).commit();
            }

            if (fragmentManager.findFragmentByTag("Your Teams") != null && fragmentManager.findFragmentByTag("Your Teams").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Your Teams")).commit();
            }

            if (fragmentManager.findFragmentByTag("About") != null && fragmentManager.findFragmentByTag("About").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("About")).commit();
            }

            if (fragmentManager.findFragmentByTag("MatchDetails") != null && fragmentManager.findFragmentByTag("MatchDetails").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();
            }

            if (fragmentManager.findFragmentByTag("Feedback") != null && fragmentManager.findFragmentByTag("Feedback").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Feedback")).commit();
            }

            enableHamburgerAndDisableBackArrow(R.id.nav_home);

        } else if (id == R.id.nav_settings) {

            if(fragmentManager.findFragmentByTag("Settings") != null) {
                isYourTeam = false;
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Settings")).commit();
            } else {
                isYourTeam = false;
                fragmentManager.beginTransaction().add(R.id.content_frame, new SettingsFragment(), "Settings").commit();
            }

            if(fragmentManager.findFragmentByTag("Home") != null && fragmentManager.findFragmentByTag("Home").isVisible()){
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Home")).commit();
            }

            if (fragmentManager.findFragmentByTag("Your Teams") != null && fragmentManager.findFragmentByTag("Your Teams").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Your Teams")).commit();
            }

            if (fragmentManager.findFragmentByTag("MatchDetails") != null && fragmentManager.findFragmentByTag("MatchDetails").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();
            }

            if (fragmentManager.findFragmentByTag("About") != null && fragmentManager.findFragmentByTag("About").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("About")).commit();
            }

            if (fragmentManager.findFragmentByTag("Feedback") != null && fragmentManager.findFragmentByTag("Feedback").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Feedback")).commit();
            }

            enableHamburgerAndDisableBackArrow(R.id.nav_settings);

        } else if (id == R.id.nav_your_teams) {

            if (fragmentManager.findFragmentByTag("Your Teams") != null) {
                //whether fragment exists, or not add it.
                isYourTeam = true;
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("Your Teams")).commit();
                fragmentManager.beginTransaction().add(R.id.content_frame, new YourTeamsFragment(), "Your Teams").commit();
                //fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Your Teams")).commit();
            } else {
                //if the fragment does not exist, add it to fragment manager.
                isYourTeam = true;
                fragmentManager.beginTransaction().add(R.id.content_frame, new YourTeamsFragment(), "Your Teams").commit();
            }

            if(fragmentManager.findFragmentByTag("Home") != null && fragmentManager.findFragmentByTag("Home").isVisible()){
                //if the other fragment is visible, hide it.
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Home")).commit();
            }

            if(fragmentManager.findFragmentByTag("Settings") != null && fragmentManager.findFragmentByTag("Settings").isVisible()){
                //if the other fragment is visible, hide it.
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Settings")).commit();
            }

            if (fragmentManager.findFragmentByTag("MatchDetails") != null && fragmentManager.findFragmentByTag("MatchDetails").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();
            }

            if (fragmentManager.findFragmentByTag("About") != null && fragmentManager.findFragmentByTag("About").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("About")).commit();
            }

            if (fragmentManager.findFragmentByTag("Feedback") != null && fragmentManager.findFragmentByTag("Feedback").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Feedback")).commit();
            }

            enableHamburgerAndDisableBackArrow(R.id.nav_your_teams);

        } else if (id == R.id.nav_about) {

            if(fragmentManager.findFragmentByTag("About") != null) {
                isYourTeam = false;
                //if the fragment exists, show it.
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("About")).commit();
            } else {
                isYourTeam = false;
                //if the fragment does not exist, add it to fragment manager.
                fragmentManager.beginTransaction().add(R.id.content_frame, new AboutFragment(), "About").commit();
            }

            if(fragmentManager.findFragmentByTag("Home") != null && fragmentManager.findFragmentByTag("Home").isVisible()){
                //if the other fragment is visible, hide it.
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Home")).commit();
            }

            if (fragmentManager.findFragmentByTag("Your Teams") != null && fragmentManager.findFragmentByTag("Your Teams").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Your Teams")).commit();
            }

            if (fragmentManager.findFragmentByTag("MatchDetails") != null && fragmentManager.findFragmentByTag("MatchDetails").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();
            }

            if (fragmentManager.findFragmentByTag("Settings") != null && fragmentManager.findFragmentByTag("Settings").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Settings")).commit();
            }

            if (fragmentManager.findFragmentByTag("Feedback") != null && fragmentManager.findFragmentByTag("Feedback").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Feedback")).commit();
            }

            enableHamburgerAndDisableBackArrow(R.id.nav_about);

        } else if (id == R.id.nav_feedback) {

            if (fragmentManager.findFragmentByTag("Feedback") != null) {
                isYourTeam = false;
                //if the fragment exists, show it.
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Feedback")).commit();
            } else {
                isYourTeam = false;
                //if the fragment does not exist, add it to fragment manager.
                fragmentManager.beginTransaction().add(R.id.content_frame, new FeedbackFragment(), "Feedback").commit();
            }

            if(fragmentManager.findFragmentByTag("Home") != null && fragmentManager.findFragmentByTag("Home").isVisible()){
                //if the other fragment is visible, hide it.
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Home")).commit();
            }

            if (fragmentManager.findFragmentByTag("Your Teams") != null && fragmentManager.findFragmentByTag("Your Teams").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Your Teams")).commit();
            }

            if (fragmentManager.findFragmentByTag("MatchDetails") != null && fragmentManager.findFragmentByTag("MatchDetails").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("MatchDetails")).commit();
            }

            if (fragmentManager.findFragmentByTag("Settings") != null && fragmentManager.findFragmentByTag("Settings").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Settings")).commit();
            }

            if (fragmentManager.findFragmentByTag("About") != null && fragmentManager.findFragmentByTag("About").isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("About")).commit();
            }

            enableHamburgerAndDisableBackArrow(R.id.nav_feedback);

        } else if (id == R.id.nav_share) {
            String playStoreLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
            String textShare = "App: " + getResources().getString(R.string.app_name) + "\nType: Sports\nDownload Link: " + playStoreLink;
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, textShare);
            shareIntent.setType("text/plain");
            this.startActivity(Intent.createChooser(shareIntent, "Share "+getResources().getString(R.string.app_name)));

        } else if (id == R.id.nav_rate_app) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }

        } else if (id == R.id.nav_more_apps) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id="+DEV_ID)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/dev?id="+DEV_ID)));
            }
        }

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
            } else if (id == R.id.nav_about) {
                getSupportActionBar().setTitle("About");
            } else if (id == R.id.nav_feedback) {
                getSupportActionBar().setTitle("User Feedback");
            } else if (id == R.id.nav_your_teams) {
                getSupportActionBar().setTitle("My Teams");
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

        Calendar c = Calendar.getInstance();
        int min = c.get(Calendar.MINUTE);

        if (!alarmActive) {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(System.currentTimeMillis());
            //calendar.set(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, min+2);
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
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
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

    private boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        assert connec != null;
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            return false;
        }

        return false;
    }

    private void loadBannerAd() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //int heightDP = AdSize.BANNER.getHeight();
                frameLayoutMargin();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.\
                //Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        mAdView.loadAd(adRequest);
    }

    private void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interestitial_fullscreen_ad));

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void frameLayoutMargin() {
        FrameLayout frameLayout = findViewById(R.id.content_frame);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 0, 0, 70);
        frameLayout.setLayoutParams(params);
    }

}
