package com.example.micka.camerapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ProgressBar;

import com.example.micka.camerapp.R;
import com.example.micka.camerapp.Utils.SharePref;

public class LaunchActivity extends AppCompatActivity {

    private SharePref sharedPreference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        sharedPreference = SharePref.getInstance(getApplicationContext());
        progressBar = (ProgressBar) findViewById(R.id.pb_screen_load);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        sharedPreference.saveScreenSize(screenWidth,screenHeight);

        Intent intent = new Intent(this,CarouselActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }



}
