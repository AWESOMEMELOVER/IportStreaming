package com.example.micka.camerapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URI;

public class VideoActivity extends AppCompatActivity {

    private final String TAG = "VIDEO URL: ";
    private final String URL = "rtsp://admin:3edcvfr4@10.10.10.66:554/cam/realmonitor?channel=1&subtype=0";
    private VideoView videoView;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = (VideoView) findViewById(R.id.vv_video_holder);
    }

    @Override
    protected void onStart() {
        super.onStart();
        uri = Uri.parse(URL);
        Log.i(TAG,uri.toString());

        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();


    }
}
