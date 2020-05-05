package com.lec.android.a006_widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class Main4Activity_RatingBar extends AppCompatActivity {

    RatingBar rb;
    TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        rb = findViewById(R.id.ratingBar);
        tvResult = findViewById(R.id.tvResult);

        // RatingBar 의 값이 변할 때 호출되는 콜백
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() { // setOn~(여기에 OnRating ~ 객체 와야함. 항상 그래왔음)
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) { // 사용자가 조작하면 true
                tvResult.setText("rating: " + rating); // rating : 최대 4까지 설정해놓았지?
            }
        });
    } // end onCreate()
} // end Activity
