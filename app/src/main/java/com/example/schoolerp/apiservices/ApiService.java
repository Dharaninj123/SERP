package com.example.schoolerp.apiservices;

import com.example.schoolerp.FCMTokenRequest;
import com.example.schoolerp.LoginResponse;
import com.example.schoolerp.SendOTPResponse;
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

    @GET("posts/{2}")
    Call<Post> getPost(@Path("user_type") int postId);


    @FormUrlEncoded
    @POST("register/")
    Call<SignupResponse> signupUser (@Field("first_name") String first_name,@Field("mobile_number") String mobile_number, @Field("date_of_birth") String date_of_birth, @Field("password") String password,@Field("password2") String password2,@Field("user_type") int user_type);


    @FormUrlEncoded
    @POST("login/")
    Call<LoginResponse> loginUser (@Field("username") String username,@Field("password") String password);

    @POST("save-token")
    Call<Void> saveFCMToken(@Body FCMTokenRequest tokenRequest);

    @POST("storeToken")
    Call<Void> sendToken(@Body FCMTokenRequest tokenRequest);

    @FormUrlEncoded
    @POST("generate-otp/")
    Call<SendOTPResponse> sendOTP(@Field("mobile_number") String mobile_number);

    @FormUrlEncoded
    @POST("validate-otp/")
    Call<SendOTPResponse> verifyOTP(@Field("otp") String otp,@Field("mobile_number") String mobile_number);



    @GET("student-details")
    Call<StudentsDetailsResponse> getStudentDetails();

    @GET("student/details")
    Call<StudentsDetailsResponse> getStudentDetails(@Header("Authorization") String authorizationHeader);

    Call<LoginResponse> loginUser(String mob, String password, String token);



}
