package com.qihoo.apitest.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.qihoo.apitest.Global;

/**
 * Created by caiyongjian on 16-6-19.
 */
public class TestProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        Log.i(Global.STEP_LOG, "TestProvider.onCreate", new Throwable("printStack"));
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(Global.STEP_LOG, "TestProvider.query", new Throwable("printStack"));
        return null;
    }

    @Override
    public String getType(Uri uri) {
        Log.i(Global.STEP_LOG, "TestProvider.getType");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(Global.STEP_LOG, "TestProvider.insert");
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(Global.STEP_LOG, "TestProvider.delete");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
