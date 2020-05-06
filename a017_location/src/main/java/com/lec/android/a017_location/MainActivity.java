package com.lec.android.a017_location;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

/**  위치기반 앱 : 사용자의 위치를 활용한 어플리케이션
 *
 *  LocationManager (위치 관리자): 안드로이드에서 위치제공자(LocationProvider)를 얻어오기 위한 객체
 *
 *  안드로이드에서 사용가능한 위치제공자(LocationProvider)들  (즉, 사용자 위치 알아내는 방법들)
 *
 *    1. GPS : 위성에서 정보를 받아 삼각측량으로 위치를 계산,
 *             정확하다(accuracy), 실내에서는 잘 안된다.  실외에서도 고층건물등 장애물에 따라 오차발생
 *             배터리 소모 크다,
 *             위치 정보 판독에 시간 걸린다.  특히 초기위치 결정시간 (TTFF : Time To First Fix) 가 많이 걸림
 *
 *    2. Network : 전화 기지국 이용 (셀룰러)
 *             WiFi 네트워크, Cell ID 위치 사용
 *             GPS 에 비해 부정확,   TTFF 는 매우 빠름, 실내에서도 사용 가능
 *             GPS 에 비해 배터리 소모 적다
 *
 *    3. Passive : WiFi AP 사용
 *             실내 위치 추적용, AP위치, AP와의 수신신호강도등을 계산하여 위치 계산
 *             만약 다른 앱이 이미 위치서비스를 사용하고 있다면 그 위치 정보를 받아올수 있다.
 *             추가적인 배터리 소모가 가장 적다.
 *
 */





public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        // 위치관리지ㅏ 객체 얻어오기
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //  getSystemService() 메소드를 통해 Context 클래스에 정의되어 있는 상수값을 받아옴. (~_SERVICE : Context class 에 public static final 로 정의되어 있음)
        //  ~~Manager 관리자 객체로 리턴됨. (Object 타입이므로 -> 형변환 꼭 필요)

        List<String> list = lm.getAllProviders(); // 모든 가용한 위치 제공자 가져오기
        String str = "[위치제공자] : 사용가능여부\n---------------------\n";
        for(String provider : list){
            str += "[" + provider + "] : " + lm.isProviderEnabled(provider) + "\n";
        }
        tvResult.setText(str);

    } // end onCreate


} // end Activity



// getSystemService()
//● 시스템에서 따로 제공하는 디바이스나 안드로이드 프레임워크 내 기능을  다른 어플과 공유하고자 시스탬으로부터 객체를 얻을 때 사용
//매개변수에 문자열 상수를 대입하고 이 값으로 식별되는 시스템 서비스를 획득하여 이용하는 구조
//어떤 파라미터 값을 전달하느냐에 따라서 각기 다른 객체를 리턴함.
//그러므로 Object 타입으로 리턴 선언.
//        즉 전달하는 파라미터에 따라서 원하는 클래스 타입으로 형변환 해주어야 한다!! (부모-> 자식)
//        전달하는 파라미터는 String 형으로 되어 있고, 이 값들은 Context 클래스에 상수로 정의되어 있음.