package com.example.stockmanagementapp.DB;

import android.provider.BaseColumns;

public final class StockContract {
    private StockContract() {}

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
        public static final String COLUMN_NAME_PRODUCT_TYPE = "product_type";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_PRICE = "price";
    }
}

