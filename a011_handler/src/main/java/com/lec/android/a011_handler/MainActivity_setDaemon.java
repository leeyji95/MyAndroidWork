package com.lec.android.a011_handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/** Thread 사용
 *   Thread 클래스(run 함수의 구현 포함)를 별도로 정의하고,
 *   Thread의 객체를 메인 Activity내에서 생성하고 Thread를 start시킴.
 *  *
 *   일반적인 자바 프로그래밍에서는 메인스레드가 종료되면, 작업스레도도 잘(?) 종료되지만
 *   안드로이드 액티비티에선 메인스레드가 종료되도 (심지어 어플이 종료되도) 작업스레드가
 *   종료되지 않는 경우가 있습니다.  그래서 setDaemon(true) 메소드를 통해
 *   메인스레드와 종료동기화를 시킵니다.
 *
 *   ★ 작업 쓰레드는 Main UI 를 직접 접근할수 없다.★
 *   ★  안드로이드는 메인 스레드를 강제로 종료시킬수 없다. ★
 */

public class MainActivity_setDaemon extends AppCompatActivity {

    int mainValue = 0;
    int backValue1 = 0;
    int backValue2 = 0;
    TextView tvMainValue;
    TextView tvBackValue1;
    TextView tvBackValue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMainValue = findViewById(R.id.tvMainValue);
        tvBackValue1 = findViewById(R.id.tvBackValue1);
        tvBackValue2 = findViewById(R.id.tvBackValue2);

        BackThread thread1 = new BackThread();
        thread1.setDaemon(true); // 메인스테드와 종료 동기화  -> 즉, 메인스레드가 죽으면 작업스레드도 죽게 하는..!
        thread1.start(); // 작업스레드 시작

        BackRunnable runnable = new BackRunnable();
        Thread thread2 = new Thread(runnable);
        thread2.setDaemon(true);
        thread2.start();
    } // end onCreate()




    public void mOnClick(View v){  //
        // mainValue 값 1씩 증가시켜볼게
        mainValue++;

        tvMainValue.setText("메인스레드 값:" + mainValue);
        tvBackValue1.setText("작업스레드1 값:" + backValue1);
        tvBackValue2.setText("작업스레드2 값:" + backValue2);
    }// end mOnClick

     //xxxxxxxxxxxxxxxxxxxxxxx
    // 안드로이드에서는 메인 쓰레드 강제 종료시킬 수 없다.
    public void mOnClick2(View v){
        Thread.currentThread().stop(); // 이건 메인쓰레드 죽이는 방법.. 근데 앱 죽음. .. - java.lang.UnspportedOperationException발생.
    } // end mOnClick()






    // 1. Thread 를 상속받은  별도의 작업 스레드
    class BackThread extends Thread{
        @Override
        public void run() {
            while(true){    // 1초에 하나씩 증가
                backValue1++; // 작업스레드 값 증가

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tvBackValue1.setText("" + backValue1); // 쓰레드 값의 변경이 발생하면 알아서 .. ?  CalledFromWrongThreadException 발생 , 왜? -> 작업쓰레드에선 메인 UI 직접 건드릴 수 없다.
            }

        }
    }

    // 2. Runnable  을 implement

    class BackRunnable implements Runnable{
        @Override
        public void run() {
            while(true){    // 1초에 2씩 증가
                backValue2 += 2; // 작업스레드 값 증가

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }



} // end Activity
