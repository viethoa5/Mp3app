package com.example.appnhac.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Adapter.ListPlayListAdapter;
import com.example.appnhac.Model.PlayList;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPlayListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewplaylists;
    ArrayList<PlayList> fullplaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_play_list);
        anhxa();
        init();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<PlayList>> callback = dataService.GetFullPlaylist();
        callback.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                fullplaylist = (ArrayList<PlayList>) response.body();
                ListPlayListAdapter listPlayListAdapter = new ListPlayListAdapter(ListPlayListActivity.this,fullplaylist);
                recyclerViewplaylists.setLayoutManager(new GridLayoutManager(ListPlayListActivity.this,2));
                recyclerViewplaylists.setAdapter(listPlayListAdapter);
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }

    public void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Playlist");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple_200));
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbarlistplaylist);
        recyclerViewplaylists = findViewById(R.id.recycleviewlistplaylist);
    }
}