package com.lec.android.a003_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView calc; // '계산기'
    EditText edit; // 텍스트 입력 공간

    String num; //  내가 클릭한 숫자 저장. 즉 숫자->연산자->숫자 에서 첫번째 숫자 저장할 변수

    Button btn_division, btn_multi, btn_plus, btn_sub, btn_result;


    // 과제: 계산기 앱 만들기

    // 액티비티(화면) 창에 내가 만들어 놓은 것들 띄우기  -> 그게 TextView, EditText, Button 등 객체생성이다
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // textView, edit 텍스트 객체
        calc= findViewById(R.id.calculator);
        edit = findViewById(R.id.edit);

        // 버튼 객체 생성
        btn_division = findViewById(R.id.btn_division);
        btn_multi = findViewById(R.id.btn_multi);
        btn_plus = findViewById(R.id.btn_plus);
        btn_sub = findViewById(R.id.btn_sub);
        btn_result = findViewById(R.id.btn_result);

        btn_division.setOnClickListener(myListener);
        btn_multi.setOnClickListener(myListener);
        btn_plus.setOnClickListener(myListener);
        btn_sub.setOnClickListener(myListener);
        btn_result.setOnClickListener(myListener);

        // 버튼 C 클릭 시 -> clear 동작
        Button clear = findViewById(R.id.btn_Clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // C 버튼 클릭 시
                num = "";               // clickedNum 에는 빈 문자 저장되고,
                edit.setText("");    // EditText 에 빈문자 띄움.(아무것도 안보임)
            }
        });
    }  // end onCreate()


    //  만들어 놓은 버튼들을 클릭했을 때 발생하는 동작들 정의 -> 그래서 onClick() 메소드 사용
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_0 : edit.setText(edit.getText().toString() + 0); break;  // 버튼 0 누르면 -> EditText 창에 0 나오는 동작.
            case R.id.btn_1 : edit.setText(edit.getText().toString() + 1); break;
            case R.id.btn_2 : edit.setText(edit.getText().toString() + 2); break;
            case R.id.btn_3 : edit.setText(edit.getText().toString() + 3); break;
            case R.id.btn_4 : edit.setText(edit.getText().toString() + 4); break;
            case R.id.btn_5 : edit.setText(edit.getText().toString() + 5); break;
            case R.id.btn_6 : edit.setText(edit.getText().toString() + 6); break;
            case R.id.btn_7 : edit.setText(edit.getText().toString() + 7); break;
            case R.id.btn_8 : edit.setText(edit.getText().toString() + 8); break;
            case R.id.btn_9 : edit.setText(edit.getText().toString() + 9); break;
        } // end switch

    } // end onClick()



    // 연산자 익명 클래스
    Button.OnClickListener myListener = new Button.OnClickListener(){

        int operator;  // 연산자 담을 변수
        final int DIVISION = 0;
        final int MULTIPLE = 1;
        final int PLUS = 2;
        final int SUB = 3;


        @Override
        public void onClick(View v) {

            switch(v.getId()) {  // 최상위 클래스인 View 객체로부터 id 값 받기
                case(R.id.btn_division) :
                    num = edit.getText().toString(); // 첫번째 숫자 저장
                    edit.setText(""); // EditText  비워주고
                    operator = DIVISION;
//                    edit.setText(btn_division.getText().toString()); // 나눗셈 기호  뽑음..
                    break;
                case (R.id.btn_multi):
                    num = edit.getText().toString();
                    edit.setText("");
                    operator = MULTIPLE;
//                    edit.setText(btn_multi.getText().toString());
                    break;
                case(R.id.btn_plus):
                    num = edit.getText().toString();
                    edit.setText("");
                    operator = PLUS;
//                    edit.setText(btn_plus.getText().toString());
                    break;
                case (R.id.btn_sub):
                    num = edit.getText().toString();
                    edit.setText("");
                    operator = SUB;
//                    edit.setText(btn_sub.getText().toString());
                    break;
                case(R.id.btn_result):
                    if(operator == DIVISION){
                        edit.setText("" + (Long.parseLong(num) / Long.parseLong(edit.getText().toString())));
                    } else if(operator == MULTIPLE) {
                        edit.setText("" + (Long.parseLong(num) * Long.parseLong(edit.getText().toString())));
                    } else if(operator == PLUS){
                        edit.setText("" + (Long.parseLong(num) + Long.parseLong(edit.getText().toString())));
                    } else if(operator == SUB){
                        edit.setText("" + (Long.parseLong(num) - Long.parseLong(edit.getText().toString())));
                    }
                    edit.getText();  // EditText 에 결과값 넣어줌. ->  연속 연산 가능
                    break;

            } // end switch
        } // end onClick()

    }; // end class myListener



} // end class
