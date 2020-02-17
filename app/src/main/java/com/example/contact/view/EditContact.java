package com.example.contact.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contact.R;
import com.example.contact.controller.SQLiteController;
import com.example.contact.model.ContactItem;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class EditContact extends AppCompatActivity {

private Button buttonSave, buttonCancel;
private EditText editTextNom, editTextPrenom, editTextPhone, editTextAdresse, editTextEmail;
private ContactItem contactItem;
private boolean filled =true;
private ArrayList<EditText> collectionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        getSupportActionBar().hide();

        //recuperation des donnees de l'intent
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String nom = intent.getStringExtra("nom");
        final String prenom = intent.getStringExtra("prenom");
        final String adresse = intent.getStringExtra("adresse");
        final String phone = intent.getStringExtra("phone");
        final String email = intent.getStringExtra("email");

//        contactItem = new ContactItem((id),nom,prenom,phone,adresse,email);

        //recuperations des champs de saisie
        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        editTextAdresse = (EditText) findViewById(R.id.editTextAdress);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        collectionEditText = new ArrayList<>();
        collectionEditText.add(editTextAdresse);
        collectionEditText.add(editTextPhone);
        collectionEditText.add(editTextEmail);
        collectionEditText.add(editTextNom);
        collectionEditText.add(editTextPrenom);


        //charger les donnees a modifier
        editTextPrenom.setText(prenom);
        editTextNom.setText(nom);
        editTextEmail.setText(email);
        editTextPhone.setText(phone);
        editTextAdresse.setText(adresse);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Modification annulee", LENGTH_SHORT);
                toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
                toast.show();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estVide();
                if(filled){

                    SQLiteController sqLiteController = new SQLiteController(getApplicationContext());
                   int i= sqLiteController.updateContact(id, editTextNom.getText().toString(), editTextPrenom.getText().toString(), editTextPhone.getText().toString(), editTextAdresse.getText().toString(), editTextEmail.getText().toString());

                   if(i==1){//si tout s'est bien deroule

                       Toast toast = Toast.makeText(getApplicationContext(), "Modification effectuee", LENGTH_SHORT);
                       toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
                       toast.show();
                       Intent intent2;
                       intent2 = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(intent2);
                       finish();

                   }else { //sinon

                       Toast toast = Toast.makeText(getApplicationContext(), "Modification echouee", LENGTH_SHORT);
                       toast.setGravity((Gravity.TOP| Gravity.CENTER_VERTICAL), 1, 5);
                       toast.show();
                       Intent intent3;
                       intent3 = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(intent3);
                       finish();
                   }

                }else{
                    filled =true;
                }
            }
        });

    }

    /* methode pr verifier si les champs sont remplis */
    private void estVide (){

        for(int i = 0; i<collectionEditText.size(); i++){
            if( TextUtils.isEmpty(collectionEditText.get(i).getText())){

                this.filled = false;
                collectionEditText.get(i).setError( "Champs obligatoire" );

            }
        }

    }
}
