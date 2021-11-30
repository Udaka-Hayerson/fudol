package com.example.futudoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnGoToSecAct = (Button) findViewById(R.id.btnGoToSecAct);

        View.OnClickListener oclBtnGoToSecAct = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , NoMainActivity.class);
                startActivity(intent);
            }
        };
        btnGoToSecAct.setOnClickListener(oclBtnGoToSecAct);

    }


}

