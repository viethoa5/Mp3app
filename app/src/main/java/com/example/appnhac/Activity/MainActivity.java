package com.example.appnhac.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appnhac.Adapter.MainViewPagerAdapter;
import com.example.appnhac.Fragment.Fragment_Tim_Kiem;
import com.example.appnhac.Fragment.Fragment_Trang_Chu;
import com.example.appnhac.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MainViewPagerAdapter mainViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        init();
    }
    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(MainActivity.this);
        mainViewPagerAdapter.addFragment(new Fragment_Trang_Chu());
        mainViewPagerAdapter.addFragment(new Fragment_Tim_Kiem());
        viewPager.setAdapter(mainViewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
          switch (position) {
                case 0 :
                   tab.setText("Trang Chu");
                   tab.setIcon(R.drawable.icontrangchu);
                   break;
                case 1 :
                   tab.setText("Tim Kiem");
                   tab.setIcon(R.drawable.icontimkiem);
                   break;
            }
       }).attach();
    }
    private void anhxa() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
    }
}