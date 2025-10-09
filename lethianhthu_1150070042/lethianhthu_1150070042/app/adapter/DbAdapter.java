package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DbAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createUser(String name) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        db.insert(TABLE_USERS, null, values);
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " + KEY_NAME + " FROM " + TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public void deleteAllUsers() {
        db.delete(TABLE_USERS, null, null);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT)";
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }
}
