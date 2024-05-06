package com.example.schoolerp.apiservices;

import com.example.schoolerp.FCMTokenRequest;
import com.example.schoolerp.LoginRequest;
import com.example.schoolerp.LoginResponse;
import com.example.schoolerp.SignupRequest;
import com.example.schoolerp.SignupResponse;
import com.example.schoolerp.StudentsDetailsResponse;
import com.example.schoolerp.apiservices.modelclass.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Query;
import retrofit2.http.Header;

public interface ApiService {

    @GET("posts/{user_id}")
    Call<Post> getPost(@Path("user_id") int postId);

    @POST("api/account/register/")
    Call<SignupResponse> signupUser(@Body SignupRequest SignupRequest);

    @POST("/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("save-token")
    Call<Void> saveFCMToken(@Body FCMTokenRequest tokenRequest);

    @POST("storeToken")
    Call<Void> sendToken(@Body FCMTokenRequest tokenRequest);

    @GET("api/account/generate-otp/")
    Call<Void> sendOTP(@Query("mobile_number") String mobileNumber);

    @FormUrlEncoded
    @POST("api/account/validate-otp/")
    Call<Void> verifyOTP(@Field("otp") String otp);

    @GET("student-details")
    Call<StudentsDetailsResponse> getStudentDetails();

    @GET("student/details")
    Call<StudentsDetailsResponse> getStudentDetails(@Header("Authorization") String authorizationHeader);

    Call<LoginResponse> loginUser(String mob, String password, String token);
}
