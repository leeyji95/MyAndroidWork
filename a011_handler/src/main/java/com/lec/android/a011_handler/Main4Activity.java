package com.lec.android.a011_handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
// TODO
// Value1
// 1~ 10까지 1초 단위로 증가시키기
// 10초에 도달하면 멈추어서 Toast 띄우기

// Value2
// 1 ~ 20 초 까지 1초 단위로 증가시키기
// 10 초에 도달하면 멈추어서 Toast 띄우기
// Handler 사용

public class Main4Activity extends AppCompatActivity {

    int value1 = 0, value2 = 0, value3 = 0, value4 = 0, value5 = 0;
    TextView tvResult1, tvResult2, tvResult3, tvResult4, tvResult5;
    Handler mHandler2, mHandler4, mHandler3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        tvResult1 = findViewById(R.id.tvResult1);
        tvResult2 = findViewById(R.id.tvResult2);
        tvResult3 = findViewById(R.id.tvResult3);
        tvResult4 = findViewById(R.id.tvResult4);
        tvResult5 = findViewById(R.id.tvResult5);

        // 방법 #1 핸들러 객체를 외부에서 생성
        mHandler1.sendEmptyMessage(0); // 앱 시작과 동시에 핸들러에 메세지 전달
        Log.d("myapp", "1");

        // 방법 #2 handler.postDelayed() 사용
        mHandler2 = new Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                value2++;
                tvResult2.setText("Value2 = " + value2);
                if(value2 < 20){
                    mHandler2.postDelayed(this, 1000);
                } else {
                    Toast.makeText(getApplicationContext(), "Value2 종료", Toast.LENGTH_LONG).show();
                }
            }
        }, 0);


        // 방법3: 메소드 내부에서 생성
        mHandler3 = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                value3++;
                tvResult3.setText("Value3 = " + value3);

                if(value3 < 5){
                    mHandler3.sendEmptyMessageDelayed(0, 1000);
                } else {
                    Toast.makeText(getApplicationContext(), "Value3 종료", Toast.LENGTH_LONG).show();
                }
            }
        };
        mHandler3.sendEmptyMessage(0 ); // 핸들러 만들고 곧바도 첫 시작...!



    // 방법 #4
    // 핸들러를 사용하지 않고도 일정시간마다 (혹은 후에) 코스를 수행할수 있도록
    // CountDownTimer 클래스가 제공된다.
    // '총시간'  과 '인터벌(간격)' 을 주면 매 간격마다 onTick 메소드를 수행한다.
    new CountDownTimer( 15 * 1000, 1000){

        @Override
        public void onTick(long millisUntilFinished) {  // 1초간격으로 사용 // 매 간격마다 수행하는 코드
            value4++;
            tvResult4.setText("Value4 = " + value4);
        }

        @Override
        public void onFinish() { // 다끝나고 나서 수행할 것 (종료시 수행하는 코드)
            Toast.makeText(getApplicationContext(), "Value4 종료", Toast.LENGTH_LONG).show();
        }
    }.start(); // 타이머 시작

    } // end onCreate


    Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            value1++;

            tvResult1.setText("Value1 = " + value1);

            if(value1 < 10){
                // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
                mHandler1.sendEmptyMessageDelayed(0,1000);
                Log.d("myapp", "");

                // 첫번째 매개변수는 message 값
                // 두번째 매개변수는 millisec
            } else {
                Toast.makeText(getApplicationContext(), "Value1 종료", Toast.LENGTH_LONG).show();
            }
        }
    };


//    Handler handler1 = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            value1++;
//            if(msg.what == 0){
//                tvResult1.setText("Value1:" + msg.arg1);
//            }
//            Toast.makeText(getApplicationContext(),  "종료되었습니다", Toast.LENGTH_LONG).show();
//
//        }
//    };
//
//    class MyThread extends Thread{
//        @Override
//        public void run() {
//            while(true){
//                Message msg = new Message();
//                msg.what = 0;
//                msg.arg1 = value1;
//
//                handler1.sendMessage(msg);
//
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    } // end MyThread





} // end Activity
