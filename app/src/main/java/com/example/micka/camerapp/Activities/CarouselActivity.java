package com.example.micka.camerapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.micka.camerapp.Entity.Camera;
import com.example.micka.camerapp.Entity.Stream;
import com.example.micka.camerapp.Interfaces.GetResponce;
import com.example.micka.camerapp.R;
import com.example.micka.camerapp.Utils.AsyncReuse;
import com.example.micka.camerapp.Utils.ImageThread;
import com.example.micka.camerapp.Utils.SharePref;
import com.example.micka.camerapp.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.videolan.libvlc.MediaPlayer;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CarouselActivity extends AppCompatActivity implements GetResponce<JsonArray,List<Camera>> {

    private CarouselView carouselView;
    private List<Camera> myList;
    private AsyncReuse asyncReuse;
    private RelativeLayout carouselHolder;
    private Handler handler;
    private SurfaceView surfaceView;
    private Stream stream;

    private  final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        carouselHolder = findViewById(R.id.carousel_holder);
        carouselView = findViewById(R.id.carouselView);
        surfaceView = findViewById(R.id.sv_stream);

        handler = new Handler();

        Camera camera = new Camera();
        camera.setUri("rtsp://admin:3edcvfr4@91.226.253.6:30554");
        /*stream = new Stream(getApplicationContext(),camera,surfaceView,1920,1080);
        stream.createPlayer();*/

        Log.i("@@@carouselSie",String.valueOf(carouselView.getWidth())+":"+String.valueOf(carouselView.getHeight()));

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user","iport");
        executeServerReq(jsonObject);

        Utils.setSize(carouselHolder,1920,1080,getApplication());

    }




    public void getData(JsonArray json) {
        Log.i("@@@CAMERA", json.toString());
        Log.i("@@@ARRAYSIZE", String.valueOf(json.size()));
        Type listType = new TypeToken<List<Camera>>() {
        }.getType();
        myList = new Gson().fromJson(json.toString(), listType);


       carouselView.setImageListener((position, imageView) -> {
           handler = new Handler((Message msg)->setBitmapForImageView(msg,imageView));
           service.scheduleWithFixedDelay(new ImageThread(handler, Utils.parseToUrl(myList.get(position))),500,500,TimeUnit.MILLISECONDS);
       });
       carouselView.setPageCount(myList.size());

    }

    @Override
    protected void onStart() {
        super.onStart();
        carouselView.setImageClickListener((int position)->startActivityFromIntent(position) );


    }

    private void startActivityFromIntent(int position){
        Camera camera = myList.get(position);
        Toast.makeText(CarouselActivity.this, "Clicked item: "+ camera.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),PreStreamActivity.class);
        intent.putExtra("transferObject",camera);
        startActivity(intent);
    }
    public void executeServerReq(JsonObject jsonObject){
        asyncReuse = new AsyncReuse(" http://z.iport.net.ua:81/rest/getMonitors",true,CarouselActivity.this,"POST");
        asyncReuse.setJsonObject(jsonObject);
        asyncReuse.getResponse = this;
        asyncReuse.execute();
    }


    private boolean setBitmapForImageView(Message msg, ImageView imageView){
        Bitmap bitmap = (Bitmap) msg.obj;
        imageView.setImageBitmap(bitmap);
        if (imageView.getDrawable()!=null)
            return true;
        return false;

    }




}
