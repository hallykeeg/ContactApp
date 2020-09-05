package com.example.contact.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contact.R
import com.example.contact.controller.SQLiteController
import com.example.contact.model.ContactItem

class AfficherContact : AppCompatActivity() {
    private var textViewNom: TextView? = null
    private var textViewPrenom: TextView? = null
    private var textViewAdresse: TextView? = null
    private var textViewPhone: TextView? = null
    private var textViewEmail: TextView? = null
    private var modifier: ImageButton? = null
    private var supprimer: ImageButton? = null
    private val contactItem: ContactItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afficher_contact)
        //        getSupportActionBar().hide();
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        /* recuperation du nom, du prenom, ...
        * du contact selectionne */
        val intent = intent
        val id = intent.getIntExtra("id", -1)
        val sqLiteController = SQLiteController(applicationContext)
        val cursor = sqLiteController.selectContactByID(id.toString())
        cursor.moveToFirst()
        val idContact = cursor.getString(cursor.getColumnIndex("id"))
        val nom = cursor.getString(cursor.getColumnIndex("nom"))
        val prenom = cursor.getString(cursor.getColumnIndex("prenom"))
        val phone = cursor.getString(cursor.getColumnIndex("phone"))
        val adresse = cursor.getString(cursor.getColumnIndex("adresse"))
        val email = cursor.getString(cursor.getColumnIndex("email"))

//        contactItem = new ContactItem(idContact, nom, prenom, phone, adresse,email);


        /* recuperation des vues */textViewNom = findViewById<View>(R.id.TextViewNom) as TextView
        textViewPrenom = findViewById<View>(R.id.TextViewPrenom) as TextView
        textViewAdresse = findViewById<View>(R.id.TextViewAdress) as TextView
        textViewPhone = findViewById<View>(R.id.TextViewPhone) as TextView
        textViewEmail = findViewById<View>(R.id.TextViewEmail) as TextView
        modifier = findViewById<View>(R.id.imageButtonEditer) as ImageButton
        supprimer = findViewById<View>(R.id.imageButtonSupprimer) as ImageButton
        supprimer!!.setOnClickListener {
            val builder = AlertDialog.Builder(this@AfficherContact)
            builder.setTitle("Confirmation")
            builder.setMessage("Voulez-vous vraiment supprimer $prenom $nom ?")
            builder.setPositiveButton("OUI") { dialog, which ->
                val sqLiteController1 = SQLiteController(applicationContext)
                val reponse = sqLiteController1.dropContact(idContact)
                if (reponse == 1) {
                    val toast = Toast.makeText(applicationContext, "CONTACT SUPPRIME", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                    toast.show()
                    val intent2: Intent
                    intent2 = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent2)
                    finish()
                } else {
                    val toast = Toast.makeText(applicationContext, "ECHEC DE SUPPRESION", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                    toast.show()
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("NON") { dialog, which ->
                val toast = Toast.makeText(applicationContext, "SUPRESSION ANNULEE", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                toast.show()
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }
        //action de modification de contact
        modifier!!.setOnClickListener {
            val intent1 = Intent(applicationContext, EditContact::class.java)
            intent1.putExtra("id", idContact)
            intent1.putExtra("nom", nom)
            intent1.putExtra("prenom", prenom)
            intent1.putExtra("adresse", adresse)
            intent1.putExtra("phone", phone)
            intent1.putExtra("email", email)
            startActivity(intent1)
            finish()
        }

        //affichage des textes dans les textview
        textViewNom!!.text = nom
        textViewPrenom!!.text = prenom
        textViewAdresse!!.text = adresse
        textViewPhone!!.text = phone
        textViewEmail!!.text = email
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
        return super.onSupportNavigateUp()
    }
}