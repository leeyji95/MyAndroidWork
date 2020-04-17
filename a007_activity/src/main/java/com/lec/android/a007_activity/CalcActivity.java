package com.lec.android.a007_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

// 화면이 없는 액티비티  ==> 걍 백지? 아무런 레이아웃 없는 화면
public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // 일단 넘어온 intent 받자
        Intent intent = getIntent();
        int num1 = intent.getIntExtra("num1", 0);
        int num2 = intent.getIntExtra("num2", 0);

        // 돌려줄 것
        // intent.putExtra("")

        intent.putExtra("plus", num1 + num2);
        intent.putExtra("minus", num1 - num2);

        // 호출한 화면에 값 되돌려 주기  -> setResult()
        setResult(RESULT_OK, intent);

        finish();  // finish 하면 onDestory() 와 동일
    }






}
