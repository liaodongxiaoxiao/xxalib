package com.ldxx.xxalib.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.ldxx.xxalib.BuildConfig;

/**
 * Created by LDXX on 2015/10/27.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 * 1. create a class extends android.content.ContentProvider
 * 2. define a CONTENT_URI (public static final)
 */
public class NewsContentProvider extends ContentProvider {

    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".news.contentprovider";

    private static final String BASE_PATH = "todos";

    public static String[] ALL_PROJECTION =new String[]{"pid","image_src","title","url","create_time"};

    public static String COLUMN_CREATE_TIME="create_time";
    public static String COLUMN_IMAGE_SRC="image_src";
    public static String COLUMN_TITLE="title";
    public static String COLUMN_PID="pid";
    public static String COLUMN_URL="url";
    /**
     * Content URI for favorite destinations.
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "XXNewsInfo";
    private final String TAG = this.getClass().getSimpleName();
    private DatabaseHelper helper;

    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());

        return helper != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = helper.getReadableDatabase();
        qb.setTables(TABLE_NAME);
        Cursor c = qb.query(db, projection, selection, null, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //创建用于存储数据的表
            db.execSQL("Create table " + TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " pid TEXT, image_src TEXT,title TEXT,url TEXT,create_time)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
