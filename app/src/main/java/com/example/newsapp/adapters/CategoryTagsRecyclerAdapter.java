package com.example.newsapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.api_services.NewsListApiService;
import com.example.newsapp.data_model.Category;

import java.util.List;

public class CategoryTagsRecyclerAdapter extends RecyclerView.Adapter<CategoryTagsRecyclerAdapter.CategoryTagViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private final OnCategoryTagItemSelected onCategoryTagItemSelected;
    private int selectedItem = 0;

    public CategoryTagsRecyclerAdapter(Context context, List<Category> categoryList, OnCategoryTagItemSelected onCategoryTagItemSelected) {
        this.context = context;
        this.categoryList = categoryList;
        this.onCategoryTagItemSelected = onCategoryTagItemSelected;

        Category allNewsCategory = new Category();
        allNewsCategory.setName(context.getString(R.string.all_news));
        allNewsCategory.setId(NewsListApiService.CATEGORY_DEFAULT_ID);
        categoryList.add(0,allNewsCategory);
    }

    @NonNull
    @Override
    public CategoryTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryTagViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryTagViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        holder.categoryNameTextView.setText(category.getName());

        if (position != selectedItem) {
            holder.categoryTagCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.categoryNameTextView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
        }else{
            holder.categoryTagCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.categoryNameTextView.setTextColor(ContextCompat.getColor(context,R.color.white));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notifyItemChanged(selectedItem);
                selectedItem = holder.getAdapterPosition();
                notifyItemChanged(selectedItem);

                onCategoryTagItemSelected.onSelected(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryTagViewHolder extends RecyclerView.ViewHolder {
        private CardView categoryTagCardView;
        private TextView categoryNameTextView;

        public CategoryTagViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTagCardView = itemView.findViewById(R.id.categoryTag_cardView);
            categoryNameTextView = itemView.findViewById(R.id.category_name_text);
        }
    }

    public interface OnCategoryTagItemSelected{
        void onSelected(Category category);
    }
}
