package com.example.appnhac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnhac.Model.PlayList;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayListAdapter extends ArrayAdapter<PlayList> {
    public PlayListAdapter(@NonNull Context context, int resource, @NonNull List<PlayList> objects) {
        super(context, resource, objects);
    }
    public class Viewholder {
        TextView playlistname;
        ImageView playlistbgimg;
        ImageView playlistimg;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder viewholder = null;
        if (viewholder == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_playlist,null);
            viewholder = new Viewholder();
            viewholder.playlistname = convertView.findViewById(R.id.playlistname);
            viewholder.playlistimg  = convertView.findViewById(R.id.ImgPlaylist);
            viewholder.playlistbgimg = convertView.findViewById(R.id.imageViewbgplaylist);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        PlayList playList = getItem(position);
        Picasso.with(getContext()).load(playList.getHinhPlaylist()).into(viewholder.playlistbgimg);
        Picasso.with(getContext()).load(playList.getIcon()).into(viewholder.playlistimg);
        viewholder.playlistname.setText(playList.getTen());
        return convertView;
    }
}
