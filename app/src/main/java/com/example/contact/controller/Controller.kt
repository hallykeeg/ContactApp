package com.example.contact.controller

import com.example.contact.model.ContactItem
import org.junit.runner.RunWith

class Controller  /* singleton pattern */
private constructor() {
    private val contactItem: ContactItem? = null
    fun nouveauContact(nom: String?, prenom: String?, phone: String?, adresse: String?, email: String?) {}

    companion object {
        var controller: Controller?=null
        fun Controller(): Controller? {

            if (controller == null) {
                controller = Controller()
            }
            return controller
        }
    }
}