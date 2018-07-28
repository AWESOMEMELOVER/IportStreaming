package com.example.micka.camerapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.micka.camerapp.Entity.Camera;
import com.example.micka.camerapp.Entity.Stream;
import com.example.micka.camerapp.R;
import com.example.micka.camerapp.Utils.ImageThread;
import com.example.micka.camerapp.Utils.Utils;

import org.videolan.libvlc.MediaPlayer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PreStreamActivity extends AppCompatActivity {

   private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_stream);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        surfaceView = findViewById(R.id.sv_prestream_image);
        Camera camera = new Camera();
        camera.setUri("rtsp://admin:3edcvfr4@91.226.253.6:30554");

        Stream stream = new Stream(getApplicationContext(),camera,surfaceView,1920,1080);
       // stream.setOnEventListener(new MediaPlayerEventListener());
        stream.createPlayer();
        stream.startPlayer();


    }

   /* private class MediaPlayerEventListener implements MediaPlayer.EventListener{
        @Override
        public void onEvent(MediaPlayer.Event event) {
            switch (event.type){
                case MediaPlayer.Event.Playing:
                    Log.i("@@@PLAYING","PLAYING");
                    break;
                case MediaPlayer.Event.Buffering:
                    Log.i("@@@Buffering","Buffering");
                    break;
            }
        }
    }*/

}
