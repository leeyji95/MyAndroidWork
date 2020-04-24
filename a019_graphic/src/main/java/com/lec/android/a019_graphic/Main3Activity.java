package com.lec.android.a019_graphic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main3);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(new MyView3(this)); // LineaLayout 의 child 로 들어감   (View 를 추가함. 레이아웃에)
        setContentView(ll);


    }
}


class MyView3 extends View {

    Paint paint= new Paint()   ;
    Path path = new Path();

    public MyView3(Context context) {
        super(context);
        paint.setStyle(Paint.Style.STROKE);// 실선으로 그려줌
        paint.setStrokeWidth(10f); // 선 두께
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y); // moveTo 이므로 path 를 그리지 말고, 위치 이동만 함.
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);  // path 에 선 그리기
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate(); // 화면 다시 그려준다

        return true;
    }
}