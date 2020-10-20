package com.example.contact.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.contact.R
import com.example.contact.controller.CustomAdapter
import com.example.contact.controller.SQLiteController
import com.example.contact.model.ContactItem
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    //attributes
    lateinit var listView: ListView
     val arrayList = ArrayList<ContactItem>()
    lateinit var resultats: ArrayList<ContactItem>
    lateinit var arrayListDoublon: ArrayList<String>
//    private val arrayAdapterDoublon: ArrayAdapter<*>? = null
    companion object{
    lateinit var arrayAdapter: CustomAdapter
}

    lateinit var ajouterContact: ImageButton
    lateinit var infoImageButton: ImageButton

    lateinit var requestQ: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<View>(R.id.listView) as ListView
        ajouterContact = findViewById<View>(R.id.addButton) as ImageButton
        infoImageButton = findViewById<View>(R.id.imageButtonInformation) as ImageButton
        ajouterContact.setOnClickListener {
            val intent = Intent(applicationContext, InsertContact::class.java)
            startActivity(intent)
            finish()
        }

        arrayListDoublon = ArrayList()

        requestQ = Volley.newRequestQueue(applicationContext)

        val url = "http://192.168.0.103/contact/manager.php?action=select"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,
                { response ->
                    var jsonArray = response.getJSONArray("contact_tbl")
                    for (i in 0 until jsonArray.length()){
                        var jsonObject = jsonArray.getJSONObject(i)
                        var id = jsonObject.getInt("id")
                        var prenom = jsonObject.getString("prenom")
                        var nom = jsonObject.getString("nom")
                        var adresse = jsonObject.getString("adresse")
                        var email= jsonObject.getString("email")
                        var phone = jsonObject.getString("phone")
                        // Toast.makeText(context, prenom+" "+ nom, Toast.LENGTH_LONG).show()

                       var contactItem = ContactItem(id, nom, prenom, phone, adresse, email)
                        arrayList.add(contactItem)
                        arrayAdapter.notifyDataSetChanged()
                        println(arrayList[i].nom)

                        arrayListDoublon.add("$nom $prenom")
                    }

                },
                {
                    error ->
                    println("There is an error: "+ error)

                })
        requestQ.add(jsonObjectRequest)

        arrayAdapter = CustomAdapter(applicationContext, R.layout.customlist, arrayList)
        listView.adapter = arrayAdapter


        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            //on va recuperer id du contact selectionne
            val intent = Intent(applicationContext, AfficherContact::class.java)
            //                String nomFiltre= arrayAdapterDoublon.getItem(position).toString();
//                int identifiant = findIdByName(arrayList, nomFiltre);

//                String tempon = String.valueOf(id);
//                Integer resultat = Integer.parseInt(tempon);
            val identifiant = arrayAdapter.getItem(position)!!.id
            val nom = arrayAdapter.getItem(position)!!.nom
            val prenom = arrayAdapter.getItem(position)!!.prenom
            val adresse = arrayAdapter.getItem(position)!!.adresse
            val phone = arrayAdapter.getItem(position)!!.phone
            val email = arrayAdapter.getItem(position)!!.email
            intent.putExtra("id", identifiant)
            intent.putExtra("nom", nom)
            intent.putExtra("prenom", prenom)
            intent.putExtra("adresse", adresse)
            intent.putExtra("phone", phone)
            intent.putExtra("email", email)
            startActivity(intent)
            //                finish();
        }
        infoImageButton.setOnClickListener {
            val builder1 = AlertDialog.Builder(this@MainActivity)
            builder1.setTitle("Infos Contact")
            val message = "Il y a " + arrayListDoublon.size + " contacts enregistres"
            builder1.setMessage(message)
            builder1.setCancelable(true)
            builder1.setNeutralButton(android.R.string.ok
            ) { dialog, id -> dialog.cancel() }
            val alert11 = builder1.create()
            alert11.show()
        }
    }

    //Implementation de search bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_item, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)

        //filtering
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                var nom: String
                var prenom: String
                var completeName: String
                val filtre: String
                filtre = newText.replace("\\s+".toRegex(), "").toLowerCase()
                resultats = ArrayList()
                for (x in arrayList) {
                    nom = x.nom.toLowerCase()
                    prenom = x.prenom.toLowerCase()
                    completeName = prenom + nom
                    if (nom.contains(newText.toLowerCase()) or prenom.contains(newText.toLowerCase()) or completeName.contains(filtre)) {
                        resultats.add(x)
                    }
                }
                (listView.adapter as CustomAdapter).update(resultats)
                return false
            }
        })
        return true
    }

    //methode pour comparer le nom resultant d une recherche avec les noms contenus ds les objets afin d en trouver l'id
    fun findIdByName(table: ArrayList<ContactItem>, name: String): Int {
        var i: Int
        var idObject = -1
        i = 0
        while (i < table.size) {
            val nameObject = table[i].nom + " " + table[i].prenom
            if (name == nameObject) {
                idObject = table[i].id
            }
            i++
        }
        return idObject
    }
}