package com.minimarket.scanminimarket.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MINIMARKET";
    private static final String SESSION_KEY = "SESSION_USER";
    public static final String NAMA_USER = "NAMA_USER";
    public static final String USERNAME = "USERNAME";
    public static final String LEVEL = "LEVEL";


    public PrefManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    //login session
    public void saveSession(){
        editor.putBoolean(SESSION_KEY,true);
        editor.commit();
    }
    public boolean getSession(){
        return preferences.getBoolean(SESSION_KEY,false);
    }
    public void removeSession(){
        editor.putBoolean(SESSION_KEY,false);
        editor.commit();
    }

    //user data
    public void setNamaUser(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getNamaUser(){
        return preferences.getString(NAMA_USER, "");
    }

    public void setUsername(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getUsername(){
        return preferences.getString(USERNAME, "");
    }

    public void setLevel(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getLevel(){
        return preferences.getString(LEVEL,"");
    }


}
