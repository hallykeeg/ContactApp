package com.example.contact.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contact.R
import com.example.contact.controller.SQLiteController
import com.example.contact.model.ContactItem
import org.junit.runner.RunWith
import java.util.*

class EditContact : AppCompatActivity() {
    private var buttonSave: Button? = null
    private var buttonCancel: Button? = null
    private var editTextNom: EditText? = null
    private var editTextPrenom: EditText? = null
    private var editTextPhone: EditText? = null
    private var editTextAdresse: EditText? = null
    private var editTextEmail: EditText? = null
    private val contactItem: ContactItem? = null
    private var filled = true
    private var collectionEditText: ArrayList<EditText?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        supportActionBar?.hide()

        //recuperation des donnees de l'intent
        val intent = intent
        val id = intent.getStringExtra("id")
        val nom = intent.getStringExtra("nom")
        val prenom = intent.getStringExtra("prenom")
        val adresse = intent.getStringExtra("adresse")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")

//        contactItem = new ContactItem((id),nom,prenom,phone,adresse,email);

        //recuperations des champs de saisie
        editTextNom = findViewById<View?>(R.id.editTextNom) as EditText
        editTextPrenom = findViewById<View?>(R.id.editTextPrenom) as EditText
        editTextAdresse = findViewById<View?>(R.id.editTextAdress) as EditText
        editTextPhone = findViewById<View?>(R.id.editTextPhone) as EditText
        editTextEmail = findViewById<View?>(R.id.editTextEmail) as EditText
        collectionEditText = ArrayList()
        collectionEditText!!.add(editTextAdresse)
        collectionEditText!!.add(editTextPhone)
        collectionEditText!!.add(editTextEmail)
        collectionEditText!!.add(editTextNom)
        collectionEditText!!.add(editTextPrenom)


        //charger les donnees a modifier
        editTextPrenom!!.setText(prenom)
        editTextNom!!.setText(nom)
        editTextEmail!!.setText(email)
        editTextPhone!!.setText(phone)
        editTextAdresse!!.setText(adresse)
        buttonCancel = findViewById<View?>(R.id.buttonCancel) as Button
        buttonSave = findViewById<View?>(R.id.buttonSave) as Button
        buttonCancel!!.setOnClickListener(View.OnClickListener {
            val toast = Toast.makeText(applicationContext, "Modification annulee", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
            toast.show()
            val intent1 = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent1)
            finish()
        })
        buttonSave!!.setOnClickListener(View.OnClickListener {
            estVide()
            if (filled) {
                val sqLiteController = SQLiteController(applicationContext)
                val i = sqLiteController.updateContact(id, editTextNom!!.getText().toString(), editTextPrenom!!.getText().toString(), editTextPhone!!.getText().toString(), editTextAdresse!!.getText().toString(), editTextEmail!!.getText().toString())
                if (i == 1) { //si tout s'est bien deroule
                    val toast = Toast.makeText(applicationContext, "Modification effectuee", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                    toast.show()
                    val intent2: Intent
                    intent2 = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent2)
                    finish()
                } else { //sinon
                    val toast = Toast.makeText(applicationContext, "Modification echouee", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                    toast.show()
                    val intent3: Intent
                    intent3 = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent3)
                    finish()
                }
            } else {
                filled = true
            }
        })
    }

    /* methode pr verifier si les champs sont remplis */
    private fun estVide() {
        for (i in collectionEditText!!.indices) {
            if (TextUtils.isEmpty(collectionEditText?.get(i)?.getText())) {
                filled = false
                collectionEditText?.get(i)?.setError("Champs obligatoire")
            }
        }
    }
}