package com.example.futudoll;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class NoMainActivity extends AppCompatActivity {
    public long dead_d;
    public long birth_b;
    public TextView t_t_die;
    public TextView t_a_birth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_main);
        Intent intent = getIntent();
        setBirthdayAndDeathdayCounts(intent);
        t_t_die = (TextView) findViewById(R.id.time_to_die);
        t_a_birth = (TextView) findViewById(R.id.time_a_birth);
        // my death and birth timers in start development day  =  b864636600 d2002896600

    }

    private void setBirthdayAndDeathdayCounts(Intent intent) {
        String rawDate = intent.getStringExtra("birthday"); //  YYYY/MM/DD
        long year_b = 2024 - (Long.parseLong(rawDate.substring(0, 4))) * 365 * 24 * 60 * 60;
        long month_b = Long.parseLong(rawDate.substring(5, 7)) * 30 * 24 * 60 * 60;
        long day_b = Long.parseLong(rawDate.substring(8)) * 24 * 60 * 60;

        long year_d = (100 - (2024 - (Long.parseLong(rawDate.substring(0, 4))))) * 365 * 24 * 60 * 60;
        long month_d = (12 - (Long.parseLong(rawDate.substring(5, 7)))) * 30 * 24 * 60 * 60;
        long day_d = (30 - (Long.parseLong(rawDate.substring(8)))) * 24 * 60 * 60;

        dead_d = year_b + month_b + day_b;
        birth_b = year_d + month_d + day_d;
    }

    public void btnBackActMethod(View v) {
        Intent intentBack = new Intent(NoMainActivity.this , MenuActivity.class);
        startActivity(intentBack);

    }
    public void btnNextMethod(View v) {
        Intent intentNext = new Intent(NoMainActivity.this , MenuActivity.class);
        startActivity(intentNext);
    }
    public void btnStartTimerMethod(View v)
    {
        new Timer().scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                t_t_die.setText("" + (dead_d--));
                t_a_birth.setText("" + (birth_b++));
            }

        },0,1000);

    }

}
