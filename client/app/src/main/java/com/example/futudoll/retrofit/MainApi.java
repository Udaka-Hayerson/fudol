package com.example.futudoll.retrofit;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface MainApi {

    @GET("users")
    Call<List<User>> getUsers();

    @POST("/signup")
    Call<UserResponse> signUp(@Body AuthRequest authRequest);

    @POST("/signin")
    Call<UserResponse> signIn(@Body UserSignInDTO userSignInDTO);

    @GET("/user")
    Call<User> getUserByToken(@Header("Authorization") String token);

    @PATCH("/timecount/increase")
    Call<ResponseBody> increaseTimeCount(@Header("Authorization") String token, @Body IncreaseTimeCountDTO increaseTimeCountDTO);

    @PATCH("/timecount/reset")
    Call<ResponseBody> resetTimeCount();

}
