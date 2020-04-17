package com.lec.android.a008_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProfileAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProfileAdapter(); // 어댑터 만들고




    } // end onCreate()

//    protected  void initAdapter(ProfileAdapter adapter){
//        // 몇개만 생성
//        for(int i = 0; i < 10; i++){
//            int idx = D.next();
//            adapter.addItem(new Profile(D.FACEID[idx], D.NAME[idx], D.PHONE[idx], D.EMAIL[idx]));
//        }
//    }
//
//    private static int idx = 0;
//
//    public static int next(){  // 계속 무한반복. 데이터의 개수 3개면, 0 1 2 를 계속 반복하도록
//
//        List<String> list = new ArrayList<String>();
//
//        idx = idx % FACEID.length;
//        return idx++; // idx 값 리턴하고 1 증가
//    }






}
