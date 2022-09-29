package com.example.projectinrealm.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.projectinrealm.events.ViewEvents;

import java.util.HashMap;

public class sharedPreference {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private static final String Pref_name= "RealmExamplePref";
    private static final String Key_username= "username";
    private static final String Key_isLoggedIn = "isLoggedIn";

    public sharedPreference(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Pref_name, 0);
        editor = sharedPreferences.edit();
    }

    public void setUsername(String username){
        editor.putString(Key_username,username);
        editor.commit();

    }
    public void setLogin(boolean login){
        editor.putBoolean(Key_isLoggedIn,login);
        editor.commit();

    }
    public String getUsername(){
        return sharedPreferences.getString("Key_username","");
    }


    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(Key_isLoggedIn,false);
    }

    public void logout(){
        editor.putBoolean(Key_isLoggedIn,false);
        editor.commit();
    }
}

