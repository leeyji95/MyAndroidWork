package com.lec.android.a015_web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    EditText etUrl;
    TextView tvResult;
    Button btnRequest, btnClear;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = findViewById(R.id.etUrl);
        tvResult = findViewById(R.id.tvResult);

        btnRequest = findViewById(R.id.btnWebView);
        btnClear = findViewById(R.id.btnBrowser);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String urlStr = etUrl.getText().toString();
                // Http request 는 별도의 Thread 로 진행해야 한다.!
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr);
                    }
                }).start();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText(""); // 내용 지우기
            }
        });

    } // end onCreate()


    // request 요청하는 메소드 작성
    public void request(String urlStr) {
        final StringBuilder sb = new StringBuilder();

        BufferedReader reader = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(5000); // 연결이 수립되는 시간. timeout 시간 설정. 경과하면 SocketTimeoutException 발생
                conn.setUseCaches(false); // 캐시 사용 안함
                conn.setRequestMethod("GET");   // GET 방식 request

                conn.setDoInput(true); // URLConnection 을 입력으로 사용하겟다. 이걸 받아옴.  이 값이 true. false 이면 출력용

                int responseCode = conn.getResponseCode(); // response  code 값. 성공하면 200

                if (responseCode == HttpURLConnection.HTTP_OK) { // 200 HTTP_OK
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) break;
                        sb.append(line + "\n");
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (conn != null) conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        handler.post(new Runnable() {
            @Override
            public void run() {

                tvResult.setText("응답결과 -> " + sb.toString());  // StringBuilder  -> final 설정해주고 오기

            }
        });


    } // end request


} // end Activity
