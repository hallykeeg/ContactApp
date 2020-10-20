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
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.contact.R
import com.example.contact.controller.SQLiteController
import com.example.contact.model.ContactItem
import org.json.JSONObject

class AfficherContact : AppCompatActivity() {
    private var textViewNom: TextView? = null
    private var textViewPrenom: TextView? = null
    private var textViewAdresse: TextView? = null
    private var textViewPhone: TextView? = null
    private var textViewEmail: TextView? = null
    private var modifier: ImageButton? = null
    lateinit var requestQ: RequestQueue
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
        val id = intent.getIntExtra("id", 1).toString()
        val nom = intent.getStringExtra("nom")
        val prenom = intent.getStringExtra("prenom")
        val phone = intent.getStringExtra("phone")
        val adresse = intent.getStringExtra("adresse")
        val email = intent.getStringExtra("email")



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
                val url="http://192.168.0.103/contact/manager.php"
                var etat :Boolean = true
                requestQ = Volley.newRequestQueue(applicationContext)
                val pushRequest : StringRequest = object : StringRequest(Method.POST, url,
                        Response.Listener {
                            val jsonResponse = JSONObject(it)
                            val retour = jsonResponse.getString("response")
//                            Toast.makeText(applicationContext,jsonResponse.getString("response"),Toast.LENGTH_LONG).show()
                            etat = jsonResponse.getString("response")=="success"
                            MainActivity.arrayAdapter!!.notifyDataSetChanged()
                        },
                        Response.ErrorListener {
//                    val jsonResponseError = JSONObject(it)
                        }){
                    override fun getParams(): MutableMap<String, String> {

                        val params: MutableMap<String, String> = HashMap()
                        params["action"]="delete"
                        params["id"] =id.toString()

                        return params
                    }
                }
                requestQ.add(pushRequest)



            if (etat) {
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
            intent1.putExtra("id", id)
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