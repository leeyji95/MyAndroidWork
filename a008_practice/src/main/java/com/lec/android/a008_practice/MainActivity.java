package com.lec.android.a008_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3;
    EditText etName, etAddress, etAge;
    ProfileAdapter adapter;  // Adapter 객체
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etAge = findViewById(R.id.etAge);

        recyclerView = findViewById(R.id.rv); // recyclerView 생성
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        // Adapter 객체 생성
        adapter = new ProfileAdapter(); // 어댑터 만들고
        initAdapter(adapter);
        recyclerView.setAdapter(adapter);

        Button btnAppend = findViewById(R.id.btnAppend);
        btnAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendData(v);
            }
        });


    } // end onCreate()


    protected void initAdapter(ProfileAdapter adapter){ // 처음에 한 번 생성
        adapter.addItem(new Profile(
                etName.getText().toString(),
                etAddress.getText().toString(),
                Integer.parseInt(etAge.getText().toString())
        ));

    } // end initAdapter()

    protected void appendData(View v){

        adapter.addItem(0, new Profile(
                etName.getText().toString(),
                etAddress.getText().toString(),
                Integer.parseInt(etAge.getText().toString())
        ));
        adapter.notifyDataSetChanged();
    }


}
