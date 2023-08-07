package com.example.basictour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class AttractionList extends AppCompatActivity implements AttractionAdapter.OnItemClickListener {

    private String baseApi;
    private RequestQueue requestQueue;
    List<Attraction> allAttractions;

    public interface AttractionsCallback {
        void onAttractionsFetched(ArrayList<Attraction> attractions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_list);

        this.baseApi = getResources().getString(R.string.baseApi);
        requestQueue = Volley.newRequestQueue(this);

        ProgressBar loadingBar = (ProgressBar) findViewById(R.id.progressBar);
        loadingBar.setVisibility(View.VISIBLE);

        //List<Attraction> attractions = getAttractions();
        //List<Attraction> attractions = new ArrayList<Attraction>();
        getAllAttractions(new AttractionsCallback() {
            @Override
            public void onAttractionsFetched(ArrayList<Attraction> attractions) {
                Log.d("ALLDATA", attractions.size() + "");
                loadingBar.setVisibility(View.GONE);
                allAttractions = attractions;
                RecyclerView recyclerView = findViewById(R.id.recyclerViewAttractions);
                recyclerView.setLayoutManager(new LinearLayoutManager(AttractionList.this));
                AttractionAdapter adapter = new AttractionAdapter(attractions);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(AttractionList.this);
            }
        });

    }

    private List<Attraction> getAttractions() {
        List<Attraction> attractions = new ArrayList<>();

        // Sample list of attractions (replace with your actual data)
        attractions.add(new Attraction("Attraction 1", 4.5f, "drawable/image1"));
        attractions.add(new Attraction("Attraction 2", 3.8f, "drawable/image2"));
        attractions.add(new Attraction("Attraction 3", 4.2f, "drawable/image3"));
        // Add more attractions here

        return attractions;
    }

    public void getAllAttractions(AttractionsCallback callback){
        String url = baseApi+"/attraction/accueil";
        Log.d("API URL", url);

        ArrayList<Attraction> allAttractions = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("TAF", "TAFIDITRA");
                            JSONArray attractionsArray = response.getJSONArray("attractions");
                            for (int i = 0; i < attractionsArray.length(); i++) {
                                JSONObject attractionObject = attractionsArray.getJSONObject(i);

                                // Get the attributes of each attraction
                                String name = attractionObject.getString("nom");
                                String description = attractionObject.getString("description");
                                float rating = new Double(attractionObject.getDouble("rating")).floatValue();
                                double prix = attractionObject.getDouble("prix_entree");
                                String heure = attractionObject.getString("heure_ouverture");
                                String background = attractionObject.getString("background");

                                Log.d("nom: ", name+" | "+description+" | "+rating+" | "+prix+" | "+heure);
                                allAttractions.add(new Attraction(name, description, rating, prix, heure, background));
                            }
                            callback.onAttractionsFetched(allAttractions);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERR REQ", "ERROR: "+error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(int position) {

        Log.d("INDEX", "POS: "+position);
        Log.d("ATO VE", "AONA AAAAAH");
        Intent newIntent = new Intent(this, AttractionDetails.class);
        Attraction tempAttraction = allAttractions.get(position);
        newIntent.putExtra("nom", tempAttraction.getName());
        newIntent.putExtra("description", tempAttraction.getDescription());
        newIntent.putExtra("rating", tempAttraction.getRating());
        newIntent.putExtra("prix", tempAttraction.getPrix());
        newIntent.putExtra("heure", tempAttraction.getHeure());
        newIntent.putExtra("background", tempAttraction.getBackgroundImageUrl());
        startActivity(newIntent);
    }
}