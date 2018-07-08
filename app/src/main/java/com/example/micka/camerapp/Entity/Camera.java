package com.example.micka.camerapp.Entity;

import android.net.Uri;

/**
 * Created by micka on 7/8/2018.
 */

public class Camera {

    private int id;
    private Uri uri;
    private int width,height;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Camera(int id, Uri uri) {
        this.id = id;
        this.uri = uri;
    }
}
