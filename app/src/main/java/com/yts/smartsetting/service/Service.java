package com.yts.smartsetting.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yts.smartsetting.receiver.ServiceReceiver;

public class Service extends android.app.Service {
    private ServiceReceiver serviceReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (serviceReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
            serviceReceiver = new ServiceReceiver();
            registerReceiver(serviceReceiver, intentFilter);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (serviceReceiver != null) {
            unregisterReceiver(serviceReceiver);
        }
        super.onDestroy();
    }
}
