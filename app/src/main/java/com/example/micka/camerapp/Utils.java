package com.example.micka.camerapp;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by micka on 6/17/2018.
 */

public class Utils {

    public static HashMap<String,Integer> getPrefearedSize(int translatedWidth, int translatedHeight, int screenWidth,int screenHeight){

        HashMap<String,Integer> preferedSize = new HashMap<>();


        Log.i("@@@@@@@width",String.valueOf(screenWidth));
        Log.i("@@@@@@@height",String.valueOf(screenHeight));



        if(translatedHeight == screenHeight && translatedWidth==screenWidth){
            preferedSize.put("height",translatedHeight);
            preferedSize.put("width",translatedWidth);
        }
        else if(translatedHeight!=screenHeight && translatedWidth != screenWidth){
            double coef = translatedHeight/translatedWidth;
            double newWidth =  translatedWidth*coef;
            Log.i("@@@@@@@newWidth",String.valueOf(newWidth));
           // preferedSize.put("width",newWidth);
            preferedSize.put("height",translatedHeight);
        }
            return preferedSize;
    }

}
