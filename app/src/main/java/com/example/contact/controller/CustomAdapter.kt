package com.example.contact.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contact.R;
import com.example.contact.model.ContactItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ContactItem> {

    private Context context;
    private ArrayList<ContactItem> arrayList;

    public CustomAdapter(@NonNull Context context, int resource, ArrayList<ContactItem> arrayList) {
        super(context, resource);

        this.context = context;
        this.arrayList = arrayList;
    }

    public void update(ArrayList<ContactItem> resultats){
        arrayList = new ArrayList<>();
        arrayList.addAll(resultats);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public ContactItem getItem(int position) {
        return arrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.customlist, parent, false);
        TextView nom = (TextView) view.findViewById(R.id.nom);

        String nomComplet = arrayList.get(position).getPrenom() + " " +arrayList.get(position).getNom();
        nom.setText(nomComplet);

        return view;
    }

//    Filter myFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//
//            FilterResults filterResults = new FilterResults();
//            ArrayList<ContactItem> templist = new ArrayList<ContactItem>();
//
//            //constraint is the text you want to filter your list with
//            //arraylist is the data set we will filter from
//            if(constraint != null && arrayList !=null){
//                int length = arrayList.size();
//                int i =0;
//                String nomComplet;
//                Integer id;
//                String nom, prenom,phone,adresse,email;
//
//                while(i<length){
//
//
//                    nomComplet = arrayList.get(i).getNom()+" "+ arrayList.get(i).getPrenom();
//
//                    if(nomComplet.toUpperCase().contains(constraint.toString().toUpperCase())){
//                        ContactItem item = (ContactItem) arrayList.get(i);
//                        templist.add(item);
//
//
//                    }
//                    i++;
//                }
//                filterResults.values = templist;
//                filterResults.count = templist.size();
//            }else {
//                filterResults.values = arrayList;
//                filterResults.count = arrayList.size();
//            }
//
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//            arrayList = (ArrayList<ContactItem>) results.values;
//            if(results.count>0){
//                notifyDataSetChanged();
//            }else{
//                notifyDataSetInvalidated();
//            }
//        }
//    };
//    @NonNull
//    @Override
//    public Filter getFilter() {
//        return myFilter;
//    }
}
