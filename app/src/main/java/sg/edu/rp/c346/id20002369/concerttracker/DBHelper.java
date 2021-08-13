package sg.edu.rp.c346.id20002369.concerttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Concert.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONCERT = "Concerts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_DESCRIPTION = "Desc";
    private static final String COLUMN_LOCATION = "Loc";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Song
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,
        // singers TEXT, stars INTEGER, year INTEGER );
        String createConcertTableSql = "CREATE TABLE " + TABLE_CONCERT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_LOCATION + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_RATING + " INTEGER )";
        db.execSQL(createConcertTableSql);
        Log.i("info", createConcertTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONCERT);
        onCreate(db);
    }

    public long insertConcert(String name, String desc, String loc, int year, int rating) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, desc);
        values.put(COLUMN_LOCATION, loc);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATING, rating);
        // Insert the row into the TABLE_SONG
        long result = db.insert(TABLE_CONCERT, null, values);
        // Close the database connection
        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Concerts> getAllConcerts() {
        ArrayList<Concerts> Concertslist = new ArrayList<Concerts>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_DESCRIPTION + ","
                + COLUMN_LOCATION + "," + COLUMN_YEAR + ","
                + COLUMN_RATING + " FROM " + TABLE_CONCERT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                String loc = cursor.getString(3);
                int year = cursor.getInt(4);
                int rating = cursor.getInt(5);

                Concerts newConcert = new Concerts(id, name, desc, loc, year, rating);
                Concertslist.add(newConcert);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Concertslist;
    }

    public ArrayList<Concerts> getAllConcertsByRatings(int ratingsFilter) {
        ArrayList<Concerts> Concertslist = new ArrayList<Concerts>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_LOCATION, COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_RATING + ">= ?";
        String[] args = {String.valueOf(ratingsFilter)};


        Cursor cursor;
        cursor = db.query(TABLE_CONCERT, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                String loc = cursor.getString(3);
                int year = cursor.getInt(4);
                int rating = cursor.getInt(5);

                Concerts newConcert = new Concerts(id, name, desc, loc, year, rating);
                Concertslist.add(newConcert);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return Concertslist;
    }

    public int updateConcert(Concerts data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDesc());
        values.put(COLUMN_LOCATION, data.getLoc());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_RATING, data.getRating());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_CONCERT, values, condition, args);
        db.close();
        return result;
    }


    public int deleteConcert(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_CONCERT, condition, args);
        db.close();
        return result;
    }

}
