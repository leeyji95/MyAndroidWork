package com.lec.android.a023_camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;
/*  카메라 화면 보여주기 --> SurfaceView 사용

                                   1.프리뷰설정
                                    --->
   SurfaceView <-->  SurfaceHolder <---   카메라   2.프리뷰 시작
              3. 프리뷰표시        3. 프리뷰 디스플레이

// SurfaceHolder 가 SurfaceView 제어, 카메라 설정 하고 SurfaceHolder 가 SurfaceView 에 뿌려준다


   SuffaceView 는 SurfaceHolder 에 의해 제어되는 모습
               - serPreviewDisplay() 로 미리보기 설정해주어야 함

   초기화 작업후 카메라객체의 startPreview() 호출 --> 카메라 영상이 SurfaveView 로 보이게 된다
   주의!: Surface 타입은 반드시 SURFACE_TYPE_PUSH_BUFFERS)

   SurfaceView 가  SURFACE_TYPE_PUSH_BUFFERS 타입인 경우, 카메라 보여주기 외에 다른 그림 못 그림
   그 위에 다른 그림 (아이콤, 마커, 증강현실..) 그리려면 별도의 레이아웃을 위에 포개야 한다

여러개의 레이아웃이 포개짐.
*/

public class Main2Activity extends AppCompatActivity {

    String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    final int REQUEST_CODE = 101;
    CameraSurfaceView cameraSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // 권한 획득 하기
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(String.valueOf(permissions)) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissions, REQUEST_CODE);  // 권한 요청하기
            }
        }// end if

        FrameLayout frameLayout = findViewById(R.id.previewFrame);
        cameraSurfaceView = new CameraSurfaceView(this);
        frameLayout.addView(cameraSurfaceView);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

    } // end onCreate

    // 사진 촬영
    // 캡쳐한 이미지 데이터 --> data
    public void takePicture() {
        cameraSurfaceView.capture(new Camera.PictureCallback() {
            // 사진 찍힐 때 호출되는 콜백
            // data :  data 전달받은 이미지 byte 배열
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // byte 배열 -> Bitmap 객체로 만들기
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  // 넘겨받은 data 만큼

                // 미디어 앨범에 추가, MediaStore.Images.Media 사용 (안드로이드 앨범 경로에 저장하겠다)
                String outUriStr = MediaStore.Images.Media.insertImage(

                        getContentResolver(), // ContentResolver 객체
                        bitmap,  // 캡쳐하여 만들어진 Bitmap 객체
                        "Captured Image", // 비트맵 제목
                        "Captured Image using Camera" // 비트맵 내용
                );

                if(outUriStr == null){
                    Log.d("myapp", "이미지 저장 실패, Image Insert Fail");
                    return;
                }else {
                    Uri outUri = Uri.parse(outUriStr);
                    // 이러한 이벤트가 발생되었다고 알려줌. 브로드 캐스팅기능.   사진 저장하면 알아서 클라우드에 저장되어 -> 새로운 사진 저장되었다고 알려줌.
                    sendBroadcast(new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                }

                camera.startPreview();  // 촬영되는 순간 프리뷰 끊어지므로, 곧바로 다시 재개 필요.
            }
        });
    }


    // SurfaceView 상속 + SurfaceHolder.Callback 구현
    private class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

        private SurfaceHolder mHolder;
        private Camera camera = null;

        // 생성자에선 SurfaceHolder 객체 참조후 설정
        public CameraSurfaceView(Context context) {
            super(context);
            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        // SurfaceView 가 만들어질 떄, 카메라 객체를 참조한 후 미리보기 화면으로
        // 홀더 객체 설정한다.
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open(); // 카메라 오픈을 사용해서 카메라 객체 만들어주고(카메라 생성!!!!)

            // 카메라 orientation 세팅
            setCameraOrientation();

            try {
                camera.setPreviewDisplay(mHolder); // 카메라에 홀터객체 통해서 프리뷰 세팅한다.    홀더객체가 카메라에 프리뷰 세팅한다는 의미

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // SurfaceView 의 화면 크기가 변경될 때 호출
        // --> 변경되는 시점에 미리보기 시작
        // 화면 크기가 변경되기 때문에  가로 세로 정보가 그대로 넘어옴
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            camera.startPreview();
        }

        // SurfaceView 가 소멸될 때 호출
        // --> 미리보기 중지
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // 서피스가 소멸되면 그때 프리뷰 멈춤
            camera.stopPreview();
            camera.release(); // 카메라도 자원이므로 해제시켜주고
            camera = null; //  null로 만든다
        }


        // 내가 만든 레이아웃 프리뷰가 있어.  카메라가 세로형태일때 ...   기준점 잡는것.   화면이 눕혀졌냐 세워졌냐,,  정확히 그 이미지를 프리뷰 어느 위치로 정해줄 것인가.
        // 카메라 orientation 세팅
        public void setCameraOrientation() { // 오리엔테이션이란?  카메라의 경우   현재 보고 있는 카메라가 뭐냐. 엑스와이좌표도 있고, 회전도 있을 것. 이러한 프리뷰 어떻게 끌고 올거야?  카메라가
            if (camera == null) return; // 카메라 없으면 뒤도 돌아보지 말고 나가세요

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(0, info); // 카메라 객체에 정보 담아 오겠다

            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation(); // 90단위로 있는 거 얻어옴

            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }

            camera.setDisplayOrientation(result);
            // 이렇게 만들어진 결과(result)로 오리엔테이션 세팅 완료해줌!!

        } // end setCameraOrientation

        // 사진촬영!
        public boolean capture(Camera.PictureCallback handler) {
            if (camera == null) return false;

            camera.takePicture(null, null, handler);
            return true;
        }


    } // end SurfaceView


}// end Activity
