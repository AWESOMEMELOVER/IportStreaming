package com.example.micka.camerapp.EventHandlers;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ProgressBar;

public class OnPreparedVideoViewListener implements MediaPlayer.OnPreparedListener {

    private Context ctx;
    private ProgressBar progressBar;

    public OnPreparedVideoViewListener(final Context ctx, ProgressBar progressBar){
        this.ctx = ctx;
        this.progressBar = progressBar;
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                           int arg2) {
                progressBar.setVisibility(View.GONE);
                mp.start();
            }
        });
    }
}
