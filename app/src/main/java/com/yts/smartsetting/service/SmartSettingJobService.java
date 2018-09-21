package com.yts.smartsetting.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.yts.smartsetting.receiver.ServiceReceiver;

public class SmartSettingJobService extends JobService {
    private ServiceReceiver serviceReceiver = new ServiceReceiver();

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d("NotificationJobService", "onStartJob");
        System.out.print("NotificationJobService = onStartJob");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        try {
            unregisterReceiver(serviceReceiver);
            registerReceiver(serviceReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d("NotificationJobService", "onStopJob");
        System.out.print("NotificationJobService = onStopJob");
        unregisterReceiver(serviceReceiver);
        return false;
    }
}
