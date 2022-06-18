package com.example.appnhac.Service;

import static com.example.appnhac.Service.MusicNotification.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.appnhac.Activity.PlayMusicActivity;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Service extends android.app.Service {
    public static MediaPlayer player = new MediaPlayer();
    public static ArrayList<BaiHat> arraysongs = new ArrayList<>();
    public static final int Action_Pause = 1;
    public static final int Action_Next  = 2;
    public static final int Action_Back  = 3;
    public static final int Action_Resume  = 4;
    public static final int Action_Exit  = 100000;
    public static final int Action_Repeat  = 5;
    public static final int Action_Nonrepeat  = -5;
    public static final int Action_Randomize  = 6;
    public static final int Action_Nonrandomize  = -6;
    public static boolean repeat = false;
    public static boolean randomize = false;
    public static boolean playingmusic = false;
    public  static int postition = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("BBBBB","MyService on Create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        GetDataIntent(intent);
        int actionmusic = intent.getIntExtra("clickaction", 0);
        if (actionmusic != 0) {
            handleaction(actionmusic);
        } else {
            Intent intent1  = new Intent(this,PlayMusicActivity.class);
            startActivity(intent1);
            startMusic(Action_Resume);
        }
        return START_NOT_STICKY;
    }

    private void startMusic(int actions) {
        playingmusic = true;
        sendNotificationsong(arraysongs.get(postition));
        sendactiontoActivity(actions);
        new PlayMp3().execute(arraysongs.get(postition).getLinkBaiHat());
    }

    private void handleaction(int action) {
        switch (action) {
            case Action_Pause :
                stopplaying();
                break;
            case Action_Resume :
                continueplaying();
                break;
            case Action_Exit:
                repeat = false;
                randomize = false;
                playingmusic = false;
                player.pause();
                postition = 0;
                sendactiontoActivity(Action_Exit);
                arraysongs.clear();
                stopSelf();
                break;
            case Action_Next :
                playingnext();
                break;
            case Action_Back :
                playingback();
                break;
            case Action_Repeat :
                if (randomize) {
                    randomize = false;
                }
                repeat = true;
                sendNotificationsong(arraysongs.get(postition));
                sendactiontoActivity(Action_Repeat);
                break;
            case Action_Nonrepeat:
                repeat = false;
                sendNotificationsong(arraysongs.get(postition));
                sendactiontoActivity(Action_Nonrepeat);
                break;
            case Action_Nonrandomize:
                randomize = false;
                sendNotificationsong(arraysongs.get(postition));
                sendactiontoActivity(Action_Nonrandomize);
                break;
            case Action_Randomize:
                if (repeat) {
                    repeat = false;
                }
                randomize = true;
                sendNotificationsong(arraysongs.get(postition));
                sendactiontoActivity(Action_Randomize);
                break;
        }
    }

    private void playingback() {
        if (player != null && playingmusic) {
            player.stop();
            player.release();
            player = null;
            playingmusic = false;
        }
        if (postition < arraysongs.size()) {
            postition--;
            if (postition < 0) {
                postition = arraysongs.size() - 1;
            }
            if (repeat) {
                postition += 1;
            }
            if (randomize) {
                Random random = new Random();
                int indext = random.nextInt(arraysongs.size());
                if (indext == postition - 1) {
                    postition = random.nextInt(arraysongs.size());
                } else {
                    postition = indext;
                }
            }
            startMusic(Action_Back);
        }
    }

    private void playingnext() {
        Log.d("BBBBB",randomize+ " "+ repeat);
        if (player != null && playingmusic) {
            player.stop();
            player.release();
            player = null;
            playingmusic = false;
        }
        if (postition < arraysongs.size()) {
            postition++;
            if (repeat) {
                if (postition == 0) {
                    postition = arraysongs.size();
                }
                postition -= 1;
            }
            if (randomize) {
                Random random = new Random();
                int indext = random.nextInt(arraysongs.size());
                if (indext == postition - 1) {
                    postition = random.nextInt(arraysongs.size());
                } else {
                    postition = indext;
                }
            }
        }
            if (postition > arraysongs.size() - 1) {
                postition = 0;
            }
            startMusic(Action_Next);
    }
    private void continueplaying() {
        if (player != null && !playingmusic) {
            player.start();
        }
        playingmusic = true;
        sendNotificationsong(arraysongs.get(postition));
        sendactiontoActivity(Action_Resume);
    }

    private void stopplaying() {
          if (player != null && playingmusic) {
              player.pause();
          }
          playingmusic = false;
          sendNotificationsong(arraysongs.get(postition));
          sendactiontoActivity(Action_Pause);
    }

    private void sendNotificationsong(BaiHat song) {
        Intent intent = new Intent(this, PlayMusicActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_layout_nofi);
        remoteViews.setTextViewText(R.id.textviewnofisongtitle,song.getTenBaiHat());
        remoteViews.setTextViewText(R.id.textviewnofisingertitle,song.getCaSi());
        Loadicon loadicon = new Loadicon();
        try {
            remoteViews.setImageViewBitmap(R.id.iconnoficationsong,loadicon.execute(song.getHinhBaiHat()).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (playingmusic) {
            remoteViews.setImageViewResource(R.id.nofiiconpause,R.drawable.iconpause);
            remoteViews.setOnClickPendingIntent(R.id.nofiiconpause,getpendingIntent(this,Action_Pause));
       }
        if (!playingmusic){
            remoteViews.setImageViewResource(R.id.nofiiconpause,R.drawable.iconplay);
            remoteViews.setOnClickPendingIntent(R.id.nofiiconpause,getpendingIntent(this,Action_Resume));
        }
        if (!randomize) {
                remoteViews.setImageViewResource(R.id.nofiiconrandomize,R.drawable.iconsuffle);
                remoteViews.setOnClickPendingIntent(R.id.nofiiconrandomize,getpendingIntent(this,Action_Randomize));
        }
        if (randomize) {
                remoteViews.setImageViewResource(R.id.nofiiconrandomize,R.drawable.iconshuffled);
                remoteViews.setOnClickPendingIntent(R.id.nofiiconrandomize,getpendingIntent(this,Action_Nonrandomize));
        }
        if (!repeat) {
                remoteViews.setImageViewResource(R.id.nofiiconrepeat,R.drawable.iconrepeat);
                remoteViews.setOnClickPendingIntent(R.id.nofiiconrepeat,getpendingIntent(this,Action_Repeat));
        }
        if (repeat) {
                remoteViews.setImageViewResource(R.id.nofiiconrepeat,R.drawable.iconsyned);
                remoteViews.setOnClickPendingIntent(R.id.nofiiconrepeat,getpendingIntent(this,Action_Nonrepeat));
        }
                remoteViews.setOnClickPendingIntent(R.id.nofiiconnext,getpendingIntent(this,Action_Next));
                remoteViews.setOnClickPendingIntent(R.id.nofiiconpreview,getpendingIntent(this,Action_Back));
                remoteViews.setOnClickPendingIntent(R.id.turnoffnofi,getpendingIntent(this,Action_Exit));
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_baseline_audiotrack_24)
                                        .setContentIntent(pendingIntent)
                                        .setCustomContentView(remoteViews)
                                        .setSound(null)
                                        .build();
        startForeground(1,notification);
    }
    private  PendingIntent getpendingIntent(Context context,int actions) {
          Intent intent = new Intent(this, Myreceiver.class);
          intent.putExtra("action_music",actions);
          return PendingIntent.getBroadcast(context.getApplicationContext(), actions, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private void UpdateTime() {
        Intent intent = new Intent("UpdateTime");
        intent.putExtra("updatetime",PlayMusicActivity.updatetime);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    private void sendactiontoActivity(int actions) {
        Intent intent = new Intent("sendDataToActivity");
        intent.putExtra("Songname",arraysongs.get(postition).getTenBaiHat());
        intent.putExtra("Action",actions);
        intent.putExtra("Run",playingmusic);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    private class Loadicon extends AsyncTask<String,Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
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
                player = new MediaPlayer();
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
               player.setDataSource(baihat);
               player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.start();
            UpdateTime();
        }
    }
    private void GetDataIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("Song")) {
                arraysongs.clear();
                BaiHat baiHat = intent.getParcelableExtra("Song");
                arraysongs.add(baiHat);
            }
            if (intent.hasExtra("Songs")) {
                arraysongs.clear();
                ArrayList<BaiHat> songarraylist = intent.getParcelableArrayListExtra("Songs");
                arraysongs = songarraylist;
            }
        }
    }
}
