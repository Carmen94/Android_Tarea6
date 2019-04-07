package com.iteso.android_tarea6.tools;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ContentProviderCategory extends ContentProvider {
    DataBaseHandler dataBaseHandler;

    static final String categoryId = DataBaseHandler.KEY_PRODUCT_CATEGORY;
    private static final String AUTHORITY = "com.iteso.android_tarea6.tools.product";
    private static final String BASE_PATH ="category";

    static final int CATEGORIES=1;
    static final int CATEGORY_ID=2;
    private SQLiteDatabase database;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, CATEGORIES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH+"/#", CATEGORY_ID);
    }

    public boolean onCreate() {
        Context context = getContext();
        dataBaseHandler = DataBaseHandler.getInstance(context);
        return (database == null)? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        database = dataBaseHandler.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(dataBaseHandler.TABLE_CATEGORY);

        switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                break;
            case CATEGORY_ID:
                queryBuilder.appendWhere(DataBaseHandler.KEY_CATEGORY_ID + "="+ uri.getLastPathSegment());
                break;
            default:
        }
        if (sortOrder == null || sortOrder == ""){
            sortOrder = DataBaseHandler.KEY_CATEGORY_NAME;
        }
        Cursor cursor=queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {


        return Uri.parse("Not implemented");
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case CATEGORIES:
                return "vnd.android.cursor.dir/vnd.com.iteso.android_tarea6.tools.product";
            case CATEGORY_ID:
                return "vnd.android.cursor.item/vnd.com.iteso.android_tarea6.tools.product";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);        }
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
