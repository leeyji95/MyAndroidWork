package com.lec.android.a014_dialog;

import androidx.appcompat.app.AlertDialog;
//import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    // 다이얼로그의 ID를 보기 좋은 상수로 선언해서 사용한다
    final int DIALOG_TEXT = 1;
    final int DIALOG_LIST = 2; // 리스트 형식의 다이얼로그 ID
    final int DIALOG_RADIO= 3; // 하나만 선택할 수 있는 다이얼로그 ID
    final int DIALOG_MULTICHOICE= 4;

    TextView tvResult;

    int choice = -1; // 라디오 버튼 선택 값. 일단 -1 로 설정해두고 다시 내려가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // AlertDialog : Dialog 를 상속받은 자식클래스로
        //          다이얼로그를 쉽게 만들수 있도록 지원해주고,
        //          Activity 라이프 사이클과 다이얼로그의 라이프사이클을
        //          맞춰줌

        tvResult = findViewById(R.id.tvResult);
        Button btnText = findViewById(R.id.btnText);
        Button btnList = findViewById(R.id.btnList);
        Button btnRadio = findViewById(R.id.btnRadio);
        Button btnMultiChoice = findViewById(R.id.btnMultiChoice);

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(DIALOG_TEXT);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(DIALOG_LIST);
            }
        });

        btnRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(DIALOG_RADIO);
            }
        });

        btnMultiChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(DIALOG_MULTICHOICE);
            }
        });
    } // end onCreate()____화면 ~


    //---------------------------------------------------------------------------------------------
    protected AlertDialog.Builder showAlert(int id) { // Builder 리턴할 것

        switch (id){
            case DIALOG_TEXT:
                AlertDialog.Builder builder1 =
                        new AlertDialog.Builder(this);

                // 메소드 체이닝 중..
                builder1.setTitle("다이얼로그 제목")
                        .setMessage("안녕하세요")
                        .setPositiveButton("긍정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "예 안녕하세용!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("부정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "안녕 못해요..!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNeutralButton("중립", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "중립이에요", Toast.LENGTH_LONG).show();
                            }
                        });
                builder1.show(); // 다이얼로그 보이기
                return builder1;
                // 지금까지 먼저 빌더 만들고(빌더를 만든 것이다), 빌더에 속성들을 부여한 것. 모두 setter 잖여.

            case DIALOG_LIST:
                AlertDialog.Builder builder2
                        = new AlertDialog.Builder(this);

                final String [] str = {"사과", "딸기", "바나나", "포도"};

                builder2.setTitle("리스트 형식의 다이얼로그 제목")   //   리스트 형태로 팝업 띄울 떈, 리스트를 String 배열에 담고, onClickListener 장착
                        .setNegativeButton("취소", null)
                        .setItems(str, new DialogInterface.OnClickListener() { // str 은  리스트 목록에 사용할 배열. 먼저 첫번째 매개변수로 넣어주고,.
                            // 리스트 아이템이 선택되었을 때 호출되는 콜백
                            // which : 몇 번째 아이템이 선택되었는지에 대한 값  =>  // 몇번째가 선택되었는지에 대한 정보를 넘겨주는 매개변수임.
                            @Override
                            public void onClick(DialogInterface dialog, int which) { // 몇번째가 선택되었는지에 대한 정보를 넘겨주는 매개변수임.
                                Toast.makeText(getApplicationContext(), "선택: " + str[which], Toast.LENGTH_LONG).show();
                            }
                        });
                builder2.show();
                return builder2;

            case DIALOG_RADIO:
                // 커스텀 스타일 적용. styles.xml 편집(res -> values -> styles 가서 AlertDialogCustiom 설정하고 옴)
                AlertDialog.Builder builder3
                        = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

                final String [] str2 = {"빨강", "녹색", "파랑"};
                builder3.setTitle("색을 고르세요")
                        .setPositiveButton("선택완료",
                                // 예 눌렀을 떄 호출
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), str2[choice] + " 을 선택", Toast.LENGTH_LONG).show();
                                    }
                                })
                        .setNegativeButton("취소", null)
                        .setSingleChoiceItems(  // -------------------->  라디오 버튼은 한개밖에 선택 못하므로
                                str2 // 리스트 배열 목록이구
                                , -1  // 기본설정값 -1 은 아무것도 선택 안함.
                                , new DialogInterface.OnClickListener() {
                                    // 라디오 버튼에서 선택했을 때 호출되는 콜백, which : 몇번째 선택(int타입 매개변수)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 선택된 which 값 기억하도록 하기 위해 onCreate() 위에 멤버변수 하나 설정하고 오기
                                        choice = which; // 선택할 때마다 choice 값 바뀌고, choice 값으로 오케이 버튼 누를때 마다 동작하도록.
                                    }
                                }
                )
                ;
                builder3.show();
                return builder3;



            case DIALOG_MULTICHOICE:
                AlertDialog.Builder builder4 =
                        new AlertDialog.Builder(this);

                final String [] data = {"한국", "북한", "소련", "영국"};
                final boolean [] checked = {true, false, true, false};

                builder4.setTitle("MultiChoice 다이얼로그 제목")
                        .setPositiveButton("선택완료",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String str = "선택된 값은 : ";

                                        // 아래에서 체크된 거 가져오기
                                        for(int i = 0; i < checked.length; i++){
                                            if(checked[i]){
                                                str = str + data[i] + ", ";  // 문자열 추가한 후, tvResult 에서 찍어주면 됨
                                            }
                                        }

                                        tvResult.setText(str);
                                    }
                                })
                        .setNegativeButton("취소", null)
                        .setMultiChoiceItems(data, // 체크박스의 리스트 항목
                                checked,  // 초기값(선택여부) 배열
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    // 체크박스 선택했을 때 호출되는 콜백
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) { // 몇번째 체크박스에 대해서 체크여부
                                        checked[which] = isChecked;
                                    }
                                }
                        )
                        ;

                builder4.show();
                return builder4;




        } // end switch

        return  null;

    }


} // end Activity
