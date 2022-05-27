package com.example.appnhac.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnhac.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Disk extends Fragment {
    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disk,container,false);
        circleImageView = view.findViewById(R.id.imageviewcircle);
        objectAnimator  = ObjectAnimator.ofFloat(circleImageView,"rotation",0f,360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        return view;
    }
    public void Playnhac(String hinhanh) {
        Loadbackground loadbackground = new Loadbackground();
        try {
            circleImageView.setImageDrawable(loadbackground.execute(hinhanh).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private class Loadbackground extends AsyncTask<String,Void, BitmapDrawable> {

        @Override
        protected BitmapDrawable doInBackground(String... strings) {
            Bitmap bitmap = null;
            BitmapDrawable bitmapDrawable = null;
            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmapDrawable;
        }
    }
}
