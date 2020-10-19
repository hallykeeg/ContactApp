package com.example.contact.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.contact.R
import com.example.contact.controller.CustomAdapter
import com.example.contact.controller.SQLiteController
import com.example.contact.model.ContactItem
import org.junit.runner.RunWith
import java.util.*

class MainActivity : AppCompatActivity() {
    private var listView: ListView? = null
    private var arrayList: ArrayList<ContactItem?>? = null
    private var arrayAdapter: CustomAdapter? = null
    private var ajouterContact: ImageButton? = null
    private var contactItem: ContactItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<View?>(R.id.listView) as ListView
        ajouterContact = findViewById<View?>(R.id.addButton) as ImageButton
        ajouterContact?.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, insertContact::class.java)
            startActivity(intent)
            finish()
        })
        arrayList = ArrayList()
        val sqLiteController = SQLiteController(applicationContext)
        //        sqLiteController.insertContact(new ContactItem(1,"Romulus", "Ronick","38471151", "34, rue casseus", "roromulus@yahoo.com"));
        val cursor = sqLiteController.selectContact()
        while (cursor?.moveToNext()!!) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val nom = cursor.getString(cursor.getColumnIndex("nom"))
            val prenom = cursor.getString(cursor.getColumnIndex("prenom"))
            val phone = cursor.getString(cursor.getColumnIndex("phone"))
            val adresse = cursor.getString(cursor.getColumnIndex("adresse"))
            val email = cursor.getString(cursor.getColumnIndex("email"))
            contactItem = ContactItem(id, nom, prenom, phone, adresse, email)
            arrayList!!.add(contactItem)
        }
        arrayAdapter = CustomAdapter(applicationContext, R.layout.customlist, arrayList)
        listView!!.setAdapter(arrayAdapter)
        listView!!.setOnItemClickListener(OnItemClickListener { parent, view, position, id -> //on va recuperer id du contact selectionne
            val intent = Intent(applicationContext, AfficherContact::class.java)
            intent.putExtra("id", arrayList!!.get(position)!!.getId())
            startActivity(intent)
            finish()
        })
    }

    //Implementation de search bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater? = menuInflater
        menuInflater!!.inflate(R.menu.menu_item, menu)
        val menuItem = menu!!.findItem(R.id.app_bar_search)

        //filtering
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                arrayAdapter!!.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                arrayAdapter!!.getFilter().filter(newText)
                return false
            }
        })
        return true
    }
}