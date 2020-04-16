package com.lec.android.a005_image;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
/**
 *  인터넷 상의 이미지 보여주기
 *      1. 권한을 획득한다 (인터넷에 접근할수 있는 권한을 획득한다)  - 메니페스트 파일
 *      2. Thread 에서 웹의 이미지를 받아온다 - honeycomb(3.0) 버젼 부터 바뀜
 *      3. 외부쓰레드에서 메인 UI에 접근하려면 Handler 를 사용해야 한다.
 *
 *       초창기때 안드로이드 메인에서 인터넷 요청했는데, 이걸 별도의 쓰레드로 만들지 않았어. 그래서 이거 받아오기까지 사용자 아무것도 못했어.
 *        그래서 안드로이드에서는  인터넷 에서 받아오는 거 무조건 쓰레드로 처리함.
 *       고로,  별도의 쓰레드에서 인터넷 받아야와야 한다.
 *
 */



public class Main4Activity extends AppCompatActivity {

    // 이미지 URl, 반드시 https:// 이어야 한다.
    String imgUrl = "https://developer.android.com/studio/images/studio-icon-stable.png";

    ImageView iv1;
    TextView tvUrl;

    Handler handler = new Handler(); // android.os 에 있는거 // 외부 쓰레드에서 메인 UI 화면에 그릴 때 사용


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        iv1 = findViewById(R.id.iv1);
        tvUrl = findViewById(R.id.tvUrl);


        // Thread t = new Thread(Runnable);
        Thread t = new Thread(new Runnable() { // 쓰레드 생성
            @Override
            public void run() {

                // "url" 문자열로 URL 객체 만들고, 여기에 inputStream 뽑고 여기서 안드로이드는 Bitmap 객체에 뽑는다.!
                // Bitmap < InputStream <- URL <- "url"
                try {
                    // Thread 없이 수행하면
                    // android.os.NetworkOnMainThreadException 발생
                    URL url = new URL(imgUrl);
                    InputStream in =  url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(in);

                    // 비트맵 가지고 이미지뷰에 장착하면 되겠네
                    // 근데!!! Handler 없이 사용하면
                    // CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
//                    iv1.setImageBitmap(bm);

                    handler.post(new Runnable() { // handler 에 runnable 객체 보낸다.
                        @Override
                        public void run() {
                            // 외부쓰레드에서 메인UI 에 접근할때는
                            // 반드시 Handler 객체 사용.
                            // ※ Handler를 사용하지 않으면 어떻게 되는지 보자
                            iv1.setImageBitmap(bm);
                            tvUrl.setText(imgUrl);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start(); // 쓰레드 시작

    } // end onCreate()


} // end Activity
