package com.example.futudoll.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainApi {

    @GET("users")
    Call<List<User>> getUsers();

    @POST("/signup")
    Call<UserResponse> signUp(@Body AuthRequest authRequest);

    @GET("/user")
    Call<User> getUserByToken(String token);


}
