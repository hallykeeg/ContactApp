package com.example.contact.controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contact.model.ContactItem
import org.junit.runner.RunWith

class SQLiteController(context: Context?) : SQLiteOpenHelper(context, name, null, version!!) {
    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(TABLE_CREATION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    fun insertContact(contactItem: ContactItem?): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nom", contactItem!!.getNom())
        contentValues.put("prenom", contactItem.getPrenom())
        contentValues.put("phone", contactItem.getPhone())
        contentValues.put("adresse", contactItem.getAdresse())
        contentValues.put("email", contactItem.getEmail())
        return db.insert("contact", null, contentValues)
    }

    fun dropContact(contactItem: ContactItem?): Int {
        val db = writableDatabase
        val whereClause = "id=?"
        val whereArgs = arrayOf<String?>(contactItem!!.getId().toString())
        return db.delete("contact", whereClause, whereArgs)
    }

    fun dropContact(id: String?): Int {
        val db = writableDatabase
        val whereClause = "id=?"
        val whereArgs = arrayOf(id)
        return db.delete("contact", whereClause, whereArgs)
    }

    fun updateContact(id: String?, name: String?, pre: String?, phone: String?, adresse: String?, email: String?): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nom", name)
        contentValues.put("prenom", pre)
        contentValues.put("phone", phone)
        contentValues.put("adresse", adresse)
        contentValues.put("email", email)
        val whereClause = "id=?"
        val params = arrayOf(id)
        return db.update("contact", contentValues, whereClause, params)
    }

    fun updateContact(contactItem: ContactItem?): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nom", contactItem!!.getNom())
        contentValues.put("prenom", contactItem.getPrenom())
        contentValues.put("phone", contactItem.getPhone())
        contentValues.put("adresse", contactItem.getAdresse())
        contentValues.put("email", contactItem.getEmail())
        val whereClause = "id=?"
        val params = arrayOf<String?>(contactItem.getId().toString())
        return db.update("contact", contentValues, whereClause, params)
    }

    fun selectContact(): Cursor? {
        val db = this.readableDatabase
        val select = "SELECT * from contact"
        return db.rawQuery(select, null)
    }

    fun selectContactByID(id: String?): Cursor? {
        val db = this.readableDatabase
        val params = arrayOf(id)
        val select = "SELECT * from contact WHERE id=?"
        return db.rawQuery(select, params)
    }

    fun save() {}

    companion object {
        private val name: String? = "contact.db"
        private val version: Int? = 1
        private val TABLE_CREATION: String? = "CREATE TABLE IF NOT EXISTS contact" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT(35) NOT NULL," +
                "prenom TEXT(35) NOT NULL, phone TEXT(25) NOT NULL," +
                " adresse TEXT(50) NOT NULL, email TEXT(30) NOT NULL )"
    }
}