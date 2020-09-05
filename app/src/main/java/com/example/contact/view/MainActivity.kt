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
import com.example.contact.R
import com.example.contact.controller.CustomAdapter
import com.example.contact.controller.SQLiteController
import com.example.contact.model.ContactItem
import java.util.*

class MainActivity : AppCompatActivity() {
    //attributes
    private var listView: ListView? = null
    private var arrayList: ArrayList<ContactItem>? = null
    private var resultats: ArrayList<ContactItem>? = null
    private var arrayListDoublon: ArrayList<String>? = null
    private val arrayAdapterDoublon: ArrayAdapter<*>? = null
    private var arrayAdapter: CustomAdapter? = null
    private var ajouterContact: ImageButton? = null
    private var infoImageButton: ImageButton? = null
    private var contactItem: ContactItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<View>(R.id.listView) as ListView
        ajouterContact = findViewById<View>(R.id.addButton) as ImageButton
        infoImageButton = findViewById<View>(R.id.imageButtonInformation) as ImageButton
        ajouterContact!!.setOnClickListener {
            val intent = Intent(applicationContext, InsertContact::class.java)
            startActivity(intent)
            finish()
        }
        arrayList = ArrayList()
        arrayListDoublon = ArrayList<String>()
        val sqLiteController = SQLiteController(applicationContext)
        //        sqLiteController.InsertContact(new ContactItem(1,"Romulus", "Ronick","38471151", "34, rue casseus", "roromulus@yahoo.com"));
        val cursor = sqLiteController.selectContact()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val nom = cursor.getString(cursor.getColumnIndex("nom"))
            val prenom = cursor.getString(cursor.getColumnIndex("prenom"))
            val phone = cursor.getString(cursor.getColumnIndex("phone"))
            val adresse = cursor.getString(cursor.getColumnIndex("adresse"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            contactItem = ContactItem(id, nom, prenom, phone, adresse, email)
            arrayList!!.add(contactItem!!)
            arrayListDoublon!!.add("$nom $prenom")
            //            arrayListDoublon.set(id, prenom+" "+nom);
        }
        //        arrayAdapterDoublon = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayListDoublon);
        arrayAdapter = CustomAdapter(applicationContext, R.layout.customlist, arrayList!!)
        listView!!.adapter = arrayAdapter
        listView!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            //on va recuperer id du contact selectionne
            val intent = Intent(applicationContext, AfficherContact::class.java)
            //                String nomFiltre= arrayAdapterDoublon.getItem(position).toString();
//                int identifiant = findIdByName(arrayList, nomFiltre);

//                String tempon = String.valueOf(id);
//                Integer resultat = Integer.parseInt(tempon);
            val identifiant = arrayAdapter!!.getItem(position)!!.id
            intent.putExtra("id", identifiant)
            startActivity(intent)
            //                finish();
        }
        infoImageButton!!.setOnClickListener {
            val builder1 = AlertDialog.Builder(this@MainActivity)
            builder1.setTitle("Infos Contact")
            val message = "Il y a " + arrayListDoublon!!.size + " contacts enregistres"
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
                for (x in arrayList!!) {
                    nom = x.nom.toLowerCase()
                    prenom = x.prenom.toLowerCase()
                    completeName = prenom + nom
                    if (nom.contains(newText.toLowerCase()) or prenom.contains(newText.toLowerCase()) or completeName.contains(filtre)) {
                        resultats!!.add(x)
                    }
                }
                (listView!!.adapter as CustomAdapter).update(resultats)
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