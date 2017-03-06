package com.example.liuteng.livedemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by liuteng on 2017/3/3.
 */
public class DemoApplication extends Application {
    public static final boolean isDebug=true;
    public static final String APP_NAME="LiveDemo";
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
