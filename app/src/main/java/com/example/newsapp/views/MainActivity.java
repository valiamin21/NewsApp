package com.example.newsapp.views;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.adapters.CategoryTagsRecyclerAdapter;
import com.example.newsapp.api_services.CategoriesApiService;
import com.example.newsapp.data_model.Category;
import com.example.newsapp.views.fragments.NewsItemListFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import static com.example.newsapp.api_services.NewsListApiService.CATEGORY_DEFAULT_ID;

public class MainActivity extends AppCompatActivity implements CategoryTagsRecyclerAdapter.OnCategoryTagItemSelected {

    private static final String TAG = "NewsPostListActivity";

    private RecyclerView categoryTagsRecyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,NewsItemListFragment.newInstance(CATEGORY_DEFAULT_ID))
                .commit();

        CategoriesApiService.requestCategoriesList(this, new CategoriesApiService.OnCategoriesApiFinished() {
            @Override
            public void onResponse(List<Category> categoryList) {
                Toast.makeText(MainActivity.this, "received", Toast.LENGTH_SHORT).show();
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
        setSupportActionBar(toolbar);

        categoryTagsRecyclerView = findViewById(R.id.categoryTags_recyclerView);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerToggle_open,R.string.drawerToggleClose);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    @Override // @Implemented :D
    public void onSelected(Category category) {
        toolbar.setTitle(category.getName());
    }
}
