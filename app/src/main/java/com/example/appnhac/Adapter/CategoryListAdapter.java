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
import com.example.appnhac.Model.TheLoai;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.Viewholder> {
    Context context;
    ArrayList<TheLoai> categorylist;

    public CategoryListAdapter(Context context,ArrayList<TheLoai> categorylist) {
        this.context = context;
        this.categorylist = categorylist;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_category_topic_list,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TheLoai category = categorylist.get(position);
        if (category == null) {
            return;
        }
        Picasso.with(context).load(category.getHinhTheLoai()).into(holder.imgcategory);
        holder.txtcategory.setText(category.getTenTheLoai());
    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imgcategory;
        TextView txtcategory;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgcategory = itemView.findViewById(R.id.imageviewcategorytopic);
            txtcategory = itemView.findViewById(R.id.textviewcategorytopic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ActivitySongList.class);
                    intent.putExtra("idtheloai",categorylist.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
