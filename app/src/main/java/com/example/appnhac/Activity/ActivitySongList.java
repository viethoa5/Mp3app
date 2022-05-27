package com.example.appnhac.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Adapter.SongListsAdapter;
import com.example.appnhac.Model.Album;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.Model.PlayList;
import com.example.appnhac.Model.QuangCao;
import com.example.appnhac.Model.TheLoai;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySongList extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewsonglists;
    FloatingActionButton floatingActionButton;
    QuangCao quangCao;
    ImageView imgsonglists;
    ArrayList<BaiHat> activitysonglists;
    SongListsAdapter songListsAdapter;
    PlayList playList;
    TheLoai category;
    Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        DataIntent();
        anhxa();
        init();
        if (quangCao != null && !quangCao.getTenBaiHat().equals("")) {
            setValueInView(quangCao.getTenBaiHat(),quangCao.getHinhBaiHat());
            GetDataQuangCao(quangCao.getIdQuangCao());
        }
        if (playList != null && !playList.getTen().equals("")) {
            setValueInView(playList.getTen(), playList.getHinhPlaylist());
            GetDataPlayList(playList.getIdPlaylist());
        }
        if (category != null && !category.getTenTheLoai().equals("")) {
            setValueInView(category.getTenTheLoai(), category.getHinhTheLoai());
            GetDataCategory(category.getIdTheloai());
        }
        if (album != null && !album.getTenAlbum().equals("")) {
            setValueInView(album.getTenAlbum(),album.getHinhanhAlbum());
            GetDataAlbum(album.getIdAlbum());
        }
    }
    private void GetDataAlbum(String idalbum) {
       DataService dataService = APIService.getService();
       Call<List<BaiHat>> callback = dataService.GetAlbumSongListbanner(idalbum);
       loaddata(callback);
    }
    private void GetDataCategory(String idtheloai) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetPlaylistSongListbanner(idtheloai);
        loaddata(callback);
    }
    private void GetDataPlayList(String idPlaylist) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetPlaylistSongListbanner(idPlaylist);
        loaddata(callback);
    }

    private void GetDataQuangCao(String idquangcao) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetSongListbanner(idquangcao);
        loaddata(callback);
    }

    private void setValueInView(String ten,String hinh) {
        collapsingToolbarLayout.setTitle(ten);
        Loadbackground loadbackground = new Loadbackground();
        try {
            collapsingToolbarLayout.setBackground(loadbackground.execute(hinh).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinh).into(imgsonglists);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbarlist);
        recyclerViewsonglists = findViewById(R.id.recycleviewsonglist);
        floatingActionButton  = findViewById(R.id.floatingbuttonaction);
        imgsonglists          = findViewById(R.id.imageviewlistsong);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
    }
    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("banner")) {
                quangCao =(QuangCao) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemplaylist")) {
                playList = (PlayList) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")) {
               category  = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("idalbum")) {
                album    = (Album) intent.getSerializableExtra("idalbum");
            }
        }
    }

    private void loaddata(Call<List<BaiHat>> callback) {
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                activitysonglists = (ArrayList<BaiHat>) response.body();
                songListsAdapter = new SongListsAdapter(ActivitySongList.this,activitysonglists);
                recyclerViewsonglists.setLayoutManager(new LinearLayoutManager(ActivitySongList.this));
                recyclerViewsonglists.setAdapter(songListsAdapter);
                eventclick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
    private class Loadbackground extends AsyncTask<String,Void, BitmapDrawable> {

        @Override
        protected BitmapDrawable doInBackground(String... strings) {
            Bitmap bitmap = null;
            BitmapDrawable bitmapDrawable = null;
            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmapDrawable;
        }
    }
    private void eventclick() {
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySongList.this,PlayMusicActivity.class);
                intent.putExtra("Songs",activitysonglists);
                startActivity(intent);
            }
        });
    }
}