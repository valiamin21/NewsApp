package com.example.newsapp;

import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {

    private static Typeface appTypeface;

    public Typeface getAppTypeface() {
        if (appTypeface == null) {
            appTypeface = Typeface.createFromAsset(getAssets(), "fonts/Vazir.ttf");
        }

        return appTypeface;
    }
}
