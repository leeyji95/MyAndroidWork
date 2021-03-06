package com.lec.android.a014_dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 대화상자 객체  ...  다이얼로그 만을 위한 레이아웃 만들고 오기
    Dialog dlg1;
    ImageView ivDlgBanner;
    Button btnDlgEvent;

    Dialog dlg2;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 이건 현재 액티비티의 setContentView

        tvResult = findViewById(R.id.tvResult);

        // Dialog 클래스로 다이얼로그 객체 생성 및 세팅
        dlg1 = new Dialog(this); // 다디얼로그 객체 생성
        dlg1.setContentView(R.layout.dialog_layout11); // 다이얼로그 화면 등록   // 다이얼로그의 setContentView 에서 레이아웃 아이디 끄집어 내기

        // Dialog  안의 View 객체들 얻어오기
        ivDlgBanner = dlg1.findViewById(R.id.ivDlgBanner);
        btnDlgEvent = dlg1.findViewById(R.id.btnDlgEvent);

        btnDlgEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivDlgBanner.setImageResource(R.drawable.face01);
                Toast.makeText(getApplicationContext(), "다이얼로그 버튼을 눌렀어요", Toast.LENGTH_SHORT).show();
            }
        });

        // 현재 Activity 에 Dialog 등록하기
        dlg1.setOwnerActivity(MainActivity.this); // 누구의 다이얼로그인지 명시해야함. -> 현재 액티비티의 다이얼로그
        dlg1.setCanceledOnTouchOutside(true); // 다이얼로그 바깥 영역 클릭시 (혹은 back 버튼 클릭 시) hide 상태가 됨. -> boolean 타입 리턴// 바깥쪽 클릭시 어떻게 동작할래?
            // 종료할 것인지 여부, true : 종료됨, false : 종료안됨.

        dlg1.show();

        // #2 두번째 다이얼로그
        dlg2 = new Dialog(this);
        dlg2.setContentView(R.layout.dialog_layout12);
        dlg2.setOwnerActivity(MainActivity.this);
        dlg2.setCanceledOnTouchOutside(false);

        final EditText etName = dlg2.findViewById(R.id.etName);
        Button btnOk = dlg2.findViewById(R.id.btnOk);
        Button btnCancel = dlg2.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = etName.getText().toString();
                tvResult.setText(str);
//                etName.setText("");
                dlg2.dismiss(); // 다이얼로그 객체 제거
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg2.dismiss();
            }
        });


        // 다이얼로그가 등장할 때 호출되는 메소드
        dlg2.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                etName.setText("");
            }
        });


    } // end onCreate()


//    public void showDialog1(View v){
//        dlg1.show(); // 다이얼로그 띄우기 (보이게)
//    }


    public void showDialog2(View v){
        dlg2.show();
    }


} // end Activity
