package com.example.newsapp.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.newsapp.R;
import com.example.newsapp.Utilities;
import com.example.newsapp.adapters.CategoryTagsRecyclerAdapter;
import com.example.newsapp.api_services.CategoriesApiService;
import com.example.newsapp.data_model.Category;
import com.example.newsapp.views.fragments.NewsItemListFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.example.newsapp.api_services.NewsListApiService.CATEGORY_DEFAULT_ID;

public class MainActivity extends AppCompatActivity implements CategoryTagsRecyclerAdapter.OnCategoryTagItemSelected {

    private static final String TAG = "NewsPostListActivity";

    private RecyclerView categoryTagsRecyclerView;
    private Toolbar toolbar;
    private ImageView collapsingImageView;

    private NewsItemListFragment newsItemListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        newsItemListFragment = NewsItemListFragment.newInstance(CATEGORY_DEFAULT_ID);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,newsItemListFragment)
                .commit();

        CategoriesApiService.requestCategoriesList(this, new CategoriesApiService.OnCategoriesApiFinished() {
            @Override
            public void onResponse(List<Category> categoryList) {
                CategoryTagsRecyclerAdapter adapter = new CategoryTagsRecyclerAdapter(MainActivity.this,categoryList, MainActivity.this);
                categoryTagsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                // TODO: 4/28/20
            }
        });
    }

    private void setupViews() {
        toolbar = findViewById(R.id.toolbar);
        Utilities.applyFontForAViewGroup(toolbar,this);
        setSupportActionBar(toolbar);

        categoryTagsRecyclerView = findViewById(R.id.categoryTags_recyclerView);
        collapsingImageView = findViewById(R.id.collapsing_image);
        startCollapsingImageAnimation();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerToggle_open,R.string.drawerToggleClose);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        final NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utilities.applyFontForAViewGroup(navigationView,MainActivity.this);
            }
        },100);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "onNavigationItemSelected: " + item.getTitle());
                return false;
            }
        });
    }

    private void startCollapsingImageAnimation(){
        int animationDuration = 20000;
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(2f,4f,2f,3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,-0.3f,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        translateAnimation.setDuration(animationDuration);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);

        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(animationDuration * 10);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);
        collapsingImageView.startAnimation(animationSet);
    }

    @Override // @Implemented :D
    public void onSelected(Category category) {
        toolbar.setTitle(category.getName());
        newsItemListFragment.setCategoryId(category.getId());
    }
}
