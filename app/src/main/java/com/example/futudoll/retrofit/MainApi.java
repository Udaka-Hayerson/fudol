package com.example.futudoll.retrofit;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainApi {

    @POST("auth/login")
    public User auth(@Body AuthRequest authRequest);

    @POST("auth/login")
    public User signUp(@Body String nickname,
                       String birthday,
                       String login,
                       String password,
                       String expected_salary);

    @GET("users/user")
    public User getUser();

}
