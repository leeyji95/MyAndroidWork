package com.gmail.ilyeel59.cgs2004.blockgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howtoplay);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // 화면 종료 되어야 원래 화면으로 돌아감. 오..
                 // 현재 화면 종료하고 이전화면으로 돌아가기
            }
        });


    } // end onCreate()



} // end Activity
