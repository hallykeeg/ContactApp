package com.example.contact.controller;

import com.example.contact.model.ContactItem;

public final class Controller {

    public static  Controller controller=null;
    private ContactItem contactItem;

    /* singleton pattern */
    private Controller(){
        super();
    }

    public static Controller getController(){
        if(controller == null){
            controller = new Controller();

        }
        return controller;

    }

    public void nouveauContact(String nom, String prenom, String phone, String adresse, String email){

    }
}
