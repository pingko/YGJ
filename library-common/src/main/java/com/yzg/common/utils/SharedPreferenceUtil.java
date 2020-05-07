package com.yzg.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yzg.base.base.BaseApplication;


/**
 * SharedPreference的工具类
 */

public class SharedPreferenceUtil {

    private static final String SHAREDPREFERENCES_NAME = "LDS_DC";

    private static final boolean DEFAULT_FIRST_TAG = false;
    public static final boolean DEFAULT_LOGIN_TAG = false;


    public static SharedPreferences getAppSp() {
        return BaseApplication.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }



    public static void setToken(String token) {
        getAppSp().edit().putString(Constant.TOKEN, token).apply();
    }

    public static String getToken() {
        return getAppSp().getString(Constant.TOKEN, "");
    }


    public static int getInt(String key, int defValue) {
        return getAppSp().getInt(key, defValue);
    }

    public static void saveInt(String key, int value) {
        getAppSp().edit().putInt(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        return getAppSp().getLong(key, defValue);
    }

    public static void saveLong(String key, long value) {
        getAppSp().edit().putLong(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        return getAppSp().getString(key, defValue);
    }

    public static void putString(String key, String value) {
        getAppSp().edit().putString(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getAppSp().getBoolean(key, defValue);
    }

    public static void saveBoolean(String key, boolean value) {
        getAppSp().edit().putBoolean(key, value).commit();
    }

    public static float getFloat(String key, float defValue) {
        return getAppSp().getFloat(key, defValue);
    }

    public static void saveFloat(String key, float value) {
        getAppSp().edit().putFloat(key, value).commit();
    }

    public static void clearAll() {
        getAppSp().edit().clear().commit();
    }


    public static Object getObject(Class clazz) {
        String key = getKey(clazz);
        String json = getAppSp().getString(key, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            return null;
        }

    }

    public static void putObject(Object object) {

        Gson gson = new Gson();
        String json = gson.toJson(object);
        String key = getKey(object.getClass());
        getAppSp().edit().putString(key, json).apply();

    }

    public static void putClassNull(Class clazz) {
        String key = getKey(clazz);
        getAppSp().edit().putString(key, "").apply();
    }

    public static String getKey(Class<?> clazz) {
        return clazz.getName();
    }

//    public static Object getObject(Class clazz) {
//        String key = getKey(clazz);
//        String json = getAppSp().getString(key, null);
//        if (TextUtils.isEmpty(json)) {
//            return null;
//        }
//        try {
//            Gson gson = new Gson();
//            return gson.fromJson(json, clazz);
//        } catch (Exception e) {
//            return null;
//        }
//
//    }

//    public static void putObject(Object object) {
//
//        Gson gson = new Gson();
//        String json = gson.toJson(object);
//        String key = getKey(object.getClass());
//        getAppSp().edit().putString(key, json).apply();
//
//    }

}
