package com.example.newsapp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.api_services.PostApiService;
import com.example.newsapp.data_model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PostActivity extends AppCompatActivity {

    public static final String INTENT_KEY_POST_ID = "post_id";

    public static void start(Context context, int postId) {
        Intent starter = new Intent(context, PostActivity.class);
        starter.putExtra(INTENT_KEY_POST_ID, postId);
        context.startActivity(starter);
    }

    private ImageView postImageView;
    private TextView titleTextView, descriptionTextView, categoryTextView, dateTextView;

    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setupViews();

        postId = getIntent().getIntExtra(INTENT_KEY_POST_ID, -1);

        loadDataFromServer();
    }

    private void setupViews() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        postImageView = findViewById(R.id.post_image);
        titleTextView = findViewById(R.id.title_text);
        descriptionTextView = findViewById(R.id.description_text);
        categoryTextView = findViewById(R.id.category_text);
        dateTextView = findViewById(R.id.date_text);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 4/13/20 share
            }
        });

        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY > oldScrollY)
                        fab.hide();
                    else
                        fab.show();
                }
            });
        }
    }

    private void loadDataFromServer() {
        PostApiService.requestPost(this, postId, new PostApiService.OnPostApiFinished() {
            @Override
            public void onResponse(Post post) {
                if(post == null)
                    return;
                Glide.with(PostActivity.this).load(post.getImage()).into(postImageView);
                titleTextView.setText(post.getTitle());
                descriptionTextView.setText(post.getLongDescription());
                categoryTextView.setText(post.getCategoryName());
                dateTextView.setText(post.getDate());
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                Toast.makeText(PostActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        finish();
        start(this,postId);
    }
}
