package com.example.micka.camerapp.Entity;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.example.micka.camerapp.Utils.SharePref;
import com.example.micka.camerapp.Utils.Utils;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

/**
 * Created by micka on 7/27/2018.
 */

public class Stream  implements IVLCVout.Callback{



    private String STREAM_URL=null;
    private final String CLASS_TAG = "@@@STREAM/CLASS";
    private LibVLC libVLC;
    private MediaPlayer mMediaPlayer;
    private Media media;
    private ArrayList<String> options = new ArrayList<>();
    private Context ctx;
    private IVLCVout vout;
    private SurfaceView surfaceView;
    private int mVideoWidth,mVideoHeight;
    private SurfaceHolder surfaceHolder;

    public Stream(Context ctx,Camera camera,SurfaceView surfaceView,int mVideoWidth,int mVideoHeight){

        this.ctx = ctx;
        this.surfaceView = surfaceView;
        this.mVideoWidth=mVideoWidth;
        this.mVideoHeight = mVideoHeight;
        this.surfaceHolder = surfaceView.getHolder();
        this.STREAM_URL = camera.getUri();


        options.add("-vvv"); // verbosity
        options.add("--http-reconnect");
        options.add("--network-caching="+6*1000);
        options.add("--rtsp-tcp");

        libVLC = new LibVLC(ctx,options);
        mMediaPlayer = new MediaPlayer(libVLC);
        vout = mMediaPlayer.getVLCVout();


    }

    public void createPlayer(){
        vout.addCallback(this);
        media = new Media(libVLC, Uri.parse(STREAM_URL));
    }

    public void startPlayer(){
        mMediaPlayer.setMedia(media);
        mMediaPlayer.play();
    }

    public void releasePlayer(){
        if (libVLC == null)
            return;
        mMediaPlayer.stop();
        final IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.removeCallback(this);
        vout.detachViews();
        libVLC.release();
        libVLC = null;

    }


    public void changeURL(Camera switchToCamera){
        releasePlayer();
        this.STREAM_URL = Utils.parseToUrl(switchToCamera);
        createPlayer();
        startPlayer();
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
    private void setSize(int width, int height) {
        mVideoWidth = width;
        mVideoHeight = height;
        if (mVideoWidth * mVideoHeight <= 1)
            return;

        if(surfaceHolder == null || surfaceView == null)
            return;

        // get screen size
        int w = SharePref.getInstance(ctx).getScreenWidth();
        int h = SharePref.getInstance(ctx).getScreenHeight();

        // getWindow().getDecorView() doesn't always take orientation into
        // account, we have to correct the values
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

        // force surface buffer size
        surfaceHolder.setFixedSize(mVideoWidth, mVideoHeight);

        // set display size
        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.width = w;
        lp.height = h;
        surfaceView.setLayoutParams(lp);
        surfaceView.invalidate();
    }

    public LibVLC getLibVLC() {
        return libVLC;
    }

    public MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    public Media getMedia() {
        return media;
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;

    }


    public void setOnEventListener(MediaPlayer.EventListener eventListener){
        mMediaPlayer.setEventListener(eventListener);
    }
}
