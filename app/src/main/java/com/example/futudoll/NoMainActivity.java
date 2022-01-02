package com.example.futudoll;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;


public class NoMainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_main);

//        Button btnSCount = (Button) findViewById(R.id.btnSCount);
//        View.OnClickListener onCickStartCount = new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Log.d(TAG, "Click GoTo");
//
//            }
//        };
//        btnSCount.setOnClickListener(onCickStartCount);


        Button btnBackAct = (Button) findViewById(R.id.btnBackAct);
        View.OnClickListener oclBtnBackAct = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(NoMainActivity.this , MainActivity.class);
                startActivity(intent);
            }
        };
        btnBackAct.setOnClickListener(oclBtnBackAct);


        Button btnNextFrame = (Button) findViewById(R.id.btnNext);
        View.OnClickListener oclBtnNext = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(NoMainActivity.this , PohuyActivity.class);
                startActivity(intent);
            }
        };
        btnNextFrame.setOnClickListener(oclBtnNext);

    }

    public void editData(View view) {
        FileInputStream fileInput2 = null;
        TextView birthShow = findViewById(R.id.time_a_b);
        TextView dieShow = findViewById(R.id.time_to_die);

        try {
            fileInput2 = openFileInput(MainActivity.FILE_NAME);
            byte[] bytes2;
            bytes2 = new byte[1024];
            fileInput2.read(bytes2);
            String sec_ab_birth = new String(bytes2);
            String sec_to_die = new String(bytes2);
            birthShow.setText(sec_ab_birth);
            dieShow.setText(sec_to_die);
            fileInput2.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInput2 != null)
                    fileInput2.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}