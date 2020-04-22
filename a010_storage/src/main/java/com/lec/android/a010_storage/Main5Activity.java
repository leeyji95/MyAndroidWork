package com.lec.android.a010_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

/*
SharedPreference
key-value 쌍으로 데이터 저장
작은 데이터들(세팅값들) 저장 용도로 활용
 */
public class Main5Activity extends AppCompatActivity {

    EditText etInput;
    String sfName = "myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        etInput = findViewById(R.id.etInput);

        // 저장되어 있는 값을 꺼내서 보여주기
        SharedPreferences sf = getSharedPreferences(sfName, MODE_PRIVATE);
        String str = sf.getString("name", ""); // 키값으로 꺼냄, 만약 없다면 디폴트값
        String cc = sf.getString("cc", "AVJ");
        String yy = sf.getString("yy", "slkh");

        etInput.setText(str);
        Log.d("myapp", str + " - " + cc + " - " + yy);



    } // end onCreate()


    @Override
    protected void onPause() {
        super.onPause();

        // Activity 가 종료되기 전에 저장
        SharedPreferences sf = getSharedPreferences(sfName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit(); // 저장하려면 Editor 객체 필요

        String str = etInput.getText().toString(); // 사용자가 입력한 값

        editor.putString("name", str);
        editor.putString("cc", "가나다");
        editor.commit(); //파일에 최종 반영함.

        // 이 동작을 지금 onPause 에서 하고 있음.
        // 종료되기 전에 이동 작을 허여 저장 하고.


    }
} // end Activity


// 화면 회전 전환 할 떄   사용해도 됨.
// 작은 값이나, 작은 세팅값