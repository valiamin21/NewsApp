package com.example.newsapp.open_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newsapp.data_model.Post;

public class PostsOpenHelper extends SQLiteOpenHelper {
    private static final String POSTS_DB_NAME = "posts_db";
    private static final int POSTS_DB_VERSION = 1;
    private static final String POSTS_TABLE_NAME = "posts_table";

    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_SHORT_DESCRIPTION = "short_description";
    private static final String COL_IMAGE = "image";
    private static final String COL_CATEGORY_NAME = "category_name";
    private static final String COL_CATEGORY_ID = "category_id";
    private static final String COL_LONG_DESCRIPTION = "long_description";
    private static final String COL_DATE = "date";


    String createTableSqlCommand = "CREATE TABLE IF NOT EXISTS " + POSTS_TABLE_NAME + "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_TITLE + " TEXT," +
            COL_SHORT_DESCRIPTION + " TEXT," +
            COL_IMAGE + " TEXT," +
            COL_CATEGORY_NAME + " TEXT," +
            COL_CATEGORY_ID + " INTEGER," +
            COL_LONG_DESCRIPTION + " TEXT," +
            COL_DATE + " TEXT);";


    public PostsOpenHelper(Context context) {
        super(context, POSTS_DB_NAME, null, POSTS_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSqlCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void savePost(Post post) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(POSTS_TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(post.getId())});
        ContentValues cv = new ContentValues();
        cv.put(COL_ID, post.getId());
        cv.put(COL_TITLE, post.getTitle());
        cv.put(COL_SHORT_DESCRIPTION, post.getShortDescription());
        cv.put(COL_IMAGE, post.getImage());
        cv.put(COL_CATEGORY_NAME, post.getCategoryName());
        cv.put(COL_CATEGORY_ID, post.getCategoryId());
        cv.put(COL_LONG_DESCRIPTION, post.getLongDescription());
        cv.put(COL_DATE, post.getDate());
        db.insert(POSTS_TABLE_NAME,null,cv);

        db.close();
    }

    public Post getPost(int postId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + POSTS_TABLE_NAME + " WHERE " + COL_ID + " = ?" ,new String[]{String.valueOf(postId)});
        Post post = null;
        if(cursor.moveToFirst()){
            post = new Post();
            post.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            post.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
            post.setShortDescription(cursor.getString(cursor.getColumnIndex(COL_SHORT_DESCRIPTION)));
            post.setImage(cursor.getString(cursor.getColumnIndex(COL_IMAGE)));
            post.setCategoryName(cursor.getString(cursor.getColumnIndex(COL_CATEGORY_NAME)));
            post.setCategoryId(cursor.getInt(cursor.getColumnIndex(COL_CATEGORY_ID)));
            post.setLongDescription(cursor.getString(cursor.getColumnIndex(COL_LONG_DESCRIPTION)));
            post.setDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));
        }

        cursor.close();
        return post;
    }
}
