package com.example.stockmanagementapp.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class StockDataSource {
    private SQLiteDatabase database;
    private StockDBHelper dbHelper;

    //3 seguintes metodos para abrir e fechar DB
    public StockDataSource(Context context) {
        dbHelper = new StockDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //metodo para inserir produtos
    public long insertProduct(String productName, String productType, int quantity, double price) {
        ContentValues values = new ContentValues();
        values.put(StockContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, productName);
        values.put(StockContract.ProductEntry.COLUMN_NAME_PRODUCT_TYPE, productType);
        values.put(StockContract.ProductEntry.COLUMN_NAME_QUANTITY, quantity);
        values.put(StockContract.ProductEntry.COLUMN_NAME_PRICE, price);

        return database.insert(StockContract.ProductEntry.TABLE_NAME, null, values);
    }

    //metodo para obter todos os dados dos produtos
    public ArrayList<String> getAllProductsDetails() {
        ArrayList<String> productDetails = new ArrayList<>();
        try {
            Cursor cursor = database.query(
                    StockContract.ProductEntry.TABLE_NAME,
                    new String[]{
                            StockContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                            StockContract.ProductEntry.COLUMN_NAME_PRODUCT_TYPE,
                            StockContract.ProductEntry.COLUMN_NAME_QUANTITY,
                            StockContract.ProductEntry.COLUMN_NAME_PRICE
                    },
                    null,
                    null,
                    null,
                    null,
                    null
            );

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(StockContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME));
                @SuppressLint("Range") String productType = cursor.getString(cursor.getColumnIndex(StockContract.ProductEntry.COLUMN_NAME_PRODUCT_TYPE));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(StockContract.ProductEntry.COLUMN_NAME_QUANTITY));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(StockContract.ProductEntry.COLUMN_NAME_PRICE));

                @SuppressLint("DefaultLocale") String productDetail = String.format("Nome: %s\nCategoria: %s\nQuantidade: %d\nPreço: %.2f\n", productName, productType, quantity, price);
                productDetails.add(productDetail);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDetails;
    }

    //metodo para atualizar os dados do produto na DB
    public void updateProduct(String oldName, String newName, String newType, int newQuantity, double newPrice) {
        ContentValues values = new ContentValues();
        values.put(StockContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, newName);
        values.put(StockContract.ProductEntry.COLUMN_NAME_PRODUCT_TYPE, newType);
        values.put(StockContract.ProductEntry.COLUMN_NAME_QUANTITY, newQuantity);
        values.put(StockContract.ProductEntry.COLUMN_NAME_PRICE, newPrice);

        String selection = StockContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME + " = ?";
        String[] selectionArgs = { oldName };

        database.update(
                StockContract.ProductEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //metodo para excluir o produto da DB
    public void deleteProduct(String productName) {
        String selection = StockContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME + " = ?";
        String[] selectionArgs = { productName };

        database.delete(StockContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
    }
}
