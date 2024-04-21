package com.example.schoolerp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.controller.Controller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText signup_name, signup_dob, signup_password, signup_confirm_password;
    private Calendar calendar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_name = findViewById(R.id.signup_name);
        signup_dob = findViewById(R.id.signup_dob);
        signup_password = findViewById(R.id.signup_password);
        signup_confirm_password = findViewById(R.id.signup_confirm_password);

        Button signup_button = findViewById(R.id.signup_button);
        Button login_button = findViewById(R.id.login2);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signup_name.getText().toString().trim();
                String dob = signup_dob.getText().toString().trim();
                String password = signup_password.getText().toString().trim();
                String confirmPassword = signup_confirm_password.getText().toString().trim();

                validateInfo(name, dob, password, confirmPassword);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        signup_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Initialize Retrofit service
        apiService = Controller.getApiService();

        // Step 1: Retrieve the token from Firebase Messaging
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            String token = task.getResult();
                            // Step 2: Store the token in SharedPreferences
                            saveTokenToSharedPreferences(token);
                        }
                    }
                });
    }

    private void validateInfo(String name, String dob, String password, String confirmPassword) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            // If all validations pass, perform signup
            signup(name, dob, password);
        }
    }

    private void signup(String name, String dob, String password) {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName(name);
        signupRequest.setDob(dob);
        signupRequest.setPassword(password);

        // Call the signup API using Retrofit
        Call<SignupResponse> signupResponseCall = apiService.signupUser(signupRequest);
        signupResponseCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    // You can add additional logic here such as starting a new activity or updating UI
                } else {
                    Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Signup Failed" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePickerDialog() {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Handle date selection
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    signup_dob.setText(selectedDate);
                }, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }


    private void saveTokenToSharedPreferences(String token) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("FCMToken", token);
        editor.apply();
    }

}
