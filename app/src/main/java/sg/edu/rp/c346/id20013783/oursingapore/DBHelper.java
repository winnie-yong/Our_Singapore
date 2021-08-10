package sg.edu.rp.c346.id20013783.oursingapore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "island.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ISLAND = "island";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AREA = "area";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_ISLAND + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_AREA + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createSongTableSql);
        Log.i("info", createSongTableSql + "\ncreated tables");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLAND);
        onCreate(db);
    }

    public long insertIsland(String title, String description, int area, int stars) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_AREA, area);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_ISLAND, null, values);
        // Close the database connection
        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Island> getAllIsland() {
        ArrayList<Island> islandList = new ArrayList<Island>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + "," + COLUMN_DESCRIPTION + ","
                + COLUMN_AREA + ","
                + COLUMN_STARS + " FROM " + TABLE_ISLAND;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                int area = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Island newIsland = new Island(id, title,description, area, stars);
                islandList.add(newIsland);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return islandList;
    }

    public ArrayList<Island> getAllIslandByStar(int starsFilter) {
        ArrayList<Island> islandList = new ArrayList<Island>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_AREA, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_ISLAND, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                int area = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Island newIsland = new Island(id, title, description, area, stars);
                islandList.add(newIsland);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return islandList;
    }

    public int updateSong(Island data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_AREA, data.getArea());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ISLAND, values, condition, args);
        db.close();
        return result;
    }


    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLAND, condition, args);
        db.close();
        return result;
    }
    public ArrayList<String> getArea() {
        ArrayList<String> theArea = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_AREA};

        Cursor cursor;
        cursor = db.query(true, TABLE_ISLAND, columns, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                theArea.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return theArea;
    }

}
