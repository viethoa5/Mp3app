package com.example.appnhac.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Adapter.CategoryListAdapter;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.Model.TheLoai;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoryFollowTopicActivity extends AppCompatActivity {
    ChuDe topic;
    RecyclerView recyclerView;
    CategoryListAdapter categoryListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category_follow_topic);
        GetIntent();
        init();
        GetData();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycleviewcategoryfollowtopic);
    }
    private void GetIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude")) {
            topic = (ChuDe) intent.getSerializableExtra("chude");
        }
    }
    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.GetCategoryFollowTopicList(topic.getIdChuDe());
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> categorylist = (ArrayList<TheLoai>) response.body();
                categoryListAdapter = new CategoryListAdapter(ListCategoryFollowTopicActivity.this,categorylist);
                recyclerView.setLayoutManager(new GridLayoutManager(ListCategoryFollowTopicActivity.this,2));
                recyclerView.setAdapter(categoryListAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }
}