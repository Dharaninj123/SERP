package com.example.schoolerp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.controller.Controller;
import com.google.firebase.messaging.FirebaseMessaging;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

        private EditText login_mobile_number, login_password;
        private Button loginButton, forgotPasswordButton, signup;
        private ApiService apiService;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);

                login_mobile_number = findViewById(R.id.login_mobile_number);
                login_password = findViewById(R.id.login_password);
                loginButton = findViewById(R.id.Login_button);
                forgotPasswordButton = findViewById(R.id.forgotpassword);
                signup = findViewById(R.id.signup);

                Controller crobj = Controller.getInstance();
                // Initialize Retrofit service
                apiService = crobj.getApiService();

                // Retrieve FCM token and store it in SharedPreferences
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                        String token = task.getResult();
                                        // Store token in SharedPreferences
                                        storeTokenLocally(token);
                                } else {
                                        Log.e("LoginActivity", "Failed to retrieve FCM token");
                                        // Handle token retrieval failure
                                        Toast.makeText(LoginActivity.this, "Failed to retrieve FCM token", Toast.LENGTH_SHORT).show();
                                }
                        });

                // Load the blink animation
                Animation blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink_animation);

                loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                // Start the blink animation
                                view.startAnimation(blinkAnimation);

                                String mobile_number = login_mobile_number.getText().toString().trim();
                                String password = login_password.getText().toString().trim();

                                // Check if fields are empty
                                if (TextUtils.isEmpty(mobile_number) || TextUtils.isEmpty(password)) {
                                        Toast.makeText(LoginActivity.this, "Mobile Number/Password Required.", Toast.LENGTH_SHORT).show();
                                } else {
                                        // Validate the information
                                        boolean isValid = validateInfo(mobile_number, password);

                                        if (isValid) {
                                                login(mobile_number, password);
                                        }
                                }
                        }
                });

                forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(LoginActivity.this, SendOTPActivity.class);
                                startActivity(intent);
                        }
                });

                signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(LoginActivity.this, Home_erp.class);
                                startActivity(intent);
                        }
                });
        }

        private void storeTokenLocally(String token) {
                // Store token in SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                editor.putString("FCMToken", token);
                editor.apply();
        }

        private void login(String mobile_number, String password) {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setMobile_number(mobile_number);
                loginRequest.setPassword(password);

                Call<LoginResponse> loginResponseCall = apiService.loginUser(mobile_number, password);
                loginResponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if (response.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        LoginResponse loginResponse = response.body();
                                        // Store access token securely
                                        storeAccessToken(loginResponse.getAccess());
                                        // Fetch and display student details
                                        fetchAndDisplayStudentDetails();
                                        // Start HomeActivity with data after a delay
                                        new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                        startActivity(new Intent(LoginActivity.this, Home_erp.class));
                                                }
                                        }, 700);
                                } else {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "Login Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                });
        }

        private void storeAccessToken(String accessToken) {
                // Store the access token securely in SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                editor.putString("AccessToken", accessToken);
                editor.apply();
        }

        // Retrieving access token from SharedPreferences
        private String retrieveAccessToken() {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                return sharedPreferences.getString("AccessToken", "");
        }

        private boolean validateInfo(String number, String password) {
                if (number.isEmpty()) {
                        login_mobile_number.requestFocus();
                        login_mobile_number.setError("FIELD CANNOT BE EMPTY");
                        return false;
                } else if (number.length() != 10) {
                        login_mobile_number.requestFocus();
                        login_mobile_number.setError("INVALID MOBILE NUMBER");
                        return false;
                } else if (!number.matches("[0-9]+")) {
                        login_mobile_number.requestFocus();
                        login_mobile_number.setError("ENTER ONLY NUMBERS");
                        return false;
                } else if (password.isEmpty()) {
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
                return true;
        }

        private void fetchAndDisplayStudentDetails() {
                // Retrieve access token from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String accessToken = sharedPreferences.getString("AccessToken", "");

                // Create an instance of ApiService using Retrofit
                ApiService apiService = Controller.getApiService();

                // Call the API to fetch student details
                Call<StudentsDetailsResponse> call = apiService.getStudentDetails();
                call.enqueue(new Callback<StudentsDetailsResponse>() {
                        @Override
                        public void onResponse(Call<StudentsDetailsResponse> call, Response<StudentsDetailsResponse> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                        // Handle successful response
                                        StudentsDetailsResponse studentDetails = response.body();
                                        String studentName = studentDetails.getName();
                                        // Update UI with student name
                                        updateNavBar(studentName);
                                }
                        }

                        @Override
                        public void onFailure(Call<StudentsDetailsResponse> call, Throwable t) {
                                // Handle failure
                                Toast.makeText(LoginActivity.this, "Failed to fetch student details", Toast.LENGTH_SHORT).show();
                        }
                });
        }

        public void updateNavBar(String studentName) {
                // Update the UI with the student's name in the navigation bar
                // For example, if you have a TextView in the navigation bar:
                TextView navBarTextView = findViewById(R.id.nameView);
                navBarTextView.setText(studentName);
        }
}
