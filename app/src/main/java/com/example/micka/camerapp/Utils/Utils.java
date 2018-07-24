package com.example.micka.camerapp.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceView;

import com.example.micka.camerapp.Entity.Camera;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by micka on 6/17/2018.
 */

public class Utils {


   public static void setVideoSize(int videoWidth, int videoHeight, Context ctx, SurfaceView surfaceView ,int orientation){
       int screenWidth=0;
       int screenHeight=0;
       float screenProportion = 0;
       float videoProportion = (float) videoWidth/(float) videoHeight;
       Log.i("@@VIDEO_PROPORTION",String.valueOf(videoProportion));

       if(orientation==1) {

           screenWidth = SharePref.getInstance(ctx).getScreenWidth();
           Log.i("@@SCREEN_WIDTH", String.valueOf(screenWidth));
           screenHeight = SharePref.getInstance(ctx).getScreenHeight();
           Log.i("@@SCREEN_HEIGHT", String.valueOf(screenHeight));
           screenProportion = (float) screenWidth / (float) screenHeight;
           Log.i("@@SCREEN_PROPORTION", String.valueOf(screenProportion));

       }else if(orientation==2){
           screenWidth=SharePref.getInstance(ctx).getScreenHeight();
           Log.i("@@SCREEN_WIDTH", String.valueOf(screenWidth));
           screenHeight = SharePref.getInstance(ctx).getScreenWidth();
           Log.i("@@SCREEN_HEIGHT", String.valueOf(screenHeight));
           screenProportion = (float) screenWidth / (float) screenHeight;
           Log.i("@@SCREEN_PROPORTION", String.valueOf(screenProportion));
       }
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
               lp.height = screenHeight;
               lp.width = (int) ((float) screenHeight / videoProportion);

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

    public static ArrayList<Camera> getCameraUri(){
       ArrayList<Camera> result = new ArrayList<>();
      /* result.add(new Camera(1, Uri.parse("rtsp://admin:3edcvfr4@10.10.10.66:554/cam/realmonitor?channel=1&subtype=0")));
       result.add(new Camera(2,Uri.parse("rtsp://zmserver:4hhWHFZDY1@10.10.10.65:10554/profile1")));*/
       return result;
    }

    public static JsonObject objectToJsonObject(Object object){
        JsonObject jsonObject = null;
        if(object instanceof JsonObject){
            jsonObject = (JsonObject)object;
        }
        return jsonObject;
    }
    public static JsonArray objectToJsonArray(Object object){
        JsonArray jsonObject = null;
        if(object instanceof JsonObject){
           // jsonObject = new Gson().toJson(object,JsonArray.class);
        }
        return jsonObject;
    }
    public static String parseToUrl(Camera camera){
       return camera.getUri()+"zm/cgi-bin/nph-zms?mode=single&scale=100&maxfps=5&buffer=1000&monitor="+camera.getId()+"&user=iport&connkey=602221&rand=1511870800";
    }
}
