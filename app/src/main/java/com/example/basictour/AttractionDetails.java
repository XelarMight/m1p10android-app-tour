package com.example.basictour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AttractionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_details);

        /* INIT DATA*/
        String nom = getIntent().getStringExtra("nom");
        String description = getIntent().getStringExtra("description");
        float rating = getIntent().getFloatExtra("rating", 3f);
        double prix = getIntent().getDoubleExtra("prix", 1000);
        String heure = getIntent().getStringExtra("heure");
        Attraction attraction = new Attraction(nom, description, rating, prix, heure, "");

        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        TextView textViewOpeningHours = findViewById(R.id.textViewOpeningHours);
        TextView textViewTransport = findViewById(R.id.textViewTransport);
        RatingBar ratingBarAttraction = findViewById(R.id.ratingBarAttraction);

        if (attraction != null) {
            textViewName.setText(attraction.getName());
            textViewPrice.setText("Price: $" + attraction.getPrix());
            textViewOpeningHours.setText("Opening Hours: " + attraction.getHeure());
            ratingBarAttraction.setRating(attraction.getRating());

            /*
            ArrayList<String> transportList = attraction.getTransportList();
            StringBuilder transportText = new StringBuilder();
            for (String transport : transportList) {
                transportText.append(transport).append(", ");
            }
            // Remove the trailing comma and space
            if (transportText.length() > 0) {
                transportText.setLength(transportText.length() - 2);
            }
            textViewTransport.setText("Transport Options: " + transportText.toString());
             */
        }

    }
}