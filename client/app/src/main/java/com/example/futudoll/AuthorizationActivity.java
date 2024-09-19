package com.example.futudoll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.futudoll.retrofit.AuthRequest;
import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizationActivity extends Activity implements View.OnClickListener {

    final String LOG_TAG = "myLOgs";

    EditText nickname;
    EditText birthday;
    EditText login;
    EditText password;
    EditText expected_salary;
    Button btnSubmit;

    MainApi mainApi;
    Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        nickname = (EditText) findViewById(R.id.et_nickname);
        birthday = (EditText) findViewById(R.id.et_birthday);
        login = (EditText) findViewById(R.id.et_login);
        password = (EditText) findViewById(R.id.et_password);
        expected_salary = (EditText) findViewById(R.id.et_expected_salary);

        btnSubmit = (Button) findViewById(R.id.sign_up);
        btnSubmit.setOnClickListener(this);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://3f3jhgmm-8080.euw.devtunnels.ms/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        mainApi = retrofit.create(MainApi.class);


    }

    @Override
    public void onClick(View v) {
        try {
            double salary = Double.parseDouble(expected_salary.getText().toString());
            AuthRequest request = new AuthRequest(
                    nickname.getText().toString(),
                    birthday.getText().toString(),
                    login.getText().toString(),
                    password.getText().toString(),
                    salary
            );

            Call<User> call = mainApi.signUp(request);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.e(LOG_TAG, response.body().toString());
                    if (response.isSuccessful()) {
                        // Handle successful response
                        User user = response.body();
                        Intent intent = new Intent(AuthorizationActivity.this, MenuActivity.class);
//                        intent.putExtra("user", user);
                        intent.putExtra("token", user.getToken());
                        Log.e(LOG_TAG, String.valueOf(response.body()));
                        Log.e(LOG_TAG, String.valueOf(response.raw()));
                        Log.e(LOG_TAG,  user.getId() + " ; " + user.getNickname() + " ; " + user.getBirthday()
                                + " ; " + user.getLogin() + " ; " + user.getPassword()
                                + " ; " + user.getToken() + " ; " + user.getExpected_salary());

                        startActivity(intent);
                    } else {
                        // Handle unsuccessful response
                        Log.e(LOG_TAG, "Response error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Handle failure
                    Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                }
            });
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Invalid salary format: " + e.getMessage());
        }
    }
}

