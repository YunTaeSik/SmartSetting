package com.yts.smartsetting.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.yts.smartsetting.receiver.ServiceReceiver;

public class SmartSettingJobService extends JobService {
    private ServiceReceiver serviceReceiver;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d("NotificationJobService", "onStartJob");
        System.out.print("NotificationJobService = onStartJob");
        //     startService(new Intent(this, Service.class));
        if (serviceReceiver == null) {
            serviceReceiver = new ServiceReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        getApplicationContext().registerReceiver(new ServiceReceiver(), intentFilter);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d("NotificationJobService", "onStopJob");
        System.out.print("NotificationJobService = onStopJob");
        try {
            if (serviceReceiver != null) {
                getApplicationContext().unregisterReceiver(serviceReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
