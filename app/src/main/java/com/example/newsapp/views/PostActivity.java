package com.example.newsapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.api_services.PostApiService;
import com.example.newsapp.data_model.Post;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView postImageView;
    private TextView descriptionTextView;

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
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        postImageView = findViewById(R.id.post_image);
        descriptionTextView = findViewById(R.id.description_text);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 4/13/20 share
            }
        });
    }

    private void loadDataFromServer() {
        PostApiService.requestPost(this, postId, new PostApiService.OnPostApiFinished() {
            @Override
            public void onResponse(Post post) {
                Glide.with(PostActivity.this).load(post.getImage()).into(postImageView);
                collapsingToolbarLayout.setTitle(post.getTitle());
                descriptionTextView.setText(post.getLongDescription());
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                Toast.makeText(PostActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
