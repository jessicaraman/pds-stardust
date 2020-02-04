package com.pds.pgmapp.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.pds.pgmapp.geolocation.LocationEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "pds";

    private static final String TABLE_LOCATION = "locations";

    private static final String KEY_LOCATION_X = "location_x";
    private static final String KEY_LOCATION_Y = "location_y";
    private static final String KEY_LOCATION_DATE = "location_date";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query to create table
        String CREATE_LOCATIONS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_LOCATION + "("
                + KEY_LOCATION_X + " INTEGER (10), "
                + KEY_LOCATION_Y + " TEXT, "
                + KEY_LOCATION_DATE + " TEXT" + ")";

        db.execSQL(CREATE_LOCATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

        onCreate(db);
    }

    public void truncateLocation() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_LOCATION);
    }

    public void saveLocation(LocationEntity location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOCATION_X, location.getX());
        values.put(KEY_LOCATION_Y, location.getY());
        values.put(KEY_LOCATION_DATE, location.getDate().toString());

        db.insert(TABLE_LOCATION, null, values);
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<LocationEntity> getAllLocations() {

        List<LocationEntity> locations = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                LocationEntity student = new LocationEntity (
                        cursor.getDouble(0),
                        cursor.getDouble(1),
                        LocalDateTime.parse(cursor.getString(2)));

                locations.add(student);
            } while (cursor.moveToNext());
        }
        return locations;
    }

}
