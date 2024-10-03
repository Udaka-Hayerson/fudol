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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class PohuyActivity extends Activity {
    public TextView timerView;
    public TextView work_sec_counter;
    Timer myTimer;
    private Chronometer chronometer;
    private long pause_off_set = 0;
    private boolean running;
    private double salary;
    int start = 0;
    int count = 0;
//    String name;
//    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohuy);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");

        timerView = (TextView) findViewById(R.id.timerView);
        work_sec_counter = (TextView) findViewById(R.id.work_sec_counter);

        Intent intent = getIntent();
        salary = intent.getDoubleExtra("salary", 500.0);
        salary = salary / 21 / 8 / 60 / 60;

//        name = intent.getStringExtra("fname");
//        age = intent.getStringExtra("lname");

        work_sec_counter.setText("" + count * salary);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();

//                if (elapsedMillis > 5000) {
//                    String strElapsedMillis = "Пройшло більше 5 секунд";
//                    Toast.makeText(getApplicationContext(),
//                                    strElapsedMillis, Toast.LENGTH_SHORT)
//                            .show();
//                }
            }
        });

    }
    public void backMenu(View v) {
        Intent intentBackMenu = new Intent(PohuyActivity.this, MenuActivity.class);
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
                start++;
                timerView.setText("" + start);
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
//    public void saveData(View view) {
//        edit = findViewById(R.id.editBirthData);
//        //edit = findViewById(R.id.editDeadData);
//        FileOutputStream fileOutput = null;
//        try {
//            String myTxt = edit.getText().toString();
//            fileOutput = openFileOutput(FILE_NAME, MODE_PRIVATE); //MODE_APPEND
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
