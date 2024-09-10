package com.example.futudoll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.example.futudoll.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class MenuActivity extends AppCompatActivity {
    String name = "";
    int age = 1;
    public AdView mAdView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        addAd();
    }

    public void onTimer(View view) {
        Intent intentTimer = new Intent(this, PohuyActivity.class);
        startActivity(intentTimer);
    }


    public void signRegistration(View view) {
        Intent intentSignReg = new Intent(this, ErrorActivity.class);
        startActivityForResult(intentSignReg, 111);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 111 && resultCode == RESULT_OK){
            name = data.getStringExtra("name");
            if(Integer.parseInt(data.getStringExtra("age")) > 0 && Integer.parseInt(data.getStringExtra("age")) < 100) {
                age = Integer.parseInt(data.getStringExtra("age"));
            } else {
                age = 6;
            }
        } else {
            name = "name";
            age = 6;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void dieEndBornTimer(View view) {
        Intent intentSignReg = new Intent(this, MainActivity.class);
        startActivity(intentSignReg);
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
