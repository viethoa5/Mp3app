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
import com.example.appnhac.Model.Album;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.Viewholder>{
    Context context;
    ArrayList<Album> albumlists;
    public AlbumAdapter(Context context,ArrayList<Album> albumlists) {
        this.context = context;
        this.albumlists = albumlists;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_album,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
             Album album = albumlists.get(position);
             if (album == null) {
                 return;
             }
        Picasso.with(context).load(album.getHinhanhAlbum()).into(holder.albumpic);
        holder.albumname.setText(album.getTenAlbum());
        holder.albumsingername.setText(album.getTenCasialbum());
    }

    @Override
    public int getItemCount() {
        return albumlists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView albumpic;
        TextView albumname,albumsingername;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            albumpic = itemView.findViewById(R.id.imageviewalbum);
            albumname = itemView.findViewById(R.id.textviewalbumname);
            albumsingername = itemView.findViewById(R.id.textviewalbumsingername);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ActivitySongList.class);
                    intent.putExtra("idalbum",albumlists.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
