package com.example.schoolerp.apiservices;

import com.example.schoolerp.FCMTokenRequest;
import com.example.schoolerp.LoginRequest;
import com.example.schoolerp.LoginResponse;
import com.example.schoolerp.SignupRequest;
import com.example.schoolerp.SignupResponse;
import com.example.schoolerp.StudentsDetialsResponse;
import com.example.schoolerp.apiservices.modelclass.Post;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Query;


public interface ApiService {

    @GET("posts/{user_id}")
    Call<Post> getPost(@Path("user_id") int postId);

    @POST("api/account/register/")
    Call<SignupResponse> signupUser(@Body SignupRequest SignupRequest);

    @POST("api/account/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("save-token")
    Call<Void> saveFCMToken(@Body FCMTokenRequest tokenRequest);

    Call<SignupResponse> signupUser(String name, String dob, String password, String ConfirmPassword, String token);

    Call<LoginResponse> loginUser(String mobileNumber, String password, String token);


    @POST("storeToken")
        // Adjust the endpoint path accordingly
    Call<Void> sendToken(@Body FCMTokenRequest tokenRequest);


    // Define the endpoint for sending OTP
    @GET("api/account/generate-otp/")
// Adjust the endpoint path accordingly
    Call<Void> sendOTP(@Query("mobile_number") String mobileNumber);

    @FormUrlEncoded
    @POST("api/account/validate-otp/")
        // Replace with your actual endpoint
    Call<Void> verifyOTP(@Field("otp") String otp);


    @GET("student-details")
    Call<ResponseBody> getStudentDetails(@Body String loginProcess);

    Call<StudentsDetialsResponse> getStudentDetails();
}


