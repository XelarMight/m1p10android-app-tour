package com.example.basictour;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_valider = (Button) findViewById(R.id.button_valider);
        TextView labelSwitchSign = (TextView) findViewById(R.id.label_log_inscri);

        labelSwitchSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity2.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editEmail = (EditText) findViewById(R.id.edit_log_email);
                EditText editPassword = (EditText) findViewById(R.id.edit_log_mdp);
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                String baseUrl = getResources().getString(R.string.baseApi);
                LoginManager.performLogin(LoginActivity2.this, baseUrl, email, password);

            }
        });
    }

    public void startMainActivity() {
        Intent intent = new Intent(LoginActivity2.this, AttractionList.class);
        startActivity(intent);
        finish();
    }
}