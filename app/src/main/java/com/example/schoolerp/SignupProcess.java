package com.example.schoolerp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.controller.Controller;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupProcess extends AppCompatActivity {

    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Retrofit service
        apiService = Controller.getInstance().create(ApiService.class);

        // Retrieve FCM token and send it to the server
        sendTokenToServer();
    }

    private void sendTokenToServer() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String token = task.getResult();
                        // Store token in SharedPreferences or send it directly to the server
                        storeTokenLocally(token);
                        sendTokenToServer(token);
                    }
                });
    }

    private void storeTokenLocally(String token) {
        // Store token in SharedPreferences or any other local storage mechanism
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("FCMToken", token);
        editor.apply();
    }

    private void sendTokenToServer(String token) {
        // Make a Retrofit call to send the token to your server
        // Assuming you have a method in ApiService to send the token
        Call<Void> call = apiService.saveFCMToken(new FCMTokenRequest(token));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("FCMToken", "Token sent to server successfully");
                } else {
                    Log.e("FCMToken", "Failed to send token to server");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("FCMToken", "Error sending token to server: " + t.getMessage());
            }
        });
    }
}
