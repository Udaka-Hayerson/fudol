package com.example.futudoll.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futudoll.R;
import com.example.futudoll.retrofit.IncreaseTimeCountDTO;
import com.example.futudoll.retrofit.MainApi;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimerActivity extends Activity {
    final String LOG_TAG = "myLogs";
    MainApi mainApi;
    double fudol;
    public TextView timerView;
    public TextView work_sec_counter;
    Timer myTimer;
    private Chronometer chronometer;
    private long pause_off_set = 0;
    private boolean running;
    private double salary;
    private String token;
    int count = 0;
    // TODO: Patch timecount/reset

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");

        timerView = (TextView) findViewById(R.id.timerView);
        work_sec_counter = (TextView) findViewById(R.id.work_sec_counter);

        Intent intent = getIntent();
        salary = intent.getDoubleExtra("salary", 500.0);
        token = intent.getStringExtra("token");
        salary = salary / 21 / 8 / 60 / 60;
        mainApi = Utils.retrofitInstance(this);

        work_sec_counter.setText("" + count * salary);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();
            }
        });

    }

    @Override
    protected void onDestroy() {
        saveTimeCountOnServer();
        super.onDestroy();
    }

    private void saveTimeCountOnServer() {
        IncreaseTimeCountDTO increaseTimeCountDTO = new IncreaseTimeCountDTO(count);
        Call<ResponseBody> call = mainApi.increaseTimeCount("Bearer " + token, increaseTimeCountDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    Log.e(LOG_TAG, String.valueOf(responseBody));

                } else {
                    Log.e(LOG_TAG, "Response error: " + response.message());
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Log.e(LOG_TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backMenu(View v) {
        saveTimeCountOnServer();
        Intent intentBackMenu = new Intent(TimerActivity.this, MenuActivity.class);
        intentBackMenu.putExtra("token", token);
        startActivity(intentBackMenu);
    }

    public void startChronometer(View view) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pause_off_set);
            chronometer.start();
            startTimer();
            running = true;
        }
    }
    public void startTimer(){
        myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerView.setText("" + count);
                count++;
                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        fudol = count * salary;
                        work_sec_counter.setText("" + fudol);
                    }
                });

            }

        }, 0, 1000);
    }

    public void pauseChronometer(View view) throws InterruptedException {
        if (running) {
            pause_off_set = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.stop();
            running = false;
            myTimer.cancel();
            fudol = count * salary;
            work_sec_counter.setText("" + fudol);
        } else {
            return;
        }
    }

    public void resetChronometer(View view) {
        fudol = count * salary;
        work_sec_counter.setText("" + fudol);;
        saveTimeCountOnServer();
        count = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
        pause_off_set = 0;
    }
}

