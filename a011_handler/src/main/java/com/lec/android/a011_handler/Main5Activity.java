package com.lec.android.a011_handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/** AsyncTask
 *    백그라운드(background) 작업 스레드 수행,
 *    좀더 쉽게 스레드 만들고 운영,
 *    작업스레드 결과값까지 쉽게 받아볼수 있다.
 *    심지어 Handler 없이도 메인UI 접근 할수 있다!
 *
 *    AsyncTask 의 메소드
 *      onPreExecute() : 백그라운드 작업 시작하기 전에 호출
 *      doInBackground() : 백그라운드 작업, 시간이 많이 걸리는 '통신' 작업이나 복잡한 연산 작업등을 (비동기로)수행케 해야 한다.
 *      onProgressUpdate() : 백그라운즈 작업 도중 (여러번) 호출가능, 중간중간에 UI업데이트시 사용 가능!
 *      onPostExecute() : doInBackground() 완료되면 호출
 *
 *    AsyncTask<Params, Progress, Result>
 *      Params: doItBackground 에서 사용할 변수 타입
 *      Progress: onProgress 에서 사용할 변수의 타입
 *      Result : onPostExecute 에서 사용할 변수의 타입
 *
 */


public class Main5Activity extends AppCompatActivity {
    int mainValue = 0;
    int backValue1 = 0;
    int backValue2 = 0;
    TextView tvMainValue;
    TextView tvBackValue1, tvBackValue2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        tvMainValue = findViewById(R.id.tvMainValue);
        tvBackValue1 = findViewById(R.id.tvBackValue1);
        tvBackValue2 = findViewById(R.id.tvBackValue2);

        Log.d("myapp", "PRE !!");
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(100); //  0.1초 * 10 단위로?  ..


        Log.d("myapp", "POST !!"); // <-- 과연 언제 찍힐까?

    } // end onCreate()










    // 요즘 안드로이드에서 가장 많이 쓰이는 방법.   얘는 핸들러 없이도 메인 UI 쓸 수 있다.
    // AsyncTask<Params, Progress, Result>
    //      Params: doItBackground 에서 사용할 변수 타입 (비동기 시작할 때 사용)
    //      Progress: onProgress 에서 사용할 변수의 타입(진행상태)
    //      Result : onPostExecute 에서 사용할 변수의 타입(다 끝나고 나서 리턴하는 변수 타입)
    // 세 걔의 타입을 받는 제네릭.
    class BackgroundTask extends AsyncTask<Integer, Integer, Integer>{

        // 백그라운드 작업 시작하기 전에 호출되는 메소드
        @Override
        protected void onPreExecute() {
            Log.d("myapp", "onPreExecute");
            super.onPreExecute();
        }


        // 백그라운드 작업 -> 반드시 구현해야함(필수)
        @Override
        protected Integer doInBackground(Integer... integers) { // 백그라운드 작업 시작할 때 호출되는 메소드임.(필수)
                                                // 가변 매개변수, integers 는 Integer[] 배열로 동작한다.
            for(backValue1 = 0 ; backValue1 < integers[0]; backValue1++){
                if(backValue1 % 10 == 0){
                    publishProgress(backValue1); // progress 상태를 update 뽑아냄 ==> onProgressUpdate 호출되고 매개변수 값 전달됨.
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return backValue1; // onPostExecute 에 넘어가는 값.
            // ※ doInBackground() 에서 시간이 많이 걸리는 '통신' 작업이나 복잡한 연산 작업등을 (비동기로)수행케 해야 한다.   이런것들을 비동기로 보내어 작업해야 한다. (시간 많이 걸리는 것들)
        }

        // 백그라운드 작업 도중(여러번) 호출 가능, 진행상황 업데이트, 중간중간 UI 업데이트시 사용
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("myapp", "Progress : " + values[0] + "%"); // 밸류의 정체 : publishProgress(i) 가 보낸 값
            super.onProgressUpdate(values);

            tvBackValue1.setText("onProgressUpdate: " + values[0]); // ★ handler 없이도 메인 UI 접근 가능!
        }


        // doInBackground() 완료되면 호출


        @Override
        protected void onPostExecute(Integer integer) { //  doInBackground 에서 return 한 값을 매개변수로 받는다.
            Log.d("myapp", "Result: " + integer);
            super.onPostExecute(integer);

            tvBackValue1.setText("onPostExecute" + integer); // ★ handler 없이도 메인 UI 접근 가능!
        }


    } // end class  BackgroundTask


    public void mOnClick(View v){
        mainValue++;
        tvMainValue.setText("MainValue: " +  mainValue);
    }



} // end Activity
