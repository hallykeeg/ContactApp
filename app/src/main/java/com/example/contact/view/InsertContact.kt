package com.example.contact.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.contact.R
import com.example.contact.controller.SQLiteController
import com.example.contact.model.ContactItem
import org.json.JSONObject
import java.util.*

class InsertContact : AppCompatActivity() {
    private var editTextPrenom: EditText? = null
    private var editTextNom: EditText? = null
    private var editTextPhone: EditText? = null
    private var editTextAdresse: EditText? = null
    private var editTextEmail: EditText? = null
    private val contactItem: ContactItem? = null
    private var sauvegarder: ImageButton? = null
    private var annuler: ImageButton? = null
    lateinit var requestQ: RequestQueue
    private var collectionEditText: ArrayList<EditText?>? = null
    private var allFieldFilled = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_contact)
        supportActionBar!!.hide()

        //recuperation des views
        sauvegarder = findViewById<View>(R.id.buttonSave) as ImageButton
        annuler = findViewById<View>(R.id.buttonCancel) as ImageButton
        editTextPrenom = findViewById<View>(R.id.editTextPrenom) as EditText
        editTextNom = findViewById<View>(R.id.editTextNom) as EditText
        editTextAdresse = findViewById<View>(R.id.editTextAdress) as EditText
        editTextPhone = findViewById<View>(R.id.editTextPhone) as EditText
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        collectionEditText = ArrayList()
        collectionEditText!!.add(editTextNom)
        //listener
        sauvegarder!!.setOnClickListener { sauvegarder() }
        annuler!!.setOnClickListener { cancel() }
    }

    //methode pr annuler sauvegarde
    fun cancel() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        val toast = Toast.makeText(applicationContext, "ENREGISTREMENT ANNULE", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
        toast.show()
        //        finish();
    }

    //methode pr sauvegarder un contact
    private fun sauvegarder() {
        estVide(collectionEditText)
        if (allFieldFilled) { //tous les champs sont remplis
           // val sqLiteController = SQLiteController(applicationContext)
            val nom: String = editTextNom!!.text.toString()
            val prenom: String = editTextPrenom!!.text.toString()
            val phone: String = editTextPhone!!.text.toString()
            val adresse: String = editTextAdresse!!.text.toString()
            val email: String = editTextEmail!!.text.toString()

           // val l = sqLiteController.insertContact(ContactItem(1, nom, prenom, phone, adresse, email))

            val url="http://192.168.0.103/contact/manager.php"
            var etat: Boolean = true
            requestQ = Volley.newRequestQueue(applicationContext)
            val pushRequest : StringRequest = object : StringRequest(Method.POST, url,
                    Response.Listener {
                        val jsonResponse = JSONObject(it)
                        MainActivity.arrayAdapter!!.notifyDataSetChanged()
                    },
                    Response.ErrorListener {
//                    val jsonResponseError = JSONObject(it)
                    }){
                override fun getParams(): MutableMap<String, String> {

                    val params: MutableMap<String, String> = HashMap()
                    params["action"]="insert"
                    params["nom"] = nom
                    params["prenom"] = prenom
                    params["phone"] = phone
                    params["adresse"]= adresse
                    params["email"] = email

                    return params
                }
            }
            requestQ.add(pushRequest)

            if (!etat) {

                //insertion echoue, reessayez
                val toast = Toast.makeText(applicationContext, "ECHEC ENREGISTREMENT", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                toast.show()
                nettoyerChamps(collectionEditText)
            } else {
                //insertion reussie
                val message = "$prenom A ETE ENREGISTRE!"
                val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                toast.show()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                toast.show()
                finish()
            }
        } else {
            allFieldFilled = true
        }
    }

    /* methode pr verifier si les champs sont remplis */
    private fun estVide(editTextArray: ArrayList<EditText?>?) {
        for (i in editTextArray!!.indices) {
            if (TextUtils.isEmpty(editTextArray[i]!!.text)) {
                allFieldFilled = false
                editTextArray[i]!!.error = "Champs obligatoire"
            }
        }
    }

    //methode pour clear les champs
    private fun nettoyerChamps(editTextArray: ArrayList<EditText?>?) {
        for (i in editTextArray!!.indices) {
            editTextArray[i]!!.setText("")
        }
    }
}