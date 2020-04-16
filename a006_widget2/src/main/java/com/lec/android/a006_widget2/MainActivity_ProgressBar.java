package com.lec.android.a006_widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

public class MainActivity_ProgressBar extends AppCompatActivity {

    ProgressBar pB1, pB2, pB3;

    int value = 0; // 막대프로그레스 바의 현재 진행값
    int add = 10; // 증가량

    int value2 = 0;
    int add2 = 1;

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pB1 = findViewById(R.id.pB1);
        pB2 = findViewById(R.id.pB2);
        pB3 = findViewById(R.id.pB3);

        ToggleButton btnToggle = findViewById(R.id.toggleButton);
        Button btn1 = findViewById(R.id.button1);

        // 토글버튼의 리스너
        // 토글버튼 상태 변화시 호출되는 콜백
        btnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pB1.setVisibility(View.INVISIBLE);
                }else{
                    pB1.setVisibility(View.VISIBLE);
                }
            }
        });


        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                value = value + add; // value 값 증가 시키기
                // 0~~ 100 범위 값을 가지고 있고, 100 도달하면 거꾸로 0으로 오도록, 다시 0 도달하면 0부터
                if(value > 100 || value < 0){
                    value = -add;
                }
                pB2.setProgress(value); // 프로그래스바의 진행값 설정
            }
        });


        // 앱 시작시 Thread 를 사용하여 ProgressBar 증가시키기
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() { // 일정시간동안 진행시키도록 해보자
                while(true){


                    value2 = value2 + add2;

                    if(value2 > 100 || value2 < 0){
                        add2 = -add2; // 이게 역방향.
                    }

                    // 지금 이거 별도의 쓰레드.
                    // 별도의 작업 Thread 에서
                    // 메인 UI 접근하려면 반.드.시 Handler 처리해야함
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pB3.setProgress(value2);
                        }
                    });

                    try {
                        Thread.sleep(100); // 0.1 초 딜레이
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } // end while

            }// end run()
        }); // end Thread
        t.start();





    } // end onCreate()
} // end Activity
