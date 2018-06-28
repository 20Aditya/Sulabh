package com.example.aditya.sulabh;

import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class PlacesDetailsActivity extends AppCompatActivity {


    EditText editText,editText1;
    TextView ratingnumber,reviewnumber,textView2;
    RatingBar ratingBar,ratingBar2;
    DatabaseReference databaseReference;
    public static String vicinity;
    int count, sumrating;
    String index;
    ListView listView;
    ArrayList<String> comment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_details);


        //Setting up firebase context
        Firebase.setAndroidContext(this);

        index = getIntent().getStringExtra(Utilities.Placeindex);
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView textView2 = (TextView)findViewById(R.id.textView2);

        Location placelocation = new Location(LocationManager.GPS_PROVIDER);
        placelocation.setLatitude(PlacesListActivity.location.get(Integer.parseInt(index)).latitude);
        placelocation.setLongitude(PlacesListActivity.location.get(Integer.parseInt(index)).longitude);

        //Retrieving the images from Google using Picasso and the photo references we saved during Json parsing
        if(!PlacesListActivity.photoref.get(Integer.parseInt(index)).equals("picture not available"))
        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&photoreference=" +PlacesListActivity.photoref.get(Integer.parseInt(index))+"&key=AIzaSyDZY49-Bu4bVmEquIXPKhLxtoHUaWVc6Q8").into(image);
        else
            image.setImageResource(R.drawable.notavailable);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Getting the views for usage
        editText = (EditText)findViewById(R.id.editText);
        editText1 = (EditText)findViewById(R.id.editText2);
        Button btn = (Button)findViewById(R.id.submit);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);
        ratingnumber = (TextView)findViewById(R.id.ratingnumber);
        reviewnumber = (TextView)findViewById(R.id.reviewnumber);
        ratingBar2 = (RatingBar)findViewById(R.id.ratingBar);
        textView2 = (TextView)findViewById(R.id.textView2);
        listView = (ListView)findViewById(R.id.comments_list);

        textView2.setText(PlacesListActivity.places.get(Integer.parseInt(index)) + " : " + PlacesListActivity.vicinity.get(Integer.parseInt(index)));


        //Rating feature and real-time updation
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vicinity = PlacesListActivity.vicinity.get(Integer.parseInt(index));
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

                retrieve();
                editText.getText().clear();
                editText1.getText().clear();
                ratingBar.setRating(0);
            }
        });

        retrieve();








    }

    //Since firebase has restrictions regarding paths so we need to encode the emails in a way so they can form a firebase path.
    public String Encode(String string)
    {
        string = string.replace(".","@");
        string = string.replace("#","@");
        string = string.replace("$","@");
        string = string.replace("[","@");
        string = string.replace("]","@");

        return string;
    }

    //function to retrieve the ratings and display the details on the page
    public void retrieve()
    {
        Firebase reference = new Firebase("https://sulabh-new.firebaseio.com/" + PlacesListActivity.vicinity.get(Integer.parseInt(index)));

        comment.clear();
        Log.d("Place", "Vicinity="+PlacesListActivity.vicinity.get(Integer.parseInt(index)));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = 0;
                sumrating=0;
                for(DataSnapshot datas:dataSnapshot.getChildren())
                {
                    sumrating+=Integer.parseInt(datas.child("Rating").getValue().toString());
                    count++;
                    String value = datas.child("Comments").getValue().toString();
                    Log.d("Place", "comment ==" + value);
                    String key = datas.getRef().toString().substring(datas.getRef().getParent().toString().length()+1);

                    Log.d("Place", "URL ==" + Decode(key));
                    comment.add(value);

                }
                Log.d("Place", "count="+count+"sum="+sumrating);
                if(count!=0) {
                    float rating = ((float)sumrating / (float)count);
                    DecimalFormat df = new DecimalFormat("#.#");

                    ratingnumber.setText(df.format(rating));
                    ratingBar2.setRating(rating);
                    reviewnumber.setText(String.valueOf(count));
                }
                else
                    ratingnumber.setText("0");
                reviewnumber.setText(String.valueOf(count));

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlacesDetailsActivity.this,android.R.layout.simple_list_item_1,comment);
                listView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(listView);
                Log.d("Places", "onCreate: " + comment.size());


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, RelativeLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public String Decode(String key)
    {
        key = key.replace("%40","@");
        key = key.replace("%20"," ");

        return key;
    }


}
