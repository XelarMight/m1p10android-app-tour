package com.example.basictour;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginManager {

    public static void performLogin(Context context, String baseUrl, String email, String password) {
        String loginUrl = baseUrl + "/login"; // Replace with your API login endpoint

        boolean result = false;
        // Create a JSON object for the login data
        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create the API login request using Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginUrl, loginData,
                new Response.Listener<JSONObject>() {   
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check the response for login success or failure
                            int loginSuccessful = response.getInt("status");
                            if (loginSuccessful == 200) {
                                showLoginNotification(context, "Connexion", "Connexion avec success");
                                startMainActivity((Activity) context);
                                //Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle failed login
                                String errorMessage = response.getString("Identifiants Invalide");
                                //Toast.makeText(context, "Login Incorrect", Toast.LENGTH_SHORT).show();
                                showLoginNotification(context, "connexion", errorMessage);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle API call error
                        Toast.makeText(context, "Connexion non établie avec le serveur", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(context).add(request);
    }

    private static void showLoginNotification(Context context, String title, String message) {
        String channelId = "login_notification_channel";
        CharSequence channelName = "Login Notifications";
        int notificationId = 1;

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

    private static void startMainActivity(Activity activity) {
        Intent intent = new Intent(activity, AttractionList.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
