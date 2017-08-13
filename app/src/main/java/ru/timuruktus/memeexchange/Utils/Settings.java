package ru.timuruktus.memeexchange.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings implements ISettings {


    private static final String APP_PREFERENCES = "mySettings";
    private static final String FIRST_OPEN = "firstOpen";

    private static SharedPreferences settings;

    public static void initSettings(Context con){
        settings = con.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static boolean isFirstOpen() {
        return getBooleanValue(FIRST_OPEN, true);
    }

    public static void setFirstOpen(boolean firstSign) {
        writeBooleanValue(FIRST_OPEN, firstSign);
    }

    /**
     * UNDER THIS LINE- 1-LVL METHODS
     * 1-lvl methods is used to write and read data from SharedReference.
     */

    private static void writeStringValue(String path, String value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(path, value);
        editor.apply();
    }

    private static void writeLongValue(String path, long value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(path, value);
        editor.apply();
    }

    private static void writeBooleanValue(String path, boolean value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(path, value);
        editor.apply();
    }

    private static String getStringValue(String path){
        return getStringValue(path, "");
    }

    private static String getStringValue(String path, String defaultValue){
        return settings.getString(path, defaultValue);
    }

    private static long getLongValue(String path){
        return getLongValue(path, 0);
    }

    private static long getLongValue(String path, long defaultValue){
        return settings.getLong(path, defaultValue);
    }

    private static boolean getBooleanValue(String path){
        return getBooleanValue(path, false);
    }

    private static boolean getBooleanValue(String path, boolean defaultValue){
        return settings.getBoolean(path, defaultValue);
    }
}
