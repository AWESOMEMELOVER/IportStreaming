package com.example.micka.camerapp.EventHandlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by micka on 7/23/2018.
 */

public interface GetResponce<T1,T2> {
    public void  getData(T1 json);
}
