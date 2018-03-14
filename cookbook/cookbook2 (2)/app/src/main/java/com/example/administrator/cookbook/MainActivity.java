package com.example.administrator.cookbook;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.ListView;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("김치볶음밥","홍길동","2017-12-20"));
        noticeList.add(new Notice("계란말이","정태수","2017-12-14"));
        noticeList.add(new Notice("탕수육","유영준","2017-12-12"));
        noticeList.add(new Notice("김치찌개","김정수","2017-12-02"));
        noticeList.add(new Notice("된장찌개","김인직","2017-11-17"));
        noticeList.add(new Notice("참치찌개","이예준","2017-11-17"));
        noticeList.add(new Notice("돈가스","한강식","2017-11-15"));
        noticeList.add(new Notice("날치알덮밥","박두일","2017-11-12"));
        noticeList.add(new Notice("전주비빔밥","이동철","2017-11-07"));
        noticeList.add(new Notice("재육볶음","쿡맘","2017-11-04"));
        adapter =new NoticeListAdapter(getApplicationContext(),noticeList);
        noticeListView.setAdapter(adapter);


    }
}
