package com.example.micka.camerapp.Activities;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.micka.camerapp.R;
import com.example.micka.camerapp.Utils.OkkHttpSingleton;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;
import java.net.URL;

import static com.example.micka.camerapp.Utils.ImageThread.JSON;

public class CarouselActivity extends AppCompatActivity {

    private CarouselView carouselView;
    int[] sampleImages = {R.drawable.circle, R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);



        carouselView = (CarouselView) findViewById(R.id.carouselView);

        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });

    }



}
