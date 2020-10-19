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

class insertContact : AppCompatActivity() {
    private var editTextPrenom: EditText? = null
    private var editTextNom: EditText? = null
    private var editTextPhone: EditText? = null
    private var editTextAdresse: EditText? = null
    private var editTextEmail: EditText? = null
    private val contactItem: ContactItem? = null
    private var sauvegarder: Button? = null
    private var annuler: Button? = null
    private var collectionEditText: ArrayList<EditText?>? = null
    private var allFieldFilled: Boolean? = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_contact)
        supportActionBar?.hide()

        //recuperation des views
        sauvegarder = findViewById<View?>(R.id.buttonSave) as Button
        annuler = findViewById<View?>(R.id.buttonCancel) as Button
        editTextPrenom = findViewById<View?>(R.id.editTextPrenom) as EditText
        editTextNom = findViewById<View?>(R.id.editTextNom) as EditText
        editTextAdresse = findViewById<View?>(R.id.editTextAdress) as EditText
        editTextPhone = findViewById<View?>(R.id.editTextPhone) as EditText
        editTextEmail = findViewById<View?>(R.id.editTextEmail) as EditText
        collectionEditText = ArrayList()
        collectionEditText!!.add(editTextAdresse)
        collectionEditText!!.add(editTextPhone)
        collectionEditText!!.add(editTextEmail)
        collectionEditText!!.add(editTextNom)
        collectionEditText!!.add(editTextPrenom)

        //listener
        sauvegarder!!.setOnClickListener(View.OnClickListener { sauvegarder() })
        annuler!!.setOnClickListener(View.OnClickListener { cancel() })
    }

    //methode pr annuler sauvegarde
    fun cancel() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        val toast = Toast.makeText(applicationContext, "Enregistrement annule", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
        toast.show()
        finish()
    }

    //methode pr sauvegarder un contact
    private fun sauvegarder() {
        estVide(collectionEditText)
        if (allFieldFilled!!) { //tous les champs sont remplis
            val sqLiteController = SQLiteController(applicationContext)
            val nom: String
            val prenom: String
            val phone: String
            val adresse: String
            val email: String
            nom = editTextNom?.getText().toString()
            prenom = editTextPrenom!!.getText().toString()
            phone = editTextPhone?.getText().toString()
            adresse = editTextAdresse?.getText().toString()
            email = editTextEmail?.getText().toString()
            val l = sqLiteController.insertContact(ContactItem(1, nom, prenom, phone, adresse, email))
            if (l != -1L) {
                //insertion reussie
                val message = "$prenom a ete enregistre avec succes!"
                val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                toast.show()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                toast.show()
                finish()
            } else {
                //insertion echoue, reessayez
                val toast = Toast.makeText(applicationContext, "Echec d'enregistrement", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                toast.show()
                nettoyerChamps(collectionEditText)
            }
        } else {
            allFieldFilled = true
        }
    }

    /* methode pr verifier si les champs sont remplis */
    private fun estVide(editTextArray: ArrayList<EditText?>?) {
        for (i in editTextArray!!.indices) {
            if (TextUtils.isEmpty(editTextArray.get(i)!!.getText())) {
                allFieldFilled = false
                editTextArray.get(i)!!.setError("Champs obligatoire")
            }
        }
    }

    //methode pour clear les champs
    private fun nettoyerChamps(editTextArray: ArrayList<EditText?>?) {
        for (i in editTextArray!!.indices) {
            editTextArray.get(i)!!.setText("")
        }
    }
}