package com.example.cherry_zhang.androidbeaconexample.SearchFeature;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Cherry_Zhang on 2014-09-21.
 */
//TODO
//TODO
//TODO
//idk how to do this content provider for search suggestions!!! muzukashisugiru!
public class OfficeContentProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        return false;
    }

    //TODO: i dont know how to do this
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (selectionArgs != null && selectionArgs.length > 0 && selectionArgs[0].length() > 0)
        {
            //return cursor based on selectionArgs[0]
//            Cursor cursor =
            return null;
        }
        else
        {
            return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
