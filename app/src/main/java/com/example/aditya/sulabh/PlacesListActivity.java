package com.example.aditya.sulabh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class PlacesListActivity extends AppCompatActivity {

    ListView list;
    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<Places> listitem = new ArrayList<>();
    static ArrayList<String> vicinity = new ArrayList<>();
    static ArrayList<LatLng> location = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);


        list = (ListView)findViewById(R.id.list);

        PlacesListAdapter adapter = new PlacesListAdapter(this,listitem);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PlacesListActivity.this,PlacesDetailsActivity.class);
                intent.putExtra(Utilities.Placeindex,String.valueOf(position));
                startActivity(intent);

            }
        });


    }
}
