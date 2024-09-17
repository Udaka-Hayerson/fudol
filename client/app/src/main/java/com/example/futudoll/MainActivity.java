package com.example.futudoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    EditText edit;
    EditText editDie;
    Button btnGoTo;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.editBirthData);
        editDie = (EditText) findViewById(R.id.editDeadData);
        btnGoTo = (Button) findViewById(R.id.btnGoTo);
        btnGoTo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , NoMainActivity.class);
                intent.putExtra("dead_day", editDie.getText().toString());
                intent.putExtra("birth_day", edit.getText().toString());
                startActivity(intent);

            }
        });

    }


}


