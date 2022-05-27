package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class SongHotAdapter extends RecyclerView.Adapter<SongHotAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> songlists;

    public SongHotAdapter(Context context,ArrayList<BaiHat> songlists) {
        this.context = context;
        this.songlists = songlists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_song_hot,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat song = songlists.get(position);
        if (song == null) {
            return;
        }
        Picasso.with(context).load(song.getHinhBaiHat()).into(holder.imgsong);
        holder.txtsongname.setText(song.getTenBaiHat());
        holder.txtsingername.setText(song.getCaSi());
    }

    @Override
    public int getItemCount() {
        return songlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtsongname,txtsingername;
        ImageView imgsong,imglike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsongname = itemView.findViewById(R.id.textviewsongtitle);
            txtsingername = itemView.findViewById(R.id.textviewsingername);
            imgsong       = itemView.findViewById(R.id.imageviewsonghot);
            imglike       = itemView.findViewById(R.id.imgaviewlike);
            imglike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imglike.setImageResource(R.drawable.iconloved);
                    DataService dataService = APIService.getService();
                    Call<String> callback   = dataService.UpdateLike("1",songlists.get(getLayoutPosition()).getIdBaiHat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            Log.d("BBBBB",result);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("Song",songlists.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
