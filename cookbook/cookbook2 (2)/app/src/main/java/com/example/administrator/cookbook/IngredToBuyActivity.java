package com.example.administrator.cookbook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class IngredToBuyActivity extends AppCompatActivity {
    phpInsert task_insert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingred_to_buy);

        Intent intent = getIntent();
        String[] IngredToBuy = intent.getStringArrayExtra("Ingred_name_ToBuy");//재료받아오기


        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;
        items.add(IngredToBuy[0]);
        //items.add("tomato");

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        Button buyButton = (Button)findViewById(R.id.buyButton) ;
        buyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();
                   // System.out.println("test"+items.get(checked).toString()); 값 정상 전달 완료
                    task_insert = new phpInsert();

                    task_insert.execute("http://192.168.1.51/insert_fridge.php?item_name=" + items.get(checked).toString());

                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked) ;

                        // listview 선택 초기화.
                        listview.clearChoices();

                        // listview 갱신.
                        adapter.notifyDataSetChanged();

                        /*디비의 냉장고를 최신화 시켜야함 => 추가해야함*/
                    }
                }
            }
        }) ;
    }
    //뒤로돌아가기 이전 엑티비티 싹 종료시킨다.
    private class phpInsert extends AsyncTask<String, Integer,String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder resultText = new StringBuilder();
            try{
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // 연결되었으면.
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if(line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            resultText.append(line);
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return resultText.toString();

        }

        protected void onPostExecute(String str){
            if(str.equals("1")){
                Toast.makeText(getApplicationContext(),"DB Insert Complete.",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"DB Insert Failed.",Toast.LENGTH_LONG).show();
            }

        }

    }
    @Override
    public void onBackPressed() {

        Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
        Cook_Activity ca = (Cook_Activity) Cook_Activity.Cook_Activity;

        ca.finish();
        this.finish();
        super.onBackPressed();


    }

}
