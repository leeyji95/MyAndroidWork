package com.lec.android.a007_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 화면(액티비티)전환 - 인텐트 사용 (인텐트 날린다?)
 *  1. 다음에 넘어갈 액티비티 준비(되어 있어야 하구)
 *  2. 메니페스트에 액티비티 등록(되어 있어야 하구요)
 *  3. Intent 객체 만들어서 startActivity() 사용한다
 *      - Intent 로 데이터 주고 받기 :  putExtra() _저장하고(주고) -> getXXXExtra()_끄집어내고(받고)
 *      - 주고받은 Object 는 Serializable 되어 있어야 한다
 *
 *  안드로이드는 startActivity() 로 새 액티비티를 시작하면
 *  적측형(stack) 구조로 액티비티가 운영된다.  ===> 계속 쌓이는 구조
 */


public class Main2Activity extends AppCompatActivity {

    EditText etName;
    EditText etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etName = findViewById(R.id.etName);  // View 를 상속받는 어떠한 객체라도 리턴할 수 있다.
        etAge = findViewById(R.id.etAge);  // activity_main2 여기 안에서 이런 id 를 가지고 있는 뷰객체를 찾아 그 객체로 리턴한다는 뜻!!!

        Button btnStarTwo = findViewById(R.id.btnStartTwo);
        btnStarTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),  MyTwo.class);
                                    // 현재 화면의 제어권자(현재 액티비티) // 다음 화면의 액티비티 클래스(객체) 지정

                // 데이터를 Intent 에 실어서 보내기
                // name : 데이터 형태로 보냄  --> 이것도 키밸류쌍으로 데이터 보냄
                intent.putExtra("num", 3); // num 이란 이름으로, 3이라는 값을 보냄
                intent.putExtra("num2", 7);
                intent.putExtra("long", 33L);
                intent.putExtra("msg", "안녕하세요");

                // 이름, 나이 --> Person 에 데이터 담은 뒤 Intent 에 실어 보내기
                Person p = new Person(
                        etName.getText().toString(),
                        Integer.parseInt(etAge.getText().toString().trim())
                );
                intent.putExtra("Person", p);  // Person객체(객체에 저장한 데이터)를 intent 에 담음 --> MyTwo 에서 받아야지.


                startActivity(intent);  // 다음화면으로 넘어간다.
            }
        });


        Button btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 액티비티 종료;
            }
        });

    } // end onCreate()


} // end Activity
