package com.example.administrator.cookbook;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;


/**
 * Created by Administrator on 2017-10-26.
 */

public class C_recipy extends Fragment {

    private static String TAG = "phpquerytest";

    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_INGRED ="ingred";
    private static final String TAG_RECIPE = "recipe";
    private static final String TAG_URL = "url";
    private static final String TAG_COOK = "COOK";

    private String currentRootPath = null;
    private ListView listView = null;

    String Recipe_Name[];
    String Recipe_Name_temp;

    String url;
    Bitmap bmImg;
    ImageView iv;

    private TextView mTextViewResult;
    ArrayList<HashMap<String, Object>> mArrayList;
    ListView mListViewList;
    EditText mEditTextSearchKeyword;
    String mJsonString;
    Thread mThread;
    static FragmentManager fragmentManager;

    public static C_recipy newInstance(FragmentManager fragmentM) {
        fragmentManager = fragmentM;
        return new C_recipy();
    }


    public static C_recipy newInstance() {
        return new C_recipy();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_recipy, container, false);

        mTextViewResult = (TextView)view.findViewById(R.id.textView_main_result);
        mListViewList = (ListView) view.findViewById(R.id.listView_main_list);
        mEditTextSearchKeyword = (EditText) view.findViewById(R.id.editText_main_searchKeyword);
        Recipe_Name_temp = "";

//        Thread GetNameThread = new Thread() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        GetName();
//
//                    }
//                });
//            }
//        };
//
//        GetNameThread.start();
//        try {
//            GetNameThread.join();
//            mTextViewResult.setText(mTextViewResult.getText() + "thread...");
//
//        }catch (InterruptedException e){
//            mTextViewResult.setText("get name err\n");
//        }
//        Recipe_Name = new String[2];
//        Recipe_Name[0] = "a";
//        Recipe_Name[1] = "b";

        try {
            new GetNameBG().execute().get();
        }catch (Exception e){
           mTextViewResult.setText(mTextViewResult.getText() + e.toString());
        }
        ArrayAdapter<String> adWord = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, Recipe_Name);

        AutoCompleteTextView autoEdit = (AutoCompleteTextView)
                view.findViewById(R.id.editText_main_searchKeyword);
        autoEdit.setAdapter(adWord);

        autoEdit.setTextColor(R.color.Black);
        Button button_search = (Button) view.findViewById(R.id.button_main_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mArrayList.clear();

                GetData task = new GetData();
                task.execute( mEditTextSearchKeyword.getText().toString());
            }
        });

        mArrayList = new ArrayList<>();

        return view;
    }
    private class GetNameBG extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            GetName();
            return null;
        }
    }


    @Override
    public void onResume(){


        super.onResume();
    }

    protected void GetName() {

        //String serverURL = "http://203.255.217.207/query_recipe_name.php";
        String serverURL = "http://192.168.1.51/query_recipe_name.php";

        Log.d(TAG,  serverURL);
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

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }

            bufferedReader.close();

            Recipe_Name_temp = sb.toString();

            JSONObject jsonObject = new JSONObject(Recipe_Name_temp);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            String nameTemp = "";

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                nameTemp = item.getString(TAG_NAME) + "#" + nameTemp;
            }
            String[] nameArrTemp = nameTemp.split("#") ;

            Object[] removeArray = null;
            TreeSet ts=new TreeSet();

            for(int i=0; i<nameArrTemp.length; i++){
                ts.add(nameArrTemp[i]);
            }
            removeArray= ts.toArray();

            int len = removeArray.length;

            Recipe_Name = new String[len];

            for (int i=0;i<removeArray.length;i++) {
                Recipe_Name[i]=removeArray[i].toString();
            }

        } catch (Exception e) {
            Log.d(TAG, "InsertData: Error ", e);
        }

    }


    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String searchKeyword = params[0];

            String serverURL = "http://192.168.1.51/query_recipe.php";
            String postParameters = "country=" + searchKeyword;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

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

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    String INGR_temp[];
    int recipe_count;
    ListAdapter adapter;
    String num_temp;

    private void showResult(){

        recipe_count = 1;

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            INGR_temp = new String[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                String name = (recipe_count++) + ") " + item.getString(TAG_NAME);
                INGR_temp[i] = item.getString(TAG_INGRED);
                String ingred = "요리 재료 \n";
                Log.d(TAG, "showResult : " + ingred);
                // split을 이용한 문자열 분리
                String[] txtArr = INGR_temp[i].split("#") ;
                for(int i2=0; i2 < txtArr.length; i2++){
                    ingred =  ingred + "\n" + (i2+1) + "." + txtArr[i2];
                }

                String recipe = "요리 방법 \n" + item.getString(TAG_RECIPE);
                url  = item.getString(TAG_URL);

                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put(TAG_NAME, name);
                hashMap.put(TAG_INGRED, ingred);
                hashMap.put(TAG_RECIPE, recipe);

                mThread = new Thread(){
                    public void run(){

                        try {
                            URL url_ = new URL(url);

                            HttpURLConnection conn = (HttpURLConnection) url_.openConnection();

                            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), 10240);
                            //InputStream is = conn.getInputStream();
                            bmImg = BitmapFactory.decodeStream(bis);

                        }
                        catch (Exception e) {

                            Log.d(TAG, "showResult : ", e);
                            mTextViewResult.setText(mTextViewResult.getText() + "\n err : " + e);

                        }
                    }

                };


                mThread.start();
                mTextViewResult.setText(url);

                try {
                    mThread.join();



                    mTextViewResult.setText(mTextViewResult.getText() + " bit size : " + bmImg.getByteCount() + " bit hi : " + bmImg.getHeight() );

                    //iv.setImageBitmap(bmImg);
                    hashMap.put(TAG_URL, bmImg);

                    mArrayList.add(hashMap);

                }catch (Exception e) {

                    Log.d(TAG, "showResult : ", e);
                    mTextViewResult.setText(mTextViewResult.getText() + "\n err set : " + e);

                }

            }

            adapter = new ExtendedSimpleAdapter(
                    getActivity(), mArrayList, R.layout.item_list,
                    new String[]{TAG_NAME, TAG_INGRED, TAG_RECIPE, TAG_URL, TAG_COOK},
                    new int[]{R.id.textView_list_name, R.id.textView_list_ingred, R.id.textView_list_recipe, R.id.imageView_list_image, R.id.recipe_cook_button}
            )    {
                public View getView (int position, View convertView, ViewGroup parent)
                {
                    View v = super.getView(position, convertView, parent);

                    final Button b=(Button)v.findViewById(R.id.recipe_cook_button);

                    final TextView NameTextV=(TextView)v.findViewById(R.id.textView_list_name);

                    num_temp = (String)NameTextV.getText();
                    b.setText(num_temp + " 요리하기");

                    //mTextViewResult.setText(num_temp);

                    b.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            //mTextViewResult.setText(INGR_temp[Integer.parseInt(num_temp)]);
                            try {
                                num_temp = b.getText().toString().substring(0, b.getText().toString().indexOf(")"));
                                mTextViewResult.setText(INGR_temp[Integer.parseInt(num_temp)-1]);

                                Intent intent = new Intent(getActivity(), Cook_Activity.class);
                                intent.putExtra("INGR", INGR_temp[Integer.parseInt(num_temp)-1]);
                                //액티비티 시작!
                                startActivity(intent);

//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                fragmentTransaction.replace(R.id.content, Cook_Activity.newInstance()).commit();

                            }catch (Exception e){
                                mTextViewResult.setText("err : " + e.toString() );
                            }
                        }
                    });
                    return v;
                }


            };

            mListViewList.setAdapter(adapter);

            //adapter.getView()

//            ((SimpleAdapter) adapter).setViewBinder(new SimpleAdapter.ViewBinder() {
//                @Override
//                public boolean setViewValue(View view, Object data, String textRepresentation) {
//                    if(view.getId() == R.id.imageView_list_image) {
//                        ImageView imageView = (ImageView) view;
//                        Drawable drawable = (Drawable) data;
//                        imageView.setImageDrawable(drawable);
//                        return true;
//                    }
//                    return false;
//                }
//            });

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
            mTextViewResult.setText(mTextViewResult.getText() + "\n iv err : " + e);

        }catch (Exception e) {

            Log.d(TAG, "showResult : ", e);
            mTextViewResult.setText(mTextViewResult.getText() + "\n last err : " + e);

        }


    }


}
