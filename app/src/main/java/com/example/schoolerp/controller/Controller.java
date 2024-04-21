package com.example.schoolerp.controller;

import android.util.Log;

import com.example.schoolerp.FCMTokenRequest;
import com.example.schoolerp.SharedPreferencesManager;
import com.example.schoolerp.apiservices.ApiService;
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

    private Controller() {
        // Initialize Retrofit
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107") // Adjust the base URL accordingly
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
                        SharedPreferencesManager.getInstance().saveFCMToken(token);
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

    public void makeApiCall() {
        // Create the API call
        Call<ResponseBody> call = apiService.getStudentDetails("loginUser"); // Pass your authentication token here

        // Enqueue the call asynchronously
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Handle successful response
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String responseData = responseBody.string();
                            Log.d("API Response", responseData);
                            // Parse the response data
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle unsuccessful response
                    try {
                        // Parse error response body
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", errorBody);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                t.printStackTrace();
            }
        });
    }

    public ApiService create(Class<ApiService> apiServiceClass) {
        return null;
    }
}
