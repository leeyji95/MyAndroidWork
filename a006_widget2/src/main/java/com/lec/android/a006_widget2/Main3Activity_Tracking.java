package com.lec.android.a006_widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.net.ssl.HandshakeCompletedEvent;

public class Main3Activity_Tracking extends AppCompatActivity {

    TextView tvResult;
    SeekBar seekBar;

    int value = 0;
    int add = 2;

    Handler handler = new Handler();

    boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvResult = findViewById(R.id.tvResult);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // 각각 언제 호출되는지 알기

            // 값의 변화가 생겼을 때 콜백된다(안드로이드시스템에서 호룰된다는 뜻)
            // progress : 진행값을 의미. 0 ~ max(200) 범위안에서 진행.
            // fromUser : 사용자에 의한 진행값 변화인 경우 true (예를 들어 영상이나 음악이 진행될때 알아서 움직이도록. 이럴떈 false. 내가 직접 움직였을 땐 true 설정)  사용자에 의해서 움직인것과 그렇지 않은 것을 구분.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvResult.setText("onProgressChanged:" + progress + "(" + fromUser + ")");
            }

            // 트래킹 시작될 때 콜백 (손으로 누르기 시작할 떄)
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "트래킹 시작", Toast.LENGTH_SHORT).show(); // 토스트 띄운다 표현
                isTracking = true;
            }

            // tracking 끝날 때 콜백( 손을 뗄 때)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "트래킹 종료", Toast.LENGTH_SHORT).show(); // 토스트 띄운다 표현
                isTracking = false;
            }
        });

        // 앱 시작시 Thread .. SeekBar 증가 시키기
        new Thread(new Runnable() {
            @Override
            public void run() {

                int max = seekBar.getMax();

                while(true){

                    if(!isTracking) {
                        value = seekBar.getProgress() + add;

                        if (value > max || value < 0) {
                            add = -add;
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                seekBar.setProgress(value); // 현재값 의미
                            }
                        });

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } // end if000

                } // end while

            }// end run()
        }).start();


    }
}
