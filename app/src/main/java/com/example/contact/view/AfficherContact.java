package com.example.contact.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contact.R;
import com.example.contact.controller.SQLiteController;
import com.example.contact.model.ContactItem;

import static android.widget.Toast.LENGTH_SHORT;

public class AfficherContact extends AppCompatActivity {
private TextView textViewNom, textViewPrenom, textViewAdresse, textViewPhone, textViewEmail;
private ImageButton modifier, supprimer;
private ContactItem contactItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_contact);
//        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /* recuperation du nom, du prenom, ...
        * du contact selectionne */
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id", -1);
        SQLiteController sqLiteController = new SQLiteController(getApplicationContext());
       Cursor cursor = sqLiteController.selectContactByID(id.toString());

       cursor.moveToFirst();

       final String idContact = cursor.getString(cursor.getColumnIndex("id"));
       final String nom = cursor.getString(cursor.getColumnIndex("nom"));
       final String prenom = cursor.getString(cursor.getColumnIndex("prenom"));
       final String phone = cursor.getString(cursor.getColumnIndex("phone"));
       final String adresse = cursor.getString(cursor.getColumnIndex("adresse"));
       final String email = cursor.getString(cursor.getColumnIndex("email"));

//        contactItem = new ContactItem(idContact, nom, prenom, phone, adresse,email);


        /* recuperation des vues */

        textViewNom = (TextView) findViewById(R.id.TextViewNom);
        textViewPrenom = (TextView) findViewById(R.id.TextViewPrenom);
        textViewAdresse = (TextView) findViewById(R.id.TextViewAdress);
        textViewPhone = (TextView) findViewById(R.id.TextViewPhone);
        textViewEmail = (TextView) findViewById(R.id.TextViewEmail);

        modifier = (ImageButton) findViewById(R.id.imageButtonEditer);
        supprimer = (ImageButton) findViewById(R.id.imageButtonSupprimer);

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AfficherContact.this);

                builder.setTitle("Confirmation");
                builder.setMessage("Voulez-vous vraiment supprimer "+prenom+ " " +nom+" ?");

                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        SQLiteController sqLiteController1 = new SQLiteController(getApplicationContext());
                       int reponse = sqLiteController1.dropContact(idContact);
                       if(reponse==1){
                           Toast toast = Toast.makeText(getApplicationContext(), "CONTACT SUPPRIME", LENGTH_SHORT);
                           toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
                           toast.show();
                           Intent intent2;
                           intent2 = new Intent(getApplicationContext(), MainActivity.class);
                           startActivity(intent2);
                           finish();
                       }else {
                           Toast toast = Toast.makeText(getApplicationContext(), "ECHEC DE SUPPRESION", LENGTH_SHORT);
                           toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
                           toast.show();
                       }

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast toast = Toast.makeText(getApplicationContext(), "SUPRESSION ANNULEE", LENGTH_SHORT);
                        toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
                        toast.show();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        //action de modification de contact

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), EditContact.class);
                intent1.putExtra("id", idContact);
                intent1.putExtra("nom", nom);
                intent1.putExtra("prenom", prenom);
                intent1.putExtra("adresse", adresse);
                intent1.putExtra("phone", phone);
                intent1.putExtra("email",email);
                startActivity(intent1);
                finish();

            }
        });

        //affichage des textes dans les textview
        textViewNom.setText(nom);
        textViewPrenom.setText(prenom);
        textViewAdresse.setText(adresse);
        textViewPhone.setText(phone);
        textViewEmail.setText(email);

       }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        return super.onSupportNavigateUp();
    }
}

