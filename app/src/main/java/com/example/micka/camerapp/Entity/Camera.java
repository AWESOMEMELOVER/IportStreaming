package com.example.micka.camerapp.Entity;

import android.net.Uri;

/**
 * Created by micka on 7/8/2018.
 */

//{"Id":11,"Name":"SNK-CAM-BAZA","ServerId":2,"zmUrl":"http://z.iport.net.ua/"}
public class Camera {

    private int id;
    private String uri;
    private String name;
    private int serverId;

   public Camera() {

    }

    public Camera(int id, String uri, String name, int serverId) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.serverId = serverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Override
    public String toString() {
       return id+"-"+uri+"-"+name+"-"+serverId;
    }
}
