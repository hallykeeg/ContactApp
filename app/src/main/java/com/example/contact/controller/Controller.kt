package com.example.contact.controller

import com.example.contact.model.ContactItem

class Controller  /* singleton pattern */
private constructor() {
    private val contactItem: ContactItem? = null
    fun nouveauContact(nom: String?, prenom: String?, phone: String?, adresse: String?, email: String?) {}

    companion object {
       private var controller: Controller? = null
        fun getController(): Controller? {
            if (controller == null) {
                controller = Controller()
            }
            return controller
        }
    }
}