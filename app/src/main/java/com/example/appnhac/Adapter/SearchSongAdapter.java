package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Activity.PlayMusicActivity;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.Viewholder>{
    Context context;
    ArrayList<BaiHat> songlists;
    public SearchSongAdapter(Context context,ArrayList<BaiHat> songlists) {
        this.context = context;
        this.songlists = songlists;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_search_song,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        BaiHat baiHat = songlists.get(position);
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.imgsong);
        holder.txtsongname.setText(baiHat.getTenBaiHat());
        holder.txtsingername.setText(baiHat.getCaSi());
    }

    @Override
    public int getItemCount() {
        return songlists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgsong,imglike;
        TextView txtsongname,txtsingername;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgsong = itemView.findViewById(R.id.imageviewsearchsong);
            imglike = itemView.findViewById(R.id.imageviewsearchlike);
            txtsingername = itemView.findViewById(R.id.textviewsearchsingername);
            txtsongname   = itemView.findViewById(R.id.textviewsearchsongname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("Song",songlists.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
            imglike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imglike.setImageResource(R.drawable.iconloved);
                    DataService dataService = APIService.getService();
                    Call<String> callback = dataService.UpdateLike("1",songlists.get(getLayoutPosition()).getIdBaiHat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            if (result.equals("Successfully")) {
                                Toast.makeText(context, "Đã Thích",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Lỗi!!!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imglike.setEnabled(false);
                }
            });
        }
    }
}
