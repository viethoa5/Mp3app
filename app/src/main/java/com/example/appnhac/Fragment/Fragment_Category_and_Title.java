package com.example.appnhac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appnhac.Activity.ActivitySongList;
import com.example.appnhac.Activity.AllTopicListActivity;
import com.example.appnhac.Activity.ListCategoryFollowTopicActivity;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.Model.TheLoai;
import com.example.appnhac.Model.Theloaitrongngay;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Category_and_Title extends Fragment {
    View view;
    HorizontalScrollView scrollView;
    TextView txtseemore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_and_title,container,false);
        scrollView = view.findViewById(R.id.horizontalscollview);
        txtseemore = view.findViewById(R.id.seemore);
        txtseemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllTopicListActivity.class);
                startActivity(intent);
            }
        });
        GetData();
        return view;
    }
    public void GetData(){
        DataService dataService = APIService.getService();
        Call<Theloaitrongngay> callback = dataService.GetCategoryCurrentDay();
        callback.enqueue(new Callback<Theloaitrongngay>() {
            @Override
            public void onResponse(Call<Theloaitrongngay> call, Response<Theloaitrongngay> response) {
                Theloaitrongngay theloaitrongngay =  response.body();
                final ArrayList<ChuDe> titlelists = new ArrayList<>();
                titlelists.addAll(theloaitrongngay.getChuDe());
                int h = titlelists.size();
                final ArrayList<TheLoai> categorylists = new ArrayList<>();
                categorylists.addAll(theloaitrongngay.getTheloai());
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(580,250);
                layout.setMargins(10,20,10,30);
                for (int i = 0; i < titlelists.size(); i++) {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (titlelists.get(i).getHinhChuDe() != null) {
                        Picasso.with(getActivity()).load(titlelists.get(i).getHinhChuDe()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ListCategoryFollowTopicActivity.class);
                            intent.putExtra("chude",titlelists.get(finalI));
                            startActivity(intent);
                        }
                    });
                }
                for (int j = 0; j < categorylists.size(); j++) {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (categorylists.get(j).getHinhTheLoai() != null) {
                        Picasso.with(getActivity()).load(categorylists.get(j).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ActivitySongList.class);
                            intent.putExtra("idtheloai",categorylists.get(finalJ));
                            startActivity(intent);
                        }
                    });
                }
                scrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<Theloaitrongngay> call, Throwable t) {

            }
        });
    }
}
