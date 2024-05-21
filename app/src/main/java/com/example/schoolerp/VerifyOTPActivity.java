package com.example.schoolerp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.controller.Controller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otpactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText inputCode1 = findViewById(R.id.inputCode1);
        EditText inputCode2 = findViewById(R.id.inputCode2);
        EditText inputCode3 = findViewById(R.id.inputCode3);
        EditText inputCode4 = findViewById(R.id.inputCode4);

        // Move focus to next input code automatically
        setEditTextChangeListener(inputCode1, inputCode2);
        setEditTextChangeListener(inputCode2, inputCode3);
        setEditTextChangeListener(inputCode3, inputCode4);

        Button buttonVerify = findViewById(R.id.buttonVerify);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apply blink animation to the button
                Animation blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
                buttonVerify.startAnimation(blinkAnimation);

                String code1 = inputCode1.getText().toString().trim();
                String code2 = inputCode2.getText().toString().trim();
                String code3 = inputCode3.getText().toString().trim();
                String code4 = inputCode4.getText().toString().trim();

                if (!code1.isEmpty() && !code2.isEmpty() && !code3.isEmpty() && !code4.isEmpty()) {
                    // All input fields are filled, concatenate OTP
                    String otp = code1 + code2 + code3 + code4;
                    // Call API to verify OTP
                    verifyOTP(otp);
                } else {
                    // Display a message indicating all fields must be filled
                    Toast.makeText(VerifyOTPActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifyOTP(String otp) {
        // Initialize Retrofit service
        ApiService apiService = Controller.getApiService();
        String mobileNumber = getIntent().getStringExtra("mobileNumber");
        // Call the API to verify OTP
        Call<SendOTPResponse> call = apiService.verifyOTP(mobileNumber,otp);
        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
                if (response.isSuccessful()) {
                    // OTP verification successful, proceed to SignupActivity
                    Intent intent = new Intent(VerifyOTPActivity.this, SignupActivity.class);
                    startActivity(intent);
                } else {
                    // OTP verification failed, display error message
                    Toast.makeText(VerifyOTPActivity.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                // Error occurred while verifying OTP, display error message
                Toast.makeText(VerifyOTPActivity.this, "Failed to verify OTP. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to move focus to the next EditText when a digit is entered
    private void setEditTextChangeListener(final EditText currentEditText, final EditText nextEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    // If a digit is entered, move focus to the next EditText
                    nextEditText.requestFocus();
                }
            }
        });
    }
}
