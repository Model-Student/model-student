package com.tnedutsledom.modelstudent;

import android.provider.BaseColumns;

public class TableSecurity {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TableSecurity() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "LastTime";
        public static final String COLUMN_DATE = "2022-07-08";
        public static final String COLUMN_TEXT= "우마이";
    }
}
