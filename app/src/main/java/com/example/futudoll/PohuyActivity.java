package com.example.futudoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PohuyActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohuy);

        Button btnBackReg = (Button) findViewById(R.id.btnBackReg);
        View.OnClickListener obtnBackReg = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intentBackRegistr = new Intent( PohuyActivity.this , MainActivity.class);
                startActivity(intentBackRegistr);


            }
        };
        btnBackReg.setOnClickListener(obtnBackReg);
    }
}