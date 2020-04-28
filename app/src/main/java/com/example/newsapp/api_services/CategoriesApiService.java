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
import com.example.newsapp.data_model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesApiService {
    private static final String TAG = "CategoriesApiService";

    public static void requestCategoriesList(Context context, final OnCategoriesApiFinished onCategoriesApiFinished){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(context.getString(R.string.root_api_url) + "/categories/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            List<Category> categoryList = new ArrayList<>();
                            Category category;
                            JSONObject jsonObject;
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                category = new Category();
                                category.setId(jsonObject.getInt("id"));
                                category.setName(jsonObject.getString("name"));
                                categoryList.add(category);
                            }

                            onCategoriesApiFinished.onResponse(categoryList);
                        }catch (JSONException e){
                            onCategoriesApiFinished.onErrorResponse("json parse error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCategoriesApiFinished.onErrorResponse("communication error");
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(8000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public interface OnCategoriesApiFinished{
        void onResponse(List<Category> categoryList);
        void onErrorResponse(String errorMessage);
    }

}
