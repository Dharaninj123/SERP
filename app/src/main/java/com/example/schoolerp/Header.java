package com.example.schoolerp;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.controller.Controller;
import com.example.schoolerp.StudentsDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Inside your class where you make API calls, e.g., Header class

public class Header {

    private ApiService apiService;

    public Header(ApiService apiService) {
        this.apiService = apiService;
    }

    public void fetchStudentDetails(String accessToken) {
        // Form the Authorization header
        String authorizationHeader = "Bearer " + accessToken;

        // Make the API call using Retrofit with the authorization header
        Call<StudentsDetailsResponse> call;
        call = apiService.getStudentDetails(authorizationHeader);

        // Enqueue the call asynchronously
        call.enqueue(new Callback<StudentsDetailsResponse>() {
            @Override
            public void onResponse(Call<StudentsDetailsResponse> call, Response<StudentsDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful response
                    StudentsDetailsResponse studentDetails = response.body();
                    // Process studentDetails as needed
                } else {
                    // Handle unsuccessful response
                    // You may want to check response codes and handle errors accordingly
                }
            }

            @Override
            public void onFailure(Call<StudentsDetailsResponse> call, Throwable t) {
                // Handle failure
                // This will be called if the network call fails
            }
        });
    }
}
