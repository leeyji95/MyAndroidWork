package com.lec.android.a017_location;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
// 아까는 구글 지도 서버로 들어가서 보는거였잖아?
// 이번에는 직접 내 앱에서 지도 띄우기
   //   ==> layout 에 fragment  작성하고 오기

/** 구글맵 v2.0 서비스 사용하기
 *  1. Play Service 라이브러리 추가
 *  2. 메니페스트에 권한, 각종 설정추가 :
 *  3. 구글맵 API key 발급 받아 메니페스트에 추가
 *  4. XML 에 MapFragment 추가  <-- 지도표시용 Fragment
 *     SupportMapFragment 객체로 사용
 *  5. GoogleMap 객체를 사용하여 지도 조작
 *
 */


public class Main4Activity extends AppCompatActivity {

    // GoogleMap  2.0
    GoogleMap map;
    SupportMapFragment mapFragment;
    MarkerOptions myLocationMarker; // 마커(지도에 기호표시 할 수 있잖아. 여러개 겹쳐서 표시 --> 오버레이 개체

    Button btnMap;
    Button btnMarker;
    EditText etLatitude;
    EditText etLongitude;
    EditText etMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        btnMap = findViewById(R.id.btnMap);
        btnMarker = findViewById(R.id.btnMarker);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        etMarker = findViewById(R.id.etMarker);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            // 지도가 준비되면 호출되는 콜백
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("myapp", "지도준비됨");
                map = googleMap;    // 여기서 드디어! 구글맵 객체 얻는다.
            }
        });

        MapsInitializer.initialize(this);

        // 버튼 누르면 입력된 좌표로 GoogleMap 이동
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });

        // 입력된 좌표 위에 마커 생성
        btnMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = Double.parseDouble(etLatitude.getText().toString());
                double lng = Double.parseDouble(etLongitude.getText().toString());

                LatLng curPoint = new LatLng(lat, lng);

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(curPoint)
                        .title(etMarker.getText().toString().trim() + "\n")
                        .snippet("★" +  String.format("%.3f %.3f", lat, lng))
                        ;  // 여기까지 MarkerOptions 객체 만든 것
                map.addMarker(markerOptions); // 맵 객체에 마커 추가!


            }
        });




    } // end onCreate

        public void startLocationService(){

            double lat = Double.parseDouble(etLatitude.getText().toString());
            double lng = Double.parseDouble(etLongitude.getText().toString());

            LatLng curPoint = new LatLng(lat, lng); // 구글맵의 좌표담는 객체 _ LatLng
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15)); // 어느 위치로 얼만큼 줌 주겠다
        }



} // end Activity
