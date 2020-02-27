package com.example.contact.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.contact.model.ContactItem;

public class SQLiteController extends SQLiteOpenHelper {

    private static final String name="contact.db";
    private static final Integer version=1;
    private final static String TABLE_CREATION="CREATE TABLE IF NOT EXISTS contact"+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT(35) NOT NULL," +
            "prenom TEXT(35) NOT NULL, phone TEXT(25) NOT NULL,"+
            " adresse TEXT(50) NOT NULL, email TEXT(30) NOT NULL )";

    public SQLiteController(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertContact(ContactItem contactItem){
         SQLiteDatabase db = this.getWritableDatabase();
         long insert = -33;
         if(!alreadyExists(contactItem)){
             //le contactn existe pas ds la base

             ContentValues contentValues = new ContentValues();

             contentValues.put("nom", contactItem.getNom());
             contentValues.put("prenom", contactItem.getPrenom());
             contentValues.put("phone", contactItem.getPhone());
             contentValues.put("adresse", contactItem.getAdresse());
             contentValues.put("email", contactItem.getEmail());

             insert= db.insert("contact", null,contentValues);
         }

        return insert;
    }

    public int dropContact(ContactItem contactItem){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String whereArgs[] = {contactItem.getId().toString()};
        int i = db.delete("contact", whereClause, whereArgs);
        return i;
    }
    public int dropContact(String id){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String whereArgs[] = {id};
        int i = db.delete("contact", whereClause, whereArgs);
        return i;
    }

    public int updateContact(String id, String name, String pre, String phone, String adresse, String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", name);
        contentValues.put("prenom", pre);
        contentValues.put("phone", phone);
        contentValues.put("adresse", adresse);
        contentValues.put("email", email);

        String whereClause = "id=?";
        String[] params = new String[]{ id };

        int i = db.update("contact",contentValues,whereClause, params);
        return  i;
    }
    public int updateContact(ContactItem contactItem){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", contactItem.getNom());
        contentValues.put("prenom", contactItem.getPrenom());
        contentValues.put("phone", contactItem.getPhone());
        contentValues.put("adresse", contactItem.getAdresse());
        contentValues.put("email", contactItem.getEmail());

        String whereClause = "id=?";
        String[] params = new String[]{ contactItem.getId().toString() };

        int i = db.update("contact",contentValues,whereClause, params);
        return  i;
    }

    public Cursor selectContact(){
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * from contact";
        final Cursor cursor = db.rawQuery(select, null);

        return cursor;

    }
    public Cursor selectContactByID(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] params = new String[]{ id };
        String select = "SELECT * from contact WHERE id=?";
        final Cursor cursor = db.rawQuery(select, params );

        return cursor;

    }

    private boolean alreadyExists(ContactItem contactItem){
        String nom = contactItem.getNom();
        String prenom = contactItem.getPrenom();
        boolean val;
        SQLiteDatabase base = this.getReadableDatabase();
        String[] conditions = new String[]{nom, prenom};
        String sql = "SELECT COUNT(id) FROM contact WHERE nom=? AND prenom=?";
        final  Cursor result = base.rawQuery(sql,conditions);
        result.moveToFirst();
        int nombre = result.getInt(0);
        if(nombre==0){
            val = true;
        }else{
            val = false;
        }
        return  val;
    }


}
