package com.example.futudoll;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ErrorActivity extends Activity implements View.OnClickListener {
    EditText name;
    EditText age;
    Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        name = (EditText) findViewById(R.id.etFName);
        age = (EditText) findViewById(R.id.etLName);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("age", age.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

}