package com.example.micka.camerapp.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.micka.camerapp.EventHandlers.GetResponce;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by micka on 7/23/2018.
 */

public class AsyncReuse extends AsyncTask<Void,Void,Void> {




    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String URLs;
    private boolean dialogE = true;
    private Activity activity;
    public GetResponce getResponse = null;
    // Dialog builder
    private ProgressDialog Dialog;
    private String TYPE_OF_REQUEST;
    private JsonObject jsonObject;
    private Response response;

    public AsyncReuse(String URLs,boolean dialogE,Activity activity,String type) {
        this.URLs = URLs;
        this.dialogE = dialogE;
        this.activity = activity;
        this.TYPE_OF_REQUEST = type;
        Dialog = new ProgressDialog(activity);
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(dialogE){
            Dialog.setMessage("Wait..");
            Dialog.setCancelable(false);
            Dialog.show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            if(TYPE_OF_REQUEST == "POST") {
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(URLs)
                        .post(body)
                        .build();

               response = client.newCall(request).execute();
            }else if(TYPE_OF_REQUEST == "GET"){
                Request request = new Request.Builder()
                        .url(URLs)
                        .build();
               response = client.newCall(request).execute();
            }else {
                Dialog.setMessage("No Type parameter ..");
                Dialog.setCancelable(true);
                Dialog.show();
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
        if (response.code()==200) {

            String stringResponce = response.body().string();
            JsonArray convertedJson = new Gson().fromJson(stringResponce, JsonArray.class);

                getResponse.getData(convertedJson);

        }else {
            Toast.makeText(activity, "Unable to reach server try again", Toast.LENGTH_LONG).show();
        }
        if (dialogE) {
            Dialog.dismiss();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
