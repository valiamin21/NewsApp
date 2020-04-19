package com.example.newsapp.views;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.example.newsapp.R;
import com.example.newsapp.views.fragments.NewsItemListFragment;

import static com.example.newsapp.api_services.NewsListApiService.CATEGORY_DEFAULT_ID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "NewsPostListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,NewsItemListFragment.newInstance(CATEGORY_DEFAULT_ID))
                .commit();
    }


    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerToggle_open,R.string.drawerToggleClose);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
}
