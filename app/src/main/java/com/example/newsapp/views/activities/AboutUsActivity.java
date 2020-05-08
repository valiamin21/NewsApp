package com.example.newsapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.newsapp.R;

public class AboutUsActivity extends AppCompatActivity {

    public static void start(Context context){
        context.startActivity(new Intent(context, AboutUsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }
}
