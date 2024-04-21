package com.example.schoolerp;

import android.content.Context;
import android.widget.Toast;

import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.controller.Controller;
import com.example.schoolerp.LoginResponse;
import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginProcess {

    private ApiService apiService;
    private Context context;

    public LoginProcess(Context context) {
        // Initialize Retrofit service
        apiService = Controller.getApiService();
        // Save the context for later use
        this.context = context.getApplicationContext();
        // Initialize SharedPreferencesManager with the application context
        SharedPreferencesManager.initialize(this.context);
    }

    public void loginUser(String mob, String password) {
        // Retrieve the saved token from SharedPreferences
        String token = SharedPreferencesManager.getInstance().getToken();

        // Proceed with your login process, including sending the token to the server
        apiService.loginUser(mob, password, token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful login response
                    LoginResponse loginResponse = response.body();
                    // Extract user data or perform further actions based on the response
                } else {
                    // Handle unsuccessful login response
                    // Display an error message or take appropriate action
                    Toast.makeText(context, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle login request failure
                // Display an error message or take appropriate action
                Toast.makeText(context, "Failed to connect to server. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
