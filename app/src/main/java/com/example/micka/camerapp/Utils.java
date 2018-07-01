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

    public static HashMap<String,Double> scallingByScreenSize(int bufCameraWidth, int bufCameraHeight, Context ctx){
        HashMap<String,Double> result = new HashMap<>();

        double screenWidth = (double)(SharePref.getInstance(ctx).getScreenWidth());
        double screenHeight = (double) (SharePref.getInstance(ctx).getScreenHeight());

        double cameraWidth = (double) bufCameraWidth;
        double cameraHeight = (double)bufCameraHeight;

        Log.i("@@DOUBLEWIDTH",String.valueOf(screenWidth));
        Log.i("@@DOUBLEHEIGHT",String.valueOf(screenHeight));

        Log.i("@@DoubleCameraWidth",String.valueOf(cameraWidth));
        Log.i("@@DoubleCameraHeight",String.valueOf(cameraHeight));
       if(screenWidth >= cameraWidth && screenHeight >= cameraHeight){
            result.put("width",cameraWidth);
            result.put("height",cameraHeight);
            return result;
        }else if(screenWidth<cameraWidth && screenHeight>=cameraHeight){
            double coeficient = cameraHeight/cameraWidth;
            double newWidth = screenWidth*coeficient;
            double newHeight = screenHeight*coeficient;
            result.put("width",newWidth);
            result.put("height",newHeight);
            return result;
        }else if (screenWidth>= cameraWidth && screenHeight<cameraHeight){
            double coeficient = cameraHeight/cameraWidth;
           double newHeight = screenHeight*coeficient;
            result.put("width",cameraWidth);
            result.put("height",newHeight);
            return result;
        }else if(screenWidth<cameraWidth && screenHeight<cameraHeight){
           double coeficient = cameraHeight/cameraWidth;
           double newWidth = screenWidth*coeficient;
           double newHeight = screenHeight*coeficient;
            result.put("width",newWidth);
            result.put("height",newHeight);
            return result;
        }
        return result;
    }

    public static int dpFromPx(final Context context, final int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public static int pxFromDp(final Context context, final int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
