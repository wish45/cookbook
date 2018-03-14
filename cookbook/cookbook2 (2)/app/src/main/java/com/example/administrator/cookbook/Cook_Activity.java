package com.example.administrator.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeSet;

public class Cook_Activity extends AppCompatActivity {
    private ListView listView;
    public static Activity Cook_Activity;
    private static        String TAG       = "[cook_activityByCSM]TEST";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_NAME = "item_name";
    //private static final String TAG_ID   = "item_id";

    String Ingred_Name_Required[];

    String Ingred_Name_Fridge[];
    String Ingred_Name_JObject;


    @Override
    protected void onStart() {
        super.onStart();
        Cook_Activity =  com.example.administrator.cookbook.Cook_Activity.this;
        try {
            new GetNameBG().execute().get();
        }catch (Exception e){
            Log.d(TAG, "response code - " + e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_);

        TextView ingr = (TextView) findViewById(R.id.recipe_ingr);

        Intent intent = getIntent();
        String ingrstr = intent.getStringExtra("INGR");//재료받아오기
        ingr.setText(ingrstr);
        //냉장고 재료 받아오기
        //메뉴 리스트 출력해주는 곳.


        //for(int i = 0; i < Ingred_Name.length; i++)
         //   System.out.println("Ingred_Name[i]__oncrete : " + Ingred_Name[i]);
        /*필요한 재료와 받아온 냉장고 재료를 비교한다.*/
        //String nameTemp = "";
        //String Ingred_Name;
        String[] nameArrTemp = ingrstr.split("#") ;//#

        Object[] removeArray = null;
        TreeSet ts=new TreeSet();

        for(int i=0; i<nameArrTemp.length; i++){
            ts.add(nameArrTemp[i]);
        }
        removeArray= ts.toArray();

        int len = removeArray.length;

        Ingred_Name_Required = new String[len];

        for (int i=0;i<removeArray.length;i++) {
            Ingred_Name_Required[i]=removeArray[i].toString();
        }
        Log.d(TAG, "response result111 (required)- " + Ingred_Name_Required[0].toString());

        //Log.d(TAG, "response result111 (fridge)- " + Ingred_Name_Fridge[0].toString());
        /*필요한 재료의 목록을 뿌려준다. 그 후 냉장고 유무 표시 및 구매버튼 표시*/

        /*구매되었을 때 -> "구매 되었습니다"*/
// 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;
        items.add("carrot");
        items.add("tomato");

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        Button deleteButton = (Button)findViewById(R.id.delete) ;
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

    }
    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.listview_item, null);
            }

            // ImageView 인스턴스
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
            Button button = (Button)v.findViewById(R.id.button);
            // 리스트뷰의 아이템에 이미지를 변경한다.
            if("Seoul".equals(items.get(position))) {
                imageView.setImageResource(R.drawable.edit);
            }else if("Busan".equals(items.get(position)))
                imageView.setImageResource(R.drawable.edit);
            else if("Daegu".equals(items.get(position)))
                imageView.setImageResource(R.drawable.edit);
            else if("Jeju".equals(items.get(position)))
                imageView.setImageResource(R.drawable.edit);


            TextView textView = (TextView)v.findViewById(R.id.textView);
            textView.setText(items.get(position));

            final String text = items.get(position);
            //Button button = (Button)v.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(com.example.administrator.cookbook.Cook_Activity.this, text, Toast.LENGTH_SHORT).show();

                }
            });

            return v;
        }
    }
    //출처: http://docko.tistory.com/entry/안드로이드-리스트뷰ListView에-이미지-넣기 [Smart Phone for Human]

    //디비로부터 데이터 받기
    private class GetNameBG extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String Ingred_Name_JObject) {
            super.onPostExecute(Ingred_Name_JObject);

            try{
                JSONObject jsonObject = new JSONObject(Ingred_Name_JObject);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                String nameTemp = "";

                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    nameTemp = item.getString(TAG_NAME) + "#" + nameTemp;
                }
                String[] nameArrTemp = nameTemp.split("#") ;//#으로 구분되어진 문자열을 #을 분리하여 배열의 각원소에 음식재료의 이름을 넣는다.

                Object[] removeArray = null; //이건 왜 있는 것 일 까?
                TreeSet ts=new TreeSet();

                for(int i=0; i<nameArrTemp.length; i++){
                    ts.add(nameArrTemp[i]);
                }
                removeArray= ts.toArray();//

                int len = removeArray.length;

                Ingred_Name_Fridge = new String[len];

                for (int i=0;i<removeArray.length;i++) {
                    Ingred_Name_Fridge[i]=removeArray[i].toString();
                }
            } catch (JSONException e) {
                Log.d(TAG, "showResult : ", e);
            }catch (Exception e) {
                Log.d(TAG, "showResult : ", e);
            }
            Log.d(TAG, "response - " + Ingred_Name_JObject);//잘나옴
            for(int i = 0; i < Ingred_Name_Fridge.length; i++)
                System.out.println("Ingred_Name_Fridge[i] : " + Ingred_Name_Fridge[i]);
            showResult();
        }
        private void showResult()
        {
            String []Ingred_name_ToBuy = new String[10];
            int []temp = new int[10];
            int countToBuy = 0;
            //받아온 재료와 전 엑티비티에서 받은 재료를 비교한다.
            for(int i = 0; i < Ingred_Name_Required.length; i++)
            {
                for(int j = 0; j < Ingred_Name_Fridge.length; j++)
                {
                    if(Ingred_Name_Required[i]==Ingred_Name_Fridge[j])//필요한게 냉장고에 있나요?
                    {
                        break;
                    }
                    if(j == Ingred_Name_Required.length-1)//해당 재료가 존재하지 않으면 사야할 목록의 배열에 저장한다.
                    {
                        Ingred_name_ToBuy[countToBuy] = Ingred_Name_Required[i];
                    }
                }
            }
            for(int i = 0; i < Ingred_name_ToBuy.length; i++)
            {
                System.out.println("Ingred_name_ToBuy "+i+":"+Ingred_name_ToBuy[i]);
            }
            Intent  intent = new Intent(getApplicationContext(),IngredToBuyActivity.class);
            intent.putExtra("Ingred_name_ToBuy", Ingred_name_ToBuy);
            startActivity(intent);

        }
        @Override
        protected String doInBackground(String... params) {
            String resultOfIngred = GetName();
            return resultOfIngred;
        }
    }
    //냉장고 데이터 불러오기
    protected String GetName() {

        String serverURL = "http://192.168.1.51/get_fridge.php";

        try {
            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d(TAG, "response code - " + responseStatusCode);

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;
// 웹페이지를 읽는다.
            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }

            bufferedReader.close();

// ㅈㅐㄹㅛ 이름을 모아놓은 제이슨 스트링
            Ingred_Name_JObject = sb.toString();
//파싱
            /*
            JSONObject jsonObject = new JSONObject(Ingred_Name_JObject);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            String nameTemp = "";

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                nameTemp = item.getString(TAG_NAME) + "#" + nameTemp;
            }
            String[] nameArrTemp = nameTemp.split("#") ;//#으로 구분되어진 문자열을 #을 분리하여 배열의 각원소에 음식재료의 이름을 넣는다.

            Object[] removeArray = null; //이건 왜 있는 것 일 까?
            TreeSet ts=new TreeSet();

            for(int i=0; i<nameArrTemp.length; i++){
                ts.add(nameArrTemp[i]);
            }
            removeArray= ts.toArray();//

            int len = removeArray.length;

            Ingred_Name = new String[len];

            for (int i=0;i<removeArray.length;i++) {
                Ingred_Name[i]=removeArray[i].toString();
            }*/

            return Ingred_Name_JObject;

        } catch (Exception e) {
            Log.d(TAG, "InsertData: Error ", e);
            return null;
        }

    }
    /*

    재료를 불러온다...

    재료 유무 물어봄, 재료 유무 보기 전 냉장고 데이터를 불러와서 있으면 제외시킴

    냉장고 보관 데이터.
    + 냉장고 엑티비티 켰을 때 현재 나의 냉장고 상황을 표현해준다.
     */
}
