package com.example.administrator.cookbook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017-10-26.
 */

public class C_Refrigerator extends Fragment {
    ArrayAdapter adapter;
    ListView listview;

    private static        String TAG       = "[cook_activityByCSM]TEST";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_NAME = "item_name";
    String Ingred_Name_Fridge[];
    public static C_Refrigerator newInstance() {
        return new C_Refrigerator();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_refrigerator, null) ;
        final ArrayList<String> items = new ArrayList<String>() ;
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items) ;
        items.add("김치");
        listview = (ListView) view.findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        Button addButton = (Button)view.findViewById(R.id.add) ;
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count;
                count = adapter.getCount();

                // 아이템 추가.
                items.add("LIST" + Integer.toString(count + 1));

                // listview 갱신
                adapter.notifyDataSetChanged();
            }
        }) ;
        // delete button에 대한 이벤트 처리.
        Button deleteButton = (Button)view.findViewById(R.id.delete) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();

                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked) ;

                        // listview 선택 초기화.
                        listview.clearChoices();

                        // listview 갱신.
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;

        return inflater.inflate(R.layout.activity_refrigerator, container, false);
    }
}
