package com.example.appnhac.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appnhac.Adapter.BannerAdapter;
import com.example.appnhac.Adapter.BannerAdapter2;
import com.example.appnhac.Model.QuangCao;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {
    View view;
    ViewPager2 viewPager;
    CircleIndicator3 circleIndicator;
    BannerAdapter bannerAdapter;
    BannerAdapter2 bannerAdapter2;
    Runnable runnable;
    Handler handler;
    int currentitem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner,container,false);
        anhxa();
        GetData();
        return view;
    }

    private void anhxa(){
        viewPager = view.findViewById(R.id.myViewPager);
        circleIndicator = view.findViewById(R.id.Indicatordefault);
    }
    private void GetData(){
        DataService dataService = APIService.getService();
        Call<List<QuangCao>> callback = dataService.GetDataBanner();
        callback.enqueue(new Callback<>() {
            @Override
            public void onResponse( Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                List<QuangCao> banners = (List<QuangCao>) response.body();
               // bannerAdapter = new BannerAdapter(getActivity(),banners);
                bannerAdapter2 = new BannerAdapter2(getActivity(),banners);
                viewPager.setAdapter(bannerAdapter2);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                  public void run() {
                       currentitem ++;
                       if (currentitem >= viewPager.getAdapter().getItemCount()) {
                           currentitem = 0;
                       }
                      viewPager.setCurrentItem(currentitem,true);
                        handler.postDelayed(runnable,4500);
                    }
               };
                handler.postDelayed(runnable,4500);
            }

            @Override
            public void onFailure(@NonNull Call<List<QuangCao>> call, @NonNull Throwable t) {

            }
        });
    }
}
