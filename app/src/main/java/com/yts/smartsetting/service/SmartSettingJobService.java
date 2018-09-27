package com.yts.smartsetting.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.yts.smartsetting.receiver.ServiceReceiver;

public class SmartSettingJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d("NotificationJobService", "onStartJob");
        System.out.print("NotificationJobService = onStartJob");
        startService(new Intent(this, Service.class));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
