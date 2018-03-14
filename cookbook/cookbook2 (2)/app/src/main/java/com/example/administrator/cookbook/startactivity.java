package com.example.administrator.cookbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class startactivity extends AppCompatActivity {



    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)  {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(C_home.newInstance());
                    Toast.makeText(startactivity.this, "1.", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_recipy:
                    try {
                        replaceFragment(C_recipy.newInstance(fragmentManager));
                        Toast.makeText(startactivity.this, "2.", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(startactivity.this, "2. "  + e, Toast.LENGTH_SHORT).show();

                    }
                    return true;

                case R.id.navigation_Refrigerator:
                    replaceFragment(C_Refrigerator.newInstance());
                    Toast.makeText(startactivity.this, "3.", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_cset:
                    replaceFragment(C_set.newInstance());
                    Toast.makeText(startactivity.this, "4.", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

    };
    public void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, C_home.newInstance()).commit();


    }




}

/*

package com.example.administrator.cookbook;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class startactivity extends AppCompatActivity {


    String myJSON;

    static final String TAG_RESULTS = "result";
    static final String TAG_ID = "id";
    static final String TAG_NAME = "name";
    static final String TAG_ADD = "address";


    JSONArray peoples = null;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> personList;


    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    FragmentManager fragmentManager;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)  {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(C_home.newInstance());
                    Toast.makeText(startactivity.this, "1.", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_recipy:
                    replaceFragment(C_recipy.newInstance());
                    Toast.makeText(startactivity.this, "2.", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_Refrigerator:
                    replaceFragment(C_Refrigerator.newInstance());
                    Toast.makeText(startactivity.this, "3.", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_cset:
                    replaceFragment(C_set.newInstance());
                    Toast.makeText(startactivity.this, "4.", Toast.LENGTH_SHORT).show();
                    return true;
            }
          return false;
        }

    };


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);
        ButterKnife.bind(this);

        personList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.1.51/getjson.php"); //수정 필요





        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, C_home.newInstance()).commit();




    }

    public void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                persons.put(TAG_NAME, name);
                persons.put(TAG_ADD, address);

                personList.add(persons);
            }
            C_home.newInstance().adapter = new SimpleAdapter(
                    null, personList, R.layout.item_list,
                    new String[]{TAG_ID, TAG_NAME, TAG_ADD},
                    new int[]{R.id.id, R.id.name, R.id.address}
            );



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
*/