package com.example.schoolerp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.controller.Controller;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class SendOTPActivity extends AppCompatActivity {

    private EditText inputMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_o_t_p);

        inputMobile = findViewById(R.id.inputMobile);
        Button buttonGetOTP = findViewById(R.id.buttonGetotp);

        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateMobileNumber()) {
                    // Proceed to get OTP if mobile number is valid
                    String mobileNumber = inputMobile.getText().toString().trim();
                    sendOTP(mobileNumber);
                    // Apply blink animation to the button
                    Animation blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
                    buttonGetOTP.startAnimation(blinkAnimation);
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
        Call<SendOTPResponse> call = apiService.sendOTP(mobileNumber);
        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
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
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                // Error occurred while sending OTP, log the error
                Log.e("SendOTPActivity", "Error sending OTP: " + t.getMessage());
                // Show error message
                Toast.makeText(SendOTPActivity.this, "Failed to send OTP. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
