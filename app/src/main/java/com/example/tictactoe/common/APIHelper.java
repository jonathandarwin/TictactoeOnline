package com.example.tictactoe.common;

import android.util.Log;

public class APIHelper {
    private final static String TAG_RESPONSE = "<RES>";
    private final static String TAG_REQUEST = "<REQ>";
    private final static String TAG_ERROR = "<ERR>";

    public final static int RESPONSE_TYPE = 1;
    public final static int REQUEST_TYPE = 2;
    public final static int ERROR_TYPE = 3;

    public static void LOG(int type, String message){
        Log.d(type == REQUEST_TYPE ? TAG_REQUEST : type == RESPONSE_TYPE ? TAG_RESPONSE : TAG_ERROR, message);
    }
}
