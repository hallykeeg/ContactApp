package com.example.contact.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.contact.R
import com.example.contact.model.ContactItem
import java.util.*

class CustomAdapter
( context: Context, resource: Int, private var arrayList: ArrayList<ContactItem>) : ArrayAdapter<ContactItem?>(context, resource) {
    fun update(resultats: ArrayList<ContactItem>?) {
        arrayList = ArrayList()
        arrayList.addAll(resultats!!)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): ContactItem? {
        return arrayList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = layoutInflater.inflate(R.layout.customlist, parent, false)
        val nom = view.findViewById<View>(R.id.nom) as TextView
        val nomComplet = arrayList[position].prenom + "  " + arrayList[position].nom.toUpperCase()
        nom.text = nomComplet
        return view
    } //    Filter myFilter = new Filter() {
}