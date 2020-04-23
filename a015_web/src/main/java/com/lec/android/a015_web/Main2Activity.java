package com.lec.android.a015_web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

// #1 WebView 사용하여 웹 페이지 보여주기(브라우저 처럼 보기)
// #2 묵시적 Intent 사용하여 웹 브라우져 띄우기()   내 앱에 있는 브라우저 앱이 뜰 것. 기본 액티비티가 뜨는 것이 묵시적 Intent.
public class Main2Activity extends AppCompatActivity {

    WebView wv;
    EditText etUrl;
    Button btnWebView, btnBrowser;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etUrl = findViewById(R.id.etUrl);
        wv = findViewById(R.id.wv);
        btnWebView = findViewById(R.id.btnWebView);
        btnBrowser = findViewById(R.id.btnBrowser);

        // WebView 세팅
        wv.getSettings().setJavaScriptEnabled(true); // JavaScript 사용여부: 디폴트 false
        btnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = etUrl.getText().toString().trim();
                wv.loadUrl(url);   // 웹페이지 url 으로 읽어오기
                wv.setWebChromeClient(new WebChromeClient());   // 안하면 alert() 같은 알리창 안뜸.   .. 브라우저에서뜨는 알림팝업창 안뜬다..
                wv.setWebViewClient(new WebViewClient()); // 안하면 웹페이지 내부에서 다른 페이지 이동 못함. // 안하면 html내부에서 다른 페이지로 이동을 할 수가 없다.

            }
        });

        //  브라우저 가동
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = etUrl.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); //   뒤에 들어가는 특정 액티비티 형태(알 수 없음)에 따라 앱이 다르게 뜬다.
                startActivity(intent);     // Intent 클래스 안의 ACTION_VIEW 라는 action 구조가 있는것.  ==> 사용자에게 화면을 보여주는 역할을 함.
            }
        });// Uri : Uniform resource of identifier  ...  URL 보다 상위 개념..


    } // end onCreate

    // 이전화면 구현(키보드 BackSpace 누르면 뒤로 돌아가기)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if((keyCode ==  KeyEvent.KEYCODE_BACK) && wv.canGoBack()){
            wv.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



}// end Activity

