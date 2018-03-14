package com.example.administrator.cookbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

public class C_home  extends Fragment {

    //////////////////////////////////////////////////////////////////////////////////////////////////

    FloatingActionButton fab;
    ListView listView;
    ListView listView2;
    ListAdapter adapter;



    public static C_home newInstance() {
        return new C_home();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_home,container, false);

        final startactivity startactivity = (startactivity)getActivity();

        String[] listString = {"1. 정태수 : 맛있는 김치 볶음밥", "2. 홍길동 : 쉽다 쉬워 동치미", "3. 임꺽정 : 속풀리는 해장국", "4. 허준 : 만병통치약 인삼죽", "5. 이소룡 : 취권을 위한 부대찌개"
                , "6. 배트맨 : 밤에다니려면 역시 뜨근한 김치국", "7. 슈퍼맨 : 외계인도 좋아하는 누룽지 탕 ", "8. 포세이돈 : 역시 바다에선 매운탕이지", "9. 짱구엄마 : 아침출근 시간 김치찌개",
                "10. 행인1 : 일하기전 간단한 부침개", "11. 행인2 : 후하.. 슬픈땐 짬뽕이지", "12. 행인3 : 얘들아 모여라 감자탕 했다"};

        String[] listString2 = {"1. 맛있는 김치 볶음밥 [2017.08.04]", "1. 쉽게 할 수 있는 된장찌개 [2017.08.15]", "3. 집에서 해먹는 탕수육 [2017.09.01]",
                "4. 자글자글 김치짜글이 [2017.09.07]", "5. 맛있지만 간단한 카레 [2017.09.24]", "6. 얼큰한 부대찌개 [2017.10.11]", "7. 정말 시원한 콩나물국 [2017.11.24]"};

        ImageButton imgbtn1 = (ImageButton)view.findViewById(R.id.ib1);
        ImageButton imgbtn2 = (ImageButton)view.findViewById(R.id.ib2);
        ImageButton imgbtn3 = (ImageButton)view.findViewById(R.id.ib3);
        ImageButton imgbtn4 = (ImageButton)view.findViewById(R.id.ib4);
        ImageButton imgbtn5 = (ImageButton)view.findViewById(R.id.ib5);


        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startactivity.replaceFragment(C_recipy.newInstance());

                Toast.makeText(getActivity(), "1", Toast.LENGTH_LONG).show();
            }
        });

        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startactivity.replaceFragment(C_recipy.newInstance());

                Toast.makeText(getActivity(), "2", Toast.LENGTH_LONG).show();
            }
        });

        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startactivity.replaceFragment(C_recipy.newInstance());
                Toast.makeText(getActivity(), "3", Toast.LENGTH_LONG).show();
            }
        });

        imgbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startactivity.replaceFragment(C_recipy.newInstance());
                Toast.makeText(getActivity(), "4", Toast.LENGTH_LONG).show();
            }
        });

        imgbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startactivity.replaceFragment(C_recipy.newInstance());
                Toast.makeText(getActivity(), "5", Toast.LENGTH_LONG).show();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////


        // 리스트 어뎁터 생성

        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_2, listString);

        ArrayAdapter<String> adapter2
                = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_2, listString2);

        listView = (ListView) view.findViewById(R.id.listview1);
        listView.setAdapter(adapter);


//        // 리스트 뷰에 어뎁터 적용
//
//        listView.setAdapter(adapter);

        listView2 = (ListView) view.findViewById(R.id.listview2);
        listView2.setAdapter(adapter2);

        // Floating Action Button을 리스트 뷰에 적용
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToListView(listView2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "1", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}



