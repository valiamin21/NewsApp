package com.example.newsapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.newsapp.R;
import com.example.newsapp.Utilities;

public class AboutUsActivity extends AppCompatActivity {

    public static void start(Context context){
        context.startActivity(new Intent(context, AboutUsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setupViews();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Utilities.applyFontForAViewGroup(toolbar,this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
