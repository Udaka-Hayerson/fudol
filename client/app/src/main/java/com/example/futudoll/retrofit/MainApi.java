package com.example.futudoll.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainApi {

    @GET("users")
    Call<List<User>> getUsers();

    @POST(".")
    Call<User> signUp(@Body AuthRequest authRequest);

    @POST(".")
    Call<User> getUserByToken(@Body String token);


}
