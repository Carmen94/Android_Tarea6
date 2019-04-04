package com.iteso.android_tarea6.tools;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.android_tarea6.beans.Category;

import java.util.ArrayList;

public class ControlCategory {
    DataBaseHandler databaseHandler;
    public ControlCategory(){

    }

    public ArrayList<Category> getCategories(DataBaseHandler dh){
        ArrayList<Category> listOfCategories = new ArrayList<>();
        String query = "SELECT C." + databaseHandler.KEY_CATEGORY_ID + ","
                + "C." + databaseHandler.KEY_CATEGORY_NAME +" FROM "
                + databaseHandler.TABLE_CATEGORY +" C";
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_CATEGORY_ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_CATEGORY_NAME)));
                listOfCategories.add(category);
                cursor.moveToNext();
            }
        }
        return listOfCategories;
    }
}
