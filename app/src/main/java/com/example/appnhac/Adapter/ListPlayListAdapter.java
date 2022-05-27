package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Activity.ActivitySongList;
import com.example.appnhac.Model.PlayList;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListPlayListAdapter extends RecyclerView.Adapter<ListPlayListAdapter.Viewholder> {
    Context context;
    ArrayList<PlayList> listplaylists;

    public ListPlayListAdapter(Context context,ArrayList<PlayList> listplaylists) {
        this.context = context;
        this.listplaylists = listplaylists;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_list_playlist,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        PlayList playList = listplaylists.get(position);
        Picasso.with(context).load(playList.getHinhPlaylist()).into(holder.imgbg);
        holder.txttitleplaylist.setText(playList.getTen());
    }

    @Override
    public int getItemCount() {
        return listplaylists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgbg;
        TextView txttitleplaylist;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgbg = itemView.findViewById(R.id.imageViewlistplaylist);
            txttitleplaylist = itemView.findViewById(R.id.textviewtitlelistplaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ActivitySongList.class);
                    intent.putExtra("itemplaylist",listplaylists.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
