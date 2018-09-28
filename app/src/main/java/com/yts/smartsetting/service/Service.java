package com.yts.smartsetting.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.yts.smartsetting.R;
import com.yts.smartsetting.receiver.ServiceReceiver;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.Register;
import com.yts.smartsetting.utill.SharedPrefsUtils;
import com.yts.smartsetting.view.ui.activity.MainActivity;

public class Service extends android.app.Service {
    private ServiceReceiver serviceReceiver;
    private final static int NOTIFICATION_ID = 111;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Intent main = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, main, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                    .setSmallIcon(R.drawable.ic_settings_white_24dp)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(getString(R.string.app_in_use))
                    .setContentText(getString(R.string.app_content))
                    .build();
            startForeground(NOTIFICATION_ID, notification);
        }


        if (serviceReceiver == null) {
            serviceReceiver = new ServiceReceiver();
        }
        Register.plug(this, serviceReceiver);
        return super.onStartCommand(intent, flags, startId);
    }


}
