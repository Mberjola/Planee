package com.example.planeeandroid;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBAdapter {
    public static final int DB_version = 1;
    public static final String DB_name = "Planee.db";

    private static final String Event_Table = "table_evenement";
    public static final String col_ID = "id";
    public static final String col_Name = "Nom";
    public static final String col_DateLimite = "Date";

    private static final String create_table_event =
            "create table " + Event_Table + "(" + col_ID + "" +
                    " integer primary key autoincrement, " + col_Name +
                    " text not null, " + col_DateLimite + "date);";

    private SQLiteDatabase myDataBase;
    private MyOpenHelper myOpenHelper;
}
