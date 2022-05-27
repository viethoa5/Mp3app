package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Activity.ActivitySongList;
import com.example.appnhac.Model.QuangCao;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerAdapter2 extends RecyclerView.Adapter<BannerAdapter2.PhotoViewholder2> {
    private List<QuangCao> quangCaos;
    Context context;
    public BannerAdapter2(Context context,List<QuangCao> quangCaos) {
        this.quangCaos = quangCaos;
        this.context = context;
    }
    @NonNull
    @Override
    public PhotoViewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_banner,parent,false);
        return new PhotoViewholder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewholder2 holder, int position) {
           QuangCao quangCao = quangCaos.get(position);
           if (quangCao == null) {
               return;
           }
        Picasso.with(context).load(quangCao.getHinhanh()).into(holder.imgbanner);
        Picasso.with(context).load(quangCao.getHinhBaiHat()).into(holder.imgsong);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivitySongList.class);
                intent.putExtra("banner",quangCao);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (quangCaos != null) {
            return quangCaos.size();
        }
            return 0;
    }

    public class PhotoViewholder2 extends RecyclerView.ViewHolder {
        ImageView imgbanner;
        ImageView imgsong;
        RelativeLayout relativeLayout;


        public PhotoViewholder2(@NonNull View itemView) {
            super(itemView);
            imgbanner = itemView.findViewById(R.id.imageView);
            imgsong   = itemView.findViewById(R.id.ImageViewbanner);
            relativeLayout = itemView.findViewById(R.id.layoutBanner);
        }
    }
}
