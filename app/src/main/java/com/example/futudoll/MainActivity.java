package com.example.futudoll;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public static String FILE_NAME = "birthday.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnGoTo = (Button) findViewById(R.id.btnGoTo);
        View.OnClickListener oBtnGoTo = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "Click GoTo");
                Intent intent = new Intent(MainActivity.this , NoMainActivity.class);
                startActivity(intent);

            }
        };
        btnGoTo.setOnClickListener(oBtnGoTo);
    }

    public void saveData(View view) {

        FileOutputStream fileOutput = null;
        try {
            EditText edit = findViewById(R.id.editTextData);
            String myTxt = edit.getText().toString();
            fileOutput = openFileOutput(FILE_NAME, MODE_PRIVATE); //MODE_APPEND
            fileOutput.write(myTxt.getBytes());
            Toast.makeText(MainActivity.this, "We are save \n your birthday", Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fileOutput != null)
                    fileOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void openData(View view) {
        FileInputStream fileInput = null;
        TextView textShow = findViewById(R.id.dataView);
        try {
            fileInput = openFileInput(FILE_NAME);
            byte[] bytes = new byte[1024];
            fileInput.read(bytes);
            String text = new String(bytes);
            textShow.setText(text);
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInput != null)
                    fileInput.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

