package com.example.appnhac.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Adapter.SongHotAdapter;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Song_Hot extends Fragment {

    View view;
    RecyclerView recyclerViewSong;
    SongHotAdapter songHotAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_song,container,false);
        recyclerViewSong = view.findViewById(R.id.RecycleviewSong);
        GetData();
        return view;
    }

    public void GetData() {
        DataService dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetSongLikedMost();
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> songlists = (ArrayList<BaiHat>) response.body();
                songHotAdapter = new SongHotAdapter(getActivity(),songlists);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewSong.setLayoutManager(linearLayoutManager);
                recyclerViewSong.setAdapter(songHotAdapter);
                Log.d("BBB",songlists.get(0).getHinhBaiHat());
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
