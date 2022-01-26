package com.example.futudoll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PohuyActivity extends Activity implements View.OnClickListener {

    TextView tvView;
    Button btnBackReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohuy);

        tvView = (TextView) findViewById(R.id.tvView);

        Intent intent = getIntent();

        String fName = intent.getStringExtra("fname");
        String lName = intent.getStringExtra("lname");

        tvView.setText("Your name is: " + fName + " " + lName);

        btnBackReg = (Button) findViewById(R.id.btnBackReg);
        btnBackReg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        Intent intentBackRegistr = new Intent( PohuyActivity.this , MainActivity.class);
        startActivity(intentBackRegistr);
    }

}