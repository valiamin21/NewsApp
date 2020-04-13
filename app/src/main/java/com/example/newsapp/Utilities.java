package com.example.newsapp;

import android.content.Context;
import android.graphics.Typeface;

public class Utilities {


    public static Typeface getAppTypeFace(Context context) {
        return getMyApplication(context).getAppTypeface();
    }

    public static MyApplication getMyApplication(Context context) {
        return (MyApplication) context.getApplicationContext();
    }


}
