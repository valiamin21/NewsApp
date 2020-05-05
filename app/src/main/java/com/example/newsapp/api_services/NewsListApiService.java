package com.example.newsapp.api_services;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.R;
import com.example.newsapp.data_model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsListApiService {
    private static final String TAG = "NewsListApiService";
    public static final int CATEGORY_DEFAULT_ID = -1;

    public static void requestNewsList(final Context context, int categoryId, final OnNewsListApiFinished onNewsListApiFinished) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(context.getString(R.string.root_api_url) + (categoryId == CATEGORY_DEFAULT_ID ? "/news/" : "/categories/" + categoryId),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<NewsItem> newsItemList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                NewsItem newsItem = new NewsItem();
                                newsItem.setId(jsonObject.getInt("id"));
                                newsItem.setTitle(jsonObject.getString("title"));
                                newsItem.setShortDescription(jsonObject.getString("short_description"));
                                newsItem.setImage(context.getString(R.string.root_api_url) + jsonObject.getString("image"));
                                newsItem.setCategoryName(jsonObject.getString("category_name"));
                                newsItem.setCategoryId(jsonObject.getInt("category"));

                                newsItemList.add(newsItem);
                            }

                            onNewsListApiFinished.onResponse(newsItemList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onNewsListApiFinished.onErrorResponse("json parse error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onNewsListApiFinished.onErrorResponse("communication error ");
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
        requestQueue.start();
    }

    public interface OnNewsListApiFinished {
        void onResponse(List<NewsItem> newsItemList);

        void onErrorResponse(String errorMessage);
    }

}
