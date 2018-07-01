package com.example.micka.camerapp;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import java.util.HashMap;

/**
 * Created by micka on 6/17/2018.
 */

public class Utils {

   /* public static HashMap<String,Double> scallingByScreenSize(int bufCameraWidth, int bufCameraHeight, Context ctx){
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
    }*/

   public static void setVideoSize(int videoWidth, int videoHeight, Context ctx, SurfaceView surfaceView){
       float videoProportion = (float) videoWidth/(float) videoHeight;
       Log.i("@@VIDEO_PROPORTION",String.valueOf(videoProportion));

       int  screenWidth = SharePref.getInstance(ctx).getScreenWidth();
       Log.i("@@SCREEN_WIDTH",String.valueOf(screenWidth));
       int  screenHeight = SharePref.getInstance(ctx).getScreenHeight();
       Log.i("@@SCREEN_HEIGHT",String.valueOf(screenHeight));
       float screenProportion = (float) screenWidth / (float) screenHeight;
       Log.i("@@SCREEN_PROPORTION",String.valueOf(screenProportion));

       android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();




       if (videoProportion > screenProportion) {
           if(screenHeight >= videoHeight && screenWidth>=videoWidth){
                lp.width = screenWidth;
                lp.height = screenHeight;
               Log.i("@@surfaceView WIDTH1",String.valueOf(lp.width));
               Log.i("@@surfaceView HEIGHT1",String.valueOf(lp.height));
           }else if(screenWidth<videoWidth && screenHeight >= videoHeight) {
               lp.width = screenWidth;
               lp.height = (int) ((float) screenWidth / videoProportion);
               Log.i("@@surfaceView WIDTH2",String.valueOf(lp.width));
               Log.i("@@surfaceView HEIGHT2",String.valueOf(lp.height));
           }else if(screenHeight < videoHeight && screenWidth>=videoWidth){
               lp.width = (int) ((float) screenWidth / videoProportion);
               lp.height = screenHeight;
               Log.i("@@surfaceView WIDTH3",String.valueOf(lp.width));
               Log.i("@@surfaceView HEIGHT3",String.valueOf(lp.height));
           }
       }
       // Commit the layout parameters
       surfaceView.setLayoutParams(lp);
   }

    public static int dpFromPx(final Context context, final int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public static int pxFromDp(final Context context, final int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
