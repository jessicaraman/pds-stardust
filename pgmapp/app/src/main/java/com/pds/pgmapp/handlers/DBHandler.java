package com.pds.pgmapp.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.pds.pgmapp.geolocation.LocationEntity;
import com.pds.pgmapp.model.Door;
import com.pds.pgmapp.model.Node;
import com.pds.pgmapp.model.NodeCategory;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to manage database
 */
public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler DBInstance;

    /**
     * Singleton
     * @param context
     * @return
     */
    public static synchronized DBHandler getInstance(Context context) {
        System.out.println("Getting INSTANCE");
        if (DBInstance == null) {
            System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWww");
            DBInstance = new DBHandler(context.getApplicationContext());
        }
        return DBInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = getWritableDatabase();
        System.out.println("Getting HANDLER");
    }

    SQLiteDatabase db ;

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "pds";

    // tables
    private static final String TABLE_LOCATION = "locations";
    private static final String TABLE_NODE = "node";
    private static final String TABLE_NODE_CATEGORY = "nodecategory";
    private static final String TABLE_ADJACENT_NODE = "adjacentnode";
    private static final String TABLE_DOOR = "door";

    // location properties
    private static final String KEY_LOCATION_X = "location_x";
    private static final String KEY_LOCATION_Y = "location_y";
    private static final String KEY_LOCATION_DATE = "location_date";

    //  node properties
    private static final String KEY_NODE_ID = "id";
    private static final String KEY_NODE_LABEL = "label";
    private static final String KEY_NODE_CATEGORY = "node_category";

    // node category properties
    private static final String KEY_NODE_CATEGORY_ID = "id";
    private static final String KEY_NODE_CATEGORY_LABEL = "label";

    // node adjacent properties
    private static final String KEY_NODE_ADJACENT_MAIN_NODE_ID = "main_node_id";
    private static final String KEY_NODE_ADJACENT_ADJACENT_NODE_ID = "adjacent_node_id";
    private static final String KEY_NODE_ADJACENT_DISTANCE = "main_adjacent_node_distance";

    // door properties
    private static final String KEY_DOOR_ID = "id";
    private static final String KEY_DOOR_X = "x";
    private static final String KEY_DOOR_Y = "y";
    private static final String KEY_DOOR_ID_NODE = "id_node";

    // create table locations
   private String CREATE_LOCATIONS_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_LOCATION + "("
            + KEY_LOCATION_X + " TEXT, "
            + KEY_LOCATION_Y + " TEXT, "
            + KEY_LOCATION_DATE + " TEXT" + ")";

    // create table node category
    private String CREATE_NODE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NODE_CATEGORY + "("
            + KEY_NODE_CATEGORY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NODE_CATEGORY_LABEL + " TEXT " + ")";

    // create table node
    private String CREATE_NODE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NODE + "("
            + KEY_NODE_ID + " INTEGER PRIMARY KEY, "
            + KEY_NODE_LABEL + " TEXT, "
            + KEY_NODE_CATEGORY + " INTEGER" + ")";

    // create table adjacent node
    private String CREATE_ADJACENT_NODE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ADJACENT_NODE + "("
            + KEY_NODE_ADJACENT_MAIN_NODE_ID + " INTEGER, "
            + KEY_NODE_ADJACENT_ADJACENT_NODE_ID + " TEXT, "
            + KEY_NODE_ADJACENT_DISTANCE + " INTEGER" + ")";

    // create table door
    private String CREATE_DOOR_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_DOOR + "("
            + KEY_DOOR_ID + " INTEGER PRIMARY KEY, "
            + KEY_DOOR_X + " TEXT, "
            + KEY_DOOR_Y + " TEXT, "
            + KEY_DOOR_ID_NODE + " INTEGER" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("", "DB onCreate beginning");
        //Query to create table
        System.out.println("CREATING TABLES");
        db.execSQL(CREATE_LOCATIONS_TABLE);
        db.execSQL(CREATE_NODE_CATEGORY_TABLE);
        db.execSQL(CREATE_NODE_TABLE);
        db.execSQL(CREATE_ADJACENT_NODE_TABLE);
        db.execSQL(CREATE_DOOR_TABLE);
        insertNodeCategory(db);
        insertNode(db);
        insertDoor(db);
        insertAdjacentNode(db);
        Log.e("", "DB onCreate end") ;
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

    /**
     * Get last known location
     *
     * @return locationEntity
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocationEntity getLastLocation() {
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " ORDER BY " + KEY_LOCATION_DATE + " DESC LIMIT 1,1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        LocationEntity locationEntity = null;

        if (cursor.moveToFirst()) {
            do {
                locationEntity = new LocationEntity(
                        cursor.getDouble(0),
                        cursor.getDouble(1),
                        LocalDateTime.parse(cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return locationEntity;
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

                LocationEntity student = new LocationEntity(
                        cursor.getDouble(0),
                        cursor.getDouble(1),
                        LocalDateTime.parse(cursor.getString(2)));

                locations.add(student);
            } while (cursor.moveToNext());
        }
        return locations;
    }

    /*public Node getNodeById (int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NODE + " WHERE "
                + KEY_NODE_ID + " = " + id;

        Log.e("log", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Node node = new Node();
        node.setId(c.getInt(c.getColumnIndex(KEY_NODE_ID)));
        node.setLabel((c.getString(c.getColumnIndex(KEY_NODE_LABEL))));
        node.setNodeCategory(new NodeCategory(c.getString(c.getColumnIndex(KEY_NODE_CATEGORY))));

        return node;
    }*/

    public Node getNodeById (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectNodesQuery = "SELECT * FROM " + TABLE_NODE + " WHERE " + KEY_NODE_ID + " = " + id + ";";
        String selectDoorsQuery = "SELECT * FROM " + TABLE_DOOR + " WHERE " + KEY_DOOR_ID_NODE + " = " + id ;
        Log.e("","get Writable Database");
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorNodes = db.rawQuery(selectNodesQuery, null);
        Cursor cursorDoors = db.rawQuery(selectDoorsQuery, null);

        Node node = null ;
        NodeCategory nodeCategory = null ;
        ArrayList<Door> doors = new ArrayList<Door> () ;

        if (cursorDoors.moveToFirst()) {
            do {
                Door d = new Door(
                        cursorDoors.getInt(0),
                        cursorDoors.getDouble(1),
                        cursorDoors.getDouble(2)
                );
                doors.add(d);
            }
            while (cursorDoors.moveToNext());
        }

        if (cursorNodes.moveToFirst()) {
            String selectNodeCategoryQuery = " SELECT * FROM " + TABLE_NODE_CATEGORY + " WHERE id = " + cursorNodes.getInt(2) ;
            Cursor cursorNodeCategory = db.rawQuery(selectNodeCategoryQuery, null);
            //nodeCategory = new NodeCategory(cursorNodeCategory.getInt(0), cursorNodeCategory.getString(1));
            nodeCategory = new NodeCategory(1,"test");
            node = new Node(
                cursorNodes.getInt(0),
                cursorNodes.getString(1),
                nodeCategory,
                doors);
        }
        return node;
    }

    /**
     * Mock node
     *
     * @param db
     */
    public void insertNode(SQLiteDatabase db) {
        Log.e("","insertNode");
        db.execSQL("INSERT INTO node (id,label,node_category) VALUES (1,'Nike',2);");
        db.execSQL("INSERT INTO node (id,label,node_category) VALUES (2,'Adidas',2);");
        db.execSQL("INSERT INTO node (id,label,node_category) VALUES (3,'JD Sports',2);");
        db.execSQL("INSERT INTO node (id,label,node_category) VALUES (4,'Uniqlo',2);");
        db.execSQL("INSERT INTO node (id,label,node_category) VALUES (5,'Pull&Bear',2);");
        db.execSQL("INSERT INTO node (id,label,node_category) VALUES (6,'Fnac',2);");
    }

    /**
     * Mock node category
     *
     * @param db
     */
    public void insertNodeCategory(SQLiteDatabase db) {
        Log.e("log", "insert Node Category");
        db.execSQL("INSERT INTO nodecategory (id,label) VALUES (1, 'Corridor');");
        db.execSQL("INSERT INTO nodecategory (id,label) VALUES (2, 'Store');");
    }

    /**
     * Mock adjacent node
     *
     * @param db
     */
    public void insertAdjacentNode(SQLiteDatabase db) {
        Log.e("log", "insertAdjacentNode");
        db.execSQL("INSERT INTO adjacentnode (main_node_id, adjacent_node_id, main_adjacent_node_distance) VALUES (1,2, 132);");
        db.execSQL("INSERT INTO adjacentnode (main_node_id, adjacent_node_id, main_adjacent_node_distance) VALUES (5,6, 242);");
        db.execSQL("INSERT INTO adjacentnode (main_node_id, adjacent_node_id, main_adjacent_node_distance) VALUES (3,2,1343);");
        db.execSQL("INSERT INTO adjacentnode (main_node_id, adjacent_node_id, main_adjacent_node_distance) VALUES (4,6,21);");
        db.execSQL("INSERT INTO adjacentnode (main_node_id, adjacent_node_id, main_adjacent_node_distance) VALUES (3,5,324);");
        db.execSQL("INSERT INTO adjacentnode (main_node_id, adjacent_node_id, main_adjacent_node_distance) VALUES (1,3,3242);");
        db.execSQL("INSERT INTO adjacentnode (main_node_id, adjacent_node_id, main_adjacent_node_distance) VALUES (4,5,321);");
    }

    /**
     * Mock Door
     *
     * @param db
     */
    public void insertDoor(SQLiteDatabase db) {
        Log.e("log", "insertDoor");
        db.execSQL("INSERT INTO door (id,x,y,id_node) VALUES (1, 0.2921868823996539, 0.7736856476831694, 1)");
        db.execSQL("INSERT INTO door (id,x,y,id_node) VALUES (2, 0.5438542339380292, 0.1838274390274859, 2)");
        db.execSQL("INSERT INTO door (id,x,y,id_node) VALUES (3, 0.4748390248790389, 0.6430892890328030, 3)");
        db.execSQL("INSERT INTO door (id,x,y,id_node) VALUES (4, 0.3294045893024090, 0.4890308709382090, 4)");
        db.execSQL("INSERT INTO door (id,x,y,id_node) VALUES (5, 0.0986384749839080, 0.1234890329480437, 5)");
        db.execSQL("INSERT INTO door (id,x,y,id_node) VALUES (6, 0.7270480935784303, 0.9084938274784929, 6)");
        db.execSQL("INSERT INTO door (id,x,y,id_node) VALUES (7, 0.7270472404809320, 0.9084837208930437, 6)");
    }
}
