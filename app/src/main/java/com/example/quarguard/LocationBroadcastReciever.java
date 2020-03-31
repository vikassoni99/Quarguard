package com.example.quarguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LocationBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("service stop","service has stoped");
        context.startService(new Intent(context ,LocationUpdateService.class));
    }
}
