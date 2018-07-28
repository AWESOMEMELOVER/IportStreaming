package com.example.micka.camerapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

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




    public static int dpFromPx(final Context context, final int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public static int pxFromDp(final Context context, final int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }



    public static String parseToUrl(Camera camera){
       return camera.getUri()+"zm/cgi-bin/nph-zms?mode=single&scale=100&maxfps=5&buffer=1000&monitor="+camera.getId()+"&user=iport&connkey=602221&rand=1511870800";
    }

        public static void setSize(View view, int mVideoWidth, int mVideoHeight, Context ctx){
            if (mVideoWidth * mVideoHeight <= 1)
                return;
            if(view==null)
                return;



            int w = SharePref.getInstance(ctx).getScreenWidth();
            int h = SharePref.getInstance(ctx).getScreenHeight();

            Log.i("@@@carouselSie",String.valueOf(w)+":"+String.valueOf(h));

            boolean isPortrait = ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
            if (w > h && isPortrait || w < h && !isPortrait) {
                int i = w;
                w = h;
                h = i;
            }

            float videoAR = (float) mVideoWidth / (float) mVideoHeight;
            float screenAR = (float) w / (float) h;

            if (screenAR < videoAR)
                h = (int) (w / videoAR);
            else
                w = (int) (h * videoAR);



            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.width = w;
            lp.height = h;

            Log.i("@@@test",String.valueOf(lp.width)+":"+String.valueOf(lp.height));

            view.setLayoutParams(lp);
            view.invalidate();
        }
}
