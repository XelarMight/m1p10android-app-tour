package com.example.basictour;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button btn_valider = (Button) findViewById(R.id.button_sign_valider);

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editNom = (EditText) findViewById(R.id.edit_sign_nom);
                EditText editEmail = (EditText) findViewById(R.id.edit_sign_email);
                EditText editPassword = (EditText) findViewById(R.id.edit_sign_mdp);
                String nom = editNom.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                String baseUrl = getResources().getString(R.string.baseApi);

                SignUpManager.signup(SignUpActivity.this, baseUrl, nom, email, password, new SignUpManager.SignupListener() {
                    @Override
                    public void onSignupSuccess(String message) {
                        showLoginNotification(SignUpActivity.this, "Inscription", message);
                        startMainActivity(SignUpActivity.this);
                    }

                    @Override
                    public void onSignupError(String error) {
                        showLoginNotification(SignUpActivity.this, "Erreur Inscription", "Inscription échoué");
                    }
                });
            }
        });
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
                .setSmallIcon(R.mipmap.ic_launcher_round)
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