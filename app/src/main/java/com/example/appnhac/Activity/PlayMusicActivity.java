package com.example.appnhac.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appnhac.Adapter.ViewPagerPlaylistSong;
import com.example.appnhac.Fragment.Fragment_Disk;
import com.example.appnhac.Fragment.Fragment_Play_Song_Lists;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {
    Toolbar toolbarplaymusic;
    TextView txttimesong,txttotaltime;
    ImageButton imgplay,imgnext,imgpre,imgrepeat,imgrandom;
    ViewPager2 viewPagerplaynhac;
    SeekBar seekBar;
    static ViewPagerPlaylistSong playlistSong;
    Fragment_Disk fragmentDisk;
    Fragment_Play_Song_Lists fragmentPlaySongLists;
    MediaPlayer mediaPlayer;
    int postition = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean checknext = false;

    public static ArrayList<BaiHat> arraysongs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        GetDataIntent();
        init();
        eventclick();
    }
    private void eventclick() {
        Handler handler = new Handler();
       // handler.postDelayed(new Runnable() {
     //       @Override
     //       public void run() {
     //          if (playlistSong.fragments.get(1) != null) {
    //               fragmentDisk.Playnhac(arraysongs.get(0).getHinhBaiHat());
    //           }
   //         }
    //    },300);
      imgplay.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (mediaPlayer.isPlaying()) {
                  mediaPlayer.pause();
                  imgplay.setImageResource(R.drawable.iconplay);
              } else {
                  mediaPlayer.start();
                  imgplay.setImageResource(R.drawable.iconpause);
              }
          }
      });
      imgrepeat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (repeat == false) {
                  if (checkrandom == true) {
                      checkrandom = false;
                      imgrandom.setImageResource(R.drawable.iconsuffle);
                  }
                  repeat = true;
                  imgrepeat.setImageResource(R.drawable.iconsyned);
              } else {
                  repeat = false;
                  imgrepeat.setImageResource(R.drawable.iconrepeat);
              }
          }
      });
      imgrandom.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (checkrandom == false) {
                  if (repeat == true) {
                      repeat = false;
                      imgrepeat.setImageResource(R.drawable.iconrepeat);
                  }
                  checkrandom = true;
                  imgrandom.setImageResource(R.drawable.iconshuffled);
              } else {
                  checkrandom = false;
                  imgrandom.setImageResource(R.drawable.iconsuffle);
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
                  mediaPlayer.seekTo(seekBar.getProgress());
          }
      });
      imgnext.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (arraysongs.size() > 0) {
                  if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                      mediaPlayer.stop();
                      mediaPlayer.release();
                      mediaPlayer = null;
                  }
                  if (postition < arraysongs.size()) {
                      imgplay.setImageResource(R.drawable.iconpause);
                      postition++;
                      if (repeat == true) {
                          if (postition == 0) {
                              postition = arraysongs.size();
                          }
                          postition -= 1;
                      }
                      if (checkrandom == true) {
                          Random random = new Random();
                          Log.d("BBB",arraysongs.size()+"");
                          int indext = random.nextInt(arraysongs.size());
                          Log.d("BBB",indext+"");
                          if (indext == postition) {
                              postition = indext - 1;
                          } else {
                              postition = indext;
                          }
                      }
                      if (postition > arraysongs.size() - 1) {
                          postition = 0;
                      }
                      new PlayMp3().execute(arraysongs.get(postition).getLinkBaiHat());
                      getSupportActionBar().setTitle(arraysongs.get(postition).getTenBaiHat());
                      updateTime();
                  }
              }
              imgpre.setClickable(false);
              imgnext.setClickable(false);
              Handler handler1 = new Handler();
              handler1.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                     imgpre.setClickable(true);
                     imgnext.setClickable(true);

                  }
              },5000);
          }
      });
        imgpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arraysongs.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (postition < arraysongs.size()) {
                        imgplay.setImageResource(R.drawable.iconpause);
                        postition--;
                        if (postition < 0) {
                            postition = arraysongs.size() - 1;
                        }
                        if (repeat == true) {
                            if (postition == 0) {
                                postition = arraysongs.size();
                            }
                            postition += 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int indext = random.nextInt(arraysongs.size());
                            if (indext == postition) {
                                postition = indext - 1;
                            } else {
                                postition = indext;
                            }
                        }
                        new PlayMp3().execute(arraysongs.get(postition).getLinkBaiHat());
                        getSupportActionBar().setTitle(arraysongs.get(postition).getTenBaiHat());
                        updateTime();
                    }
                }
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);

                    }
                },5000);
            }
        });
    }

    private void GetDataIntent() {
        Intent intent = getIntent();
        arraysongs.clear();
        if (intent != null) {
            if (intent.hasExtra("Song")) {
                BaiHat baiHat = intent.getParcelableExtra("Song");
                arraysongs.add(baiHat);
            }
            if (intent.hasExtra("Songs")) {
                ArrayList<BaiHat> songarraylist = intent.getParcelableArrayListExtra("Songs");
                arraysongs = songarraylist;
            }
        }
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
                finish();
                mediaPlayer.pause();
                arraysongs.clear();
            }
        });
        toolbarplaymusic.setTitleTextColor(Color.WHITE);
        playlistSong = new ViewPagerPlaylistSong(PlayMusicActivity.this);
        fragmentDisk = new Fragment_Disk();
        fragmentPlaySongLists = new Fragment_Play_Song_Lists();
        playlistSong.addFragment(fragmentPlaySongLists);
        playlistSong.addFragment(fragmentDisk);
    //    fragmentDisk = (Fragment_Disk) playlistSong.fragments.get(1);
        viewPagerplaynhac.setAdapter(playlistSong);
        if (arraysongs.size() > 0) {
            new PlayMp3().execute(arraysongs.get(0).getLinkBaiHat());
            getSupportActionBar().setTitle(arraysongs.get(0).getTenBaiHat());
            imgplay.setImageResource(R.drawable.iconpause);
        }
    }
    class PlayMp3 extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            updateTime();
        }
    }
        private void TimeSong() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            txttotaltime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
            seekBar.setMax(mediaPlayer.getDuration());
        }
        private void updateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm:ss");
                    txttimesong.setText(simpleDateFormat2.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            checknext = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
                 if (postition < arraysongs.size()) {
                     imgplay.setImageResource(R.drawable.iconpause);
                     postition++;
                     if (repeat == true) {
                         if (postition == 0) {
                             postition = arraysongs.size();
                         }
                         postition -= 1;
                     }
                     if (checkrandom == true) {
                         Random random = new Random();
                         int indext = random.nextInt(arraysongs.size());
                         if (indext == postition) {
                             postition = indext - 1;
                         }
                     }
                     if (postition > arraysongs.size() - 1) {
                         postition = 0;
                     }
                     new PlayMp3().execute(arraysongs.get(postition).getLinkBaiHat());
                     getSupportActionBar().setTitle(arraysongs.get(postition).getTenBaiHat());
                 }
                imgpre.setClickable(false);
                imgnext.setClickable(false);
                Handler handler4 = new Handler();
                handler4.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgnext.setClickable(true);

                    }
                },3000);
                checknext = false;
                handler4.removeCallbacks(this);
             } else {
                 handler3.postDelayed(this,1000);
             }
            }
        },1000);

    }
}