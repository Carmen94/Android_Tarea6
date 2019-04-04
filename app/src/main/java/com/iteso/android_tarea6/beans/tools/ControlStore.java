package com.iteso.android_tarea6.beans.tools;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.android_tarea6.beans.City;
import com.iteso.android_tarea6.beans.Store;

import java.util.ArrayList;

public class ControlStore {

     public ControlStore(){

    }

    public ArrayList<Store> getStores(DataBaseHandler databaseHandler){
        ArrayList<Store> listOfStores = new ArrayList<>();
        String query = "SELECT S." + databaseHandler.KEY_STORE_ID + ","
                + "S." + databaseHandler.KEY_STORE_NAME + ","
                + "S." + databaseHandler.KEY_STORE_LAT + ","
                + "S." + databaseHandler.KEY_STORE_LNG + ","
                + "S." + databaseHandler.KEY_STORE_PHONE + ","
                + "S." + databaseHandler.KEY_STORE_THUMBNAIL + ","
                + "C." + databaseHandler.KEY_CITY_ID + ","
                + "C." + databaseHandler.KEY_CITY_NAME + " FROM "
                + databaseHandler.TABLE_STORE + " S, "
                + databaseHandler.TABLE_CITY + " C WHERE S."
                + databaseHandler.KEY_STORE_CITY
                + " = C." + databaseHandler.KEY_CITY_ID;
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Store store = new Store();
                City city = new City();
                store.setId(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_STORE_ID)));
                store.setName(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_STORE_NAME)));
                store.setPhone(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_STORE_PHONE)));
                store.setThumbnail(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_STORE_THUMBNAIL)));
                store.setLongitude(cursor.getDouble(cursor.getColumnIndex(databaseHandler.KEY_STORE_LNG)));
                store.setLatitude(cursor.getDouble(cursor.getColumnIndex(databaseHandler.KEY_STORE_LAT)));
                city.setId(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_CITY_ID)));
                city.setName(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_CITY_NAME)));
                store.setCity(city);
                listOfStores.add(store);
                cursor.moveToNext();
            }
        }
        return listOfStores;
    }

    public long addStore(Store store, DataBaseHandler databaseHandler) {
        long inserted = 0;
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(databaseHandler.KEY_STORE_CITY, store.getCity().getId());
        values.put(databaseHandler.KEY_STORE_NAME, store.getName());
        values.put(databaseHandler.KEY_STORE_PHONE, store.getPhone());
        values.put(databaseHandler.KEY_STORE_LAT, store.getLatitude());
        values.put(databaseHandler.KEY_STORE_LNG, store.getLongitude());
        values.put(databaseHandler.KEY_STORE_THUMBNAIL, store.getThumbnail());
        inserted = db.insert(databaseHandler.TABLE_STORE, null, values);
        try {
            db.close();
        } catch (Exception e)
        {}
        db = null; values = null;
        return inserted;
    }

    public Store getStoreById(int idStore, DataBaseHandler databaseHandler){
        Store store = new Store();
        String selectQuery = "SELECT S." + databaseHandler.KEY_STORE_ID + ","
                + "S." + databaseHandler.KEY_STORE_LAT + ","
                + "S." + databaseHandler.KEY_STORE_LNG + ","
                + "S." + databaseHandler.KEY_STORE_NAME + ","
                + "S." + databaseHandler.KEY_STORE_PHONE + ","
                + "S." + databaseHandler.KEY_STORE_THUMBNAIL + ","
                + "C." + databaseHandler.KEY_CITY_ID + ","
                + "C." + databaseHandler.KEY_CITY_NAME + " FROM "
                + databaseHandler.TABLE_STORE + " S, "
                + databaseHandler.TABLE_CITY + " C WHERE S."
                + databaseHandler.KEY_STORE_ID + "=" + idStore
                + " AND S." + databaseHandler.KEY_STORE_CITY
                + " = C." + databaseHandler.KEY_CITY_ID;
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            store.setId(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_STORE_ID)));
            store.setName(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_STORE_NAME)));
            store.setPhone(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_STORE_PHONE)));
            store.setThumbnail(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_STORE_THUMBNAIL)));
            store.setLongitude(cursor.getDouble(cursor.getColumnIndex(databaseHandler.KEY_STORE_LNG)));
            store.setLatitude(cursor.getDouble(cursor.getColumnIndex(databaseHandler.KEY_STORE_LAT)));
            City city = new City();
            city.setId(cursor.getInt(cursor.getColumnIndex(databaseHandler.KEY_CITY_ID)));
            city.setName(cursor.getString(cursor.getColumnIndex(databaseHandler.KEY_CITY_NAME)));
            store.setCity(city);
        }
        try {
            cursor.close();db.close();
        } catch (Exception e) {}
        db = null;
        cursor = null;

        return store;
    }
}
