package com.lec.android.a016_sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    private TextView tv;
    private SensorManager sm;

    // 롤 아즈모토, 피치 계산하기 위해 필요
    // TYPE_ORIENTATION <-- 현재 deprecated 됨
    Sensor accelerometer;
    Sensor magnetometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv = findViewById(R.id.textView1);

//        Sensor s = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);  //옛날에 쓰던 방식. 지금은 deprecated
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    } // end onCreate

    // 화면이 동작하기 직전에 센서 자원을 획득하기 위해 onResume 에서 처리
    @Override
    protected void onResume() {
        super.onResume();
        // 센서값이 변할 때마다 콜백 받기 위한 리스너 등록하기 위해 ->  SensorEventListener 객체 사용  해야함
        // -> 액티비티 올라가서 implements 하자 ==> 아래 두개의 SensorEventListener 객체의 메소드생김
        sm.registerListener((SensorEventListener)this,
                accelerometer, // 콜백 원하는 센서
                SensorManager.SENSOR_DELAY_UI // 지연시간, 2ms 임. 샘플링. 표본추출하는 시간, 최소한 2ms 로 받겠다.
                );

        sm.registerListener((SensorEventListener) this,
                magnetometer,
                SensorManager.SENSOR_DELAY_UI
        );
    }




    // 화면 빠져나가기 전에 센서 자원반납!
    @Override
    protected void onPause() {
        super.onPause();
        // 센서에 등록된 리스너 해제함
        sm.unregisterListener(this);// 반납할 센서
    }
//========================================== SensorEventListener 객체의 메소드들임 ================================================================

    float [] mGravity;
    float [] mGeomagnetic;

    // 센서값이 변경될때마다 호출되는 콜백
    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values; // 센서값들은 float[] 로 넘어옴

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if(mGravity != null && mGeomagnetic != null){
            float [] R = new float[9];
            float [] I = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);  //  값을 얻어와서  계산해서 배열에 담음. 그것이 블린값으로 넘어옴(리턴)

            if(success){
                float [] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);  // R값과 orientation값 계산 됨

                float azimuth = orientation[0]; // z 축 회전방향 (-3 ~ +3)
                float pitch = orientation[1]; // x 축 회전방향  (- 1.5 ~ +1.5)
                float roll = orientation[2]; // y 축 회전방향

                String str = String.format("%10s:%10s:%10s\n%10.2f:%10.2f:%10.2f\n",
                        "방위각", "피치", "롤",
                        azimuth, pitch,roll);

                tv.setText(str);
                Log.d("myapp", "" + str);

            }
        }

        tv.setText("onSensorChanged");
        Log.d("myapp", "onSensorChanged");
    }


    // 센서의 정확도가 변경되었을 때 호출되는 콜백   // 정밀도가 변할 땐 따로 처리 메소드 필요
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        tv.setText("onAccuracyChanged");
        Log.d("myapp", "onAccuracyChanged");
    }



} // end Activity
