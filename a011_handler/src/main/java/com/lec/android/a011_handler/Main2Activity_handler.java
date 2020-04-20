package com.lec.android.a011_handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

/** Handler
 *  자바는 자바가상머신 위에서 자체적으로 스레드를 생성하고 운영하긴 하지만,
 *  스레드 부분 만큼은 '운영체제'의 영향을 받는다.
 *  안드로이드에서 돌아가는 자바는 결국 '안드로이드 운영체제'의 영향을 받을수 밖에 없는데, ..
 *  안드로이드 운영체제의 경우 '작업스레드' 가 '메인스레드'의 변수를 참조하거나 변경을 할수 있어도,
 *  '메인스레드' 에서 정의된 UI 를 변경할수는 없게 하고 있습니다.  --> CalledFromWrongThreadException !! (이전 예제 참조)
 *
 *  안드로이드에서 '작업 스레드' 가 '메인스레드의 UI' 에 접근(변경/사용) 하려면 Handler 를 사용해야 합니다
 * (   핸들러의 본질   ) Handler 는 메인스레드와 작업스레드 간에 통신을 할 수 있는 방법입니다ㅣ
 *
 *  사용 방법:
 *      ▫ 'Handler 를 생성'한 스레드만이 다른 작업스레드가 전송하는 'Message' 나 'Runnable객체' 를 수신하는 기능을 할 수 있다. [쓰레드 끼리 주고 받을 수 있는 건 이 두가지. 받고자 하는 쪽에서 핸들러 생성해야한다.]
 *      ▫  Message 전송은 sendMessgae()
 *      ▫  Runnable 전송은 postXXX()
 */

// 받는 쪽에 Handler 만들어서 별도의 스레드에서 받은 메시지 or Runnable 객체를  다루어야 함.
// 주는 쪽은 메시지를 보내는 거니까 sendMessage(msg ) 메시지 객체 담아서 보냄. 이때 handler 객체 이용해서 보내야함.



public class Main2Activity_handler extends AppCompatActivity {

    int mainValue = 0;
    int backValue1 = 0;
    int backValue2 = 0;
    TextView tvMainValue;
    TextView tvBackValue1, tvBackValue2, tvBackValue3, tvBackValue4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvMainValue = findViewById(R.id.tvMainValue);
        tvBackValue1 = findViewById(R.id.tvBackValue1);
        tvBackValue2 = findViewById(R.id.tvBackValue2);
        tvBackValue3 = findViewById(R.id.tvBackValue3);
        tvBackValue4 = findViewById(R.id.tvBackValue4);

        // 방법 1, 방법 2
        // 스레드 생성하고 시작
        // 1초 단위로 백밸류 1씩 증가시킴.
        BackThread1 thread1 = new BackThread1();
        thread1.setDaemon(true);
        thread1.start();

        // 방법3
        BackThread3 thread3 = new BackThread3(handler3);  // 메인스레드의 Handler 객체를 외부 클래스에 넘겨줌
        thread3.setDaemon(true);
        thread3.start();

        // 방법4
        BackThread4 thread4 = new BackThread4(handler4);  // 메인스레드의 Handler 객체를 외부 클래스에 넘겨줌
        thread4.setDaemon(true);
        thread4.start();



    } // end onCreate()

    public void mOnClick(View v){
        mainValue++;
        tvMainValue.setText("MainValue: " + mainValue);
    }



    // 이너클래스로
    class BackThread1 extends Thread{  // 여긴 작업쓰레드임  -> 여기서 메인쓰레드에 있는 핸들러에 메시지 보내기 위해 sendEmptyMessage() 사용.  작업쓰레드에서 메인의 변경을 핸들러 통해 메시지 보냄.  받는 쪽(메ㅔ인)에서 핸들러 생성하고 받음.
        @Override
        public void run() {
            while(true){
                // 방법1) 메인에서 생성된 Handler 객체의 sendEmptyMessage 를 통해 Message 전달
                backValue1++;
                handler1.sendEmptyMessage(1);

                // 방법2) 메인에서 생성된 Handler 객체의 postXXX() 통해 Runnable 객체 전달
                backValue2 += 2;
                handler2.post(new Runnable() {
                    @Override
                    public void run() { // Runnable 의 run()에서 메인 UI 접근
                        tvBackValue2.setText("BackValue2: " + backValue2);
                    }
                });


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    } // end BackThread1

    //---------------------------------------------------------------------------------------------
    // 방법1
    // '메인스레드' 에서 Handler 객체를 생성한다.
    // Handler 객체를 생성한 스레드 만이 다른 스레드가 전송하는 Message나 Runnable 객체를
    // 수신할수 있다.
    // 아래 생성된 Handler 객체는 handleMessage() 를 오버라이딩 하여
    // Message 를 수신합니다.
    Handler handler1= new Handler(){ // 여긴 메인쓰레드
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){ // Message id 가 1이면
                tvBackValue1.setText("BackValue1:" + backValue1); // 메인스레드의 UI변경
            }
        }
    };


    // 방법 2
    Handler handler2 = new Handler();  // 메인에 핸들러 생성

//--------------------------------------------------------------------------------------------------

    // 방법 3
    Handler handler3 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0){ // 0번 메시지에 반응
                // 그 메시지를 통해 받은 값을  메인 UI 에 출력하는 것임
                tvBackValue3.setText("BackValue3: " + msg.arg1);
            }
        }
    };

    // 방법4
    Handler handler4 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0){
                tvBackValue4.setText("BackValue4: " + msg.arg1);
            }
        }
    };

} // end Activity
//--------------------------------------------------------------------------------------------------------------------------------------



// 외부  클래스





// #3
// 작업스레드가 메인스레드와 완전히 분리되어 있어서 메인스레드에서 생성한 핸들러를 작업스레드에서
// 직접 참조 할수 없을때, Message 생성자 함수로 메세지를 생성하여 보내주면 됩니다.
// 가령 아래와 같이 메인스레드의 핸들러를 직접 사용할수 없는 분리된 작업 스레드

// 지금까지는 액티비티 클래스에서 동작했음
// 근데 다른 클래스에서 스레드 동작시키면?
class BackThread3 extends Thread{
    int backValue = 0;
    Handler handler;
    // 핸들러를 미리 만들어 놓구,

    BackThread3(Handler handler) {this.handler = handler;}

    // 메세지 생성하고ㅡ,

    // 핸들러에 sendMessage 에서 메시지 보내준다. 어디에?> 메인스레드에.
   //  이 핸들러는 생성할때 받은거

    // 0번 메시지로 보내는 핸들러 만들었으니까 메인 쪽에 가서도 0번 메시지를 ㅂ다는 핸들러 만든다.

    @Override
    public void run() {
        while(true){
            backValue += 3; // 3씩 증가

            Message msg = new Message(); //메세지 객체 생성
            msg.what = 0;  // 메세지 id // 메시지의 id 값이  아까 what 이라고 했어. 그걸 0 으로 만들고,
            msg.arg1 = backValue;   // 메시지의 정수값 arg1(정수값을 보내기 위한 효율적인 방법).. 에 백밸류값을 넣는다.

            handler.sendMessage(msg);   // 메인스레드의 핸들러에 메세지 보내기(메인스레드의 핸들러를 통해 메시지 객체

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // end run()
} // end BackThread3



// 방법4 : 메인스레드의 Handler 를 직접 사용할수 없는 분리된 작업 스레드
class BackThread4 extends Thread{
    int backValue = 0;
    Handler handler;

    BackThread4(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        while(true){
            backValue += 4;

            // obtain 메소드로 메세지 생성
            // obtain(Handler h, int what, int arg1, int arg2)
            // Message.obtain(..) ← 다양하게 오버로딩 되어 있슴
            Message msg = Message.obtain(handler, 0, backValue, 0);
            handler.sendMessage(msg);   // 메인스레드의 Handler 에 메세지 보내기

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    } // end run()
} // end BackThread4






