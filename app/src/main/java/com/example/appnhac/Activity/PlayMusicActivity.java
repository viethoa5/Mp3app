package com.example.appnhac.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appnhac.Adapter.ViewPagerPlaylistSong;
import com.example.appnhac.Fragment.Fragment_Disk;
import com.example.appnhac.Fragment.Fragment_Play_Song_Lists;
import com.example.appnhac.R;
import com.example.appnhac.Service.Service;

import java.text.SimpleDateFormat;

public class PlayMusicActivity extends AppCompatActivity {
    public static final int updatetime = -100000;
    Toolbar toolbarplaymusic;
    TextView txttimesong,txttotaltime;
    ImageButton imgplay,imgnext,imgpre,imgrepeat,imgrandom;
    ViewPager2 viewPagerplaynhac;
    public SeekBar seekBar;
    static ViewPagerPlaylistSong playlistSong;
    Fragment_Disk fragmentDisk;
    Fragment_Play_Song_Lists fragmentPlaySongLists;
    int postition = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean checknext = false;
    public String songname = "";
    public boolean playingmusic = true;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             if (intent == null) {
                 return;
             }
             songname = intent.getStringExtra("Songname");
             Log.d("BBBBBB",songname);
             playingmusic  =  intent.getBooleanExtra("Run",false);
             int actionmusic = intent.getIntExtra("Action",0);
             handleactionfromserver(actionmusic);
        }
    };
    private BroadcastReceiver timeSong = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            TimeSong();
            updateTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        init();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("sendDataToActivity"));
        LocalBroadcastManager.getInstance(this).registerReceiver(timeSong, new IntentFilter("UpdateTime"));
        TimeSong();
        updateTime();
        eventclick();
    }


    private void eventclick() {
    imgplay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("BBBBBBB",playingmusic + "");
            if (playingmusic) {
                sendDatatoService(Service.Action_Pause);
            } else {
                sendDatatoService(Service.Action_Resume);
            }
        }
    });
      imgrepeat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             if (repeat) {
                 sendDatatoService(Service.Action_Nonrepeat);
             } else {
                 sendDatatoService(Service.Action_Repeat);
             }
        }
    });
      imgrandom.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           if (checkrandom) {
               sendDatatoService(Service.Action_Nonrandomize);
           } else {
               sendDatatoService(Service.Action_Randomize);
           }
        }
    });
      seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (playingmusic) {
                Service.player.seekTo(seekBar.getProgress());
            }
        }
    });
      imgnext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           sendDatatoService(Service.Action_Next);
        }
    });
        imgpre.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendDatatoService(Service.Action_Back);
        }
    });
}
    private void sendDatatoService(int actions) {
        Intent intent = new Intent(this,Service.class);
        intent.putExtra("clickaction",actions);
        startService(intent);
    }

    private void init() {
        toolbarplaymusic = findViewById(R.id.toolbarplaysong);
        txttimesong      = findViewById(R.id.textviewtimesong);
        txttotaltime     = findViewById(R.id.totaltimesong);
        imgplay          = findViewById(R.id.imagebuttonplay);
        imgnext          = findViewById(R.id.imagebuttonnext);
        imgpre           = findViewById(R.id.imagebuttonpreview);
        imgrepeat        = findViewById(R.id.imagebuttonrepeat);
        imgrandom        = findViewById(R.id.imagebuttonsuffle);
        viewPagerplaynhac = findViewById(R.id.viewpagerplaymusic);
        seekBar           = findViewById(R.id.seekbarsong);
        setSupportActionBar(toolbarplaymusic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaymusic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendDatatoService(Service.Action_Exit);
            }
        });
        toolbarplaymusic.setTitleTextColor(Color.WHITE);
        playlistSong = new ViewPagerPlaylistSong(PlayMusicActivity.this);
        fragmentDisk = new Fragment_Disk();
        fragmentPlaySongLists = new Fragment_Play_Song_Lists();
        playlistSong.addFragment(fragmentPlaySongLists);
        playlistSong.addFragment(fragmentDisk);
        viewPagerplaynhac.setAdapter(playlistSong);
        if (Service.arraysongs.size() > 0) {
            getSupportActionBar().setTitle(Service.arraysongs.get(0).getTenBaiHat());
            imgplay.setImageResource(R.drawable.iconpause);
        }
    }
        public void TimeSong() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            txttotaltime.setText(simpleDateFormat.format(Service.player.getDuration()));
            seekBar.setMax(Service.player.getDuration());
        }
        public void updateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Service.player != null) {
                    seekBar.setProgress(Service.player.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm:ss");
                    txttimesong.setText(simpleDateFormat2.format(Service.player.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    Service.player.setOnCompletionListener(mediaPlayer -> {
                        checknext = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
              }
            },300);
        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
             if (checknext) {
                 sendDatatoService(Service.Action_Next);
                 imgpre.setClickable(false);
                 imgnext.setClickable(false);
                Handler handler4 = new Handler();
                handler4.postDelayed(() -> {
                    imgpre.setClickable(true);
                    imgnext.setClickable(true);

                },3000);
                checknext = false;
                handler4.removeCallbacks(this);
             } else {
                 handler3.postDelayed(this,1000);
             }
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timeSong);
    }

    private void handleactionfromserver(int actionmusic) {
            getSupportActionBar().setTitle(songname);
            switch (actionmusic) {
                case Service.Action_Back:
                case Service.Action_Next:
                    imgplay.setImageResource(R.drawable.iconpause);
                    imgpre.setClickable(false);
                    imgnext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(() -> {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);

                    },5000);
                    updateTime();
                    break;
                case Service.Action_Exit:
                    finish();
                    break;
                case Service.Action_Nonrandomize:
                    checkrandom = false;
                    imgrandom.setImageResource(R.drawable.iconsuffle);
                    break;
                case Service.Action_Nonrepeat:
                    repeat = false;
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    break;
                case Service.Action_Randomize:
                    if (repeat) {
                        repeat = false;
                        imgrepeat.setImageResource(R.drawable.iconrepeat);
                    }
                        checkrandom = true;
                        imgrandom.setImageResource(R.drawable.iconshuffled);
                    break;
                case Service.Action_Repeat :
                    if (checkrandom) {
                        checkrandom = false;
                        imgrandom.setImageResource(R.drawable.iconsuffle);
                    }
                        repeat = true;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                    break;
                case Service.Action_Resume:
                        imgplay.setImageResource(R.drawable.iconpause);
                    break;
                case Service.Action_Pause:
                        imgplay.setImageResource(R.drawable.iconplay);
                    break;
            }
    }
}
