package com.example.contact.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.contact.R;
import com.example.contact.controller.CustomAdapter;
import com.example.contact.controller.SQLiteController;
import com.example.contact.model.ContactItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //attributes
private ListView listView;
private ArrayList<ContactItem> arrayList;
private ArrayList arrayListDoublon;
private ArrayAdapter arrayAdapterDoublon;
private CustomAdapter arrayAdapter;
private ImageButton ajouterContact;
private ContactItem contactItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        ajouterContact = (ImageButton) findViewById(R.id.addButton);

        ajouterContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), insertContact.class);
                startActivity(intent);
                finish();
            }
        });

        arrayList = new ArrayList<>();
        arrayListDoublon = new ArrayList();

        SQLiteController sqLiteController = new SQLiteController(getApplicationContext());
//        sqLiteController.insertContact(new ContactItem(1,"Romulus", "Ronick","38471151", "34, rue casseus", "roromulus@yahoo.com"));
        Cursor cursor = sqLiteController.selectContact();
        while(cursor.moveToNext()){
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String nom = cursor.getString(cursor.getColumnIndex("nom"));
            String prenom = cursor.getString(cursor.getColumnIndex("prenom"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String adresse = cursor.getString(cursor.getColumnIndex("adresse"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            contactItem = new ContactItem(id, nom, prenom,phone,adresse,email);
            arrayList.add(contactItem);
            arrayListDoublon.add(nom + " "+prenom);


        }
        arrayAdapterDoublon = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayListDoublon);
       // arrayAdapter = new CustomAdapter(getApplicationContext(), R.layout.customlist,arrayList);
        listView.setAdapter(arrayAdapterDoublon);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //on va recuperer id du contact selectionne
                Intent intent = new Intent(getApplicationContext(), AfficherContact.class);
                intent.putExtra("id", arrayList.get(position).getId() );
                startActivity(intent);
                finish();
            }
        });

    }

    //Implementation de search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        //filtering
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayAdapterDoublon.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapterDoublon.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}
