package com.lec.android.a004_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Main4Activity_radioButton extends AppCompatActivity {

    RadioGroup radioGrop;
    Button btnResult;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // 체크박스와는 달리
        // RadioButton 객체는 각각 생성하는 것이 아니라 RadioGroup 으로 선언하여 사용

        radioGrop = findViewById(R.id.rg);  // id 값도 rafactoring 됨.
        btnResult = findViewById(R.id.btnResult);
        tvResult = findViewById(R.id.tvResult);

        //버튼을 누르면 이니까 -> 리스너 장착
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 먼저 선택된 RadioButton 의 id 값을 RadioGroup 으로부터 가져온다.
                int id = radioGrop.getCheckedRadioButtonId(); // 라디오 그룹에 있는 getCheckedRadioButtonId() 메소드를 통해 버튼의 id 가져오기

                // 그 다음 위에서 얻은 id값으로부터  RadioButton 객체 생성한다.
                RadioButton rB = findViewById(id);

                // 생성한 RadioButton 으로부터 텍스트 뽑아서 -> TextView 화면에 보여준다(setText)
                tvResult.setText("결과: " + rB.getText());
            }
        });


        // 라디오버튼을 선택했을때도 위와 같은 동작하도록 해보자 -> 이건 라디오 그룹에서 setOn 한거
        radioGrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // checkedId ; 선택된 라디오버튼의 id
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rB = findViewById(checkedId);
                tvResult.setText("결과: " + rB.getText());
            }
        });


    } // end onCreate()


} // end Activity
