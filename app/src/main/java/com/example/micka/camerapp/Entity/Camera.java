package com.example.micka.camerapp.Entity;

import android.net.Uri;

/**
 * Created by micka on 7/8/2018.
 */

public class Camera {

    private int id;
    private Uri uri;

    public Camera(int id, Uri uri) {
        this.id = id;
        this.uri = uri;
    }

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
}
