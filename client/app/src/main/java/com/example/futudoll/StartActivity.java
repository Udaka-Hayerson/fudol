package com.example.futudoll;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity{}

//(new Handler()).postDelayed(this::null, 5000);

// extends AppCompatActivity {
//    ImageView image_view;
//    int img0 = R.drawable.start_screen;
//    int img1 = R.drawable.logo;
//    final Handler handler = new Handler();
//    final Runnable r = new Runnable() {
//        public void run() {
//            Intent intent = new Intent(StartActivity.this, MenuActivity.class);
//            startActivity(intent);
//        }
//    };
//    final Runnable r_img = new Runnable() {
//        public void run() {
//            image_view.setImageResource(img1);
//        }
//    };
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start);
//        image_view = (ImageView) findViewById(R.id.image_start);
//        handler.postDelayed(r_img, 3000);
//        handler.postDelayed(r, 2000);
//
//    }
//    private void launchApp() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                image_view.setImageResource(img1);
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                }, 3000);
//            }
//        }, 5000);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(StartActivity.this, MenuActivity.class);
//                startActivity(intent);
//            }
//        }, 5000);
//    }
//}
