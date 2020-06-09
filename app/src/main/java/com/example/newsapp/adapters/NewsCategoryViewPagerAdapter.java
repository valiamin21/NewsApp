package com.example.newsapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.newsapp.data_model.Category;
import com.example.newsapp.views.fragments.NewsItemListFragment;

import java.util.List;

public class NewsCategoryViewPagerAdapter extends FragmentStateAdapter {
    private List<Category> categoryList;

    public NewsCategoryViewPagerAdapter(@NonNull FragmentActivity fa, List<Category> categoryList) {
        super(fa);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return NewsItemListFragment.newInstance(categoryList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();

    }
}
