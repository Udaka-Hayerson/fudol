package com.example.futudoll.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.futudoll.R;
import com.example.futudoll.retrofit.AuthRequest;
import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends Activity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    final String FILE_NAME = "user_token";
    EditText nickname;
    EditText login;
    EditText password;
    EditText expected_salary;
    Button btnSubmit;
    DatePicker datePicker;
    String birthday;
    MainApi mainApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        nickname = (EditText) findViewById(R.id.et_nickname);

        login = (EditText) findViewById(R.id.et_login);
        password = (EditText) findViewById(R.id.et_password);
        expected_salary = (EditText) findViewById(R.id.et_expected_salary);

        btnSubmit = (Button) findViewById(R.id.sign_up);
        btnSubmit.setOnClickListener(this);
        datePicker = this.findViewById(R.id.datePicker);
        datePicker.init(2000, 02, 01, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthday =  view.getYear() + "/" +
                        (view.getMonth() + 1) + "/" + view.getDayOfMonth();
            }
        });

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        if(sharedPreferences.contains("token")){
            Intent intent = new Intent(AuthorizationActivity.this, MenuActivity.class);
            intent.putExtra("token", sharedPreferences.getString("token", "loh"));
            startActivity(intent);
        } else {
            mainApi = Utils.retrofitInstance(this);
        }

    }

    @Override
    public void onClick(View v) {
        if(validateInputs()) {
            try {
                double salary = Double.parseDouble(expected_salary.getText().toString());
                AuthRequest request = new AuthRequest(
                        nickname.getText().toString(),
                        birthday,
                        login.getText().toString(),
                        password.getText().toString(),
                        salary
                );

                Call<UserResponse> call = mainApi.signUp(request);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            UserResponse userResponse = response.body();
                            token = userResponse.getToken();
                            editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.apply(); // Або editor.commit();
                            Intent intent = new Intent(AuthorizationActivity.this, MenuActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);

                            Log.e(LOG_TAG, token);
                            Log.e(LOG_TAG, String.valueOf(response.body()));

                        } else {
                            Log.e(LOG_TAG, "Response error: " + response.message());
                            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        // Handle failure
                        Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (NumberFormatException e) {
                Log.e(LOG_TAG, "Invalid salary format: " + e.getMessage());
            }
        }
    }

    private boolean validateInputs() {
        if (nickname.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter your nickname.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (birthday.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your birthday.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (login.getText().toString().trim().isEmpty()) { // && login.getText().toString().trim().length() < 8
            Toast.makeText(this, "Please enter your login.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) { // && password.getText().toString().trim().length() < 8
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (expected_salary.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter your expected salary.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void signIn(View view) {
        Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
        startActivity(intent);
    }

}

