package com.example.micka.camerapp.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.micka.camerapp.Activities.PreStreamActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class ImageThread implements Runnable {

    private Handler handler;
    private Message message;

    public ImageThread(Handler handler){
        this.handler = handler;
    }

    private static final String url = "http://91.226.253.10:20080//zm/cgi-bin/nph-zms?mode=single&scale=100&maxfps=5&buffer=1000&monitor=56&user=iport&connkey=602221&rand=1511870800";
    private static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final  Request request = new Request.Builder()
            .url(url)
            .build();


    @Override
    public void run() {

        try {
            Bitmap bitmap = doGetRequest(url);
            message = handler.obtainMessage(0,bitmap);
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap doGetRequest(String url) throws IOException {
        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(url).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }



}