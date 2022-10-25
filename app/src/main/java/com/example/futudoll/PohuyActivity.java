package com.example.futudoll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class PohuyActivity extends Activity {

    public TextView tvView;
    public TextView timerView;
    public Button btnBackReg;
    public Button btnStartWorkTime;
    int start = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohuy);

        tvView = (TextView) findViewById(R.id.tvView);
        timerView = (TextView) findViewById(R.id.timerView);

        Intent intent = getIntent();

        String fName = intent.getStringExtra("fname");
        String lName = intent.getStringExtra("lname");

        tvView.setText("Your name is: " + fName + " " + lName);

        btnBackReg = (Button) findViewById(R.id.btnBackReg);
        btnBackReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackRegistr = new Intent(PohuyActivity.this, MainActivity.class);
                startActivity(intentBackRegistr);


            }
        });

        btnStartWorkTime = (Button) findViewById(R.id.work_timer);
        btnStartWorkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        timerView.setText("" + (start++));
                        //Log.i("tag", "A Kiss every 5 seconds");
                    }

                }, 0, 1);
                //If the timer counts in seconds, the period must be equal to a thousand.
                // adds a time counter in milliseconds


            }
        });
    //timer variation
        Timer myTimer;
        myTimer = new Timer();

        myTimer.schedule(new TimerTask() {
            public void run() {
                timerTick();
            }
        }, 0, 3000); // каждые 5 секунд

    }
    private void timerTick () {
            this.runOnUiThread(doTask);
        }

    private Runnable doTask = new Runnable() {
        public void run() {
            Toast toast = Toast.makeText(getApplicationContext(), "3 секунды!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    };
    //timer variation





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