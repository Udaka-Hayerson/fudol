package com.example.futudoll.retrofit;

import com.example.futudoll.todo.todoapp.model.ClassTODO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface MainApi {

    //auth
    @POST("/auth/signup")
    Call<UserResponse> signUp(@Body AuthRequest authRequest);

    @POST("/auth/signin")
    Call<UserResponse> signIn(@Body UserSignInDTO userSignInDTO);

    //fudol
    @GET("/fudol")
    Call<Fudol> getFudol(@Header("Authorization") String token);

    @GET("/fudol/users")
    Call<List<UserChalange>> getUsers(@Header("Authorization") String token);
    //todo public user [{"timeCount":7,"user":{"id":"672fa930db08248dbc982148","nickname":"nick"}}]

    @PATCH("/fudol/increase")
    Call<ResponseBody> increaseTimeCount(@Header("Authorization") String token, @Body IncreaseTimeCountDTO increaseTimeCountDTO);

    @PATCH("/fudol/reset")
    Call<ResponseBody> resetTimeCount();

    // appTODO
    @GET("/todo")
    Call<List<ClassTODO>> getToDoLIst(@Header("Authorization") String token);

    @POST("/todo")
    Call<ResponseBody> addToDo(@Header("Authorization") String token, @Body RequestTodoDTO requestTodoDTO);

    @DELETE("/todo")
    Call<ResponseBody> deleteToDo(@Header("Authorization") String token, @Body DeleteTodoDTO deleteTodoDTO);

    @PATCH("/todo/completed")
    Call<ResponseBody> setCompletedState(@Header("Authorization") String token, @Body CompletedTodoDTO completedTodoDTO);


    //user
    @GET("/user")
    Call<User> getUserByToken(@Header("Authorization") String token);

}
