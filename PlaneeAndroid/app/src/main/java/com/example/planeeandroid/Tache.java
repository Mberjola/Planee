package com.example.planeeandroid;

import java.net.URL;
import java.util.Date;

public class Tache {
    private String nom;
    private String nomMagasin;
    private String SiteMagasin;

    public Tache(String nom, String nomMagasin, String siteMagasin) {
        this.nom = nom;
        this.nomMagasin = nomMagasin;
        this.SiteMagasin = siteMagasin;
    }

    public Tache() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomMagasin() {
        return nomMagasin;
    }

    public void setNomMagasin(String nomMagasin) {
        this.nomMagasin = nomMagasin;
    }

    public String getSiteMagasin() {
        return SiteMagasin;
    }

    public void setSiteMagasin(String siteMagasin) {
        SiteMagasin = siteMagasin;
    }
}
