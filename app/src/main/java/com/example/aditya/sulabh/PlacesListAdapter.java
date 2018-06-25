package com.example.aditya.sulabh;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aditya on 25/6/18.
 */

public class PlacesListAdapter extends ArrayAdapter<Places> {

    ViewGroup p;
    int po;
    Places currentplace;

    public PlacesListAdapter(Activity context, ArrayList<Places> places) {
        super(context, 0, places);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listitemview = convertView;
        if (listitemview == null) {
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        p = parent;
        po = position;
        currentplace = getItem(position);

        TextView nametextview = (TextView) listitemview.findViewById(R.id.name);

        nametextview.setText(currentplace.getVicinity());


        ImageView iconview = (ImageView) listitemview.findViewById(R.id.list_item_icon);


        iconview.setImageResource(currentplace.getImage());


        return listitemview;

    }
}
