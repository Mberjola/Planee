package com.example.planeeandroid;

import java.util.ArrayList;
import java.util.Date;

public class Evenement {
    private String nom;
    private Date dateLimite;
    private ArrayList<Tache> Taches;

    public Evenement(String nom, Date dateLimite, ArrayList<Tache> taches) {
        this.nom = nom;
        this.dateLimite = dateLimite;
        Taches = taches;
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

    public Date getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(Date dateLimite) {
        this.dateLimite = dateLimite;
    }
}
