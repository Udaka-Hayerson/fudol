package com.example.futudoll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;


public class PohuyActivity extends Activity {

    public TextView tvView;
    public TextView timerView;
    public Button btnBackReg;
    Timer myTimer;
    private Chronometer chronometer;
    private long pause_off_set;
    private boolean running;
    int start = 0;
    int count = 0;
    String fName;
    String lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohuy);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        tvView = (TextView) findViewById(R.id.tvView);
        timerView = (TextView) findViewById(R.id.timerView);

        Intent intent = getIntent();

        fName = intent.getStringExtra("fname");
        lName = intent.getStringExtra("lname");

        tvView.setText(" " + fName + " " + lName + " works " + count + " seconds");

        btnBackReg = (Button) findViewById(R.id.btnBackReg);
        btnBackReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackRegistr = new Intent(PohuyActivity.this, MainActivity.class);
                startActivity(intentBackRegistr);


            }
        });


    }


    public void startChronometer(View view) {
        if(!running){
            myTimer = new Timer();
            myTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    timerView.setText("" + (start++));
                }

            }, 0, 1000);
            chronometer.setBase(SystemClock.elapsedRealtime() - pause_off_set);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View view) {
        if(running) {
            myTimer.cancel();
            count = count + start;
            start = 0;
            tvView.setText(" " + fName + " " + lName + " works " + count + " seconds");
            chronometer.stop();
            pause_off_set = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer(View view) {
        count = count + start;
        start = 0;
        tvView.setText(" " + fName + " " + lName + " works " + count + " seconds");
        chronometer.setBase(SystemClock.elapsedRealtime());
        pause_off_set = 0;
    }
}

//    public void saveData(View view) {
//        edit = findViewById(R.id.editBirthData);
//        //edit = findViewById(R.id.editDeadData);
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
