package com.example.schoolerp;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.controller.Controller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Header {

    private LoginActivity activity;

    public Header(LoginActivity activity) {
        this.activity = activity;
    }

    public void fetchAndDisplayStudentDetails() {
        // Retrieve access token from SharedPreferences
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPrefs", LoginActivity.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("AccessToken", "");

        // Create an instance of ApiService using Retrofit
        ApiService apiService = Controller.getApiService();

        // Call the API to fetch student details
        Call<StudentsDetialsResponse> call = apiService.getStudentDetails();
        call.enqueue(new Callback<StudentsDetialsResponse>() {
            @Override
            public void onResponse(Call<StudentsDetialsResponse> call, Response<StudentsDetialsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful response
                    StudentsDetialsResponse studentDetails = response.body();
                    String studentName = studentDetails.getName();
                    // Update UI with student name
                    activity.updateNavBar(studentName);
                }
            }

            @Override
            public void onFailure(Call<StudentsDetialsResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(activity, "Failed to fetch student details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
