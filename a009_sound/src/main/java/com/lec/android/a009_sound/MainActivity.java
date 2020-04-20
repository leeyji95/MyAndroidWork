package com.lec.android.a009_sound;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/** 음향: SoundPool
 *      짧은 음향 리소스(들)을 SoundPool 에 등록(load)하고, 원할때마다 재생(play)
 *
 *  res/raw 폴더 만들고  음향 리소스 추가하기
 */

public class MainActivity extends AppCompatActivity {

    private SoundPool sp;

    // 음향 리소스 id
    private final int [] SOUND_RES = {R.raw.gun, R.raw.gun2, R.raw.gun3};

    // 음향 id 값
    int [] soundID = new int[SOUND_RES.length]; // 위에 사운드 개수만큼 id 값 담겠다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlay1 = findViewById(R.id.btnPlay);
        Button btnPlay2 = findViewById(R.id.btnPlay2);
        Button btnPlay3 = findViewById(R.id.btnPlay3);
        Button btnStop = findViewById(R.id.btnStop);

        // SoundPool 객체 생성
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // API21 이상에서는 아래와 같이 SoundPool 생성
            sp = new SoundPool.Builder().setMaxStreams(10).build(); // Builder를 생성한 것. 스태틱 이너클래스임(클래스이름쩜).  사운드풀에 소속되어 있는 Builder객체 사용. 그걸 사용해서 -> 빌더 일단 만들고 그걸 통해서 사운드객체를 최종적으로 만듦.
            // 담아 놓는 것이 pool. 사운드를 담아놓는 용도. ~pool하면 객체들을 담아놓는 객체임.
        } else {
            sp = new SoundPool(1, // 재생 음향 최대 개수
                    AudioManager.STREAM_MUSIC,  // 재생 미디어 타입
                    0); // 재생 품질.. (안쓰임. 디폴트 0) 별로 중요하지 않음.

        }
        // SoundPool 에 음향 리소스들을 load
        for(int  i = 0; i < SOUND_RES.length; i++){

           soundID[i] = sp.load(this, // 현재 화면의 제어권자(현재 리소스는 어떤 객체에서 재생하는가? )
                    SOUND_RES[i], // 음원 파일 리소스
                    1);   // 재생 우선순위 (동시에 음향재생 개수 중 선별해서 몇 개만 내보내야함. 우선순위 배정 해서 재생해야 한다)
            // 사운드를 재생할 수 있는 아이디값을 리턴한다. load가.
        }

        btnPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(soundID[0],     // 준비한 sound 리소스 id
                        1,   // 왼쪽볼륨 float 0.0 ~ 1.0
                        1, // 오른쪽 볼륨 float
                        0,     // 우선순위 int
                        0,      // 반복횟수 int,  0: 반복안함 -1: 무한반복    // 특정 횟수만큼 반복
                        1f);    // 재생속도 float, 0.5(절반속도) ~ 2.0(2배속)
            }
        });

        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(soundID[1],     // 준비한 sound 리소스 id
                        1,   // 왼쪽볼륨 float 0.0 ~ 1.0
                        0, // 오른쪽 볼륨 float
                        0,     // 우선순위 int
                        2,      // 반복횟수 int,  0: 반복안함 -1: 무한반복    // 특정 횟수만큼 반복
                        2f);    // 재생속도 float, 0.5(절반속도) ~ 2.0(2배속)
            }
        });

        btnPlay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(soundID[2],     // 준비한 sound 리소스 id
                        0,   // 왼쪽볼륨 float 0.0 ~ 1.0
                        1, // 오른쪽 볼륨 float
                        0,     // 우선순위 int
                        -1,      // 반복횟수 int,  0: 반복안함 -1: 무한반복    // 특정 횟수만큼 반복
                        0.5f);    // 재생속도 float, 0.5(절반속도) ~ 2.0(2배속)
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < soundID.length; i++){
                    sp.stop(soundID[i]);
                }
            }
        });



    } // end onCreate()


} // end Activity
