package com.example.futudoll.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.futudoll.R;

import java.util.Timer;
import java.util.TimerTask;


public class NoMainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    TextView t_t_die;
    TextView t_a_birth;
    long seconds_after_birth , seconds_to_dead , year_birth , month_birth , day_birth , year_dead , month_dead , day_dead;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_main);
        Intent intent = getIntent();
        String rawDate = intent.getStringExtra("birthday");
        setBirthdayAndDeathdayCounts(rawDate);
        t_t_die = (TextView) findViewById(R.id.time_to_die);
        t_a_birth = (TextView) findViewById(R.id.time_a_birth);
        // my death and birth timers in start development day  =  b864636600 d2002896600

    }

    private void setBirthdayAndDeathdayCounts(String rawDate) {
        //  YYYY/MM/DD // 0123/56/89 // 0123/5/7
        Log.e(LOG_TAG, "birthday: " + rawDate);
        Log.e(LOG_TAG, "yyyy: " + rawDate.substring(0, 4));
        Log.e(LOG_TAG, "mm: " + rawDate.substring(5, 7));
//        Log.e(LOG_TAG, "m: " + rawDate.substring(5, 6));
        Log.e(LOG_TAG, "dd: " + rawDate.substring(8));
//        Log.e(LOG_TAG, "d: " + rawDate.substring(7));
        year_birth = (2024 - (Long.parseLong(rawDate.substring(0, 4)))) * 365 * 24 * 60 * 60;
        year_dead = (100 - (2024 - (Long.parseLong(rawDate.substring(0, 4))))) * 365 * 24 * 60 * 60;
        Log.e(LOG_TAG, "" + (100 - (2024 - (Long.parseLong(rawDate.substring(0, 4))))));
        if(rawDate.charAt(7) == '/'){
            month_birth = Long.parseLong(rawDate.substring(5, 7)) * 30 * 24 * 60 * 60;
            month_dead = (12 - (Long.parseLong(rawDate.substring(5, 7)))) * 30 * 24 * 60 * 60;
            day_birth = Long.parseLong(rawDate.substring(8)) * 24 * 60 * 60;
            day_dead = (30 - (Long.parseLong(rawDate.substring(8)))) * 24 * 60 * 60;
        } else {
            month_birth = Long.parseLong(rawDate.substring(5, 6)) * 30 * 24 * 60 * 60;
            month_dead = (12 - (Long.parseLong(rawDate.substring(5, 6)))) * 30 * 24 * 60 * 60;
            day_birth = Long.parseLong(rawDate.substring(7)) * 24 * 60 * 60;
            day_dead = (30 - (Long.parseLong(rawDate.substring(7)))) * 24 * 60 * 60;
        }
        seconds_after_birth  = year_birth + month_birth + day_birth;
        Log.e(LOG_TAG, "" + year_birth);
        Log.e(LOG_TAG, "" + month_birth);
        Log.e(LOG_TAG, "" + day_birth);
        Log.e(LOG_TAG, "" + seconds_after_birth);
        seconds_to_dead = year_dead + month_dead + day_dead;
    }

    public void btnBackActMethod(View v) {
        Intent intentBack = new Intent(NoMainActivity.this , MenuActivity.class);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        intentBack.putExtra("token", sharedPreferences.getString("token", "loh"));
        startActivity(intentBack);

    }
    public void btnStartTimerMethod(View v)
    {
        new Timer().scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                t_t_die.setText("" + (seconds_to_dead--));
                t_a_birth.setText("" + (seconds_after_birth++));
            }

        },0,1000);

    }

}
