package com.example.newsapp.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.R;
import com.example.newsapp.adapters.NewsListRecyclerAdapter;
import com.example.newsapp.api_services.NewsListApiService;
import com.example.newsapp.data_model.NewsItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NewsItemListFragment extends Fragment {
    private static final String ARG_CATEGORY_ID = "category_id";

    private int categoryId;

    private RecyclerView newsListRecyclerView;
    private NewsListRecyclerAdapter recyclerAdapter;

    private OnNewsListFragmentErrorResponse onNewsListFragmentErrorResponse;

    public NewsItemListFragment() {
        // Required empty public constructor
    }

    public static NewsItemListFragment newInstance(int categoryId) {
        NewsItemListFragment fragment = new NewsItemListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnNewsListFragmentErrorResponse){
            onNewsListFragmentErrorResponse = (OnNewsListFragmentErrorResponse) context;
        }else{
            throw new ClassCastException(context.toString() + " must implement OnNewsListFragmentErrorResponse");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_item_list, container, false);
        newsListRecyclerView = view.findViewById(R.id.recyclerView_newsList);
        loadDataFromServer();
        return view;
    }

    private void loadDataFromServer() {
        NewsListApiService.requestNewsList(getContext(), categoryId, new NewsListApiService.OnNewsListApiFinished() {
            @Override
            public void onResponse(List<NewsItem> newsItemList) {
                if (recyclerAdapter == null) {
                    recyclerAdapter = new NewsListRecyclerAdapter(getContext(), newsItemList);
                    newsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    newsListRecyclerView.setAdapter(recyclerAdapter);
                } else {
                    recyclerAdapter.refresh(newsItemList);
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                onNewsListFragmentErrorResponse.onErrorResponse(errorMessage);
            }
        });
    }

    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
        loadDataFromServer();
    }

    public interface OnNewsListFragmentErrorResponse{
        void onErrorResponse(String errorMessage);
    }
}
