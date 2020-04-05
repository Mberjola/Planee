package com.example.planeeandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBAdapter {
    public static final int DB_version = 1;
    public static final String DB_name = "Planee.db";

    private static final String Event_Table = "table_evenement";
    public static final String col_ID = "id";
    public static final String col_Name = "Nom";
    public static final String col_DateLimite = "date_limite";

    private static final String create_table_event =
            String.format("create table %s(%s integer primary key autoincrement, %s text not null, %s text not null);", Event_Table, col_ID, col_Name, col_DateLimite);


    private static final String Tache_Table = "table_tache";
    private static final String col_IDEvent = "id_event";
    private static final String col_Magasin = "magasin";
    private static final String col_URL = "url_magasin";
    private static final String create_table_tache = String.format("create table %s(%s integer primary key autoincrement, %s text not null, %s text not null, %s text, %s integer, FOREIGN KEY (%s) REFERENCES %s(%s));", Tache_Table, col_ID, col_Name, col_Magasin, col_URL, col_IDEvent, col_IDEvent, Event_Table, col_ID);

    private SQLiteDatabase myDataBase;
    private MyOpenHelper myOpenHelper;

    public MyDBAdapter(Context context) {
        myOpenHelper = new MyOpenHelper(context, DB_name, null, DB_version);
    }

    public void open() {
        myDataBase = myOpenHelper.getWritableDatabase();
    }

    public void close() {
        myDataBase.close();
    }

    public void InsertUnEvent(Evenement event) {
        insertEvent(event);
        long idEvent = getEventID(event.getNom(), event.getDateLimite());
        Log.i("idEvent", "" + idEvent);
        for (int i = 0; i < event.getTaches().size(); i++) {

            Log.i("Tache", "" + event.getTaches().get(i).getNom());
            insertTache(event.getTaches().get(i), idEvent);
        }
    }

    public ArrayList<Evenement> getAllEvent() {
        ArrayList<Evenement> events = new ArrayList<Evenement>();
        Cursor c = myDataBase.query(Event_Table, new String[]{col_ID, col_Name, col_DateLimite},
                null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            ArrayList<Tache> taches = getEventTache(c.getLong(0));
            events.add(new Evenement(c.getLong(0), c.getString(1), c.getString(2), taches));
            c.moveToNext();
        }
        return events;
    }

    public long insertEvent(Evenement event) {
        ContentValues values = new ContentValues();
        values.put(col_Name, event.getNom());
        values.put(col_DateLimite, event.getDateLimite());
        return myDataBase.insert(Event_Table, null, values);
    }

    public long getEventID(String name, String dateLimite) {
        long id = 0;
        Cursor c = myDataBase.query(Event_Table, new String[]{col_ID, col_Name, col_DateLimite},
                col_Name + "='" + name + "' AND " + col_DateLimite + "='" + dateLimite + "'", null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            id = c.getLong(0);
        }
        return id;
    }

    public Evenement getEvent(long id) {
        Evenement event = new Evenement();
        Cursor c = myDataBase.query(Event_Table, new String[]{col_ID, col_Name, col_DateLimite},
                col_ID + "='" + id + "'", null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                ArrayList<Tache> taches = getEventTache(id);
                event = new Evenement(c.getLong(0), c.getString(1), c.getString(2), taches);
                c.moveToNext();
            }
        }
        return event;
    }

    public long insertTache(Tache tache, long idEvent) {
        ContentValues values = new ContentValues();
        values.put(col_Name, tache.getNom());
        values.put(col_Magasin, tache.getNomMagasin());
        values.put(col_URL, tache.getSiteMagasin());
        values.put(col_IDEvent, idEvent);
        return myDataBase.insert(Tache_Table, null, values);
    }

    public ArrayList<Tache> getEventTache(long id) {
        ArrayList<Tache> taches = new ArrayList<Tache>();
        Cursor c = myDataBase.query(Tache_Table, new String[]{col_ID, col_Name, col_Magasin, col_URL, col_IDEvent},
                col_IDEvent + "='" + id + "'", null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                taches.add(new Tache(c.getString(1), c.getString(2), c.getString(3)));
                c.moveToNext();
            }
        }
        c.close();
        return taches;
    }

    private class MyOpenHelper extends SQLiteOpenHelper {
        public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
            super(context, name, cursorFactory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(create_table_event);
            db.execSQL(create_table_tache);
            Log.i("test", "Test de création");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(String.format("drop table %s ;", create_table_event));
            db.execSQL(String.format("drop table %s;", create_table_tache));
            onCreate(db);
        }
    }
}
