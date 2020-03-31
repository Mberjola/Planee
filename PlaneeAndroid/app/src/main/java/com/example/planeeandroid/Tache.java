package com.example.planeeandroid;

import java.net.URL;
import java.util.Date;

public class Tache {
    private String nom;
    private String nomMagasin;
    private URL SiteMagasin;

    public Tache(String nom, String nomMagasin, URL siteMagasin) {
        this.nom = nom;
        this.nomMagasin = nomMagasin;
        this.SiteMagasin = siteMagasin;
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

    public URL getSiteMagasin() {
        return SiteMagasin;
    }

    public void setSiteMagasin(URL siteMagasin) {
        SiteMagasin = siteMagasin;
    }
}
