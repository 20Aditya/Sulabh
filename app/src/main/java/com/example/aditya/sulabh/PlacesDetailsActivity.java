package com.example.aditya.sulabh;

import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class PlacesDetailsActivity extends AppCompatActivity {


    EditText editText,editText1;
    RatingBar ratingBar,ratingBar2;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_details);



        final String index = getIntent().getStringExtra(Utilities.Placeindex);
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView textView2 = (TextView)findViewById(R.id.textView2);

        Location placelocation = new Location(LocationManager.GPS_PROVIDER);
        placelocation.setLatitude(PlacesListActivity.location.get(Integer.parseInt(index)).latitude);
        placelocation.setLongitude(PlacesListActivity.location.get(Integer.parseInt(index)).longitude);

        if(!PlacesListActivity.photoref.get(Integer.parseInt(index)).equals("picture not available"))
        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&photoreference=" +PlacesListActivity.photoref.get(Integer.parseInt(index))+"&key=AIzaSyDZY49-Bu4bVmEquIXPKhLxtoHUaWVc6Q8").into(image);
        else
            image.setImageResource(R.drawable.notavailable);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editText = (EditText)findViewById(R.id.editText);
        editText1 = (EditText)findViewById(R.id.editText2);
        Button btn = (Button)findViewById(R.id.submit);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vicinity = PlacesListActivity.vicinity.get(Integer.parseInt(index));
                if(editText1.getText().toString().trim().isEmpty())
                    editText1.setError("This field is required");
                else if(ratingBar.getRating()==0)
                    Toast.makeText(PlacesDetailsActivity.this,"You haven't rated",Toast.LENGTH_LONG).show();
                else
                {
                    String email = Encode(editText1.getText().toString().trim());
                    Log.d("Mail","Email = "+ email);
                    databaseReference.child(vicinity).child(email).child("Rating").setValue(ratingBar.getRating());

                    if(editText.getText().toString().trim().isEmpty())
                        databaseReference.child(vicinity).child(email).child("Comments").setValue("No comments available");
                    else
                        databaseReference.child(vicinity).child(email).child("Comments").setValue(editText.getText().toString().trim());


                }

                editText.getText().clear();
                editText1.getText().clear();
                ratingBar.setRating(0);
            }
        });


        ratingBar2 = (RatingBar)findViewById(R.id.ratingBar);




    }

    public String Encode(String string)
    {
        string = string.replace(".","@");
        string = string.replace("#","@");
        string = string.replace("$","@");
        string = string.replace("[","@");
        string = string.replace("]","@");

        return string;
    }



}
