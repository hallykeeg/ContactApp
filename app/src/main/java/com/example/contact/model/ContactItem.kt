package com.example.contact.model

import org.junit.runner.RunWith

class ContactItem     //constructeur
(private var id: Int?, private var nom: String?, private var prenom: String?, private var phone: String?, private var adresse: String?, private var email: String?) {
    override fun toString(): String {
        return String.format("%s %s", nom, prenom)
    }

    //getter
    fun getId(): Int? {
        return id
    }

    fun getNom(): String? {
        return nom
    }

    fun getPrenom(): String? {
        return prenom
    }

    fun getPhone(): String? {
        return phone
    }

    fun getAdresse(): String? {
        return adresse
    }

    fun getEmail(): String? {
        return email
    }

    //setter
    fun setId(id: Int?) {
        this.id = id
    }

    fun setNom(nom: String?) {
        this.nom = nom
    }

    fun setPrenom(prenom: String?) {
        this.prenom = prenom
    }

    fun setPhone(phone: String?) {
        this.phone = phone
    }

    fun setAdresse(adresse: String?) {
        this.adresse = adresse
    }

    fun setEmail(email: String?) {
        this.email = email
    }
}