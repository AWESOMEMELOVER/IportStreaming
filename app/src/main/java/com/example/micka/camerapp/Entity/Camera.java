package com.example.micka.camerapp.Entity;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by micka on 7/8/2018.
 */

//{"Id":11,"Name":"SNK-CAM-BAZA","ServerId":2,"zmUrl":"http://z.iport.net.ua/"}
    @SuppressWarnings("serial")
public class Camera implements Serializable{

    private int Id;
    private String zmUrl;
    private String Name;
    private int ServerId;

    Camera() {
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getUri() {
        return zmUrl;
    }

    public void setUri(String uri) {
        this.zmUrl = uri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getServerId() {
        return ServerId;
    }

    public void setServerId(int serverId) {
        this.ServerId = serverId;
    }

    @Override
    public String toString() {
       return Id+"-"+zmUrl+"-"+Name+"-"+ServerId;
    }
}
