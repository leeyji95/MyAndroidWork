package com.lec.android.a008_recycler;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

    // Adapter 객체 정의
    // 데이터(Phonebook)를 받아서, 각 item 별로 View 를 생성
public class PhonebookAdapter extends RecyclerView.Adapter<PhonebookAdapter.ViewHolder> {

    // 폰북을 담는 리스트 만들기
    // Adapter는 리스트에서 다룰 데이터가 필요하다
    // Adapter가 데이터에 연결되어야 하는 것은 사실이나, 데이터를 Adapter를 직접 다룰지
    // 아니면 별도의 데이터 관리는 따로 하는 구조로 만들지는 선택의 몫
    // 본 예제에서는 Adapter 안에 직접 데이터를 다루어보겠습니다
    List<Phonebook> itemsList = new ArrayList<Phonebook>();

    static PhonebookAdapter adapter;
    // Adapter 생성자
    public PhonebookAdapter(){this.adapter = this;} // ????????????? 자기자신이 갖고 있는 리스트에 있는 값들을 가져와야 삭제 가능함으로,

    // 언제 호출되고 무슨 역할을 하는지 알기

    // onCreateViewHolder() : ViewHolder 가 생성될때 호출됨
    // 각 item 을 위해 정의한 레이아웃(ex:XML) 으로 View 객체를 만들어 줍니다.
    // 이들 View객체를 새로 만들 ViewHolder 에 담아 리턴.
    ////
    //  'View  타입' 을 위한 정수값이 매개변수로 넘겨진 --> viewType
    //    이를 통해 아이템별로 View를 다양하게 표현 가능.  (ListView 에는 없던 개선점)
    //    예를들면, 각각의 'View 타입' 별로 따로따로 XML레이아웃을 달리 하여 보여줄수 있는 겁니다.
    //    * 그러나, 일반적으로는 한가지만 운용함.*
    //
    //  매개변수로 전달된 ViewGroup 객체는 각 아이템을 위한 객체
    //  이로부터 Context 객체를 뽑아내어 Layout inflation 을 해야 한다.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 주어진 Viewgroup 으로부터 LayoutInflater 추출
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        // 준비된 레이아웃(XML) 으로부터 View 를 만들어 ViewGroup 에 붙이고
        // 그렇게 만들어진 View 를 리턴한다
        View itemView = inf.inflate(R.layout.item, parent, false);

        // 위에서 만들어진 새로운 view 를 ViewHolder 에 담아 리턴한다
        return new ViewHolder(itemView);
    }

    // onBindViewHolder() : ViewHolder 가 '재사용' 될때 호출됨
    // View 객체는 그대로 기존것을 사용 (이것이 재사용!) 하고 데이터만 바꾸어 주면 됨.
    //  이전에 이미 만들어진. 재활용할수 있는 ViewHolder 객체  와
    //  리스트 상에 몇번째 데이터인지에 대한 정보 (position) 가 넘어온다
    // -> 새로이 데이터를 실어야 하는 그 시점에서 호출된다.      방뺸 뷰홀더 값이 넘어오고, 목록의 포지션값이 넘어와서 몇번째 데이터인지 알 수 있다. 몇번째 데이터를 꺼내서 뷰홀더 객체에 싹 넣어주는 역할이 온 바인드()
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Phonebook item = itemsList.get(position); // List<> 의 get()
        holder.setItem(item); // 뷰홀더 클래스에 있는 setItem 메소드 이용해서 view객체로 phonebook 데이터 화면에 띄운다.
    }

     // getItemCount() : 어댑터에서 다루는 현시점 아이템(데이터)의 개수
    //   Selection Widget 에서 수시로 getItemCount() 를 호출하여 뷰를 업데이트 한다
    // 항상 지금 다루고 있는 데이터가 몇개인지 , 즉 현재 데이터가 몇 개 인지 알아야 한다.
    @Override
    public int getItemCount() {
        return itemsList.size(); // List<> 의 size()
    }

    // 각각의 보여지는 뷰들을 담는 뷰홀더 필요
    // 레이아웃 뷰 하나하나들을 뷰홀더가 가지고 있다. 그러므로 레이아웃 뷰 하나하나들을 정의 해야 한다.
    // nested class (static inner) 로 ViewHolder 클래스 정의
    static class ViewHolder extends RecyclerView.ViewHolder{ // 하나의 아이템을 담을 뷰홀더 객체 정의 한 것이다.
        //ViewHolder 에 담긴 각각의 View 들을 담을 변수
        ImageView ivPhoto;
        TextView tvName, tvPhone, tvEmail;
        ImageButton btnDelItem;
        Switch swOnOff;

        // 생성자 필수
        public ViewHolder(@NonNull View itemView) { // View 타입을 매개변수로 받는 생성자 필수! 이 매개변수는  그림 사각형 전체가 넘어온다.   // item 레이아웃(사각형)의 View 객체가 전달됨.
            super(itemView);
            // itemView(사각형) 에 담겨있는 각각의 View 객체 가져오기
            ivPhoto = itemView.findViewById(R.id.ivPhoto); // 어디서 찾냐면, itemView(사각형) 에서 찾는다.!!
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);

            btnDelItem = itemView.findViewById(R.id.btnDelItem);
            swOnOff = itemView.findViewById(R.id.swOnOff);

            // 스위치 누르면 전화번호, 이메일 숨기기/보이기
            swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        tvPhone.setVisibility(View.INVISIBLE);
                        tvEmail.setVisibility(View.INVISIBLE);
                    }else{
                        tvPhone.setVisibility(View.VISIBLE);
                        tvEmail.setVisibility(View.VISIBLE);
                    }
                }
            });

            // 삭제버튼 누르면 item 삭제되게 하기
            btnDelItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // position 정보 필요 -> 몇번째 지울건지 (이것만 필요한 것이 아니라... 데이터삭제까지 하기 위해 위에다가 adapter 만들고 옴..)
                     // 데이터를 지우려고 하는데.. adapter 객체가 필요..

                    // adapter 로부터 데이터 삭제도 진행되어야 한다.
                    adapter.removeItem(getAdapterPosition()); // 데이터 삭제..  -> 여기서 죽음. -> 데이터 지운것이 adpter 에 반영되어야 한다.
                    // 데이터 변경 내역이 adapter 에 반영되어야 정상적으로 동작한다!!! ★★★★
                    adapter.notifyDataSetChanged(); // 이것까지 꼭! 해줘야 시각적으로까지 사라진다!
                }
            });


            // --- 사각형 클릭 리스너 장착 ---              ==> 사각형 클릭하면 다음 상세페이지로 넘어가도록 하는 동작  작성해보자.   무엇을 넘길 건지 ... intent 날려보자..!
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // 이 리스너가 장착된 item 의 리스트상의 position 값
//                    Toast.makeText(v.getContext(), "position:" + position, Toast.LENGTH_SHORT).show();

                    // 아이템을 클릭하면 해당 세부 정보 액티비티로 넘겨주기
                    Intent intent = new Intent(v.getContext(), PhoneebookDetail.class);  //Phonebook 시리얼라이저블 하고 오기
                    intent.putExtra("pb", adapter.getItem(position));

                    v.getContext().startActivity(intent); // 여기는 Activity 가 아니므로 -> 뷰 객체를 통해 getContext()_ 현재 실행되고 있는 액티비티 로 접근.

                }
            });

        } // end 생성자

        // ViewHolder 클래스에 정의한 메소드 -> 뷰홀더 객체로 얼마든지 다른 곳에서 호출가능(public)
        // Phonebook 타입으로 받아서  뷰객체들로  화면에 띄우기
        public void setItem(Phonebook pb){
            ivPhoto.setImageResource(pb.getPhoto());
            tvName.setText(pb.getName());
            tvPhone.setText(pb.getPhone());
            tvEmail.setText(pb.getEmail());
        } // end setItem()

    } // end ViewHolder

    // 데이터를 다루기 위한 메소드들
    // ArrayList 의 메소드들 사용
    public void addItem(Phonebook item) {   // 리스트에 폰북 데이터 추가
        itemsList.add(item);
    }

    public void addItem(int position, Phonebook item) { // 리스트에 position 값이랑 폰북 데이터 추가
        itemsList.add(position, item);
    }

    public void setItems(ArrayList<Phonebook> items) { //  리스트타입 받아서 -> 자기 자신 리스트에 덮어씌우기(수정)
        this.itemsList = items;
    }

    public Phonebook getItem(int position) { // position 값 받아서  해당 인덱스번호의 리스트 원소 리턴
        return itemsList.get(position);
    }

    public void setItem(int position, Phonebook item) { // position 이랑 폰북 타입 받아서  수정
        itemsList.set(position, item);
    }

    public void removeItem(int position){
        itemsList.remove(position);    // 해당 position 번호 받아서 -> 삭제
    }




} // end PhonebookAdapter
