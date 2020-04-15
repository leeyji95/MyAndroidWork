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
    EditText edit; // 첫번째 숫자 입력..
    String number; //  내가 입력한 숫자 저장. 즉 숫자->연산자->숫자 에서 첫번째 숫자 저장할 변수

    Button btn_division, btn_multi, btn_plus, btn_sub, btn_result;


    // 과제: 계산기 앱 만들기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // textView, edit 텍스트 객체
        textView = findViewById(R.id.calculator);
        edit = findViewById(R.id.edit);

        // 버튼 객체 가져오기
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

        Button clear = findViewById(R.id.btn_Clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // C 버튼 클릭 시
                number = "";    // number 에는 빈 문자 저장되고,
                edit.setText("");    // EditText 엘리먼트에 있는 Text 를 0 문자로 화면에 출력. ->
            }
        });

//        Button btn_0 = findViewById(R.id.btn_0);
//    Button btn_1 = findViewById(R.id.btn_1);
//    Button btn_2 = findViewById(R.id.btn_2);
//    Button btn_3 = findViewById(R.id.btn_3);
//    Button btn_4 = findViewById(R.id.btn_4);
//    Button btn_5 = findViewById(R.id.btn_5);
//    Button btn_6 = findViewById(R.id.btn_6);
//    Button btn_7 = findViewById(R.id.btn_7);
//    Button btn_8 = findViewById(R.id.btn_8);
//    Button btn_9 = findViewById(R.id.btn_9);
//
//    class MyListener implements View.OnClickListener{ // inner class
//
//        String number; // 매개변수 받아줌
//        public MyListener(String name){this.number=number;}
//
//
//        @Override
//        public void onClick(View v) {
//            edit.setText(edit.getText().append(number));  // 기존의 텍스트에 name 을 추가함. 버튼 누를때 마다 그 버튼의 name 을 추가할 것 임.
//        }
//    } // end class
//
//    // 동일한 동작을 하는 버튼 메소드는 하나의 클래스로 정의한 다음에 쓰는 것이 낫지.
//        btn_0.setOnClickListener(new MyListener("0"));
//        btn_1.setOnClickListener(new MyListener("1"));
//        btn_2.setOnClickListener(new MyListener("2"));
//        btn_3.setOnClickListener(new MyListener("3"));
//        btn_4.setOnClickListener(new MyListener("4"));
//        btn_5.setOnClickListener(new MyListener("5"));
//        btn_6.setOnClickListener(new MyListener("6"));
//        btn_7.setOnClickListener(new MyListener("7"));
//        btn_8.setOnClickListener(new MyListener("8"));
//        btn_9.setOnClickListener(new MyListener("9"));

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
                case(R.id.btn_division) :
                    number = edit.getText().toString(); // 첫번째 숫자 저장
                    edit.setText(""); // 비워주고
                    value = DIVISION;
                    edit.setText(btn_division.getText().toString()); // 나눗셈 텍스트 뽑음..
                    break;
                case (R.id.btn_multi):
                    number = edit.getText().toString();
                    edit.setText("");
                    value = MULTIPLE;
                    edit.setText(btn_multi.getText().toString());
                    break;
                case(R.id.btn_plus):
                    number = edit.getText().toString();
                    edit.setText("");
                    value = PLUS;
                    edit.setText(btn_multi.getText().toString());
                    break;
                case (R.id.btn_sub):
                    number = edit.getText().toString();
                    edit.setText("");
                    value = SUB;
                    edit.setText(btn_sub.getText().toString());
                    break;
                case(R.id.btn_result):
                    if(value == DIVISION){
                        edit.setText("" + (Integer.parseInt(number) / Integer.parseInt(edit.getText().toString())));
                    } else if(value == MULTIPLE) {
                        edit.setText("" + (Integer.parseInt(number) * Integer.parseInt(edit.getText().toString())));
                    } else if(value == PLUS){
                        edit.setText("" + (Integer.parseInt(number) + Integer.parseInt(edit.getText().toString())));
                    } else if(value == SUB){
                        edit.setText("" + (Integer.parseInt(number) - Integer.parseInt(edit.getText().toString())));
                    }

                    edit.getText();  // 마지막에 클릭한 숫자가 들어가도록 덮어씌운다. -> 연속 연산 가능
                    break;

            } // end switch
        } // end onClick()

    }; // end class myListener

    public void onClick(View v) {
        int number;
        switch (v.getId()){
            case R.id.btn_0 : edit.setText("0"); break;
            case R.id.btn_1 : edit.setText("1"); break;
            case R.id.btn_2 : edit.setText("2"); break;
            case R.id.btn_3 : edit.setText("3"); break;
            case R.id.btn_4 : edit.setText("4"); break;
            case R.id.btn_5 : edit.setText("5"); break;
            case R.id.btn_6 : edit.setText("6"); break;
            case R.id.btn_7 : edit.setText("7"); break;
            case R.id.btn_8 : edit.setText("8"); break;
            case R.id.btn_9 : edit.setText("9"); break;


        }
    } // end onClick()





} // end class
