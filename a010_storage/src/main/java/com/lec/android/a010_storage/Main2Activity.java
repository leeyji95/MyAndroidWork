package com.lec.android.a010_storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 외부 메모리 (External Memory 혹은 Shared storage)
 *      사용자 영역 에 저장 ( sdcard 같은 외장 메모리를 의미하는게 아니다)    // 사용자 영역을 의미하는것이다. 그러면 다른 앱에서 사용가능하다.  (외장메모리가 아님)
 *      메모리가 장착(mount)되어 있어야 사용 가능
 *      모든 앱에서 접근 가능 (공유 가능)
 *
 * 외부 메모리에 파일 읽기/쓰기
 *      1. 외부 메모리 장치가 있는지 확인해야한다 :  getExternalStorageState()
 *      2-1 AndroidManifest.xml 외부메모리 저장권한을 선언해야 함  (API 23 이전 버젼)
 *              WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE
 *      2-2 API 23+ (마시멜로): 새로운 권한 획득 방법 사용.
 *          Run-time 에 onRequestPermissionsResult() 사용 해야 한다!!!
 *          https://developer.android.com/training/permissions/requesting.html
 *
 *     3. 읽기/쓰기 경로를 지정한다
 */

public class Main2Activity extends AppCompatActivity {
    EditText etInput;
    Button btnSave, btnRead;
    TextView tvResult;

    // permission 관련
    final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etInput = findViewById(R.id.etInput);
        btnRead = findViewById(R.id.btnRead);
        btnSave = findViewById(R.id.btnSave);
        tvResult = findViewById(R.id.tvResult);


        // 위험권한  획득
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission((String.valueOf(PERMISSIONS))) == PackageManager.PERMISSION_DENIED){
            requestPermissions(PERMISSIONS, REQUEST_CODE); //권한요청하기
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkExternalStorage()) return; // 외부 메모리 사용 못하면 종료

                String data = etInput.getText().toString();

//                Environment.getExternalStoragePublicDirectory(); API29 부터 deprecated됨
                try {
                   // 저장 경로 가져오기    외부 저장경로를 받아와야 한다.   안드로이드 서비스 이용해서 받아와야 함.ㅏ
                    File path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File f = new File(path, "external.txt"); // 경로/파일명
                    FileWriter writer = new FileWriter(f, false); // 두번째 매개변수 : true 이면 append 모드로 생성하게 된다. false 이면? write 모드. 이거 실행할떄마다 기존 파일 모두 지워짐.
                    PrintWriter out = new PrintWriter(writer);
                    out.println(data);
                    out.close();
                    tvResult.setText("저장완료우");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // 저장한 걸 읽어와야해
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkExternalStorage()) return; // 외부메모리 사용 가능여부 체크

                    try {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        File f = new File(path, "external.txt");
                        BufferedReader reader = new BufferedReader(new FileReader(f));

                        StringBuffer data = new StringBuffer();
                        String str = reader.readLine();
                        while(str != null){
                            data.append(data);
                            str = reader.readLine();
                        }
                        tvResult.setText(data);
                        reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    } // end onCreate()

    // 권한 획득 결과 받으면 실행되는 콜백


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE:

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){  // grantResults 하나라도 담겨야 승인, 0이면 승인되지 않았다는 것.
                    Toast.makeText(this, "권한승인", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(this, "권한거부", Toast.LENGTH_LONG).show();

                    // 권한이 없으면  더이상 진행 못하게 하든지 다른 조치를 취해야한다.
                    // onDestroy(); 직접 호출해도 된다.
                }

                return;
        } // end switch

    } // end onRequestPermissionsResult()

    // 외부 메모리 상태 확인 메소드
    protected boolean checkExternalStorage(){

        //
       String state =  Environment.getExternalStorageState(); // ?  메모리 상태값 뽑아내는 메소드. 이걸 문자열로 가져와

        String msg = "";

        // 외부 메모리 상태
        if(Environment.MEDIA_MOUNTED.equals(state)){ // "mounted" __ state 가 mounted 로 나오면  -> 외부 메모리 장착되어 있는 상태이다.
            // mounted 의 의미? 읽기와 쓰기 모두 가능 상태를 말함.
            msg = "외부메모리 읽기 쓰기 모두 가능";
            Log.d("myapp", msg);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            return true;
        } else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){ // "mounted_ro" 외부메모리가 있는데 읽기만 가능하고 쓰기 불가능.
            // 읽기전용
            msg = "외부메모리 읽기만 가능";
            Log.d("myapp", msg);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            return false;
        } else {
            // 읽기 쓰기 모두 안됨
            msg = "외부메모리 읽기쓰기 모두 안됨: " + state;
            Log.d("myapp", msg);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            return false;
        }


     }


} // end Activity
