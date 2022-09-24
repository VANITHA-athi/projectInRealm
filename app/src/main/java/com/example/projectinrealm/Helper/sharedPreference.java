package com.example.projectinrealm.Helper;

import android.content.Context;
import android.content.SharedPreferences;

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

    public void saveLoginCredenetial(String username){
        editor.putString(Key_username,username);
        editor.putBoolean(Key_isLoggedIn,true);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(Key_isLoggedIn,false);
    }

    public void logout(){
        editor.putBoolean(Key_isLoggedIn,false);
        editor.commit();
    }
}

