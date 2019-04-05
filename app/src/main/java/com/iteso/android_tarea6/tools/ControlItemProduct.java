package com.iteso.android_tarea6.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.android_tarea6.beans.Category;
import com.iteso.android_tarea6.beans.ItemProduct;
import com.iteso.android_tarea6.beans.Store;

import java.util.ArrayList;

public class ControlItemProduct {
    Context context;
    public ControlItemProduct(Context context){
        this.context = context;
    }

    public void addItemProduct(ItemProduct itemProduct,DataBaseHandler databaseHandler){
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(databaseHandler.KEY_PRODUCT_TITLE, itemProduct.getTitle());
        values.put(databaseHandler.KEY_PRODUCT_DESCRIPTION, itemProduct.getDescription());
        values.put(databaseHandler.KEY_PRODUCT_IMAGE, itemProduct.getImage());
        values.put(databaseHandler.KEY_PRODUCT_CATEGORY, itemProduct.getCategory().getId());
        long row  = db.insert(databaseHandler.TABLE_PRODUCT, null, values);
        ContentValues storeProduct = new ContentValues();
        storeProduct.put(databaseHandler.KEY_STORE_PRODUCT_P_ID, row);
        storeProduct.put(databaseHandler.KEY_STORE_PRODUCT_S_ID, itemProduct.getStore().getId());
        db.insert(databaseHandler.TABLE_STORE_PRODUCT, null, storeProduct);
        try {
            db.close();
        } catch (Exception e)
        {}
        db = null;
        values = null;
        return;
    }

    public ArrayList<ItemProduct> getItemProductsByCategory(int idCategory, DataBaseHandler databaseHandler){
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        ArrayList<ItemProduct> itemsByCategory = new ArrayList<>();
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance(context);
        String selectQuery = "SELECT "
                +"P." + databaseHandler.KEY_PRODUCT_ID + ","
                +"P." + databaseHandler.KEY_PRODUCT_TITLE+","
                +"P." + databaseHandler.KEY_PRODUCT_CATEGORY+","
                +"P." + databaseHandler.KEY_PRODUCT_DESCRIPTION+","
//                +"P." + databaseHandler.KEY_PRODUCT_IMAGE +""
                +"P." + databaseHandler.KEY_PRODUCT_IMAGE +","
//                +"SP." + databaseHandler.KEY_STORE_PRODUCT_P_ID+","
                +"SP." + databaseHandler.KEY_STORE_PRODUCT_S_ID
                +" FROM "
                + databaseHandler.TABLE_PRODUCT + " P, "
//                + databaseHandler.TABLE_PRODUCT + " P "
                + databaseHandler.TABLE_STORE_PRODUCT + " SP "
                +" WHERE "
                +"P."+databaseHandler.KEY_PRODUCT_CATEGORY + "=" + idCategory
                +" AND SP." + databaseHandler.KEY_STORE_PRODUCT_P_ID+" = P."+databaseHandler.KEY_PRODUCT_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ItemProduct itemProduct = new ItemProduct();
                itemProduct.setCode(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_PRODUCT_ID)));
                itemProduct.setTitle(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_PRODUCT_TITLE)));
                itemProduct.setDescription(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_PRODUCT_DESCRIPTION)));
                itemProduct.setImage(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_PRODUCT_IMAGE)));
                Category category = new Category();
                itemProduct.setCategory(category);
                ControlStore controlStore = new ControlStore();
                int storeId = cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_STORE_ID));
                Store store = controlStore.getStoreById(storeId,dataBaseHandler);
                itemProduct.setStore(store);
                itemsByCategory.add(itemProduct);
                cursor.moveToNext();
            }
        }
        try {
            cursor.close();
            db.close();
        } catch (Exception e) {}
        db = null;
        cursor = null;
        return itemsByCategory;
    }

    String getQuery(int idCategory){
        String selectQuery = "SELECT "
                +"P." + DataBaseHandler.KEY_PRODUCT_ID + ","
                +"P." + DataBaseHandler.KEY_PRODUCT_TITLE+","
                +"P." + DataBaseHandler.KEY_PRODUCT_CATEGORY+","
                +"P." + DataBaseHandler.KEY_PRODUCT_DESCRIPTION+","
//                +"P." + databaseHandler.KEY_PRODUCT_IMAGE +""
                +"P." + DataBaseHandler.KEY_PRODUCT_IMAGE +","
//                +"SP." + databaseHandler.KEY_STORE_PRODUCT_P_ID+","
                +"SP." + DataBaseHandler.KEY_STORE_PRODUCT_S_ID
                +" FROM "
                + DataBaseHandler.TABLE_PRODUCT + " P, "
//                + databaseHandler.TABLE_PRODUCT + " P "
                + DataBaseHandler.TABLE_STORE_PRODUCT + " SP "
                +" WHERE "
                +"P."+DataBaseHandler.KEY_PRODUCT_CATEGORY + "=" + idCategory
                +" AND SP." + DataBaseHandler.KEY_STORE_PRODUCT_P_ID+" = P."+DataBaseHandler.KEY_PRODUCT_ID;
        return selectQuery;
    }
}
