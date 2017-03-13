package com.example.liuteng.livedemo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liuteng on 2017/3/13.
 */

public class CommonSharePreference {
    private static final String PREFERENCE_NAME = "common_share_preference";
    private static final int MODE_WORLD_READABLE = Context.MODE_PRIVATE;
    public static SharedPreferences sharedPreferences = null;
    private static SharedPreferences.Editor editor = null;
    public static final String DEVICE_ID = "device_id";
    public static final String USER_ID = "user_id";

    @SuppressLint("WorldReadableFiles")
    public static void init(Context context) {
        if (null == sharedPreferences) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, MODE_WORLD_READABLE);
        }
    }


    private static void beginSet() {
        editor = sharedPreferences.edit();
    }

    private static void endSet() {
        if (null != editor) {
            editor.commit();
        }
    }

    public static void set(String key, String value) {
        beginSet();
        if (null != editor) {
            editor.putString(key, value);
        }
        endSet();
    }

    public static String get(String key, String defualt) {
        return sharedPreferences.getString(key, defualt);
    }

    public static void set(String key, int value) {
        beginSet();
        if (null != editor) {
            editor.putInt(key, value);
        }
        endSet();
    }

    public static int get(String key, int defualt) {
        return sharedPreferences.getInt(key, defualt);
    }

    public static void set(String key, long value) {
        beginSet();
        if (null != editor) {
            editor.putLong(key, value);
        }
        endSet();
    }

    public static long get(String key, long defualt) {
        return sharedPreferences.getLong(key, defualt);
    }

    public static void set(String key, float value) {
        beginSet();
        if (null != editor) {
            editor.putFloat(key, value);
        }
        endSet();
    }

    public static float get(String key, float defualt) {
        return sharedPreferences.getFloat(key, defualt);
    }

    public static void set(String key, boolean value) {
        beginSet();
        if (null != editor) {
            editor.putBoolean(key, value);
        }
        endSet();
    }

    public static boolean get(String key, boolean defualt) {
        return sharedPreferences.getBoolean(key, defualt);
    }

    public static void setDeviceid(String input) {
        beginSet();
        if (null != editor) {
            editor.putString(DEVICE_ID, input);
        }
        endSet();
    }

    public static String getDeviceid() {
        return sharedPreferences.getString(DEVICE_ID, "");
    }

    public static void setUserId(String input) {
        beginSet();
        if (null != editor) {
            editor.putString(USER_ID, input);
        }
        endSet();
    }

    public static String getUserId() {
        return sharedPreferences.getString(USER_ID, "");
    }

    public static void remove(String key) {
        beginSet();
        if (editor != null) {
            editor.remove(key);
        }
        endSet();
    }

    public static void clear() {
        beginSet();
        if (editor != null) {
            editor.clear();
        }
        endSet();
    }

}
