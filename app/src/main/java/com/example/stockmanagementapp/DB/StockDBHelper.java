package com.example.stockmanagementapp.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StockDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stockDB.db";

    private static final String SQL_CREATE_PRODUCTS =
            "CREATE TABLE " + StockContract.ProductEntry.TABLE_NAME + " (" +
                    StockContract.ProductEntry._ID + " INTEGER PRIMARY KEY," +
                    StockContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME + " TEXT," +
                    StockContract.ProductEntry.COLUMN_NAME_PRODUCT_TYPE + " TEXT," +
                    StockContract.ProductEntry.COLUMN_NAME_QUANTITY + " INTEGER," +
                    StockContract.ProductEntry.COLUMN_NAME_PRICE + " REAL)";

    private static final String SQL_DELETE_PRODUCTS =
            "DROP TABLE IF EXISTS " + StockContract.ProductEntry.TABLE_NAME;

    public StockDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PRODUCTS);
        onCreate(db);
    }
}

