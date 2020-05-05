package com.example.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.data_model.NewsItem;
import com.example.newsapp.views.PostActivity;

import java.util.List;

public class NewsListRecyclerAdapter extends RecyclerView.Adapter<NewsListRecyclerAdapter.NewsItemViewHolder> {

    private Context context;
    private List<NewsItem> newsItemList;

    public NewsListRecyclerAdapter(Context context, List<NewsItem> newsItemList){
        this.context = context;
        this.newsItemList = newsItemList;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        final NewsItem newsItem = newsItemList.get(position);
        holder.titleText.setText(newsItem.getTitle());
        holder.shortDescriptionText.setText(newsItem.getShortDescription());
        Glide.with(context).load(newsItem.getImage()).into(holder.newsImage);
        holder.bookmarkButton.setImageResource(newsItem.isBookmarked()? R.drawable.ic_bookmark_black_24dp: R.drawable.ic_bookmark_border_black_24dp);
        holder.categoryNameText.setText(newsItem.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.start(context,newsItem.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public void refresh(List<NewsItem> newsItemList){
        this.newsItemList.clear();
        this.newsItemList.addAll(newsItemList);
        notifyDataSetChanged();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView newsImage;
        private TextView titleText, shortDescriptionText, categoryNameText;
        private ImageButton bookmarkButton;

        public NewsItemViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.news_image);
            titleText = itemView.findViewById(R.id.title_text);
            shortDescriptionText = itemView.findViewById(R.id.shortDescription_text);
            categoryNameText = itemView.findViewById(R.id.category_name_text);
            bookmarkButton = itemView.findViewById(R.id.bookmark_button);

            newsImage.setClipToOutline(true);
        }
    }
}
