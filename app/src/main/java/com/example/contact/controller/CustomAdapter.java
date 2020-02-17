package com.example.contact.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contact.R;
import com.example.contact.model.ContactItem;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ContactItem> {

    private Context context;
    private ArrayList<ContactItem> arrayList;

    public CustomAdapter(@NonNull Context context, int resource, ArrayList<ContactItem> arrayList) {
        super(context, resource, arrayList);

        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.customlist, parent, false);
        TextView nom = (TextView) convertView.findViewById(R.id.nom);

        String nomComplet = arrayList.get(position).getPrenom() + " " +arrayList.get(position).getNom();
        nom.setText(nomComplet);

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }
}
