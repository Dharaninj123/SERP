package com.example.schoolerp.controller;

import com.example.schoolerp.apiservices.ApiService;
import com.example.schoolerp.utils.URLS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class Controller {


    public void getinfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLS.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<POST> call = apiService.getPost(1);
        call.enqueue(new Callback<POST>() {
            @Override
            public void onResponse(Call<POST> call, Response<POST> response) {
                if (response.isSuccessful() && response.body() != null) {
                    POST post = response.body();
// Handle the post data
                }
            }

            @Override
            public void onFailure(Call<POST> call, Throwable t) {
                System.out.println("hi");
                int i=0;
// Handle the failure
            }
        });
    }
}
