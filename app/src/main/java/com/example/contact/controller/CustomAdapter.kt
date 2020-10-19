package com.example.contact.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.contact.R
import com.example.contact.model.ContactItem
import org.junit.runner.RunWith
import java.util.*

class CustomAdapter(context: Context, resource: Int, arrayList: ArrayList<ContactItem?>?) : ArrayAdapter<ContactItem?>(context, resource, arrayList!!) {

    private val arrayList: ArrayList<ContactItem?>?
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val layoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        convertView = layoutInflater.inflate(R.layout.customlist, parent, false)
        val nom = convertView.findViewById<View?>(R.id.nom) as TextView
        val nomComplet = arrayList!!.get(position)!!.getPrenom() + " " + arrayList.get(position)!!.getNom()
        nom.text = nomComplet
        return convertView
    }

    override fun getFilter(): Filter {
        return super.getFilter()
    }

    init {

        this.arrayList = arrayList
    }
}