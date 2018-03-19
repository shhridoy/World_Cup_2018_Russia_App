package com.shhridoy.worldcup2018russia;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by Dream Land on 3/19/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }
}
