package com.lec.android.a005_image;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

// 앞에서는 내 컴퓨터에 저장되어 있는 이미지를 가져오는 코드를 배워봤다.
// 이번에는,
// 사용자 앨범에 있는 이미지 보여줘보자. 근데!
// 폰에서 이미지를 가져오려면 꼭 "사용자 권한"을 받아야해. 그걸 받는 코드 작성 필요.   <-- 이게 핵심!
// 주소, 통화내역 등 모두 개인정보에 해당. 이런걸 안드로이드에서는 "위험권한 요청"이라고 하는데,  이는 런타임에서 요청되어진다. 즉 실행시점에서 요청하도록 되어 있다.
// 모든 권한이 아니라, 위험한 권한 그룹만 사용자 승인 받으면 된다. (그룹: 캘린더, 전화관련, 카메라, 문자, 위치 , 이미지 등 저장소(storage)등등) -> 반드시 런타임때 요청받아야한다.
// 위험권한 획득을 해야  사용자 폰에서 이미지, 연락처 등등 접근가능

// 이번시간에는 위험권한 획득하는 코드 작성해보자.

/**
 * 폰의 저장장치에 있는 사진을 ImageView 에 띄우기
 * 1. 권한 획득
 * - 메니페스트
 * - '위험권한'요청  (Android 6.0 / API23 이상)
 * https://developer.android.com/guide/topics/security/permissions?hl=ko  (위험권한및 위험권한 목록들)
 * https://developer.android.com/training/permissions/requesting?hl=ko#java
 * <p>
 * 설치 앱별 '권한' 확인 가능하다 :
 * 픽셀2 폰의 경우 '앱' 아이콘 롱클릭후 App Info 확인
 * <p>
 * 2. 이미지 경로
 * 3. Bitmap 사용하여 ImageView 에 띄우기
 */


public class Main3Activity_permission extends AppCompatActivity {

    ImageView imgPhoto;
    TextView tvPath;
    Button btnAction;

    // 앨범에 있는 이미지 경로 찾아내서 직접 적어줌
    // 이미지 경로 알아내기 (제조사, 모델 마다 다를 수 있다)
    // 픽셀폰 : 갤러리 이미지보기 -> 하단의 i 버튼
    // 삼성폰 : 갤러리 이미지 롱클릭 후 상세정보...
    private String imgPath = "/storage/emulated/0/DCIM/Camera/france.png";
    private final int PERMISSION_REQUEST_CODE = 101; // 권한 요청 코드값 (int) 상수로 박아두기//
    private final String[] PERMISSIONS = { // 요청할 권한들을 String[] 로 준비
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        imgPhoto = findViewById(R.id.ivPhoto);
        tvPath = findViewById(R.id.tvPath);
        btnAction = findViewById(R.id.btnAction);

        // 여기까지 등장시키고~ 메니페스트에 권한 요청하고 오기 -> uses-permission~ READ, WRITE - EXTERNAL ~

        // 버튼 클릭 시  파일경로에서 이미지 받아오고 그걸  화면에 띄운다
//        btnAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bitmap bitmap =  BitmapFactory.decodeFile(imgPath); // 파일경로명 읽어 들여서 -> Bitmap 객체도 만들어줌.
//                imgPhoto.setImageBitmap(bitmap); // 이미지뷰  Bitmap 객체로 띄우기
//                tvPath.setText(imgPath);  // 이미지 경로명 TextView 로 띄우기
//            }
//        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawPhoto();
            }
        });

        // '위험권한' 획득
        // API23 (마시멜로) 이상에서만 권한 요청
        // 권한을 획득하지 않은 상태에서만 요청 (최초 단 한 번만 요청함)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&  // 걍 뜻 : API 버전이 23 이상이고,
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) // 현재 이 전체 코드가 실행되고 있는 객체에서(Activity) 해당 코드가 읽히는지...   아무튼 int 리턴
                        != PackageManager.PERMISSION_GRANTED) {  // PackageManager.PERMISSION_GRANTED('권한승인' 을 의미) 그러니까 권한을 승인 받지 않았을 때! 그때 권한 요청을 수행한다는 뜻

            // 권한 요청 : 비동기로 진행됨. // 사용자가 오케이하면 그제서야 수행하는 코드 따로 작성해야하는 경우
            // 사용자가 Allow 눌렀을 때 수행되는 코드
            ActivityCompat.requestPermissions(this,  // requestPermissions @NonNull android.app.Activity activity, @NonNull String[] permissions,int requestCod
                    PERMISSIONS, // 요청할 위험권한(들)
                    PERMISSION_REQUEST_CODE // 권한 요청 코드
            );
        } else {
            // 권한을 이미 획득한 상태
            drawPhoto();
        }
    } // end onCreate()



    // 사진 그려주는 메소드
    public void drawPhoto() {
        // 경로명에서 BitMap 뽑아서 -> ImageView 에 장착할 거임( 비트맵 객체로 이미지뷰 장착)
        Bitmap bm = BitmapFactory.decodeFile(imgPath);  // 파일 경로명을 읽어들여서 비트맵 객체로 만듦!
        imgPhoto.setImageBitmap(bm);
        tvPath.setText(imgPath);
    }


    // onRequestPermissionsResult 오버라이딩
    // 사용자가 권한 획득 결과 처리하는 메소드 .사용자가 받은 요청을 승인하거나 거부하거나 한 결과에 대해서 수행하는 코드다.
    // requestCode : 권한요청 코드 값(int)
    // permissions : 권한 승인 요청한 것(들) (String[] 타입)
    // grantResults : 사용자가 승인한 내역(들) (int[] 타입)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: // requestCode 가 내가 위에서 박아둔 PERMISSION~ 요청 코드 값과 같다면,  아래를 수행하시오
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { /// PackageManager 에 있는 상수값 PERMISSION_GRANTED = 0
                    // 사용자가 권한 승인함!(하면)
                    drawPhoto(); // 이미지 띄우고,

                    Log.d("myapp", "권한획득결과\n\t" + Arrays.toString(permissions) + " : " + Arrays.toString(grantResults));
                } else {
                    // 사용자가 권한 승인 안함!(안하면)
                }
                break;
        } // end switch
    } // end onRequestPerssionResult()




} // end Activity




