package com.example.shioka.navigationdrawer.App;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by Shioka on 28/03/2018.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
