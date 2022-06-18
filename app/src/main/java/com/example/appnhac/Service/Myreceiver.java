package com.example.appnhac.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Myreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
           int actionmusic = intent.getIntExtra("action_music",0);
           Intent intentservice = new Intent(context,Service.class);
           intentservice.putExtra("clickaction", actionmusic);
           context.startService(intentservice);
    }
}
