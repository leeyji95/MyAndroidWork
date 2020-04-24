package com.lec.android.a017_location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

// 지오코딩..   구글제공 서비스.
// 위도경도 보내주거나, 주소 등 보내면 구글이 가지고 있는 데이터베이스를 얻을 수 있음
/**  지오코딩(GeoCoding) : 주소,지명 => 위도(latitude),경도(longitude) 좌표로 변환하는 구글 서비스
 *    위치정보를 얻기위한 권한을 획득 필요, AndroidManifest.xml
 *      ACCESS_FINE_LOCATION : 현재 나의 위치를 얻기 위해서 필요함
 *      INTERNET : 구글서버에 접근하기위해서 필요함
 */

public class Main3Activity extends AppCompatActivity {

    TextView tvResult;

    EditText etLatitude, etLongitude;
    EditText etAddress;
    Button btnGeoCoder1, btnGeoCoder2;
    Button btnMap1, btnMap2;

    //지오코딩
    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvResult =  findViewById(R.id.tvResult); // 결과창
        btnGeoCoder1 = findViewById(R.id.btnGeoCoder1);
        btnGeoCoder2 = findViewById(R.id.btnGeoCoder2);
        btnMap1 = findViewById(R.id.btnMap1);
        btnMap2 = findViewById(R.id.btnMap2);

        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        etAddress = findViewById(R.id.etAddress);

        geocoder = new Geocoder(this);


        btnGeoCoder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 위도, 경도 입력 후 주소정보 변환
                double lat = Double.parseDouble(etLatitude.getText().toString());
                double lng = Double.parseDouble(etLongitude.getText().toString());

                List<Address> list =  null;  // 얻어온 주소 정보를 담음. 어디에? Address 라는 객체에. 안드로이드가 이미 만들어놨어
                try {
                    list = geocoder.getFromLocation(
                            lat, // 위도
                            lng, // 경도
                            10 //얻어올 결과값의 최대 개수 .. 번화가의 경우 여러개의 정보가 있을 수 있음. 그 중에 최대 10개의 정보만 가져오겠다
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("myapp", "입출력 오류 - 서버에서 주소 변환시 에서 발생"); // 예를 들어 태평양 한가운데 지정할 경우..
                }

                // 지오코더로 받아온 정보가 list 에 하나라도 담겨있으면
                if(list != null){
                    if(list.size() == 0){  // 이때가 정말 태평양 한가운데 찍었을 경우..
                        tvResult.setText("헤당되는 주소 정보가 없습니다");
                    }else{
                        StringBuffer reslut = new StringBuffer(list.size() + "개의 결과\n");

                        for(Address addr : list){
                            reslut.append("--------------------------------\n");
                            reslut.append(addr.toString() + "\n");
                        }

                        tvResult.setText(reslut);
                    }
                } // end if


            } // end onClick()
        });


        btnGeoCoder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // 지명, 주소
                String str = etAddress.getText().toString();

                List<Address> list =  null;  // 얻어온 주소 정보를 담음. 어디에? Address 라는 객체에. 안드로이드가 이미 만들어놨어
                try {
                    list = geocoder.getFromLocationName(
                            str,    // 지명이름
                            10 //얻어올 결과값의 최대 개수 .. 번화가의 경우 여러개의 정보가 있을 수 있음. 그 중에 최대 10개의 정보만 가져오겠다
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("myapp", "입출력 오류 - 서버에서 주소 변환시 에서 발생"); // 예를 들어 태평양 한가운데 지정할 경우..
                }

                // 지오코더로 받아온 정보가 list 에 하나라도 담겨있으면
                if(list != null){
                    if(list.size() == 0){  // 이때가 정말 태평양 한가운데 찍었을 경우..
                        tvResult.setText("헤당되는 주소 정보가 없습니다");
                    }else{
                        StringBuffer reslut = new StringBuffer(list.size() + "개의 결과\n");

                        for(Address addr : list){
                            reslut.append("--------------------------------\n");
                            reslut.append(addr.getCountryName() + ", " + addr.getFeatureName() + ", " + addr.getLocality());
                        } // end for

                        tvResult.setText(reslut);
                    }
                } // end if


            } // end onClick()
        });

        btnMap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 위도, 경도 입력 후 지도 버튼 클릭  -> 지도 앱 화면으로 묵시적 인텐트 날리기
                // 위도, 경도 입력 후 주소정보 변환
                double lat = Double.parseDouble(etLatitude.getText().toString());
                double lng = Double.parseDouble(etLongitude.getText().toString());

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: " + lat + "," + lng));
                startActivity(intent);
            }
        });

        btnMap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 지명, 주소
                String str = etAddress.getText().toString();

                List<Address> list =  null;  // 얻어온 주소 정보를 담음. 어디에? Address 라는 객체에. 안드로이드가 이미 만들어놨어
                try {
                    list = geocoder.getFromLocationName(
                            str,    // 지명이름
                            10 //얻어올 결과값의 최대 개수 .. 번화가의 경우 여러개의 정보가 있을 수 있음. 그 중에 최대 10개의 정보만 가져오겠다
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("myapp", "입출력 오류 - 서버에서 주소 변환시 에서 발생"); // 예를 들어 태평양 한가운데 지정할 경우..
                }

                // 지오코더로 받아온 정보가 list 에 하나라도 담겨있으면
                if(list != null){
                    if(list.size() == 0){  // 이때가 정말 태평양 한가운데 찍었을 경우..
                        tvResult.setText("헤당되는 주소 정보가 없습니다");
                    }else{
                        // 해당되는 주소의 위치 좌표로 묵시적 인텐트 날리기(지도앱)
                        Address addr = list.get(0);
                        double lat = addr.getLatitude();
                        double lng = addr.getLongitude();

                        String uri = String.format("geo:%f,%f", lat, lng);

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));



                        }
                    }
                } // end if


        });

    } // end onCreate


} // end Activity
