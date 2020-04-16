package com.lec.android.a004_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // EditText id 이름으로 선언
    // TextView id 이름으로 선언
    EditText etName, etPassword, etNumber, etEmail;
    TextView tvName, tvPassword, tvNumber, tvEmail, tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etNumber = findViewById(R.id.etNumber);
        etEmail = findViewById(R.id.etEmail);

        tvName = findViewById(R.id.tvName);
        tvPassword = findViewById(R.id.tvPassword);
        tvNumber = findViewById(R.id.tvNumber);
        tvEmail = findViewById(R.id.tvEmail);

        tvResult = findViewById(R.id.tvResult);

        // 포커스 변화
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            // hasFocus: true 포커스 받은 경우  false: 포커스 잃은 경우

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ((EditText)v).setBackgroundColor(Color.YELLOW);
                } else{
                    // 투명색 ( 포커스 이동했을때 원위치로 만들기)
                    ((EditText)v).setBackgroundColor(Color.parseColor("#00000000")); // 포커스 이동하면 색을 완전히 잃음
                }
            }
        });


        // 키보드가 눌릴때
        etPassword.setOnKeyListener(new View.OnKeyListener() {

            // keyCode: 눌린 키의 코드값
            // 자판 키보드에만 반응 뭐가? keyCode
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvResult.setText( ((EditText)v).getText().toString());  // 자판 키보드에 먹힘  // 앱 죽음. NPE 나옴 // 하단에 Run 에서 Exception 확인,
                // Run, Locat 에 Error 에서 Exception 확인하기
                return false;
            }
        });


        // 값의 변화(입력완료)
        etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                tvResult.setText("입력완료" + actionId);
                return false;
            }
        });



    } // end onCreate()


} // end Activity
