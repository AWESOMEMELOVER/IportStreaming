package com.example.micka.camerapp.Activities;

import android.content.Context;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.micka.camerapp.EventHandlers.OnSwipeTouchListener;
import com.example.micka.camerapp.R;
import com.example.micka.camerapp.Utils.Utils;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity implements IVLCVout.Callback{
    private static final ArrayList<com.example.micka.camerapp.Entity.Camera> cameraList = Utils.getCameraUri();
    private static final Uri SAMPLE_URL =  Uri.parse("rtsp://admin:3edcvfr4@10.10.10.66:554/cam/realmonitor?channel=1&subtype=0");

    private int currentCamera = 0;

    public final static String TAG = "CameraApp/VideoActivity";

    private SurfaceView mSurface;
    private SurfaceHolder holder;
    private SwipeRefreshLayout refreshLayout;

    private LibVLC libVLC;
    private MediaPlayer mMediaPlayer = null;

    private int mVideoWidth;
    private int mVideoHeight;
    private final static int VideoSizeChange =-1;

    /*************
     * Activity
     *************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        mSurface = (SurfaceView) findViewById(R.id.vv_video_holder);
        holder = mSurface.getHolder();
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setSize(mVideoWidth,mVideoHeight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createPlayer(SAMPLE_URL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayoutImpl());
    }

    /*************
     * Surface
     *************/
    private void setSize(int width, int height) {
        mVideoWidth = width;
        mVideoHeight = height;
        if (mVideoWidth * mVideoHeight <= 1)
            return;

        if(holder == null || mSurface == null)
            return;

        // get screen size
        int w = getWindow().getDecorView().getWidth();
        int h = getWindow().getDecorView().getHeight();

        // getWindow().getDecorView() doesn't always take orientation into
        // account, we have to correct the values
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
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

        // force surface buffer size
        holder.setFixedSize(mVideoWidth, mVideoHeight);

        // set display size
        ViewGroup.LayoutParams lp = mSurface.getLayoutParams();
        lp.width = w;
        lp.height = h;
        mSurface.setLayoutParams(lp);
        mSurface.invalidate();
    }

    /*************
     * Player
     *************/

    private void createPlayer(Uri media) {
        releasePlayer();
        try {
            if (media != null) {
                Toast toast = Toast.makeText(this, media.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
                        0);
                toast.show();
            }

            // Create LibVLC
            // TODO: make this more robust, and sync with audio demo
            ArrayList<String> options = new ArrayList<String>();
            //options.add("--subsdec-encoding <encoding>");
            options.add("--aout=opensles");
            options.add("--audio-time-stretch"); // time stretching
            options.add("-vvv"); // verbosity
            options.add("--http-reconnect");
            options.add("--network-caching="+6*1000);
            options.add("--rtsp-tcp");
            libVLC = new LibVLC(getApplicationContext(),options);



            // Create media player
            mMediaPlayer = new MediaPlayer(libVLC);


            // Set up video output
            final IVLCVout vout = mMediaPlayer.getVLCVout();
            vout.setVideoView(mSurface);
            //vout.setSubtitlesView(mSurfaceSubtitles);
            vout.addCallback(this);
            vout.attachViews();

            Media m = new Media(libVLC, media);
            mMediaPlayer.setMedia(m);
            mMediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


       mSurface.setOnTouchListener(new OnSwipeTouchListenerImpl(getApplicationContext()));
    }

    private void releasePlayer() {
        if (libVLC == null)
            return;
        mMediaPlayer.stop();
        final IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.removeCallback(this);
        vout.detachViews();
        holder = null;
        libVLC.release();
        libVLC = null;

        mVideoWidth = 0;
        mVideoHeight = 0;
    }

    @Override
    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
        if (width * height == 0)
            return;

        // store video size
        mVideoWidth = width;
        mVideoHeight = height;
        setSize(mVideoWidth, mVideoHeight);
    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {

    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {

    }



    private class OnSwipeTouchListenerImpl extends OnSwipeTouchListener{

        public OnSwipeTouchListenerImpl(Context ctx) {
            super(ctx,mSurface);
        }

        @Override
        public void onSwipeRight() {
            super.onSwipeRight();
            Toast.makeText(VideoActivity.this,"RIGHT",Toast.LENGTH_SHORT).show();
            if(currentCamera!= cameraList.size()) {
                currentCamera++;
                Log.i("@@@CURRENTCAMERA",String.valueOf(currentCamera));
                releasePlayer();
               // createPlayer(cameraList.get(currentCamera).getUri());
            }
        }

        @Override
        public void onSwipeLeft() {
            super.onSwipeLeft();
            Toast.makeText(VideoActivity.this,"LEFT",Toast.LENGTH_SHORT).show();
            if(currentCamera!=0) {
                currentCamera--;
                Log.i("@@@CURRENTCAMERA",String.valueOf(currentCamera));
                releasePlayer();
               // createPlayer(cameraList.get(currentCamera).getUri());
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

    private class SwipeRefreshLayoutImpl implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    releasePlayer();
                    createPlayer(SAMPLE_URL);
                    refreshLayout.setRefreshing(false);
                }
            }, 5000);

        }
        }
    }



