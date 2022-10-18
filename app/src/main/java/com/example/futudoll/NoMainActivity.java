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


public class NoMainActivity extends AppCompatActivity
{
    public long dead_d;
    public long birth_b;
    public Button btnBackAct;
    public Button btnNext;
    public Button btnStartCount;
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

        btnBackAct = (Button) findViewById(R.id.btnBackAct);
        btnNext = (Button) findViewById(R.id.btnNextAct);
        btnStartCount = (Button) findViewById(R.id.btnStartCount);


        OnClickListener oclBack = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(NoMainActivity.this , MainActivity.class);
                startActivity(intentBack);

            }
        };
        btnBackAct.setOnClickListener(oclBack);


        OnClickListener oclNext = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNext = new Intent(NoMainActivity.this , ErrorActivity.class);
                startActivity(intentNext);
            }
        };
        btnNext.setOnClickListener(oclNext);


        OnClickListener oclStartCount = new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new Timer().scheduleAtFixedRate(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        t_t_die.setText("" + (dead_d--));
                        t_a_birth.setText("" + (birth_b++));
                        //Log.i("tag", "A Kiss every 5 seconds");
                    }

                },0,1000);

            }
        };
        btnStartCount.setOnClickListener(oclStartCount);
    }

}
