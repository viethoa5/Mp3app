package com.example.appnhac.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Adapter.FullAlbumAdapter;
import com.example.appnhac.Model.Album;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullAlbumListsActivity extends AppCompatActivity {
    RecyclerView recyclerViewalbum;
    Toolbar toolbar;
    FullAlbumAdapter fullAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_album_lists);
        init();
        GetData();
    }
    private void  init() {
        recyclerViewalbum = findViewById(R.id.recycleviewallalbum);
        toolbar           = findViewById(R.id.toolbaralbum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Album");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.GetFullAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumlists = (ArrayList<Album>) response.body();
                fullAlbumAdapter = new FullAlbumAdapter(FullAlbumListsActivity.this,albumlists);
                recyclerViewalbum.setLayoutManager(new GridLayoutManager(FullAlbumListsActivity.this,2));
                recyclerViewalbum.setAdapter(fullAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}