package com.example.futudoll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizationActivity extends Activity implements View.OnClickListener {

//    request
//    {
//        "nickname": "",
//        "birthday": "",
//        "login": "",
//        "password": "",
//        "expected_salary": 0.0
//    }


//    Response
//{
//    "id": 0,
//    "token": ""
//}

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

        retrofit = new Retrofit.Builder()
                .baseUrl("http//example/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mainApi = retrofit.create(MainApi.class);


    }


    @Override
    public void onClick(View v) {
        User user = mainApi.signUp(nickname.getText().toString(),
                birthday.getText().toString(),
                login.getText().toString(),
                password.getText().toString(),
                expected_salary.getText().toString());
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}