package com.gmail.ilyeel59.cgs2004.blockgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Start extends AppCompatActivity implements View.OnClickListener{

    TextView tvTime;    //시간표시
    TextView tvPoint; // 점수표시

    int time = 30; // 시간값
    int point = 0; // 점수값

    //  블럭이미지 리소스 배열__ 하단 버튼
    int [] img = {R.drawable.block_red, R.drawable.block_green, R.drawable.block_blue};

    // 블럭 이미지 뷰(객체)를 관리할 배열 필요 __ 중간 블럭이미지
    ImageView[] iv = new ImageView[8]; // iv[0] <--- ?  8개 모두 null로 초기화 되어 있지

    private Vibrator vibrator;    // 진동
    private SoundPool soundPool; // 음향

    private int soundID_OK;     // 음향 id: 블럭 맞추었을 떄
    private int soundID_Error; // 음향 id : 블럭 못 맞추었을 때

    private MediaPlayer mp;

    final int DIALOG_TIMEOVER = 1; // 다이얼로그 ID

    Handler handler = new Handler(); // 시간,


    // 게임진행 쓰레드 만들기
    class GameThread extends Thread{
        @Override
        public void run() {
            // 시간을 1초마다 다시 표시 (업데이트)
            // Handler 사용하여 화면 UI 업데이트

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(time >= 0){ // 이때만 시간이 흘러감(handler 동작)
                        tvTime.setText("시간:" + time);

                        if(time > 0 ){
                            time--; // 1초 감소하고, 1초 후에 다시 Runnable 수행(run 메소드 수행한 것)
                        handler.postDelayed(this,1000); // 이렇게 함으로써 이 run 메소드는 1초마다 실행
                        } else{ // 시간은 1초마다 1씩 감소되면서 진행될 것.
                            // time => 0 이 된 경우 : 게임을 끝내야함.  --> 팝업띄우기
                            AlertDialog.Builder builder
                                    = new AlertDialog.Builder(Start.this);
                            builder.setTitle("타임아웃")
                                    .setMessage("점수 : " + point)
                                    .setNegativeButton("그만하기", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish(); // 현재 화면 종료. 메인화면으로 가기
                                        }
                                    })
                                    .setPositiveButton("다시하기", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 게임 리셋하고, 새 게임 시작하기
                                            time = 30; // 시간 다시 돌리기
                                            point = 0; // 점수도 원상복귀
                                            tvTime.setText("시간: " + time);
                                            tvPoint.setText("점수: " + point);

                                            // 새로운 스레드 시작
                                            new GameThread().start(); // 새로운 게임 시작!
                                        }
                                    })
                                    ;
                            builder.show();
                        }// end if
                    } // end of
                } // end run()
            }, 1000); // 1초 후에 시간 표시

        } // end run()

    } // end GameThread


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 싱테바(status bar) 없애기, 반드시 setContentView()  앞에서 처리
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.start);

        // 레이아웃 객체(뷰) 초기화
        tvTime = findViewById(R.id.tvTime);
        tvPoint = findViewById(R.id.tvPoint);

        ImageView ivRed = findViewById(R.id.ivRed);
        ImageView ivGreen = findViewById(R.id.ivGreen);
        ImageView ivBlue = findViewById(R.id.ivBlue);

        iv[0] = findViewById(R.id.ivBlock1);
        iv[1] = findViewById(R.id.ivBlock2);
        iv[2] = findViewById(R.id.ivBlock3);
        iv[3] = findViewById(R.id.ivBlock4);
        iv[4] = findViewById(R.id.ivBlock5);
        iv[5] = findViewById(R.id.ivBlock6);
        iv[6] = findViewById(R.id.ivBlock7);
        iv[7] = findViewById(R.id.ivBlock8);

        // 게임이 시작되면 초기화, 랜덤으로 8개의 블럭 색상 지정
        for(int i = 0; i < iv.length; i++){
            // 인덱스번호 0, 1, 2 각각 <- red, green, blue(위에 img 리소스 배열)
            int num = new Random().nextInt(3); // 0, 1, 2 중의 랜덤 정수
            iv[i].setImageResource(img[num]);
            iv[i].setTag(num + ""); // 태그를 버튼의 색상 판단하기  위한 값으로 활용  // 각각의 태그값에 색상별 인덱스 번호  '문자열'로 담김
        }

        // 점수 초기화
        point = 0;
        tvPoint.setText("점수: " + point);

        // r, g, b 버튼의 이벤트 리스너 등록
        ivRed.setOnClickListener(this);
        ivGreen.setOnClickListener(this);
        ivBlue.setOnClickListener(this);

        // 시간표시, 게임진행 쓰레드 시작하기
        new GameThread().start();



    } // end onCreate()


    // Activity 에 View.onClickListener implements 하고, onClick 오버라이딩
    @Override
    public void onClick(View v) {
        // 버튼을 눌렀을 떄 호출되는 콜백
        // 블럭과 같은 색깔의 버튼이 눌렸는지 판별 (빨간불이면 빨간 블럭,)   그리고 같은 블럭이면 이미지 블럭 한칸씩 내려오기, 맨 위에는 새로운 블럭 생성
        // 맨 마지막에 오는, 배열로 따지면 7번에 오는 블럭을 맞춰야한다.
        boolean isOk = false; // 먼저 맞췄는지 아닌지 기억할 변수 하나 필요  // isOk 가 true 이면 맞춘 것.

        ImageView imageView = (ImageView)v; // 넘어온 뷰 객체 받고, 이미지뷰로 형변환해서 받기

        switch (imageView.getId()){
            // 마지막 맨 아래 블럭 ImageView 의 색상과 일치하는 버튼인지 판정
            case R.id.ivRed:  // 빨강버튼 클릭시
                if("0".equals(iv[7].getTag().toString())) isOk = true; // 빨강블럭의 tag값 "0"
                break;
            case R.id.ivGreen: // 초록버튼 클릭시
                if("1".equals(iv[7].getTag().toString())) isOk = true; // 초록블럭의 tag값 "1"
                break;
            case R.id.ivBlue: // 파랑버튼 클릭시
                if("2".equals(iv[7].getTag().toString())) isOk = true; // 파랑블럭의 tag값 "2"
                break;
        } // end switch

        if(isOk){ // 버튼 색깔을 맞추었다면!

            // 위의 7개 블럭을 한칸 아래로 이동, iv[i] 를 -> iv[i + 1] (그대로 복사..)
             for(int i = iv.length-2; i >= 0; i--){   // for 문에서 0 번째 방이 없네. 그래서 밑에 0번째 방 정해줘야.
                 int num = Integer.parseInt(iv[i].getTag().toString()); // "0", "1", "2" 중 하나일 것
                 iv[i + 1].setImageResource(img[num]);  // i 아래쪽 블럭 이미지 업데이트
                 iv[i + 1].setTag(num + ""); // i 아래쪽 블럭 tag값 업데이트
             } // end for

            // 가장 위의 블럭(iv[0]) ImageView 는 랜덤으로 생성
            int num = new Random().nextInt(3); // 0, 1, 2 중 랜덤
            iv[0].setImageResource(img[num]);
            iv[0].setTag(num + "");


            // 진동 & 음향
            vibrator.vibrate(200);
            soundPool.play(soundID_OK, 1, 1, 0, 0, 1);

            // 점수 업데이트(점수올리기)
            point++;
            tvPoint.setText("점수: " + point);

        }else{ // 버튼 색깔이 틀리다면!
            // 진동 & 음향
            vibrator.vibrate(new long[] {20, 80, 20, 80, 20, 80}, -1);
            soundPool.play(soundID_Error, 1, 1, 0, 0, 1);

            // 점수 업데이트(점수깎기)
            point--;
            tvPoint.setText("점수: " + point);

        }// end if

    } // end onClick()

    @Override
    protected void onResume() { // 앱이 가동상태에 들어갔을 떄 (사용자와 상호작용 시작)
        super.onResume();
        // 여기서 자원획득할 것임

        // Vibrator  객체 얻어오기
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // SoundPool 객체
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        soundID_OK = soundPool.load(Start.this, R.raw.gun3, 1);
        soundID_Error = soundPool.load(Start.this, R.raw.error, 1);

        // MediaPlayer 객체
        mp = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
        mp.setLooping(false); // 반복재생 안함
        mp.start(); // 배경음악 시작
    }

    @Override
    protected void onPause() { // 사용자와의 상호작용 끝남
        super.onPause();
        // 여기서 자원해제할 것임
        if(mp != null){
            mp.stop();
            mp.release();
        }
    }

} // end Activity
