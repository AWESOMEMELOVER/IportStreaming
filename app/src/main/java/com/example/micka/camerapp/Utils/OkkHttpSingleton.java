package com.example.micka.camerapp.Utils;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by micka on 7/18/2018.
 */

public class OkkHttpSingleton {

    private static OkkHttpSingleton instance;
    private static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkkHttpSingleton(){}

    public static OkkHttpSingleton getInstance() {
        if (instance == null) {
            instance = new OkkHttpSingleton();
        }
        return instance;
    }

    public JsonObject doPostRequest(Uri uri , JsonObject json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(uri.toString())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String stringResponce = response.body().toString();
        JsonObject convertedJson = new Gson().fromJson(stringResponce,JsonObject.class);
        return convertedJson;
    }

}
