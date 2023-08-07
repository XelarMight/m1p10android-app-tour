package com.example.basictour;

import java.util.ArrayList;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Attraction {
    private String name;
    private String description;
    private float rating;
    private double prix;
    private String heure;
    private String backgroundImageUrl;

    public Attraction(String name, String description, float rating, double prix, String heure, String backgroundImageUrl) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.prix = prix;
        this.heure = heure;
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public Attraction(String name, float rating, String backgroundImageUrl) {
        this.name = name;
        this.rating = rating;
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public String getHeure() {
        return heure;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public ArrayList<String> getTransportList() {
        return null;
    }
}
