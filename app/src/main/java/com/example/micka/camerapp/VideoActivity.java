package com.example.micka.camerapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;

import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.micka.camerapp.EventHandlers.OnPreparedVideoViewListener;
import com.example.micka.camerapp.EventHandlers.OnSwipeTouchListener;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class VideoActivity extends AppCompatActivity implements IVLCVout.Callback{
    private static final Uri SAMPLE_URL = Uri.parse("rtsp://admin:3edcvfr4@10.10.10.66:554/cam/realmonitor?channel=1&subtype=0");
    private final String TAG = "VIDEO URL: ";
    private SurfaceView mVideoSurface;
   /* private RelativeLayout mainContainer;
    private ProgressBar progressBar;*/
    private Uri uri;
    private SharePref sharePref;
    private int currentCamera=0;
    private LibVLC libVLC=null;
    private MediaPlayer mMediaPlayer = null;
    private static final ArrayList<String> args = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private int  mVideoHeight,mVideoWidth,mVideoVisibleHeight,mVideoVisibleWidth,mVideoSarNum,mVideoSarDen=0;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        sharePref = SharePref.getInstance(getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        int orientation =getResources().getConfiguration().orientation;

        Log.i("@@@orientation",String.valueOf(orientation));


        args.add("--rtsp-tcp");

        libVLC = new LibVLC(getApplicationContext(),args);
        mMediaPlayer = new MediaPlayer(libVLC);


        mVideoSurface = (SurfaceView) findViewById(R.id.vv_video_holder);
        Utils.setVideoSize(1280,720,getApplicationContext(),mVideoSurface,orientation);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 5000);

            }
        });

        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        mVideoSurface.setOnTouchListener(new OnSwipeTouchListenerImpl(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
       // progressBar.setVisibility(View.VISIBLE);
        initVideoView();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("@@@ONDESTROY ","CHANGE ORIENTATION");
    }

    private void initVideoView(){

        final IVLCVout vlcVout = mMediaPlayer.getVLCVout();
        currentCamera = 1;
        vlcVout.setVideoView(mVideoSurface);
        vlcVout.attachViews();
        mMediaPlayer.getVLCVout().addCallback(this);
        Media media = new Media(libVLC,SAMPLE_URL);
        mMediaPlayer.setMedia(media);
        media.release();
        mMediaPlayer.play();
    }

    @Override
    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
        mVideoWidth = width;
        mVideoHeight = height;
        mVideoVisibleWidth = visibleWidth;
        mVideoVisibleHeight = visibleHeight;
        mVideoSarNum = sarNum;
        mVideoSarDen = sarDen;
    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {

    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {

    }


    private class OnSwipeTouchListenerImpl extends OnSwipeTouchListener{

        public OnSwipeTouchListenerImpl(Context ctx) {
            super(ctx);
        }

        @Override
        public void onSwipeRight() {
            super.onSwipeRight();
            Toast.makeText(VideoActivity.this,"RIGHT",Toast.LENGTH_SHORT).show();
            mVideoSurface.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this,R.anim.left_to_right));
            if(currentCamera != Utils.getCameraUri().size()){
                currentCamera++;
                com.example.micka.camerapp.Entity.Camera camera = Utils.getCameraUri().get(currentCamera);
                libVLC.
            }
        }

        @Override
        public void onSwipeLeft() {
            super.onSwipeLeft();
            Toast.makeText(VideoActivity.this,"LEFT",Toast.LENGTH_SHORT).show();
            mVideoSurface.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this,R.anim.right_to_left));
            if(currentCamera!=0){

            }
        }

        @Override
        public void onSwipeTop() {
            super.onSwipeTop();
            Toast.makeText(VideoActivity.this,"TOP",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSwipeBottom() {
            super.onSwipeBottom();
            Toast.makeText(VideoActivity.this,"BOTTOM",Toast.LENGTH_SHORT).show();
        }
    }

}
