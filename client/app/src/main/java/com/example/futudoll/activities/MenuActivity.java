package com.example.futudoll.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.futudoll.R;
import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.User;
import com.example.futudoll.todo.todoapp.MainTODOActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    String token = "";
//    public AdView mAdView;
    MainApi mainApi;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        getUser();
//        addAd();
    }

    public void getUser() throws NumberFormatException {
        mainApi = Utils.retrofitInstance(this);
        Call<User> call = mainApi.getUserByToken("Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response){
                if(response.isSuccessful()){
                    Log.e(LOG_TAG, " menu user " + response.body());
                    user = response.body();
                }
                else {
                    // Handle unsuccessful response
                    Log.e(LOG_TAG, "Response error: " + response.message());
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onTimer(View view) {
        Intent intentTimer = new Intent(this, TimerActivity.class);
        Log.e(LOG_TAG, "salary: " + user.getExpectedSalary());
        intentTimer.putExtra("salary", user.getExpectedSalary());
        intentTimer.putExtra("token", token);
        startActivity(intentTimer);
    }

    public void gameOfThrones(View view) {
        Intent intentTimer = new Intent(this, PlayersActivity.class);
        startActivity(intentTimer);
    }


    public void logOut(View view) {
        Intent intentSignReg = new Intent(this, AuthorizationActivity.class);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply(); // Або editor.commit();
        startActivity(intentSignReg);
    }


    public void dieEndBornTimer(View view) {
        Intent intentDieEndBornTimer = new Intent(this, NoMainActivity.class);
        Log.e(LOG_TAG, "birthday: " + user.getBirthday());
        intentDieEndBornTimer.putExtra("birthday", user.getBirthday());
        startActivity(intentDieEndBornTimer);
    }

    public void appTODO(View view) {
        Intent intentToDo = new Intent(this, MainTODOActivity.class);
        startActivity(intentToDo);
    }



//    public void addAd() {
//        mAdView = findViewById(R.id.adView);
//        @SuppressLint("VisibleForTests") AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdImpression() {
//                // Code to be executed when an impression is recorded
//                // for an ad.
//            }
//
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//        });
//    }
}
