package com.bokecc.live.activity;

import android.app.Application;

//import com.squareup.leakcanary.LeakCanary;
//import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by liufh on 2016/10/31.
 */

public class MyApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();

//        CrashReport.initCrashReport(getApplicationContext(), "a662d046b0", true);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        // Normal app init code...
    }
}