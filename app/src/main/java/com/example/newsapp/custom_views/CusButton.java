package com.example.newsapp.custom_views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.newsapp.Utilities;


public class CusButton extends AppCompatButton {
    public CusButton(Context context) {
        super(context);
        setTypeface();
    }

    public CusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface();
    }

    public CusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface();
    }

    private void setTypeface() {
        if (!isInEditMode()) {
            setTypeface(Utilities.getAppTypeFace(getContext()));
        }
    }
}
