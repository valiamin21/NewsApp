package com.example.newsapp.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.OnTabSelectionChangedListener;
import com.example.newsapp.R;
import com.example.newsapp.Utilities;
import com.example.newsapp.adapters.NewsCategoryViewPagerAdapter;
import com.example.newsapp.api_services.CategoriesApiService;
import com.example.newsapp.data_model.Category;
import com.example.newsapp.views.fragments.NewsItemListFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import static com.example.newsapp.api_services.NewsListApiService.CATEGORY_DEFAULT_ID;


public class MainActivity extends AppCompatActivity implements CategoriesApiService.OnCategoriesApiFinished, NewsItemListFragment.OnNewsListFragmentErrorResponse {

    private ImageView collapsingImageView;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        loadDataFromServer();
    }

    private void loadDataFromServer() {
        CategoriesApiService.requestCategoriesList(this, this);
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        Utilities.applyFontForAViewGroup(toolbar, this);
        setSupportActionBar(toolbar);

        collapsingImageView = findViewById(R.id.collapsing_image);
        startCollapsingImageAnimation();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerToggle_open, R.string.drawerToggleClose);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        final NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utilities.applyFontForAViewGroup(navigationView, MainActivity.this);
            }
        }, 100);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_categories:

                        break;
                    case R.id.nav_settings:

                        break;
                    case R.id.nav_about:
                        AboutUsActivity.start(MainActivity.this);
                        break;
                }
                return false;
            }
        });

        viewPager = findViewById(R.id.news_ViewPager);
        tabLayout = findViewById(R.id.categoryTags_tabLayout);

        tabLayout.addOnTabSelectedListener(new OnTabSelectionChangedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                applySelectionStateToTabLayoutTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                applySelectionStateToTabLayoutTab(tab);
            }
        });
    }

    private void applySelectionStateToTabLayoutTab(TabLayout.Tab tab, boolean isSelected) {
        if (isSelected) {
            ((CardView) tab.getCustomView()).setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.not_selected_tag_background_color));
            ((TextView) tab.getCustomView().findViewById(R.id.category_name_text)).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        } else {
            ((CardView) tab.getCustomView()).setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            ((TextView) tab.getCustomView().findViewById(R.id.category_name_text)).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.not_selected_tag_background_color));
        }
    }

    private void applySelectionStateToTabLayoutTab(TabLayout.Tab tab) {
        applySelectionStateToTabLayoutTab(tab, tab.isSelected());
    }

    private void startCollapsingImageAnimation() {
        int animationDuration = 20000;
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(2f, 4f, 2f, 3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.3f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setDuration(animationDuration);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(animationDuration * 10);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);
        collapsingImageView.startAnimation(animationSet);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onResponse(final List<Category> categoryList) {
        // category for showing all news
        Category category = new Category();
        category.setName(getString(R.string.all_news));
        category.setId(CATEGORY_DEFAULT_ID);
        categoryList.add(0, category);

        viewPager.setAdapter(new NewsCategoryViewPagerAdapter(MainActivity.this, categoryList));
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, final int position) {
                tab.setCustomView(R.layout.item_category_tab_layout_tag);
                ((TextView) tab.getCustomView().findViewById(R.id.category_name_text)).setText(categoryList.get(position).getName());

                applySelectionStateToTabLayoutTab(tab, position == viewPager.getCurrentItem());

                tab.getCustomView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(position);
                    }

                });
            }
        }).attach();
    }

    @Override
    public void onErrorResponse(String errorMessage) {
        Snackbar.make(viewPager, R.string.server_communication_problem, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDataFromServer();
                    }
                }).show();
    }
}
