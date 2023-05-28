package com.example.futudoll;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    ImageView image_view;
    int img0 = R.drawable.start_screen;
    int img1 = R.drawable.logo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        image_view = (ImageView) findViewById(R.id.image_start);
        try {
            wait(3500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        image_view.setImageResource(img1);
        try {
            wait(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
