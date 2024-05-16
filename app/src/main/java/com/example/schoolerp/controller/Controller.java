package com.example.schoolerp.controller;

import android.util.Log;

import com.example.schoolerp.FCMTokenRequest;
import com.example.schoolerp.MySharedPreferences;
import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.StudentsDetailsResponse;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    private static Controller instance;
    private Retrofit retrofit;
    private static ApiService apiService;
    private MySharedPreferences mySharedPreferences;

    OkHttpClient client;

    private Controller() {
        // Initialize Retrofit
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        retrofit = new Retrofit.Builder()
                // Ensure the base URL includes the API endpoint
                .baseUrl("http://192.168.0.106/api/account/") // Adjust the base URL accordingly
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        // Create ApiService instance
        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public static ApiService getApiService() {
        return apiService;
    }

    public void sendTokenToServer() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String token = task.getResult();
                        MySharedPreferences.saveString("FCMToken", token);
                        sendTokenToServer(token);
                    }
                });
    }

    private void sendTokenToServer(String token) {
        FCMTokenRequest tokenRequest = new FCMTokenRequest(token);
        Call<Void> call = apiService.saveFCMToken(tokenRequest);
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

    public void makeApiCall(String accessToken) {
        // Form the Authorization header
        String authorizationHeader = "Bearer " + accessToken;

        // Create the API call with the authorization header
        Call<StudentsDetailsResponse> call = apiService.getStudentDetails(authorizationHeader);

        // Enqueue the call asynchronously
        call.enqueue(new Callback<StudentsDetailsResponse>() {
            @Override
            public void onResponse(Call<StudentsDetailsResponse> call, Response<StudentsDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful response
                    StudentsDetailsResponse studentDetails = response.body();
                    // Process student details
                } else {
                    // Handle unsuccessful response
                    // Log error or show error message
                }
            }

            @Override
            public void onFailure(Call<StudentsDetailsResponse> call, Throwable t) {
                // Handle failure
                t.printStackTrace();
            }
        });
    }

    // If you need to create an instance of ApiService with a different class
    public ApiService create(Class<ApiService> apiServiceClass) {
        return retrofit.create(apiServiceClass);
    }
}
