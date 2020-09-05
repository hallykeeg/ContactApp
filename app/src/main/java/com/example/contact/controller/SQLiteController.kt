package com.example.contact.controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contact.model.ContactItem

class SQLiteController(context: Context?) : SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun insertContact(contactItem: ContactItem): Long {
        val db = this.writableDatabase
        val insert: Long

        //le contact n existe pas ds la base
        val contentValues = ContentValues()
        contentValues.put("nom", contactItem.nom)
        contentValues.put("prenom", contactItem.prenom)
        contentValues.put("phone", contactItem.phone)
        contentValues.put("adresse", contactItem.adresse)
        contentValues.put("email", contactItem.email)
        insert = db.insert("contact", null, contentValues)
        return insert
    }

    fun dropContact(contactItem: ContactItem): Int {
        val db = writableDatabase
        val whereClause = "id=?"
        val whereArgs = arrayOf(contactItem.id.toString())
        return db.delete("contact", whereClause, whereArgs)
    }

    fun dropContact(id: String): Int {
        val db = writableDatabase
        val whereClause = "id=?"
        val whereArgs = arrayOf(id)
        return db.delete("contact", whereClause, whereArgs)
    }

    fun updateContact(id: String, name: String?, pre: String?, phone: String?, adresse: String?, email: String?): Int {
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

    fun updateContact(contactItem: ContactItem): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("nom", contactItem.nom)
        contentValues.put("prenom", contactItem.prenom)
        contentValues.put("phone", contactItem.phone)
        contentValues.put("adresse", contactItem.adresse)
        contentValues.put("email", contactItem.email)
        val whereClause = "id=?"
        val params = arrayOf(contactItem.id.toString())
        return db.update("contact", contentValues, whereClause, params)
    }

    fun selectContact(): Cursor {
        val db = this.readableDatabase
        val select = "SELECT * from contact ORDER BY nom, prenom"
        return db.rawQuery(select, null)
    }

    fun selectContactByID(id: String): Cursor {
        val db = this.readableDatabase
        val params = arrayOf(id)
        val select = "SELECT * from contact WHERE id=?"
        return db.rawQuery(select, params)
    }

    private fun alreadyExists(contactItem: ContactItem): Boolean {
        val nom = contactItem.nom
        val prenom = contactItem.prenom
        val `val`: Boolean
        val base = this.readableDatabase
        val conditions = arrayOf(nom, prenom)
        val sql = "SELECT COUNT(id) FROM contact WHERE nom=? AND prenom=?"
        val result = base.rawQuery(sql, conditions)
        result.moveToFirst()
        val nombre = result.getInt(0)
        `val` = if (nombre == 0) {
            false
        } else {
            true
        }
        return `val`
    }

    companion object {
        private const val name = "contact.db"
        private const val version = 1
        private const val TABLE_CREATION = "CREATE TABLE IF NOT EXISTS contact" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT(35) NOT NULL," +
                "prenom TEXT(35) , phone TEXT(25)," +
                " adresse TEXT(50), email TEXT(30) )"
    }
}