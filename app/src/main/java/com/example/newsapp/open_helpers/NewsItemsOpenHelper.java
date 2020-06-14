package com.example.newsapp.open_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newsapp.data_model.NewsItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.newsapp.api_services.NewsListApiService.CATEGORY_DEFAULT_ID;

public class NewsItemsOpenHelper extends SQLiteOpenHelper {
    private static final String NEWS_ITEMS_DB_NAME = "news_items_db";
    private static final int NEWS_ITEMS_DB_VERSION = 1;

    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_SHORT_DESCRIPTION = "short_description";
    private static final String COL_IMAGE = "image";
    private static final String COL_CATEGORY_NAME = "category_name";
    private static final String COL_CATEGORY_ID = "category_id";

    private String tableName;

    public NewsItemsOpenHelper(Context context, int categoryId) {
        super(context, NEWS_ITEMS_DB_NAME, null, NEWS_ITEMS_DB_VERSION);
        this.tableName = "categ_" + (categoryId == CATEGORY_DEFAULT_ID ? "all_news" : categoryId);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableIfNotExists(db, tableName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase db = super.getReadableDatabase();
        createTableIfNotExists(db,tableName);
        return db;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = super.getWritableDatabase();
        createTableIfNotExists(db,tableName);
        return db;
    }

    private void createTableIfNotExists(SQLiteDatabase db, String tableName) {
        String createTableSqlCommand = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT," +
                COL_SHORT_DESCRIPTION + " TEXT," +
                COL_IMAGE + " TEXT," +
                COL_CATEGORY_NAME + " TEXT," +
                COL_CATEGORY_ID + " INTEGER);";
        db.execSQL(createTableSqlCommand);
    }

    public void saveNewsItems(List<NewsItem> newsItemList) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName); // clearing the table

        for (NewsItem newsItem :
                newsItemList) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ID, newsItem.getId());
            cv.put(COL_TITLE, newsItem.getTitle());
            cv.put(COL_SHORT_DESCRIPTION, newsItem.getShortDescription());
            cv.put(COL_IMAGE, newsItem.getImage());
            cv.put(COL_CATEGORY_NAME, newsItem.getCategoryName());
            cv.put(COL_CATEGORY_ID, newsItem.getCategoryId());

            db.insert(tableName, null, cv);
        }

        db.close();
    }

    public List<NewsItem> getNewsItems() {
        List<NewsItem> newsItemList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if (cursor.moveToFirst()) {
            do {
                NewsItem newsItem = new NewsItem();
                newsItem.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                newsItem.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                newsItem.setShortDescription(cursor.getString(cursor.getColumnIndex(COL_SHORT_DESCRIPTION)));
                newsItem.setImage(cursor.getString(cursor.getColumnIndex(COL_IMAGE)));
                newsItem.setCategoryName(cursor.getString(cursor.getColumnIndex(COL_CATEGORY_NAME)));
                newsItem.setCategoryId(cursor.getInt(cursor.getColumnIndex(COL_CATEGORY_ID)));
                newsItemList.add(newsItem);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return newsItemList;
    }
}
