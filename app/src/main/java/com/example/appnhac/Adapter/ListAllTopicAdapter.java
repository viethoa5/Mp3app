package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Activity.ListCategoryFollowTopicActivity;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAllTopicAdapter extends RecyclerView.Adapter<ListAllTopicAdapter.ViewHolder> {
    Context context;
    ArrayList<ChuDe> topiclist;
    public ListAllTopicAdapter(Context context,ArrayList<ChuDe> topiclist) {
           this.context = context;
           this.topiclist = topiclist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_topic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           ChuDe topic = topiclist.get(position);
           if (topic == null) {
               return;
           }
           Picasso.with(context).load(topic.getHinhChuDe()).into(holder.imgpictopic);
    }

    @Override
    public int getItemCount() {
        return topiclist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgpictopic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgpictopic = itemView.findViewById(R.id.imageviewtopic);
            imgpictopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListCategoryFollowTopicActivity.class);
                    intent.putExtra("chude",topiclist.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
