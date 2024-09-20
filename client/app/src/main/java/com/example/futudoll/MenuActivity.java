package com.example.futudoll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.User;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
    final String LOG_TAG = "myLOgs";
    String token = "";
    int age = 1;
    public AdView mAdView;
    Retrofit retrofit;
    MainApi mainApi;
    User response_user;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        user = getUser(token);
//        User  user = (User) intent.getSerializableExtra("user");
        addAd();
    }

    private User getUser(String token) throws NumberFormatException {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://3f3jhgmm-8080.euw.devtunnels.ms/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mainApi = retrofit.create(MainApi.class);
        Call<User> call = mainApi.getUserByToken(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response){
                if(response.isSuccessful()){
                    response_user = response.body();
                }
                else {
                    // Handle unsuccessful response
                    Log.e(LOG_TAG, "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(LOG_TAG, "API call failed: " + t.getMessage());
            }
        });
        return response_user;
    }

    public void onTimer(View view) {
        Intent intentTimer = new Intent(this, PohuyActivity.class);
        intentTimer.putExtra("salary", user.getExpected_salary());
        startActivity(intentTimer);
    }


    public void signInOrUp(View view) {
        Intent intentSignReg = new Intent(this, AuthorizationActivity.class);
        startActivity(intentSignReg);
    }


    public void dieEndBornTimer(View view) {
        Intent intentDieEndBornTimer = new Intent(this, NoMainActivity.class);
        intentDieEndBornTimer.putExtra("birthday", user.getBirthday());
        startActivity(intentDieEndBornTimer);
    }



    public void addAd() {
        mAdView = findViewById(R.id.adView);
        @SuppressLint("VisibleForTests") AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
    }
}
