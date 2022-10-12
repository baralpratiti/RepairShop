package com.depauw.repairshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "repairshop.db";
    private static final int DB_VERSION = 1;
    private static DBHelper myInstance;

    public static final String TABLE_VEHICLE = "vehicle";
    public static final String COL_VEHICLE_ID ="vid";
    public static final String COL_YEAR = "year";
    public static final String COL_MAKE_AND_MODEL = "make_and_model";
    public static final String COL_PURCHASE_PRICE = "purchase_price";
    public static final String COL_IS_NEW = "is_new";

    public static final String TABLE_REPAIR = "repair";
    public static final String COL_REPAIR_ID = "rid";
    public static final String COL_REPAIR_VEHICLE_ID = "vehicle_id";
    public static final String COL_REPAIR_DATE = "date";
    public static final String COL_REPAIR_PRICE = "repair_price";
    public static final String COL_REPAIR_DESCRIPTION = "description";


    private DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE " + TABLE_VEHICLE +" (" +
                COL_VEHICLE_ID + " INTEGER," +
                COL_YEAR + " INTEGER," +
                COL_MAKE_AND_MODEL + " TEXT NOT NULL," +
                COL_PURCHASE_PRICE + " FLOAT," +
                COL_IS_NEW + " BOOLEAN," +
                "PRIMARY KEY (" + COL_VEHICLE_ID + " AUTOINCREMENT)" +
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_REPAIR + "(" +
                COL_REPAIR_ID + " INTEGER," +
                COL_REPAIR_VEHICLE_ID + " INTEGER," +
                COL_REPAIR_DATE + " TEXT NOT NULL," +
                COL_REPAIR_PRICE + " REAL NOT NULL," +
                COL_REPAIR_DESCRIPTION + " TEXT NOT NULL," +
                "PRIMARY KEY(" + COL_REPAIR_ID + " AUTOINCREMENT)" +
                ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    public static DBHelper getInstance(Context context)
    {
        if (myInstance==null)
        {
            myInstance = new DBHelper(context);
        }
        return myInstance;
    }

    public long insertVehicle(Vehicle vehicle)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_IS_NEW, vehicle.isNew());
        cv.put(COL_MAKE_AND_MODEL, vehicle.getMakeModel());
        cv.put(COL_PURCHASE_PRICE, vehicle.getPrice());
        cv.put(COL_YEAR, vehicle.getYear());

        long result = db.insert(TABLE_VEHICLE,null,cv);
        db.close();

        return result;
    }

    public long insertRepair(Repair repair)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_REPAIR_DATE,repair.getDate());
        cv.put(COL_REPAIR_PRICE,repair.getPrice());
        cv.put(COL_REPAIR_DESCRIPTION,repair.getDescription());
        cv.put(COL_REPAIR_VEHICLE_ID,repair.getVehicle_id());

        long result = db.insert(TABLE_REPAIR,null,cv);
        db.close();

        return result;
    }


    public List<Vehicle> getVehicles()

    {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_VEHICLE;
        Cursor cursor = db.rawQuery(sql, null);

        int idx_vid = cursor.getColumnIndex(COL_VEHICLE_ID);
        int idx_year = cursor.getColumnIndex(COL_YEAR);
        int idx_make_model = cursor.getColumnIndex(COL_MAKE_AND_MODEL);
        int idx_purchase_price = cursor.getColumnIndex(COL_PURCHASE_PRICE);
        int idx_is_new = cursor.getColumnIndex(COL_IS_NEW);

        List <Vehicle> vehicles = new ArrayList<Vehicle>();
        if(cursor.moveToFirst()){
            do
            {
                int vid = cursor.getInt(idx_vid);
                int year = cursor.getInt(idx_year);
                String makeModel = cursor.getString(idx_make_model);
                float price = cursor.getFloat(idx_purchase_price);
                int isNew = cursor.getInt(idx_is_new);

                Vehicle newVehicle = new Vehicle(vid,year,makeModel,price,isNew);
                vehicles.add(newVehicle);

            }
            while(cursor.moveToNext());
        }

        db.close();
        return vehicles;

    }

    public List<RepairWithVehicle> getRepairsWithVehicle(String s)

    {
        SQLiteDatabase db = getReadableDatabase();

        String sql = String.format("SELECT * FROM %s INNER JOIN %s ON %s = %s WHERE %s LIKE '%%%s%%'",TABLE_VEHICLE,
                TABLE_REPAIR,
                COL_VEHICLE_ID,
                COL_REPAIR_VEHICLE_ID,
                COL_REPAIR_DESCRIPTION,s);

        Cursor cursor = db.rawQuery(sql,null);

        int idx_vid = cursor.getColumnIndex(COL_VEHICLE_ID);
        int idx_year = cursor.getColumnIndex(COL_YEAR);
        int idx_make_model = cursor.getColumnIndex(COL_MAKE_AND_MODEL);
        int idx_purchase_price = cursor.getColumnIndex(COL_PURCHASE_PRICE);
        int idx_is_new = cursor.getColumnIndex(COL_IS_NEW);

        int idx_rid = cursor.getColumnIndex(COL_REPAIR_ID);
        int idx_vehicle_id = cursor.getColumnIndex(COL_REPAIR_VEHICLE_ID);
        int idx_date = cursor.getColumnIndex(COL_REPAIR_DATE);
        int idx_cost = cursor.getColumnIndex(COL_REPAIR_PRICE);
        int idx_description = cursor.getColumnIndex(COL_REPAIR_DESCRIPTION);

        List<RepairWithVehicle> repairVehicles = new ArrayList<RepairWithVehicle>();

        if(cursor.moveToFirst())
        {
            do
            {
                int vid = cursor.getInt(idx_vid);
                int year = cursor.getInt(idx_year);
                String make_model = cursor.getString(idx_make_model);
                Float purchase_price = cursor.getFloat(idx_purchase_price);
                int is_new = cursor.getInt(idx_is_new);
                int rid = cursor.getInt(idx_rid);
                int vehicle_id = cursor.getInt(idx_vehicle_id);
                String date = cursor.getString(idx_date);
                Float cost = cursor.getFloat(idx_cost);
                String description = cursor.getString(idx_description);
                Vehicle v = new Vehicle(vid,year,make_model, purchase_price,is_new);
                Repair r = new Repair(date,cost,description,vehicle_id);
                RepairWithVehicle repVehicle = new RepairWithVehicle(r,v);
                repairVehicles.add(repVehicle);
            }
            while(cursor.moveToNext());
        }
        db.close();
        return repairVehicles;
    }

    public int deleteVehicle(String description)
    {
        SQLiteDatabase db = getWritableDatabase();
        String where = String.format("%s LIKE '%s'",COL_REPAIR_DESCRIPTION,description);
        int numRows = db.delete(TABLE_REPAIR,where,null);
        db.close();
        return numRows;

    }

}

