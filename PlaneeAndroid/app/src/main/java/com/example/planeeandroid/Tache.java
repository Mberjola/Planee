package com.example.planeeandroid;

public class Tache {
    //Mise en place d'une tâche
    private long id;
    private String nom;
    private String nomMagasin;
    private String SiteMagasin;

    //Constructeurs
    public Tache(long id, String nom, String nomMagasin, String siteMagasin) {
        this.id = id;
        this.nom = nom;
        this.nomMagasin = nomMagasin;
        SiteMagasin = siteMagasin;
    }

    public Tache(String nom, String nomMagasin, String siteMagasin) {
        this.nom = nom;
        this.nomMagasin = nomMagasin;
        this.SiteMagasin = siteMagasin;
    }

    public Tache() {

    }

    //Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
