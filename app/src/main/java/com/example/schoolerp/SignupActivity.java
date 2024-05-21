package com.example.schoolerp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private EditText signup_name, signup_dob, signup_password, signup_confirm_password, signup_mobile_number;
    private Button signup_button;
    private Calendar calendar;
    private ApiService apiService;
    private int user_type;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_name = findViewById(R.id.signup_name);
        signup_dob = findViewById(R.id.signup_dob);
        signup_password = findViewById(R.id.signup_password);
        signup_confirm_password = findViewById(R.id.signup_confirm_password);
        signup_mobile_number=findViewById(R.id.signup_mobile_number);
        signup_button = findViewById(R.id.signup_button);
        Button login_button = findViewById(R.id.login2);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = signup_name.getText().toString().trim();
                String date_of_birth = signup_dob.getText().toString().trim();
                String password = signup_password.getText().toString().trim();
                String password2 = signup_confirm_password.getText().toString().trim();
                String mobile_number= signup_mobile_number.getText().toString().trim();

                validateInfo(first_name, date_of_birth, password, password2, mobile_number);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
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

        // Apply blink animation to the signup button
        Animation blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
        signup_button.startAnimation(blinkAnimation);
    }

    private void validateInfo(String first_name, String date_of_birth, String password, String password2, String mobile_number) {
        if (TextUtils.isEmpty(first_name) || TextUtils.isEmpty(date_of_birth) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            showCustomToast("All fields are required", 0xFFFF0000); // Red color
        } else if (!password.equals(password2)) {
            showCustomToast("Passwords do not match", 0xFFFF0000); // Red color
        } else {
            // If all validations pass, perform signup
            signup(first_name, date_of_birth, password, password2, mobile_number, user_type);
        }
    }

    private void signup(String first_name, String date_of_birth, String password, String password2, String mobile_number, int user_type) {
        // Call the signup API using Retrofit
        Call<SignupResponse> signupResponseCall = apiService.signupUser(first_name, date_of_birth, password, password2, mobile_number,2);
        signupResponseCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()) {
                    showCustomToast("Signup Successful", 0xFF00FF00); // Green color
                    // Navigate to Home_erp activity on successful signup
                    Intent intent = new Intent(SignupActivity.this, Home_erp.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        // Parse the error response
                        String errorMessage = response.errorBody().string();
                        showCustomToast("Signup Failed: " + errorMessage, 0xFFFF0000); // Red color
                    } catch (Exception e) {
                        // Handle any errors that may occur while parsing the error response
                        showCustomToast("Signup Failed: An error occurred", 0xFFFF0000); // Red color
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                showCustomToast("Signup Failed: " + t.getMessage(), 0xFFFF0000); // Red color
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
                    // Create a Calendar object and set the selected date
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);

                    // Format the date to YYYY-MM-DD
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    String formattedDate = sdf.format(selectedDate.getTime());

                    // Set the formatted date to the EditText
                    signup_dob.setText(formattedDate);
                }, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    private void saveTokenToSharedPreferences(String token) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("FCMToken", token);
        editor.apply();
    }

    private void showCustomToast(String message, int textColor) {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View toastView = inflater.inflate(R.layout.custom_toast, null);

        // Find the TextView from the custom layout and set the message
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText(message);
        toastText.setTextColor(textColor); // Set the text color

        // Create the Toast and set its properties
        Toast customToast = new Toast(getApplicationContext());
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(toastView);

        // Set the position of the Toast (optional)
        customToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);

        // Show the Toast
        customToast.show();
    }
}
