package com.example.appnhac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;

import java.util.ArrayList;

public class PlaySongAdapter extends RecyclerView.Adapter<PlaySongAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> arraysong;
    public PlaySongAdapter(Context context,ArrayList<BaiHat> arraysong) {
        this.context = context;
       this.arraysong = arraysong;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_play_song,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                BaiHat song = arraysong.get(position);
                holder.txtsongname.setText(song.getTenBaiHat());
                holder.txtsingername.setText(song.getCaSi());
                String h = position + "";
                holder.txtindext.setText(h);
    }

    @Override
    public int getItemCount() {
        return arraysong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtsongname,txtsingername,txtindext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtindext = itemView.findViewById(R.id.textviewplayindext);
            txtsingername = itemView.findViewById(R.id.textviewplaysingersong);
            txtsongname   = itemView.findViewById(R.id.textviewplaynamesong);
        }
    }
}
