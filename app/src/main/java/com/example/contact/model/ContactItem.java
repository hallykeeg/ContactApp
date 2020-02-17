package com.example.contact.model;

import static java.lang.String.format;

public class ContactItem {
    private String  nom, prenom, phone, adresse, email;
    private Integer id;

    //constructeur


    public ContactItem(Integer id, String nom, String prenom, String phone, String adresse, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.adresse = adresse;
        this.email = email;
    }

    @Override
    public String toString(){
        String format = format("%s %s", this.nom, this.prenom);
        return format;


    }

    //getter

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    //setter

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
