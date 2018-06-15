package com.example.micka.camerapp;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Camera;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
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

public class VideoActivity extends AppCompatActivity implements IVLCVout.Callback{
    private static final Uri SAMPLE_URL = Uri.parse("rtsp://admin:3edcvfr4@91.226.253.6:30554/cam/realmonitor?channel=1&subtype=0");
    private final String TAG = "VIDEO URL: ";
    private SurfaceView mVideoSurface;
    private RelativeLayout mainContainer;
    private ProgressBar progressBar;
    private Uri uri;
    private int currentCamera;
    private LibVLC libVLC=null;
    private MediaPlayer mMediaPlayer = null;
    private static final ArrayList<String> args = new ArrayList<>();
    private int  mVideoHeight,mVideoWidth,mVideoVisibleHeight,mVideoVisibleWidth,mVideoSarNum,mVideoSarDen=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        args.add("--rtsp-tcp");

        libVLC = new LibVLC(getApplicationContext(),args);
        mMediaPlayer = new MediaPlayer(libVLC);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.i("@@@@@@@width",String.valueOf(dm.widthPixels));
        Log.i("@@@@@@@height",String.valueOf(dm.heightPixels));

        mainContainer = (RelativeLayout) findViewById(R.id.rl_main_container);
        progressBar = (ProgressBar) findViewById(R.id.pb_video_load);
        mVideoSurface = (SurfaceView) findViewById(R.id.vv_video_holder);

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        initVideoView();

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

    /*private OnSwipeTouchListener initVideoViewTouchEventHandler (Context ctx){
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
    }*/


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
}
