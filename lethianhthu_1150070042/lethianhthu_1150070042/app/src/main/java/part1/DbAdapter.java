package part1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter {
    // Thông tin DB
    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Lớp SQLiteOpenHelper
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    // Constructor
    public DbAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    // Open & Close
    public DbAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    // CRUD
    public long createUser(String name) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        return db.insert(TABLE_USERS, null, values);
    }

    public boolean deleteAllUsers() {
        return db.delete(TABLE_USERS, null, null) > 0;
    }

    public Cursor getAllUsers() {
        return db.query(TABLE_USERS, new String[]{KEY_ID, KEY_NAME},
                null, null, null, null, null);
    }
}
