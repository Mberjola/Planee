package com.example.planeeandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBAdapter {
    //Mise en place des éléments de la base de données (Version, création des tables...)
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

    //Insertion d'un évènement dans la base (Evenement + tâches)
    public void InsertUnEvent(Evenement event) {
        insertEvent(event);
        long idEvent = getEventID(event.getNom(), event.getDateLimite());
        for (int i = 0; i < event.getTaches().size(); i++) {
            if (!(event.getTaches().get(i).getNom().equals("")))
                insertTache(event.getTaches().get(i), idEvent);
        }
    }

    //Récupération de tout les évènements
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

    //Insertion d'un évènement dans la table évènement
    public long insertEvent(Evenement event) {
        ContentValues values = new ContentValues();
        values.put(col_Name, event.getNom());
        values.put(col_DateLimite, event.getDateLimite());
        values.put(col_Heure, event.getHeure());
        return myDataBase.insert(Event_Table, null, values);
    }

    //Mise à jour d'un évènement (évènement + tâches)
    public long UpdateEvent(Evenement event, long id) {
        ContentValues cv = new ContentValues();
        cv.put(col_Name, event.getNom());
        cv.put(col_DateLimite, event.getDateLimite());
        cv.put(col_Heure, event.getHeure());
        ArrayList<Tache> Taches = event.getTaches();
        for (int i = 0; i < Taches.size(); i++) {
            updateTask(Taches.get(i), id);
        }
        return myDataBase.update(Event_Table, cv, col_ID + "=" + id, null);
    }

    //Récupérer l'id d'un évènement
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
        }
        for (int i = 0; i < evenements.size(); i++) {
            Evenement evenement = evenements.get(i);
            if (evenement.getNom().equals(name)) {
                id = evenement.getId();
            }
        }
        return id;
    }

    //Récupération d'un évènement à partir de l'id
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

    //Suppression d'un évènement (évènement + tâches)
    public void supprimerEvent(long id) {
        supprimerTache(id);
        myDataBase.delete(Event_Table, col_ID + "=" + id, null);
    }


    //Insertion d'un tâche
    public long insertTache(Tache tache, long idEvent) {
        ContentValues values = new ContentValues();
        values.put(col_Name, tache.getNom());
        values.put(col_Magasin, tache.getNomMagasin());
        values.put(col_URL, tache.getSiteMagasin());
        values.put(col_IDEvent, idEvent);
        return myDataBase.insert(Tache_Table, null, values);
    }

    //Mise à jour d'une tâche
    public long updateTask(Tache tache, Long IdEvent) {
        ContentValues values = new ContentValues();
        values.put(col_Name, tache.getNom());
        values.put(col_Magasin, tache.getNomMagasin());
        values.put(col_URL, tache.getSiteMagasin());
        values.put(col_IDEvent, IdEvent);
        return myDataBase.update(Tache_Table, values, col_ID + "=" + tache.getId(), null);
    }

    //Récupérer l'ensemble des tâches d'un évènement
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

    //Suppression d'une tâche à partir de la clé étrangère IdEvenement
    public void supprimerTache(long idEvent) {
        myDataBase.delete(Tache_Table, col_IDEvent + "=" + idEvent, null);
    }

    //Suppression d'une tâche à partir de son Id
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
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(String.format("drop table %s ;", create_table_event));
            db.execSQL(String.format("drop table %s;", create_table_tache));
            onCreate(db);
        }
    }
}
