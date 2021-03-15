package edu.byu.cs428.workoutprogresstracker.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.byu.cs428.workoutprogresstracker.R;

public class LoginView extends AppCompatActivity {
    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        loginButton = findViewById(R.id.login_button);
        loginButton.setEnabled(false);

        usernameField = findViewById(R.id.username);
        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkLoginFields();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFields();
            }
        });

        passwordField = findViewById(R.id.password);
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkLoginFields();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginFields();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void checkLoginFields() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if(username.equals("") || password.equals("")) {
            loginButton.setEnabled(false);
        }
        else {
            loginButton.setEnabled(true);
        }
    }

    public void login(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}
