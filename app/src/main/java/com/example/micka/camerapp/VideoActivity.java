package com.example.micka.camerapp;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.micka.camerapp.EventHandlers.OnPreparedVideoViewListener;
import com.example.micka.camerapp.EventHandlers.OnSwipeTouchListener;

import java.net.URI;

public class VideoActivity extends AppCompatActivity {

    private final String TAG = "VIDEO URL: ";
    private VideoView videoView;
    private RelativeLayout mainContainer;
    private ProgressBar progressBar;
    private Uri uri;
    private int currentCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hide top bar
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        mainContainer = (RelativeLayout) findViewById(R.id.rl_main_container);
        progressBar = (ProgressBar) findViewById(R.id.pb_video_load);
        videoView = (VideoView) findViewById(R.id.vv_video_holder);

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        initVideoView();

    }

    private void initVideoView(){

        videoView.setVideoURI(CameraURI.URI_ONE);
        currentCamera = 1;
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new OnPreparedVideoViewListener(getApplicationContext(),progressBar));
        videoView.setOnTouchListener(initVideoViewTouchEventHandler(getApplicationContext()));
    }

    private OnSwipeTouchListener initVideoViewTouchEventHandler (Context ctx){
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(ctx) {
            public void onSwipeLeft() {

                progressBar.setVisibility(View.VISIBLE);
                currentCamera--;
                videoView.setVideoURI(CameraURI.uriList.get(currentCamera));
                videoView.start();
            }

            public void onSwipeRight() {

                progressBar.setVisibility(View.VISIBLE);
                currentCamera++;
                videoView.setVideoURI(CameraURI.uriList.get(currentCamera));
                videoView.start();


            }
        };
        return onSwipeTouchListener;
    }




}
