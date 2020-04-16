package com.lec.android.a005_image;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

// 안드로이드의 모~든 리소스(Resource) 로 사용하는 파일 들은
// 파일명 규칙
// - 대문자 불가!
// - 숫자 시작 불가!
// - 공백, 특수문자 불가
// - 특수문자는 _언더바만 가능.
public class MainActivity_setImageResource extends AppCompatActivity {

    // 정수값들을 담는 int 배열 만들자
    int[] imageId = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6};
    ImageView iv; // 이미지뷰 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv1); // 객체 생성

        // 이미지 뷰에 setImage리소스 장착해서 화면에 보여주기.
        // 레이아웃에 해도 되고 여기 onCreate() 에다가 이렇게 해도 되고.
        // res/drawble 폴더에 있는 이미지로  화면 창 띄우기
        iv.setImageResource(R.drawable.a1);

        // 이미지뷰 객체에 리스너 장착
        iv.setOnClickListener(new MyListener());

    } // end onCreate()


    class MyListener implements View.OnClickListener{  // myListener 객체 정의한 것  이걸 올라가서 onCreate에서 이미지뷰에 리스너 장착

        int i = 0;
        TextView tvResult = findViewById(R.id.tvResult);

        @Override
        public void onClick(View v) {
            i++; //일단 i 1증가. 배열엔 0~5번까지.  5번다음엔 다시 0번으로 돌아오도록 하는 동작 메소드 만들자.
            if(i == imageId.length) i = 0; // 끝까지 돌아갔을 때 다시 첫번째로 돌아오자

            iv.setImageResource(imageId[i]);
            tvResult.setText("이미지뷰: " + i);
        } // onClick()
    } // end MyListener
} // end Activity
