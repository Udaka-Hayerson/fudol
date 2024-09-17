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
    public long age;
    public long year_to_sec;
    public long dead_d;
    public long birth_b;
    public TextView t_t_die;
    public TextView t_a_birth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_main);

        t_t_die = (TextView) findViewById(R.id.time_to_die);
        t_a_birth = (TextView) findViewById(R.id.time_a_birth);
        Intent intent = getIntent();
        dead_d = Long.parseLong(intent.getStringExtra("dead_day"));
        birth_b = Long.parseLong(intent.getStringExtra("birth_day"));
//        age = Long.parseLong(intent.getStringExtra("age"));
//        year_to_sec = 365 * 60 * 60;
//        birth_b = age * year_to_sec;
//        dead_d = (100 - age) * year_to_sec;

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
