package com.qihoo.apitest.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.qihoo.apitest.Global;

/**
 * Created by caiyongjian on 16-6-19.
 */
public class TestLocalProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        Log.i(Global.STEP_LOG, "TestLocalProvider.onCreate", new Throwable("printStack"));
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(Global.STEP_LOG, "TestLocalProvider.query", new Throwable("printStack"));
        return null;
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
}
