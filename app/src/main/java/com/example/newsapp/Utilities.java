package com.example.newsapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Utilities {

    public static void applyFontForAViewGroup(ViewGroup viewGroup, Activity context) {
        View view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(getAppTypeFace(context));
            } else if (view instanceof ViewGroup) {
                applyFontForAViewGroup((ViewGroup) view, context);
            }
        }
    }

    public static Typeface getAppTypeFace(Context context) {
        return getMyApplication(context).getAppTypeface();
    }

    public static MyApplication getMyApplication(Context context) {
        return (MyApplication) context.getApplicationContext();
    }


    // TODO: 5/10/20 offline mode handling (local database)
    // TODO: 5/10/20 making library for typeface management
    // TODO: 6/7/20 modify date showing implementation
    // TODO: 6/7/20 making categories activity
    // TODO: 6/8/20 connection error handling and empty category handling
    // TODO: 6/12/20 implementing pagination in app
    // TODO: 6/12/20 specifying posts that already have been viewed
    // TODO: 6/14/20 using animations in RecyclerViews
}