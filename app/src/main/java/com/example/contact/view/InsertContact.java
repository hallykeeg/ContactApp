package com.example.contact.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.contact.R;
import com.example.contact.controller.SQLiteController;
import com.example.contact.model.ContactItem;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class InsertContact extends AppCompatActivity {

    private EditText editTextPrenom, editTextNom, editTextPhone, editTextAdresse, editTextEmail;
    private ContactItem contactItem;
    private ImageButton sauvegarder, annuler;
    private ArrayList<EditText> collectionEditText;
    private Boolean allFieldFilled=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_contact);
        getSupportActionBar().hide();

        //recuperation des views
        sauvegarder = (ImageButton) findViewById(R.id.buttonSave);
        annuler = (ImageButton) findViewById(R.id.buttonCancel);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextAdresse = (EditText) findViewById(R.id.editTextAdress);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        collectionEditText = new ArrayList<>();

        collectionEditText.add(editTextNom);


        //listener
        sauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sauvegarder();
            }
        });
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });


    }

    //methode pr annuler sauvegarde

    public void cancel (){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        Toast toast = Toast.makeText(getApplicationContext(), "ENREGISTREMENT ANNULE", LENGTH_SHORT);
        toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
        toast.show();
//        finish();
    }

    //methode pr sauvegarder un contact

    private void sauvegarder(){
        this.estVide(collectionEditText);
        if(this.allFieldFilled){//tous les champs sont remplis

            SQLiteController sqLiteController = new SQLiteController(getApplicationContext());
            String nom, prenom, phone, adresse, email;
            nom = editTextNom.getText().toString();
            prenom = editTextPrenom.getText().toString();
            phone = editTextPhone.getText().toString();
            adresse = editTextAdresse.getText().toString();
            email = editTextEmail.getText().toString();

            long l = sqLiteController.insertContact(new ContactItem(1, nom, prenom, phone, adresse, email));
            if(l==-1){

                //insertion echoue, reessayez
                Toast toast = Toast.makeText(getApplicationContext(), "ECHEC ENREGISTREMENT", LENGTH_SHORT);
                toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
                toast.show();
                this.nettoyerChamps(collectionEditText);


            }
//            else if (l==-33){
//                //ce contact existe deja
//
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(InsertContact.this);
//                builder1.setTitle("Redondance");
//                String message =prenom+" "+nom+" existe deja dans vos contacts";
//                builder1.setMessage(message);
//                builder1.setCancelable(true);
//                builder1.setNeutralButton(android.R.string.ok,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//            }
            else{
                //insertion reussie
                String message = prenom+" A ETE ENREGISTRE!";
                Toast toast = Toast.makeText(getApplicationContext(), message, LENGTH_SHORT);
                toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                toast.show();
                finish();


            }

        }else{
            this.allFieldFilled = true;
        }
    }

    /* methode pr verifier si les champs sont remplis */
    private void estVide (ArrayList<EditText> editTextArray){

        for(int i = 0; i<editTextArray.size(); i++){
            if( TextUtils.isEmpty(editTextArray.get(i).getText())){

                this.allFieldFilled = false;
                editTextArray.get(i).setError( "Champs obligatoire" );

            }
        }

    }


    //methode pour clear les champs
    private void nettoyerChamps(ArrayList<EditText> editTextArray){
        for(int i = 0; i<editTextArray.size(); i++){
            editTextArray.get(i).setText("");
        }
    }




}
