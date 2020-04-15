package com.lec.android.a003_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    TextView textView; // '계산기'  문자열
    EditText edit; // number 타입 숫자 입력..
    String number; //  내가 입력한 숫자 저장. 즉 숫자->연산자->숫자 에서 첫번째 숫자 저장할 변수

    Button btn_division, btn_multiple, btn_plus, btn_sub, equal;


    // 과제: 계산기 앱 만들기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // textView, edit 텍스트 객체
        textView = findViewById(R.id.calculator);
        edit = findViewById(R.id.edit);

        // 버튼 객체 가져오기
        btn_division = findViewById(R.id.bttn_division);
        btn_multiple = findViewById(R.id.bttn_multiple);
        btn_plus = findViewById(R.id.bttn_plus);
        btn_sub = findViewById(R.id.bttn_sub);
        equal = findViewById(R.id.bttn_equal);

        btn_division.setOnClickListener(myListener);
        btn_multiple.setOnClickListener(myListener);
        btn_plus.setOnClickListener(myListener);
        btn_sub.setOnClickListener(myListener);
        equal.setOnClickListener(myListener);

        Button clear = findViewById(R.id.bttn_Clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // C 버튼 클릭 시
                number = "";    // number 에는 빈 문자 저장되고,
                edit.setText("0");    // EditText 엘리먼트에 있는 Text 를 0 문자로 화면에 출력. ->
            }
        });

    }  // end onCreate()

    // 연산자에 대한 익명 내부 클래스
    Button.OnClickListener myListener = new Button.OnClickListener(){

        int value;
        final int DIVISION = 0;
        final int MULTIPLE = 1;
        final int PLUS = 2;
        final int SUB = 3;


        @Override
        public void onClick(View v) {

            switch(v.getId()) {
                case(R.id.bttn_division) :
                    number = edit.getText().toString(); // 첫번째 숫자 저장
                    edit.setText(""); // 비워주고
                    value = DIVISION;
                    edit.setText(btn_division.getText().toString()); // 나눗셈 텍스트 뽑음..
                    break;
                case (R.id.bttn_multiple):
                    number = edit.getText().toString();
                    edit.setText("");
                    value = MULTIPLE;
                    edit.setText(btn_multiple.getText().toString());
                    break;
                case(R.id.bttn_plus):
                    number = edit.getText().toString();
                    edit.setText("");
                    value = PLUS;
                    edit.setText(btn_multiple.getText().toString());
                    break;
                case (R.id.bttn_sub):
                    number = edit.getText().toString();
                    edit.setText("");
                    value = SUB;
                    edit.setText(btn_sub.getText().toString());
                    break;
                case(R.id.bttn_equal):
                    if(value == DIVISION){
                        edit.setText(Integer.parseInt(number) / Integer.parseInt(edit.getText().toString()));
                    } else if(value == MULTIPLE) {
                        edit.setText(Integer.parseInt(number) * Integer.parseInt(edit.getText().toString()));
                    } else if(value == PLUS){
                        edit.setText(Integer.parseInt(number) + Integer.parseInt(edit.getText().toString()));
                    } else if(value == SUB){
                        edit.setText(Integer.parseInt(number) - Integer.parseInt(edit.getText().toString()));
                    }

                    number = edit.getText().toString();  // 마지막에 클릭한 숫자가 들어가도록 덮어씌운다. -> 연속 연산 가능
                    break;

            } // end switch
        } // end onClick()

    }; // end class myListener

    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bttn_0: edit.setText(edit.getText().toString()); break;
            case R.id.bttn_1: edit.setText(edit.getText().toString()); break;
            case R.id.bttn_2 : edit.setText(edit.getText().toString()); break;
            case R.id.bttn_3 : edit.setText(edit.getText().toString()); break;
            case R.id.bttn_4 : edit.setText(edit.getText().toString()); break;
            case R.id.bttn_5 : edit.setText(edit.getText().toString()); break;
            case R.id.bttn_6 : edit.setText(edit.getText().toString()); break;
            case R.id.bttn_7 : edit.setText(edit.getText().toString()); break;
            case R.id.bttn_8 : edit.setText(edit.getText().toString()); break;
            case R.id.bttn_9 : edit.setText(edit.getText().toString()); break;

        }




    } // end onClick()


} // end class
