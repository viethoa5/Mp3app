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

public class FullAlbumAdapter extends RecyclerView.Adapter<FullAlbumAdapter.Viewholder>{
    Context context;
    ArrayList<Album> albumlist;

    public FullAlbumAdapter(Context context, ArrayList<Album>albumlist) {
        this.context = context;
        this.albumlist = albumlist;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_all_album,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
         Album album = albumlist.get(position);
         if (album == null) {
             return;
         }
        Picasso.with(context).load(album.getHinhanhAlbum()).into(holder.imgalbum);
        holder.txtalbumname.setText(album.getTenAlbum());
    }

    @Override
    public int getItemCount() {
        return albumlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgalbum;
        TextView txtalbumname;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgalbum = itemView.findViewById(R.id.imageViewlistalbum);
            txtalbumname = itemView.findViewById(R.id.textviewalbumname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ActivitySongList.class);
                    intent.putExtra("idalbum",albumlist.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
