package com.example.micka.camerapp.Activities;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.micka.camerapp.Entity.Camera;
import com.example.micka.camerapp.EventHandlers.GetResponce;
import com.example.micka.camerapp.R;
import com.example.micka.camerapp.Utils.AsyncReuse;
import com.example.micka.camerapp.Utils.OkkHttpSingleton;
import com.example.micka.camerapp.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.micka.camerapp.Utils.ImageThread.JSON;

public class CarouselActivity extends AppCompatActivity implements GetResponce<JsonArray,List<Camera>> {

    private CarouselView carouselView;
    int[] sampleImages = {R.drawable.circle, R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground};
    List<Camera> myList;
    AsyncReuse asyncReuse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);



        carouselView = (CarouselView) findViewById(R.id.carouselView);


        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user","iport");
        executeServerReq(jsonObject);








    }

    public void executeServerReq(JsonObject jsonObject){
        asyncReuse = new AsyncReuse(" http://z.iport.net.ua:81/rest/getMonitors",true,CarouselActivity.this,"POST");
        asyncReuse.setJsonObject(jsonObject);
        asyncReuse.getResponse = this;
        asyncReuse.execute();
    }



    public void getData(JsonArray json) {
        Log.i("@@@CAMERA",json.toString());
        myList = jsonArrayToList(json);
        Log.i("@@@ARRAYSIZE", String.valueOf(myList.size()));
      /*  for(Camera camera:myList)
            Log.i("@@@CAMERA",camera.toString());*/
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* carouselView.setPageCount(myList.size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
               Picasso.with(getApplicationContext()).load(myList.get(position).getUri()).into(imageView);
            }
        });
    }*/
    }

    private List<Camera> jsonArrayToList(JsonArray jsonArray){
        Gson gson = new Gson();
        List<Camera> list = new ArrayList<>();
        /*for(int i =0; i<jsonArray.size();i++){
            JsonElement jsonObject = jsonArray.get(i);
           Camera camera = gson.fromJson(jsonObject,new Camera().getClass());
            Log.i("@@@CAMERATEST",camera.toString());
            list.add(camera);
        }*/

        return list;
    }
}
