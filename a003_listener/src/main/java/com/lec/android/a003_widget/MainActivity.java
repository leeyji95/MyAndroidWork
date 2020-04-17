package com.lec.android.a003_widget;
// 리스너 지정하는 방법
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // 멤버변수
    TextView tvResult;
    EditText et;

    // 방법5 : 액티비티가 implement
    // 지금 메인 액티비티 작업중.  메인에다가 View.OnClickListener implements
    @Override
    public void onClick(View v) { // 메인에서 Alt + Insert 누르고 메소드 추가한 것임
        Log.d("myapp", "clear 버튼이 클릭되엇습니다");
        tvResult.setText("Clear 버튼이 클릭되었습니다");
        et.setText("");
    }

    // onCreate()
    // 액티비티(화면 객체)가 생성될 떄 호출되는 메소드
    // 액티비티 초기화 하는 코드 작성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn1);  // 버튼 객체를 가져오는
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);

        tvResult = findViewById(R.id.tvResult);
        et = findViewById(R.id.et);
        final LinearLayout ll = findViewById(R.id.ll);  // ll 변수 선언  ---> effective final 로 선언해준다.  멤버변수  null로 초기화하고 find~ 해주면 -> 지역변수 에러. effective final로 선언해주어야 한다.

        // 방법2 : 익명클래스 사용
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 클릭되었을 때 호출되는 메소드  (콜백 메소드) : 우린 여기만 작성하면 된다. (클릭되었을떄에만 수행하라)
                Log.d("myapp", "버튼2 가 클릭 되었습니다");
                tvResult.setText("버튼2가 클릭됨");
                tvResult.setBackgroundColor(Color.YELLOW);
            }
        });

        // 다양한이벤트, 다양한 리스너 등록가능'
        btn2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { // 롱클릭 발생시 수행하는 콜백 메소드 (길게 꾹~ 눌렀을때)
                Log.d("myapp", "버튼2가 롱클릭 되었습니다");
                tvResult.setText("버튼2가 롱클릭 되었습니다 ");
                tvResult.setBackgroundColor(Color.CYAN);

                // return false; // false 리턴하면 이벤트가 click 까지 간다.
                return true; // true 리턴하면 이벤트가 Long click으로 소멸(consume)된다.
            }
        });


        // 방법3 : Lambda expression 사용하기
        // Android Studio  의 세팅 필요! ppt 참조
        btn3.setOnClickListener((v) -> { // onClick(View v)
            Log.d("myapp", "버튼3 가 클릭되었슈");
            tvResult.setText("버튼3 클릭됨");
            ll.setBackgroundColor(Color.rgb(164, 198, 57));
        });


        // 방법4 : implemnet 한 클래스 사용

        Button btnA = findViewById(R.id.btnA);
        Button btnB = findViewById(R.id.btnB);
        Button btnC = findViewById(R.id.btnC);
        Button btnD = findViewById(R.id.btnD);
        Button btnE = findViewById(R.id.btnE);
        Button btnF = findViewById(R.id.btnF);

        // 6개의 버튼에 대해서 각각 setOnClick 하는것보다 하나만 만들어놓고 쓰자

        class MyListener implements View.OnClickListener{ // inner class

            String name; // 매개변수 받아줌
            public MyListener(String name){this.name=name;}


            @Override
            public void onClick(View v) {

                String tag = (String)v.getTag(); // view 객체:  최상위클래스니까  다형성으로 강제형변환해서 사용해야한다.
                String text = (String)((Button)v).getText(); // getText() 는 Charsequence 객체 리턴한다.
                // 지금 태그랑 텍스트 끌고 들어온 것임.

                String msg = String.format("%s 버튼 %s 이 클릭[%s]", name, text, tag);
                Log.d("myapp", msg);
                tvResult.setText(msg);
                et.setText(et.getText().append(name));  // 기존의 텍스트에 name 을 추가함. 버튼 누를때 마다 그 버튼의 name 을 추가할 것 임.

                // 이와 같은 클릭 리스너 메소드를 만들고 이를 장착할 것임.

            }
        } // end class

        // 동일한 동작을 하는 버튼 메소드는 하나의 클래스로 정의한 다음에 쓰는 것이 낫지.
        btnA.setOnClickListener(new MyListener("안녕1"));
        btnB.setOnClickListener(new MyListener("안녕2"));
        btnC.setOnClickListener(new MyListener("안녕3"));
        btnD.setOnClickListener(new MyListener("안녕4"));
        btnE.setOnClickListener(new MyListener("안녕5"));
        btnF.setOnClickListener(new MyListener("안녕6"));


        // 방법5 : 액티비티가 implement
        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this); // 구현된 나 자신을 넣음..

        // 연습
        // + - 버튼 누르면 tvResult 의 글씨가 커지거나 작아지게 하기
        // getTextSize() : float 값 리턴

        // 버튼 객체 가져오기
        Button btnInc = findViewById(R.id.btnInc);
        Button btnDec = findViewById(R.id.btnDec);
        btnInc.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                float size = tvResult.getTextSize(); // float 리턴
                Log.d("myapp", "글꼴사이즈" + size);
                tvResult.setTextSize(0, size + 1);
            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float size = tvResult.getTextSize(); // float 리턴
                Log.d("myapp", "글꼴사이즈" + size);
                tvResult.setTextSize(0, size - 1);
            }
        });


    } // onCreate()



    // 방법1 : 레이아웃 xml  의 onXXX 속성으로 지정
    public void changeText(View v){
        // 대문자 V로 시작하는거 : Alt + Enter :  exception 이나import 해주는 단축키
        // Log.d(tag, message)
        // Log 창의 Debug 메세지로 출력
        Log.d("myapp", "버튼 1이 클릭되었습니다");
        tvResult.setText("버튼1 이 클릭 되었습니다");
    } // end changeText()

}  // main class()
