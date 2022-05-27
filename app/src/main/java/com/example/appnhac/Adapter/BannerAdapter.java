package com.example.appnhac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.appnhac.Model.QuangCao;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    Context context;
    List<QuangCao> quangCaos;

    public BannerAdapter(Context context,List<QuangCao> quangCaos) {
        this.context = context;
        this.quangCaos = quangCaos;
    }

    @Override
    public int getCount() {
        return quangCaos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_banner,null);

        ImageView bgbanner = view.findViewById(R.id.imageView);
        ImageView songpic = view.findViewById(R.id.ImageViewbanner);
        Picasso.with(context).load(quangCaos.get(position).getHinhanh()).into(bgbanner);
        Picasso.with(context).load(quangCaos.get(position).getHinhBaiHat()).into(songpic);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
