package com.example.futudoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futudoll.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//(Y * 365*24*60*60)+(M * 30*24*60*60)+(D * 24*60*60)+(h * 60*60)+(m * 60*60)  =  seconds after born.
// ( (100-Y)*365*24*60*60)+( (12-M) * 30*24*60*60)+( (30-D) * 24*60*60)+( (24-h) * 60*60)+( (60-m) * 60)   =  seconds to dead.
//
//(Y * 365 * 24 * 60 * 60) + (M * 30 * 24 * 60 * 60) +
// (D * 24 * 60 * 60) + (h * 60 * 60) + (m * 60 * 60)
// =  seconds after born.
// ((100 - Y) * 365 * 24 * 60 * 60) +
// ((12 - M) * 30 * 24 * 60 * 60) +
// ((30 - D) * 24 * 60 * 60) + ((24 - h) * 60 * 60) +
// ((60 - m) * 60) = seconds to dead.


public class MainActivity extends AppCompatActivity {

//    public static String FILE_NAME = "birthday.txt";
    EditText edit;
    EditText editDie;
    Button btnGoTo;
    private AdView mAdView;




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.editBirthData);
        editDie = (EditText) findViewById(R.id.editDeadData);
        btnGoTo = (Button) findViewById(R.id.btnGoTo);
        btnGoTo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , NoMainActivity.class);
                intent.putExtra("dead_day", editDie.getText().toString());
                intent.putExtra("birth_day", edit.getText().toString());
                startActivity(intent);

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
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
            public void onAdFailedToLoad(LoadAdError adError) {
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

//    public void saveData(View view) {
//        edit = findViewById(R.id.editBirthData);
////        edit = findViewById(R.id.editDeadData);
//        FileOutputStream fileOutput = null;
//        try {
//            String myTxt = edit.getText().toString();
//            fileOutput = openFileOutput(FILE_NAME, MODE_PRIVATE); //MODE_APPEND
//            fileOutput.write(myTxt.getBytes());
//            Toast.makeText(MainActivity.this, "We are save \n your birthday", Toast.LENGTH_LONG).show();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                if (fileOutput != null)
//                    fileOutput.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public void openData(View view) {
//        FileInputStream fileInput = null;
//        textShow = findViewById(R.id.dataView);
//        try {
//            fileInput = openFileInput(FILE_NAME);
//            byte[] bytes = new byte[1024];
//            fileInput.read(bytes);
//            String text = new String(bytes);
//            textShow.setText(text);
//            fileInput.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fileInput != null)
//                    fileInput.close();
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

