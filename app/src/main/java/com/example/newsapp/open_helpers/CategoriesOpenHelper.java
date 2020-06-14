package com.example.newsapp.open_helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newsapp.data_model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesOpenHelper extends SQLiteOpenHelper {
    private static final String CATEGORIES_DATABASE_NAME = "categories_database";
    private static final int CATEGORIES_DATABASE_VERSION = 1;
    private static final String CATEGORIES_TABLE_NAME = "categories_table";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";

    public CategoriesOpenHelper(@Nullable Context context) {
        super(context, CATEGORIES_DATABASE_NAME, null, CATEGORIES_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS " + CATEGORIES_TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT);";
        db.execSQL(sqlCommand);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveCategories(List<Category> categoryList) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + CATEGORIES_TABLE_NAME); // clearing the table
        for (Category category :
                categoryList) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ID, category.getId());
            cv.put(COL_NAME, category.getName());
            db.insert(CATEGORIES_TABLE_NAME, null, cv);
        }

        db.close();
    }

    public List<Category> getCategories() {
        List<Category> categoryList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CATEGORIES_TABLE_NAME, null);
        if(cursor.moveToFirst()){
            do{
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                categoryList.add(category);
            }while(cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return categoryList;
    }
}
