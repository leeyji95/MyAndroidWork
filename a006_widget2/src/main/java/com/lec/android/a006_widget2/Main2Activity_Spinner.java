package com.lec.android.a006_widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity_Spinner extends AppCompatActivity {

    TextView tvResult;
    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvResult = findViewById(R.id.tvResult);
        spinner1 = findViewById(R.id.spinner1);

        // 어떤 아이템 선택했을 떄 동작하는 리스너 장착
        // 아이템을 선택했을 때 호출되는 콜백00000
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // 아이템을 선택했을 때
            // position : 몇번째 item 인지, 0 부터 시작 ~
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvResult.setText("position: " + position + parent.getItemAtPosition(position) );
                Toast.makeText(getApplicationContext(), (String)parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "선택안했어요", Toast.LENGTH_LONG).show();
                // 컨택스(이 토스를 보여줄 컨텍스트가 무엇이냐. 지금은 Activity ), 보여줄 메시지 , 토스트Length long & short
            }
        });


    } // end onCreate()

}// end Activity

