package com.lec.android.a009_sound;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class Main2Activity extends AppCompatActivity {

    private ImageView btnPlay;
    private ImageView btnPause;
    private ImageView btnResume;
    private ImageView btnStop;
    SeekBar sb; // 음악 재생위치를 나타내는 시크바

    // 조금 긴 음향(배경음악)은 MediaPlayer 객체 사용
    MediaPlayer mp; // 음악 재생을 위한 객체

    int pos; // 재생 위치(int) 인트값 으로 받아줄 변수
    boolean isTracking = false;

    // 시크바 체인지 리스너 하고 > 쓰레드 이너클래스로 정의
    class MyThread extends Thread{   // 이 쓰래드는 뭐냐? 이 쓰레드는 일시정지 시(플레이멈춤시)  끝남.
        @Override
        public void run() {
            super.run();

            while (mp.isPlaying()) {  // 연주 중이면 true, 아니면 false
                    pos = mp.getCurrentPosition();  // 현재 재생 중인 위치 ms (int 리턴) 를 -> int pos 에 담기
                    if(!isTracking) sb.setProgress(pos); // 현재 재생 위치까지 SeekBar 이동 -->  setProgress(pos)는  =>  onProgressChanged() 호출함


            } // while
        }//run()
    } //Thread


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnResume = findViewById(R.id.btnResume);
        btnStop = findViewById(R.id.btnStop);
        sb = findViewById(R.id.sb);

        btnPlay.setVisibility(View.VISIBLE);  // 처음엔 Play 만 활성화
        btnPause.setVisibility(View.INVISIBLE);
        btnResume.setVisibility(View.INVISIBLE);
        btnStop.setVisibility(View.INVISIBLE);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // SB 값 변경될 때 마다 호출
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 음악이 끝까지 연주 완료 되었다면
                if(seekBar.getMax() == progress && !fromUser){

                    btnPlay.setVisibility(View.VISIBLE);
                    btnPause.setVisibility(View.INVISIBLE);
                    btnResume.setVisibility(View.INVISIBLE);
                    btnStop.setVisibility(View.INVISIBLE);
                    if(mp != null) mp.stop(); // 곡을 종료시킴

                }
            }

            // 드래그 시작(트래킹) 하면 호출
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTracking = true;
            }

             // 드래그 마치면(트래킹 종료) 하면 호출
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pos = seekBar.getProgress(); // 시크바의 현재위치를 pos포지션 값에 넣음\

                if(mp != null) mp.seekTo(pos); // 미디어플레이어가 이동함. 어디로? 사용자가 손을 뗀 그 위치로

                isTracking = false;
            }
        }); // end setOnSeekBar~()


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    // MediaPlayer 객체 초기화, 재생
                    mp = MediaPlayer.create(
                            getApplicationContext(), // 현재 화면의 제어권자
                            R.raw.chacha);  // 음악 파일 리소스
                    mp.setLooping(false); // true: 무한반복

                    mp.start(); // 노래 재생 시작

                    // Seekbar 음악의 재생길이에 맞춰서 같이 움직임.
                    int duration = mp.getDuration();// 음악의 재생시간(ms)  (곡의 전체길이)
                    sb.setMax(duration);// SeekBar 의 범위를 음악의 재생시간으로 설정
                    new MyThread().start();// SeekBar 쓰레드 시작.



                btnPlay.setVisibility(View.INVISIBLE); // 최초 Play 누르면 비활성화
                btnPause.setVisibility(View.VISIBLE);  // pause 랑 stop  활성화(보여야겠지)
                btnStop.setVisibility(View.VISIBLE);



                // 재생이 끝나면 호출되는 콜백 메소드
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // 어느 시점에서 종료되는지 찍어보자.
                        Log.d("myapp", "연주종료" + mp.getCurrentPosition() + "/" + mp.getDuration());
                        // mp.getCurrentPosition() : 현재 재생중인 위치나옴.
                        // mp.getDuration() : 곡의 길이가 ms 밀리 세컨단위로 나온다.(정확하지 않다)
                        btnPlay.setVisibility(View.VISIBLE);
                        btnPause.setVisibility(View.INVISIBLE);
                        btnResume.setVisibility(View.INVISIBLE);
                        btnStop.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 음악 종료.
                pos = 0 ;   // 위치를 0으로 (처음)으로 만들어준다..

                if(mp != null){
                    mp.stop(); // 재생 멈춤
                    mp.seekTo(0); //음악의 처음으로 이동
                    mp.release(); // 자원해제
                    mp = null;
                }

                sb.setProgress(0);   //SeekBar도 초기 위치로.

                btnPlay.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
                btnResume.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
            }
        });

        // 일시중지
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = mp.getCurrentPosition(); // 현재 재생중이던 위치 저장.
                mp.pause(); // 일시중지
                btnPause.setVisibility(View.INVISIBLE);
                btnResume.setVisibility(View.VISIBLE);
            }
        });

        // 멈춘 시점부터 재시작 -> 멈춘시점에 위에 쓰레드 끝남.(플레이할떄만 쓰레드되니까)  다시 재개할 때 쓰레드 다시 시작해주는 메소드 작성필요
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.seekTo(pos); // 일시 정지시 위치로 이동. => 내가 일시중지했던 그 위치(pos_밀리세컨으로 리턴한 위치) 찾아감. _ Seeks to specified time position
                mp.start(); // 그런 다음 다시 재생 시작(start() 또 해줘야 재생되지)
                new MyThread().start(); // SeekBar  이동 시작(쓰레드)

                // Resume 버튼 보이면 안되고, Pauase 버튼 다시 보이고
                btnResume.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });
    } // end onCreate()


    // MediaPlay 도 자원이다.  -> 꼭 자원 해제 해줘야 한다.  release()
    // Activity 사이클에서  자원은 onPause() 에서 걸어줘야 한다
    @Override
    protected void onPause() {
        super.onPause();

        if(mp != null){
            mp.release(); // 자원 해제
        }

        btnPlay.setVisibility(View.VISIBLE);  // 다시 Play 만 보이도록
        btnPause.setVisibility(View.INVISIBLE);
        btnResume.setVisibility(View.INVISIBLE);
        btnStop.setVisibility(View.INVISIBLE);
    } // end onPause()


} // end Activity
