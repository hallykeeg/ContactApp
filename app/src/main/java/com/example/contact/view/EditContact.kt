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

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EditContact : AppCompatActivity() {
    private var buttonSave: ImageButton? = null
    private var buttonCancel: ImageButton? = null
    private var editTextNom: EditText? = null
    private var editTextPrenom: EditText? = null
    private var editTextPhone: EditText? = null
    private var editTextAdresse: EditText? = null
    lateinit var requestQ: RequestQueue
    private var editTextEmail: EditText? = null
    private val contactItem: ContactItem? = null
    private var filled = true
    private var collectionEditText: ArrayList<EditText?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        supportActionBar!!.hide()

        //recuperation des donnees de l'intent
        val intent = intent
        val id = intent.getStringExtra("id")
        val nom = intent.getStringExtra("nom")
        val prenom = intent.getStringExtra("prenom")
        val adresse = intent.getStringExtra("adresse")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val id2 = intent.getStringExtra("id")

//        contactItem = new ContactItem((id),nom,prenom,phone,adresse,email);

        //recuperations des champs de saisie
        editTextNom = findViewById<View>(R.id.editTextNom) as EditText
        editTextPrenom = findViewById<View>(R.id.editTextPrenom) as EditText
        editTextAdresse = findViewById<View>(R.id.editTextAdress) as EditText
        editTextPhone = findViewById<View>(R.id.editTextPhone) as EditText
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        collectionEditText = ArrayList()
        collectionEditText!!.add(editTextNom)
        //
//        collectionEditText.add(editTextAdresse);
//        collectionEditText.add(editTextPhone);
//        collectionEditText.add(editTextEmail);
//
//        collectionEditText.add(editTextPrenom);


        //charger les donnees a modifier
        editTextPrenom!!.setText(prenom)
        editTextNom!!.setText(nom)
        editTextEmail!!.setText(email)
        editTextPhone!!.setText(phone)
        editTextAdresse!!.setText(adresse)
        buttonCancel = findViewById<View>(R.id.buttonCancel) as ImageButton
        buttonSave = findViewById<View>(R.id.buttonSave) as ImageButton
        buttonCancel!!.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "MODIFICATION ANNULEE", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 1, -5)
            toast.show()
            val intent1 = Intent(applicationContext, MainActivity::class.java)
//            intent1.putExtra("id", id_for_Intent)
            startActivity(intent1)
            finish()
        }
        buttonSave!!.setOnClickListener {
            estVide()
            if (filled) {
                val nomEdit: String = editTextNom!!.text.toString()
                val prenomEdit: String = editTextPrenom!!.text.toString()
                val phoneEdit: String = editTextPhone!!.text.toString()
                val adresseEdit: String = editTextAdresse!!.text.toString()
                val emailEdit: String = editTextEmail!!.text.toString()

                val params: MutableMap<String, String> = HashMap()
//                val sqLiteController = SQLiteController(applicationContext)
               // val i = sqLiteController.updateContact(id, editTextNom!!.text.toString(), editTextPrenom!!.text.toString(), editTextPhone!!.text.toString(), editTextAdresse!!.text.toString(), editTextEmail!!.text.toString())
                val url="http://192.168.0.103/contact/manager.php"
                var etat: Boolean = true
                requestQ = Volley.newRequestQueue(applicationContext)
                val pushRequest : StringRequest = object : StringRequest(Method.POST, url,
                        Response.Listener {
                            val jsonResponse = JSONObject(it)
                            val retour = jsonResponse.getString("response")
                            if (retour=="success"){
                                etat = true
                            }else{
                               etat= false
                            }
                            MainActivity.arrayAdapter.notifyDataSetChanged()
                        },
                        Response.ErrorListener {
//                    val jsonResponseError = JSONObject(it)
                        }){
                    override fun getParams(): MutableMap<String, String> {

                        params["action"]="update"
                        params["id"] =id
                        params["nom"] = nomEdit
                        params["prenom"] = prenomEdit
                        params["phone"] = phoneEdit
                        params["adresse"]= adresseEdit
                        params["email"] = emailEdit

                        return params
                    }
                }
                requestQ.add(pushRequest)

                if (etat) { //si tout s'est bien deroule
                    val toast = Toast.makeText(applicationContext, "EFFECTUE", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                    toast.show()
                    val intentRetour: Intent
                    intentRetour = Intent(this@EditContact, MainActivity::class.java)
//                    intentRetour.putExtra("id", id_for_Intent)

                    startActivity(intentRetour)
                    finish()
                } else { //sinon
                    val toast = Toast.makeText(applicationContext, "ECHEC DE MODIFICATION", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_VERTICAL, 1, 5)
                    toast.show()
//                    val intent3: Intent
//                    intent3 = Intent(applicationContext, AfficherContact::class.java)
//                    intent3.putExtra("id", id_for_Intent)
//                    startActivity(intent3)
//                    finish()
                }
            } else {
                filled = true
            }
        }
    }

    /* methode pr verifier si les champs sont remplis */
    private fun estVide() {
        for (i in collectionEditText!!.indices) {
            if (TextUtils.isEmpty(collectionEditText!![i]!!.text)) {
                filled = false
                collectionEditText!![i]!!.error = "Champs obligatoire"
            }
        }
    }
}