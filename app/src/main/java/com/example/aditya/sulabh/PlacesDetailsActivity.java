package com.example.aditya.sulabh;

import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PlacesDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_details);


        String index = getIntent().getStringExtra(Utilities.Placeindex);
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView textView2 = (TextView)findViewById(R.id.textView2);

        Location placelocation = new Location(LocationManager.GPS_PROVIDER);
        placelocation.setLatitude(PlacesListActivity.location.get(Integer.parseInt(index)).latitude);
        placelocation.setLongitude(PlacesListActivity.location.get(Integer.parseInt(index)).longitude);

        if(!PlacesListActivity.photoref.get(Integer.parseInt(index)).equals("picture not available"))
        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&photoreference=" +PlacesListActivity.photoref.get(Integer.parseInt(index))+"&key=AIzaSyDZY49-Bu4bVmEquIXPKhLxtoHUaWVc6Q8").into(image);
        else
            image.setImageResource(R.drawable.notavailable);

        textView2.setText(String.valueOf(placelocation.getLatitude()));

    }


    public void getphotoURL(){

    }
}
