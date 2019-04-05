package com.iteso.android_tarea6.tools;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ContentProviderStore extends ContentProvider {
    DataBaseHandler dataBaseHandler;

    private static final String AUTHORITY = "com.iteso.android_tarea6.tools.store";
    private static final String BASE_PATH ="store";

    static final int STORES=1;
    static final int STORE_ID=2;
    private SQLiteDatabase database;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, STORES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH+"/#", STORE_ID);
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
        queryBuilder.setTables(dataBaseHandler.TABLE_STORE);
        Cursor cursor=null;
        switch (uriMatcher.match(uri)) {
            case STORES:
                cursor = queryBuilder.query(database, null, null, selectionArgs, null, null, sortOrder);
                break;
            case STORE_ID:
                cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
        }
        if (sortOrder == null || sortOrder == ""){
            sortOrder = DataBaseHandler.KEY_PRODUCT_TITLE;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        database=dataBaseHandler.getWritableDatabase();
        long inserted ;
        inserted = database.insert(dataBaseHandler.TABLE_STORE, null, values);
        ControlStore controlStore = new ControlStore();
//        id = controlStore.addStore(values,database,dataBaseHandler);
        switch (uriMatcher.match(uri)) {
            case STORES:
                inserted = controlStore.addStore(values,database,dataBaseHandler);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(dataBaseHandler.TABLE_STORE + "/" + inserted);
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case STORES:
                return "vnd.android.cursor.dir/vnd.iteso.android_tarea6.tools.store";
            case STORE_ID:
                return "vnd.android.cursor.item/vnd.iteso.android_tarea6.tools.store";
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
