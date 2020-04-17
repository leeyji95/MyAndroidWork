package com.lec.android.a007_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 액티비티 (Activity) :
 *      안드로이드의 4대 컴포넌트중 하나인 '화면' UI 객체
 *      앱에서 사용하는 액티비티는 반.드.시 메니페스트 에 등록되어야 함.
 *
 * 액티비티의 라이프 사이클
 *      onCreate() : 액티비티가 생성될때 호출, 사용자 인터페이스 초기화에 사용
 *          onStart() : 액티비티가 사용자에게 보여지기 바로 직전에 호출됨
 *              onResume() : 액티비티가 동작, 즉 사용자와 상호작용. (포커스를 갖기시작) 할때 호출
 *              onPause() : 다른 액티비티가 보여질때 (혹은 다른 액티비티에 의해 가려지기 시작할때) 호출됨
 *          onStop() : 액티비티가 더이상 안보여질때 호출되는 메소드.
 *      onDestory() : 액티비티 소멸될때 호출.
 *      onRestart() : onStop -> onStart 전환될때 호출됨.
 *
 *      [공식] : CTRL+클릭 !
 *      https://developer.android.com/guide/components/activities/activity-lifecycle
 *      https://developer.android.com/reference/android/app/Activity
 *
 *
 * 액티비티의 상태정보(state) 저장및 복원
 *      onRestoreInstanceState() :  onStart 직후에 호출됨.
 *      onSaveInstanceState() : 액티비티 소멸전에 호출된다.
 *
 *  ※ AVD/안드로이드폰 테스트시:  '화면 회전' 옵션을 켜주세요.
 */



public class MainActivity_LifeCycle extends AppCompatActivity {

    EditText et1, et2;
    TextView tvResult;
    Button btnAction;

    // 액티비티가 생성될 때 호출
    // 주로, 사용자 인터페이스 초기화에 사용용
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        tvResult = findViewById(R.id.tvResult);
        btnAction = findViewById(R.id.btnAction);

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int a = Integer.parseInt(et1.getText().toString().trim());
               int b = Integer.parseInt(et2.getText().toString().trim());

                tvResult.setText("" + (a + b));
            }
        });
       Log.d("myapp", "onCreate");
    }// end onCreate()

    //  액티비티가 사용자에게 보여지기 시작할 때 (바로직전)에 호출
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myapp", "onStart");
    }

    // 액티비티가 동작, 즉 사용자와의 상호작용(포커스를 갖기 시작) 할 때 호출(실제 액티비티가 러닝상태에 들어감)
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myapp", "onResume");
    }

    // 다른 액티비티가 보여질때 (혹은 다른 액티비티에 의해 가려지기 시작할때) 호출
    // 액티비티를 통해 다루고 있던 데이터 저장, 쓰레드 중지.. 등의 처리를 해야 함.
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myapp", "onPause");
    }

    // 액티비티가 더이상 안보여질때 호출되는 메소드
    // 안드로이드 메모리 상황에 따라 호출 안 될 수도 있음
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myapp", "onStop");
}

    // 액티비티 소멸될 때 호출
    // 액티비티 소멸은
    //      시스템에 의해서 소멸되기도 하고
    //      코드를 통해 제거되기도 함 : ex : finish()
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myapp", "onDestory");
    }

    // onStop 에서 -> onStart 로 전환될 때 호출
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myapp", "onRestart");
    }



    //회전할 떄 한 번 소멸한 다음에 다시 onCreate 한다.
    //그러면서 결과창에 있는 데이터 날라간다.
    // 데이터 백업위한 메소드 오버라이딩

    //직전에 저장되어 있던 액티비티의 상태정보가 있다면,
    // onRestoreOnstanceState() 는 onStart 직후에 호출됨.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("myapp", "onRestoreInstanceState");

        if(savedInstanceState != null){
            tvResult.setText(savedInstanceState.getString("value"));
        }

    }

    // 액티비티 소멸 전에 호출된다. (주의! onPause 뒤에 호출된다고 간주하지 말것!)- 즉 onPause 거치지 않고 소멸될 수 있다.
    // outState : <-- 액티비티 정보 저장(백업)하여 나중에 onCreate 에서 사용가능.
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("myapp", "onSaveInstanceState");

        outState.putString("value", tvResult.getText().toString()); // 밸류 값을 TextView에 담겠다.   밸류라는 키값으로 저장했으면  밸류라는 키값으로 끄집어내야 한다.
   }


} // end Activity
