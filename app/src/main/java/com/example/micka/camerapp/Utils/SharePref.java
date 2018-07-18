package com.example.micka.camerapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    private static SharePref sharePref = new SharePref();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String WIDTH_NAME = "SCREEN_WIDTH";
    private static final String HEIGHT_NAME = "SCREEN_HEIGHT";

    private SharePref() {} //prevent creating multiple instances by making the constructor private

    //The context passed into the getInstance should be application level context.
    public static SharePref getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }
    

    public void saveScreenSize(int screenWidth,int screenHeight){
        editor.putInt(WIDTH_NAME,screenWidth);
        editor.putInt(HEIGHT_NAME,screenHeight);
        editor.commit();
    }
    public int getScreenWidth(){
        return sharedPreferences.getInt(WIDTH_NAME,0);
    }
    public int getScreenHeight(){
        return sharedPreferences.getInt(HEIGHT_NAME,0);
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

}
