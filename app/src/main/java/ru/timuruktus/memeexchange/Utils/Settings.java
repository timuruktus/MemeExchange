package ru.timuruktus.memeexchange.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings implements ISettings {


    private static final String APP_PREFERENCES = "mySettings";
    private static final String FIRST_OPEN = "firstOpen";
    private static final String USER_TOKEN = "token";
    private static final String USER_OBJECT_ID = "objectId";
    public static final int MEMES_CACHE_MAX_SIZE = 50; //Memes

    private static SharedPreferences settings;

    public static ISettings getInstance(Context con){
        if(settings == null){
            settings = con.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        }
        return new Settings();
    }

    private Settings(){}

    @Override
    public boolean isFirstOpen() {
        return getBooleanValue(FIRST_OPEN, true);
    }

    @Override
    public void setFirstOpen(boolean firstSign) {
        writeBooleanValue(FIRST_OPEN, firstSign);
    }

    @Override
    public boolean isUserLoggedIn(){
        return !FieldsValidator.isStringEmpty(getStringValue(USER_TOKEN));
    }

    @Override
    public String getUserToken(){
        return getStringValue(USER_TOKEN);
    }

    @Override
    public void setUserToken(String token){
        writeStringValue(USER_TOKEN, token);
    }

    @Override
    public String getUserObjectId(){
        return getStringValue(USER_OBJECT_ID);
    }

    @Override
    public void setUserObjectId(String objectId){
        writeStringValue(USER_OBJECT_ID, objectId);
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
