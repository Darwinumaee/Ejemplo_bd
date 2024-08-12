package com.umaee.ejemplo_bd;

import static android.app.DownloadManager.COLUMN_TITLE;
import static android.media.tv.TvContract.WatchNextPrograms.COLUMN_AUTHOR;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "bookLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final SQLiteDatabase.CursorFactory factory = null;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUM_ID = "id";
    private static final String COLUM_TITLE = "title";
    private static final String COLUM_AUTHOR = "author";
    private static final String COLUM_PAGES = "pages";

    private static final String TABLE2_NAME = "the_autors";
    private static final String COLUM2_ID = "id";
    private static final String COLUM2_NAME = "name";
    private static final String COLUM2_SURNAME = "surname";
    private static final String COLUM2_BIRTHDAY = "birthday";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTableLibrary = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUM_TITLE + " TEXT, " +
                COLUM_AUTHOR + " TEXT, " +
                COLUM_PAGES + " INTEGER);";
        db.execSQL(queryTableLibrary);

        String queryTableAuthors = "CREATE TABLE " + TABLE2_NAME +
                "(" + COLUM2_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUM2_NAME + " TEXT, " +
                COLUM2_SURNAME + " TEXT, " +
                COLUM2_BIRTHDAY + " TEXT);";
        db.execSQL(queryTableAuthors);

        String queryforeingKeyAlter = "ALTER TABLE " + TABLE_NAME + " ADD FOREIGN KEY (" + COLUM_AUTHOR + ") REFERENCES " + TABLE2_NAME + "(" + COLUM2_ID + ");";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    void addBook(String title, String author, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUM_TITLE, title);
        cv.put(COLUM_AUTHOR, author);
        cv.put(COLUM_PAGES, pages);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
