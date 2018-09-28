package com.yts.smartsetting.utill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.yts.smartsetting.receiver.ServiceReceiver;

public class Register {
    public static void plug(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        context.getApplicationContext().registerReceiver(new ServiceReceiver(), intentFilter);
    }

    public static void plug(Context context, BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        context.getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
    }
}
