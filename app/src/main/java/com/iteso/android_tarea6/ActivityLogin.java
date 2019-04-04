package com.iteso.android_tarea6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iteso.android_tarea6.beans.User;
import com.iteso.android_tarea6.beans.tools.Constants;

public class ActivityLogin extends AppCompatActivity {
    Button loginButton;
    EditText usernameEditText, passwordEditText;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.activity_login_button);
        usernameEditText = findViewById(R.id.activity_username_edit_text);
        passwordEditText = findViewById(R.id.activity_password_edit_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferences();
                Intent intent = new Intent(ActivityLogin.this,ActivityMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    public void saveSharedPreferences(){
        user = new User();
        user.setName(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        user.setLogged(true);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PACKAGE_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_USERNAME,user.getName());
        editor.putString(Constants.SHARED_PREFERENCES_PASSWORD,user.getPassword());
        editor.putBoolean(Constants.SHARED_PREFERENCES_LOGGED,user.isLogged());
        editor.apply();
    }
}
