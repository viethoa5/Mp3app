package com.example.appnhac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnhac.Activity.ActivitySongList;
import com.example.appnhac.Activity.ListPlayListActivity;
import com.example.appnhac.Adapter.PlayListAdapter;
import com.example.appnhac.Model.PlayList;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Playlist extends Fragment {
    View view;
    ListView lvplaylists;
    TextView titleplaylist,viewmoreplaylist;
    List<PlayList> playLists;
    PlayListAdapter playlistAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist,container,false);
        lvplaylists = view.findViewById(R.id.listviewplaylist);
        titleplaylist = view.findViewById(R.id.titleplaylist);
        viewmoreplaylist = view.findViewById(R.id.viewmoreplaylist);
        viewmoreplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListPlayListActivity.class);
                startActivity(intent);
            }
        });
        GetData();
        return view;
    }

    public void GetData(){
        DataService dataService = APIService.getService();
        Call<List<PlayList>> callback = dataService.GetPlaylistCurrentDay();
        callback.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                playLists = response.body();
                playlistAdapter = new PlayListAdapter(getActivity(), android.R.layout.simple_list_item_1, playLists);
                lvplaylists.setAdapter(playlistAdapter);
                setListViewHeightBasedOnChildren(lvplaylists);
                lvplaylists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), ActivitySongList.class);
                        intent.putExtra("itemplaylist",playLists.get(i));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
