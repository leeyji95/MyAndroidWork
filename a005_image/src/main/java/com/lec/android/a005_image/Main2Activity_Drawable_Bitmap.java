package com.lec.android.a005_image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class Main2Activity_Drawable_Bitmap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView iv1 = findViewById(R.id.iv1);
        ImageView iv2 = findViewById(R.id.iv2);
        ImageView iv3 = findViewById(R.id.iv3);

        // 방법1. 프로젝트 내의 res/drawable 폴더에 있는 파일을 보여주는 방법
        iv1.setImageResource(R.drawable.a2);


        // 방법2. Drawable 객체를 이용해서 보여주는 방법   // deprecated 됨.
//        Drawable drawable = getResources().getDrawable(R.drawable.a3);  // getResources() : 현재 프로젝트의 리소스에 접근할 수 있는 객체 리턴.
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.a3);
                            // ※ ContextCompat : API23 부터 추가
        // 안드로이에는 이런거 엄청 많음.
        iv2.setImageDrawable(drawable);
        /*
        getApplicationContext()
        현재 이 코드가 실행되고 있는 객체는 어느객체냐?
        현재 Activity 에서 실행되고 있으므로, 액티비티 객체가 온다.
        현재 실행되고 있는 객체를 끌고 들어오는 메소드가 getApplicationContext() 메소드다.
         */


        // 방법3 bitmap 객체를 이용해서 보여주는 방법
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a4);  // 리소스에서, a4번쨰 이미지 가져온다.  (매개변수 뜻)
        iv3.setImageBitmap(bitmap);

    }// end onCreate()

} // end Activity
