package com.lec.android.a013_menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
/* HTTP 요청하기
   - 메니페스트 설정 하기 : android.permission.INTERNET 권한
   - <application> 에 추가 usesCleartextTraffic="true"
       HTTP와 같은 cleartext 네트워크 트래픽을 사용할지 여부를 나타내는 flag로
       이 플래그가 flase 로 되어 있으면, 플랫폼 구성 요소 (예 : HTTP 및 FTP 스택, DownloadManager, MediaPlayer)는
       일반 텍스트 트래픽 사용에 대한 앱의 요청을 거부하게 됩니다. 이 flag를 설정하게 되면 모든 cleartext 트래픽은 허용처리가 됩니다

   - URL 객체 만들기 -> HttpURLConnection 객체 만들기
       setXXX() 메소르도 Conneciton 세팅
           ex) setRequestMethod(method) :  "GET" "POST " 등의 문자열
           ex) setRequestProperty(field, value) :

   - request 는 별도의 Thread 로 진행!
   - 위 Thread에서 화면 UI 접근하려면 (당연히) Handler 사용
*/

// SubMenu & Popup Menu
public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Button btnPopup = findViewById(R.id.btnPopup);
        btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팝업 메뉴 나오게 하기
                // PopupMenu 는 API 11 부터 제공
                PopupMenu p = new PopupMenu(getApplicationContext(), v); // anchor  : 팝업을 띄울 기준이 될 뷰  => 앵커 포인트가 팝업메뉴가 버튼뷰 객체 v  (좌하단)에 걸림..
                getMenuInflater().inflate(R.menu.menu_main5, p.getMenu());

                // 팝업메뉴 이벤트 처리 따로 필요
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        showInfo(item);
                        return false;
                    }
                });

                p.show();// 팝업메뉴 띄우기


            }
        });




    } // end onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main5, menu ); // 메뉴 장착!
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        return super.onOptionsItemSelected(item);
    }




    public void showInfo(MenuItem item) {
        int id = item.getItemId();   // 옵션메뉴 아이템의 id 값
        String title = item.getTitle().toString();   // 옵션 메뉴의 title
        int groupId = item.getGroupId();   // 옵션 메뉴의 그룹아이디
        int order = item.getOrder();

        String msg = "id:" + id + " title:" + title + " groupid:" + groupId + " order:" + order;
        Log.d("myapp", msg);
        Toast.makeText(getApplicationContext(), title + " 메뉴 클릭", Toast.LENGTH_SHORT).show();
    }
} // end Activity
