package com.example.futudoll.retrofit;

import retrofit2.http.GET;

public interface MainApi {
    @GET("users/user")
    public User getUser();

}
