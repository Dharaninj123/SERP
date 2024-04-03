package com.example.schoolerp.apiservices;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("posts/{id}")
    Call<POST> getPost(@Path("id") int postId);

    @POST("posts")
    Call<POST> createPost(@Body POST post);
}
