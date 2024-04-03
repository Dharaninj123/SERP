package com.example.schoolerp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolerp.controller.Controller;

public class LoginActivity extends AppCompatActivity {

    EditText login_mobile_number, login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_mobile_number = findViewById(R.id.login_mobile_number);
        login_password = findViewById(R.id.login_password);

        // Call the method to set up button click listeners
        setupButtons();
    }

    private void setupButtons() {
        Button loginButton = findViewById(R.id.Login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller ct = new Controller();
                ct.getinfo();
                String number = login_mobile_number.getText().toString();
                String password = login_password.getText().toString();

                // Validate the information
                boolean isValid = validateInfo(number, password);

                if (isValid) {
                    // Proceed if data is valid
                    Intent intent = new Intent(LoginActivity.this, SendOTPActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button forgotPasswordButton = findViewById(R.id.signup);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SendOTPActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInfo(String number, String password) {


        if (number.isEmpty()) {
            login_mobile_number.requestFocus();
            login_mobile_number.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (number.length() > 10) {
            login_mobile_number.requestFocus();
            login_mobile_number.setError("INVALID");
            return false;
        } else if (number.length() < 10) {
            login_mobile_number.requestFocus();
            login_mobile_number.setError("INVALID");
            return false;
        } else if (!number.matches("[0-9]+")) {
            login_mobile_number.requestFocus();
            login_mobile_number.setError("ENTER ONLY NUMBERS");
            return false;
        }    else if (password.isEmpty()) {
                login_password.requestFocus();
                login_password.setError("PASSWORD REQUIRED");
                return false;
            } else if (password.length() < 6) {
                login_password.requestFocus();
                login_password.setError("MINIMUM 6 CHARACTERS REQUIRED");
                return false;
            } else if (!password.matches(".*\\d.*")) {
                login_password.requestFocus();
                login_password.setError("PASSWORD MUST CONTAIN AT LEAST ONE DIGIT");
                return false;
            } else if (!password.matches(".*[a-zA-Z].*")) {
                login_password.requestFocus();
                login_password.setError("PASSWORD MUST CONTAIN AT LEAST ONE LETTER");
                return false;
            } else if (!password.matches(".*[!@#$%^&*()-_=+\\\\|\\[{\\]};:'\",<.>/?].*")) {
                login_password.requestFocus();
                login_password.setError("PASSWORD MUST CONTAIN AT LEAST ONE SPECIAL CHARACTER");
                return false;
            }
// Add more conditions as needed

// If all conditions pass, return true indicating the password is valid
            return true;


        }
    }


