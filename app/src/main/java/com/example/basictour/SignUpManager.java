package com.example.basictour;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpManager {

    public interface SignupListener {
        void onSignupSuccess(String message);
        void onSignupError(String error);
    }

    public static void signup(Context context, String baseUrl, String name, String email, String password, SignupListener listener) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = baseUrl + "/signup";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("nom", name);
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            listener.onSignupSuccess(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onSignupError("Error parsing response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onSignupError("Error signing up: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
