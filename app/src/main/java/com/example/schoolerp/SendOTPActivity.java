package com.example.schoolerp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SendOTPActivity extends AppCompatActivity {

    private EditText inputMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sent_o_t_p);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputMobile = findViewById(R.id.inputMobile);
        Button buttonGetOTP = findViewById(R.id.buttonGetotp);
        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateMobileNumber()) {
                    // Proceed to get OTP if mobile number is valid
                    String mobileNumber = inputMobile.getText().toString().trim();
                    sendOTP(mobileNumber);
                }
            }
        });
    }

    private boolean validateMobileNumber() {
        String mobileNumber = inputMobile.getText().toString().trim();
        if (mobileNumber.length() != 10) {
            inputMobile.setError("Please enter a valid 10-digit mobile number");
            inputMobile.requestFocus();
            return false;
        }
        return true;
    }

    private void sendOTP(String mobileNumber) {
        // Initialize Retrofit service
        ApiService apiService = Controller.getApiService();

        // Call the API to send OTP
        Call<Void> call = apiService.sendOTP(mobileNumber);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // OTP sent successfully, proceed to verify OTP
                    Intent intent = new Intent(SendOTPActivity.this, VerifyOTPActivity.class);
                    intent.putExtra("mobileNumber", mobileNumber);
                    startActivity(intent);
                } else {
                    // Failed to send OTP, show error message
                    Toast.makeText(SendOTPActivity.this, "Failed to send OTP. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Error occurred while sending OTP, log the error
                Log.e("SendOTPActivity", "Error sending OTP: " + t.getMessage());
                // Show error message
                Toast.makeText(SendOTPActivity.this, "Failed to send OTP. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
