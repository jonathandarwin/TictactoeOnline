package com.example.tictactoe.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void make(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
