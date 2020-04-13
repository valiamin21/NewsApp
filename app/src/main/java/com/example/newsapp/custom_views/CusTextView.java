package com.example.newsapp.custom_views;

import android.content.Context;
import android.util.AttributeSet;

import com.example.newsapp.Utilities;

public class CusTextView extends androidx.appcompat.widget.AppCompatTextView {


    public CusTextView(Context context) {
        super(context);
        setTypeface();
    }

    public CusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface();
    }

    public CusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface();
    }

    private void setTypeface() {
        if (!isInEditMode()) {
            setTypeface(Utilities.getAppTypeFace(getContext()));
        }
    }
}
