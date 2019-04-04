package com.iteso.android_tarea6.tools;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

public class ContentProviderProducts extends ContentProvider {
    DataBaseHandler dataBaseHandler;
    static final String PROVIDER_NAME = "com.iteso.android_tarea6.tools.ContentProviderProducts";
    static final String URL = "content://" + PROVIDER_NAME + "/"+DataBaseHandler.TABLE_PRODUCT;

    static final int CATEGORY_ID=1;
    private SQLiteDatabase db;
    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "product/#", CATEGORY_ID);
    }

    public boolean onCreate() {
        Context context = getContext();
        dataBaseHandler = DataBaseHandler.getInstance(context);
        db = dataBaseHandler.getReadableDatabase();
        return (db == null)? false : true;

    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DataBaseHandler.TABLE_PRODUCT);
        switch (uriMatcher.match(uri)) {
            case CATEGORY_ID:
                qb.appendWhere( DataBaseHandler.KEY_PRODUCT_CATEGORY + "=" + uri.getPathSegments().get(1));
                break;
            default:
        }
        Cursor c = qb.query(db,	projection,	selection,selectionArgs,null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return Uri.parse("");
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case 1:
                return "vnd.android.cursor.item/vnd.example.product";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
       return 0;
    }

}
