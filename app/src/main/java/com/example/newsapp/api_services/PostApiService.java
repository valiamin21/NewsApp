package com.example.newsapp.api_services;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.R;
import com.example.newsapp.data_model.Post;
import com.example.newsapp.open_helpers.PostsOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class PostApiService {

    private static PostsOpenHelper postsOpenHelper;

    public static void requestPost(final Context context, int postId, final OnPostApiFinished onPostApiFinished) {
        if(postsOpenHelper == null){
            postsOpenHelper = new PostsOpenHelper(context);
        }
        onPostApiFinished.onResponse(postsOpenHelper.getPost(postId));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(context.getString(R.string.root_api_url) + "/news/" + postId,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Post post = new Post();
                    post.setId(response.getInt("id"));
                    post.setTitle(response.getString("title"));
                    post.setLongDescription(response.getString("long_description"));
                    post.setImage(context.getString(R.string.root_api_url) + response.getString("image"));
                    post.setDate(response.getString("date_create"));
                    post.setCategoryName(response.getString("category_name"));
                    post.setCategoryId(response.getInt("category"));

                    postsOpenHelper.savePost(post);
                    onPostApiFinished.onResponse(post);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onPostApiFinished.onErrorResponse("json parse error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onPostApiFinished.onErrorResponse("server communication error");
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public interface OnPostApiFinished {
        void onResponse(@Nullable Post post);

        void onErrorResponse(String errorMessage);
    }
}
