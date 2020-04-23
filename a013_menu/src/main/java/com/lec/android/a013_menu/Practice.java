package com.lec.android.a013_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Practice extends AppCompatActivity {

    WebView wv;
    EditText etUrl;
    Button btnWebView, btnBrowser;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        etUrl = findViewById(R.id.etUrl);
        wv = findViewById(R.id.wv);
        btnWebView = findViewById(R.id.btnWebView);
        btnBrowser = findViewById(R.id.btnBrowser);

        // WebView 세팅
        wv.getSettings().setJavaScriptEnabled(true);  // 자바스크립트 사용여부
        btnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = etUrl.getText().toString();
                wv.loadUrl(url); // 웹페이지 url 읽어오기
                wv.setWebChromeClient(new WebChromeClient()); // 웹뷰 객체 사용하여 크롬를라이언트 메소드 호출.  이거해야 알림팝업창 뜸
                wv.setWebViewClient(new WebViewClient());  // 이거 해야 웹페이지 내에서 다른페이지로 이동 가는ㅇ
            }
        });


        // 브라우저 가동
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = etUrl.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); // 사용자에게 데이터를 보여줌.ACTION_VIEW
                startActivity(intent);
            } // Uri : Uniform resource of identifier  ...  URL 보다 상위 개념..

        });



    } // end onCreate


    // 키보드 내려가기


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()){
            wv.goBack();
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }
} // end Activity
