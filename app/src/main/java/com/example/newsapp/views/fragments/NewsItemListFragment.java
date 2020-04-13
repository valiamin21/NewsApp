package com.example.newsapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.adapters.NewsListRecyclerAdapter;
import com.example.newsapp.api_services.NewsListApiService;
import com.example.newsapp.data_model.NewsItem;

import java.util.List;

public class NewsItemListFragment extends Fragment {
    private static final String ARG_CATEGORY_ID = "category_id";

    private int categoryId;

    private RecyclerView newsListRecyclerView;
    private NewsListRecyclerAdapter recyclerAdapter;

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
                    Toast.makeText(getContext(), "" + newsItemList.size(), Toast.LENGTH_SHORT).show();
                    recyclerAdapter = new NewsListRecyclerAdapter(getContext(), newsItemList);
                    newsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    newsListRecyclerView.setAdapter(recyclerAdapter);
                } else {
                    // TODO: 4/13/20
                }
            }

            @Override
            public void onErrorResponse(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
