package com.example.futudoll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futudoll.retrofit.IncreaseTimeCountDTO;
import com.example.futudoll.retrofit.MainApi;
import com.example.futudoll.retrofit.UserResponse;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TimerActivity extends Activity {
    final String LOG_TAG = "myLogs";
    MainApi mainApi;
    public TextView timerView;
    public TextView work_sec_counter;
    Timer myTimer;
    private Chronometer chronometer;
    private long pause_off_set = 0;
    private boolean running;
    private double salary;
    private String token;
    int start = 0;
    int count = 0;

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
        IncreaseTimeCountDTO increaseTimeCountDTO = new IncreaseTimeCountDTO(count);
        Call<ResponseBody> call = mainApi.increaseTimeCount("Bearer " + token, increaseTimeCountDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    Log.e(LOG_TAG, String.valueOf(response.body()));

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
        super.onDestroy();
    }

    public void backMenu(View v) {
        IncreaseTimeCountDTO increaseTimeCountDTO = new IncreaseTimeCountDTO(count);
        Call<ResponseBody> call = mainApi.increaseTimeCount("Bearer " + token, increaseTimeCountDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    Log.e(LOG_TAG, String.valueOf(response.body()));
                    Intent intentBackMenu = new Intent(TimerActivity.this, MenuActivity.class);
                    intentBackMenu.putExtra("token", token);
                    startActivity(intentBackMenu);

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
                start++;
                timerView.setText("" + start);
                count = count + start; //TODO:
                work_sec_counter.setText("" + count * salary);//TODO:

            }

        }, 0, 1000);
    }

    public void pauseChronometer(View view) throws InterruptedException {
        if (running) {
            pause_off_set = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.stop();
            running = false;
            myTimer.cancel();
            count = count + start;
            start = 0;
            work_sec_counter.setText("" + count * salary);
            //saveData(work_sec_counter); потім змінити
        } else {
            return;
        }
    }

    public void resetChronometer(View view) {
        count = count + start;
        start = 0;
        work_sec_counter.setText("" + count * salary);
        chronometer.setBase(SystemClock.elapsedRealtime());
        pause_off_set = 0;
        //saveData(work_sec_counter); потім змінити
    }
}
//    public void saveData(View view) {
//        timerView = findViewById(R.id.timerView);
//        FileOutputStream fileOutput = null;
//        try {
//            String myTxt = work_sec_counter.getText().toString();
//            fileOutput = openFileOutput("work_time.txt", MODE_PRIVATE); //MODE_APPEND
//            fileOutput.write(myTxt.getBytes());
//            Toast.makeText(PohuyActivity.this, "We are save \n your birthday", Toast.LENGTH_LONG).show();
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
//    TODO: потрібно зробити так щоб кожного разу в
//     файл записувався сумма , з того що в ньому є
//     і лічильника роботи в секундах за останній
//     робочій період. Тобто метод openDate  має
//     бути інтегрованим в метод saveDate
//    public void openData(View view) {
//        FileInputStream fileInput = null;
//        work_sec_counter = findViewById(R.id.work_sec_counter);
//        try {
//            fileInput = openFileInput("work_time.txt");
//            byte[] bytes = new byte[1024];
//            fileInput.read(bytes);
//            String text = new String(bytes);
//            work_sec_counter.setText(text);
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

