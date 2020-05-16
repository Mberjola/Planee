package com.example.planeeandroid;

import java.util.ArrayList;

public class Evenement {
    //Création d'un évènement
    private long id;
    private String nom;
    private String dateLimite;
    private ArrayList<Tache> Taches;
    private String heure;

    //Constructeurs
    public Evenement(long id, String nom, String dateLimite, ArrayList<Tache> Taches, String heure) {
        this.id = id;
        this.nom = nom;
        this.dateLimite = dateLimite;
        this.Taches = Taches;
        this.heure = heure;
    }

    public Evenement(String nom, String dateLimite, ArrayList<Tache> Taches, String heure) {
        this.id = 0;
        this.nom = nom;
        this.dateLimite = dateLimite;
        Taches = Taches;
        this.heure = heure;
    }

    public Evenement() {

    }

    //Getters et setters
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

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}
