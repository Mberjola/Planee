package com.example.planeeandroid;

import java.util.ArrayList;
import java.util.Date;

public class Evenement {
    private long id;
    private String nom;
    private String dateLimite;
    private ArrayList<Tache> Taches;

    public Evenement(long id, String nom, String dateLimite, ArrayList<Tache> taches) {
        this.id = id;
        this.nom = nom;
        this.dateLimite = dateLimite;
        Taches = taches;
    }

    public Evenement(String nom, String dateLimite, ArrayList<Tache> taches) {
        this.id = 0;
        this.nom = nom;
        this.dateLimite = dateLimite;
        Taches = taches;
    }

    public Evenement() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Tache> getTaches() {
        return Taches;
    }

    public void setTaches(ArrayList<Tache> taches) {
        Taches = taches;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(String dateLimite) {
        this.dateLimite = dateLimite;
    }
}
