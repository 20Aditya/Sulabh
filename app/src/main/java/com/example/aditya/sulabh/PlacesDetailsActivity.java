package com.example.aditya.sulabh;

import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlacesDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_details);


        String index = getIntent().getStringExtra(Utilities.Placeindex);
        TextView textView = (TextView)findViewById(R.id.textView);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);

        Location placelocation = new Location(LocationManager.GPS_PROVIDER);
        placelocation.setLatitude(PlacesListActivity.location.get(Integer.parseInt(index)).latitude);
        placelocation.setLongitude(PlacesListActivity.location.get(Integer.parseInt(index)).longitude);

        textView.setText(PlacesListActivity.places.get(Integer.parseInt(index)));
        textView2.setText(String.valueOf(placelocation.getLatitude()));
        textView3.setText( String.valueOf(placelocation.getLongitude()));

    }


    public void getphotoURL(){

    }
}
