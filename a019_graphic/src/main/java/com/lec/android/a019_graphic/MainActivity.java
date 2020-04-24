package com.lec.android.a019_graphic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);  // XML 레이아웃 사용하지 않고 내가 만든 뷰로 화면에 띄우기

        // 사용자가 작성한 View 로 액티비티 레이아웃 세팅
        MyView v = new MyView(MainActivity.this);
        setContentView(v); // 내가 만든 뷰를 액티비티의 뷰로 설정하는 것(방법) ! !


    } // end onCreate


} // end Activity


class MyView extends View{// 내가 직접 화면 만들 수 있따

    public MyView(Context context) {
        super(context);
    }

    // 화면이 업데이트 될때, '그려주기'
    @Override
    protected void onDraw(Canvas canvas) { // Canvas : 그림을 그릴 대상 객체
        Paint paint = new Paint(); // 화면에 그려줄 도구 세팅
        paint.setColor(Color.RED);  // 색상 지정

        setBackgroundColor(Color.GREEN);

        // 사각형의 좌상, 우하 좌표 (100,100) (200, 200) 대각선 사각형 으로 이해하면 쉬움
        canvas.drawRect(100, 100, 200, 200, paint); //

        // 원의 중심 x, y, 반지름
        canvas.drawCircle(300, 300, 40, paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10f); // 선의 굵기
        // 직선
        canvas.drawLine(400, 100, 800, 150, paint);

        // Path 자취(?) 만들기
        Path path = new Path(); // android.graphic.Path
        path.moveTo(20, 100); // 자취 이동
        path.lineTo(100, 200); // 직선
        path.cubicTo(150, 30, 300, 400,400, 200); // 곡선 (베지어 곡선)

        paint.setColor(Color.BLACK);
        canvas.drawPath(path, paint);

    }  // end onDraw 
}