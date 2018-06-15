package com.example.micka.camerapp;

import android.net.Uri;

import java.util.HashMap;

public class CameraURI {

    public static final Uri URI_ONE = Uri.parse("rtsp://admin:3edcvfr4@10.10.10.66:554/cam/realmonitor?channel=1&subtype=0");
    public static final Uri URI_TWO = Uri.parse("rtsp://admin:3edcvfr4@10.10.10.25:554/ch01/0");
    public static final Uri URI_THREE = Uri.parse("rtsp://admin:3edcvfr4@10.10.10.42:554/ch01/0");
    public static final Uri URI_FORTH = Uri.parse("rtsp://admin:3edcvfr4@10.10.10.14:554/Streaming/Channels/101");

    public static final HashMap<Integer, Uri> uriList;
    static {
        uriList =  new HashMap<Integer, Uri>();
        uriList.put(1,URI_ONE);
        uriList.put(2,URI_TWO);
        uriList.put(3,URI_THREE);
        uriList.put(4,URI_FORTH);
    }


}
