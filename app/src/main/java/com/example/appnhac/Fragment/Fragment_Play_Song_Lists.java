package com.example.appnhac.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Activity.PlayMusicActivity;
import com.example.appnhac.Adapter.PlaySongAdapter;
import com.example.appnhac.R;

public class Fragment_Play_Song_Lists extends Fragment {
    View view;
    RecyclerView recyclerviewplaysong;
    PlaySongAdapter playSongAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_song_list,container,false);
        recyclerviewplaysong = view.findViewById(R.id.recyclevierplaysong);
        if (PlayMusicActivity.arraysongs.size() > 0) {
            playSongAdapter = new PlaySongAdapter(getActivity(), PlayMusicActivity.arraysongs);
            recyclerviewplaysong.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerviewplaysong.setAdapter(playSongAdapter);
        }

        return view;
    }
}
