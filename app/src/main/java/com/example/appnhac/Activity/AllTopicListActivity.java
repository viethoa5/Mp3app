package com.example.appnhac.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Adapter.ListAllTopicAdapter;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTopicListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListAllTopicAdapter listAllTopicAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_topic_list);
        init();
        GetData();
    }
    private void init() {
        recyclerView = findViewById(R.id.recycleviewalltopic);
        toolbar      = findViewById(R.id.toolbarlisttopic);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callback = dataService.GetFullTopic();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                   ArrayList<ChuDe> topic = (ArrayList<ChuDe>) response.body();
                   listAllTopicAdapter = new ListAllTopicAdapter(AllTopicListActivity.this,topic);
                   recyclerView.setLayoutManager(new GridLayoutManager(AllTopicListActivity.this,1));
                   recyclerView.setAdapter(listAllTopicAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }
}