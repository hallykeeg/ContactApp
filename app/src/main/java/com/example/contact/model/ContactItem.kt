package com.example.contact.model

class ContactItem
(var id: Int, var nom: String, var prenom: String, var phone: String, var adresse: String, var email: String) {

    override fun toString(): String {
        return nom
    }
}