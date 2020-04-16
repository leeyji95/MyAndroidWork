package com.lec.android.a004_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity_calculatorReview extends AppCompatActivity {

    TextView tvResult;
    EditText op1, op2;
    Button add, sub, mul, div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvResult = findViewById(R.id.tvResult);

        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);

        add = findViewById(R.id.btnPlus);
        sub = findViewById(R.id.btnMinus);
        mul = findViewById(R.id.btnMul);
        div = findViewById(R.id.btnDiv);


        op1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // hasFocus: true 포커스 받은 경우  false: 포커스 잃은 경우
                if (hasFocus) {
                    ((EditText) v).setBackgroundColor(Color.CYAN);
                } else {
                    ((EditText) v).setBackgroundColor(Color.parseColor("#00000000")); // 포커스 이동하면 색을 완전히 잃음
                }
            }
        });



        Button.OnClickListener myListener = new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                String oper1 = op1.getText().toString();
                String oper2 = op2.getText().toString();

                int a, b;

                if(oper1 != null && !oper1.trim().equals("")){
                    a = Integer.parseInt(oper1);
                } else {
                    a = 0;
                }

                if(oper2 != null && !oper2.trim().equals("")){
                    b = Integer.parseInt(oper2);
                } else {
                    b = 0;
                }

                switch (v.getId()){
                    case R.id.btnPlus:
                        tvResult.setText((a + b) + ""); // a + b
                        break;
                    case R.id.btnMinus:
                        tvResult.setText((a - b) + ""); // a - b
                        break;
                    case R.id.btnDiv:
                        tvResult.setText((a / b) + ""); // a / b
                        break;
                    case R.id.btnMul:
                        tvResult.setText((a * b) + ""); // a * b
                        break;
                } // end switch
            }
        };




    } // end onCreate()

}
