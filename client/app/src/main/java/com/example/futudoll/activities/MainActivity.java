package com.example.futudoll.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.futudoll.R;
import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.UserResponse;
import com.example.futudoll.retrofit.UserSignInDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText login;
    EditText password;
    Button logInIn;
    String token;
    MainApi mainApi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        login = (EditText) findViewById(R.id.auth_login);
        password = (EditText) findViewById(R.id.auth_password);
        logInIn = (Button) findViewById(R.id.log_in_in);
        mainApi = Utils.retrofitInstance(this);

        logInIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()){
                    try {
                        UserSignInDTO userSignInDTO = new UserSignInDTO(
                                login.getText().toString(),
                                password.getText().toString()
                        );

                        Call<UserResponse> call = mainApi.signIn(userSignInDTO);
                        call.enqueue(new Callback<UserResponse>() {
                            @Override
                            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                if (response.isSuccessful()) {
                                    UserResponse userResponse = response.body();
                                    token = userResponse.getToken();
                                    editor = sharedPreferences.edit();
                                    editor.putString("token", token);
                                    editor.apply(); // Або editor.commit();
                                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                    intent.putExtra("token", token);
                                    startActivity(intent);

//                                    Log.e(LOG_TAG, token);
//                                    Log.e(LOG_TAG, String.valueOf(response.body()));

                                } else {
//                                    Log.e(LOG_TAG, "Response error: " + response.message());
                                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserResponse> call, Throwable t) {
                                // Handle failure
//                                Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (NumberFormatException e) {
//                        Log.e(LOG_TAG, "Invalid salary format: " + e.getMessage());
                    }


                }
            }
        });

    }

    public void backSignInFromLogIn(View view) {
        Intent intentSignIn = new Intent(this, AuthorizationActivity.class);
        startActivity(intentSignIn);
    }

    private boolean validateInputs() {
        if (login.getText().toString().trim().isEmpty()) { // && login.getText().toString().trim().length() < 8
            Toast.makeText(this, "Please enter your login.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) { // && password.getText().toString().trim().length() < 8
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}


