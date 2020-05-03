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
    public static final String col_Heure = "heure";

    private static final String create_table_event =
            String.format("create table %s(%s integer primary key autoincrement, %s text not null, %s text not null,%s text non null);", Event_Table, col_ID, col_Name, col_DateLimite, col_Heure);


    private static final String Tache_Table = "table_tache";
    private static final String col_IDEvent = "id_event";
    private static final String col_Magasin = "magasin";
    private static final String col_URL = "url_magasin";
    private static final String create_table_tache = String.format("create table %s(%s integer primary key autoincrement, %s text , %s text , %s text, %s integer, FOREIGN KEY (%s) REFERENCES %s(%s));", Tache_Table, col_ID, col_Name, col_Magasin, col_URL, col_IDEvent, col_IDEvent, Event_Table, col_ID);

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
            if (!(event.getTaches().get(i).getNom().equals("")))
                insertTache(event.getTaches().get(i), idEvent);
        }
    }

    public ArrayList<Evenement> getAllEvent() {
        ArrayList<Evenement> events = new ArrayList<Evenement>();
        Cursor c = myDataBase.query(Event_Table, new String[]{col_ID, col_Name, col_DateLimite, col_Heure},
                null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            ArrayList<Tache> taches = getEventTache(c.getLong(0));
            events.add(new Evenement(c.getLong(0), c.getString(1), c.getString(2), taches, c.getString(3)));
            c.moveToNext();
        }
        c.close();
        return events;
    }

    public long insertEvent(Evenement event) {
        ContentValues values = new ContentValues();
        values.put(col_Name, event.getNom());
        values.put(col_DateLimite, event.getDateLimite());
        values.put(col_Heure, event.getHeure());
        return myDataBase.insert(Event_Table, null, values);
    }

    public long getEventID(String name, String dateLimite) {
        long id = -1;
        ArrayList<Evenement> evenements = new ArrayList<Evenement>();
        Cursor c = myDataBase.query(Event_Table, new String[]{col_ID, col_Name, col_DateLimite, col_Heure},
                col_DateLimite + "='" + dateLimite + "'", null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                ArrayList<Tache> taches = getEventTache(c.getLong(0));
                evenements.add(new Evenement(c.getLong(0), c.getString(1), c.getString(2), taches, c.getString(3)));
                c.moveToNext();
            }
        } else {
            Log.i("message d'erreur", "Rien trouver ID");
        }
        for (int i = 0; i < evenements.size(); i++) {
            Evenement evenement = evenements.get(i);
            if (evenement.getNom().equals(name)) {
                id = evenement.getId();
                Log.i("messageevenementgetId", "" + id);
            }
        }
        return id;
    }

    public Evenement getEvent(long id) {
        Evenement event = new Evenement();
        Cursor c = myDataBase.query(Event_Table, new String[]{col_ID, col_Name, col_DateLimite, col_Heure},
                col_ID + "='" + id + "'", null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                ArrayList<Tache> taches = getEventTache(id);
                event = new Evenement(c.getLong(0), c.getString(1), c.getString(2), taches, c.getString(3));
                c.moveToNext();
            }
        }
        c.close();
        return event;
    }

    public void supprimerEvent(long id) {
        supprimerTache(id);
        myDataBase.delete(Event_Table, col_ID + "=" + id, null);
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
                taches.add(new Tache(c.getLong(0), (c.getString(1) == null ? "" : c.getString(1)), (c.getString(2) == null ? "" : c.getString(2)),
                        (c.getString(3) == null ? "" : c.getString(3))));
                c.moveToNext();
            }
        }
        c.close();
        return taches;
    }

    public void supprimerTache(long idEvent) {
        myDataBase.delete(Tache_Table, col_IDEvent + "=" + idEvent, null);
    }

    public void supprimerTacheId(long id) {
        myDataBase.delete(Tache_Table, col_ID + "=" + id, null);
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
