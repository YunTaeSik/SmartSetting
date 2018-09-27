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
    private String TAG = "SmartSettingJobService";
    private ServiceReceiver serviceReceiver;

    @Override
    public boolean onStartJob(JobParameters job) {
        System.out.print(TAG + " = onStartJob");
        if (serviceReceiver == null) {
            serviceReceiver = new ServiceReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        getApplicationContext().registerReceiver(serviceReceiver, intentFilter);
    /*    try {
            startService(new Intent(this, Service.class));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        System.out.print(TAG + " = onStopJob");
        try {
            if (serviceReceiver != null) {
               // getApplicationContext().unregisterReceiver(serviceReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
