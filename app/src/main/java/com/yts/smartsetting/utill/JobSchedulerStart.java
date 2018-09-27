package com.yts.smartsetting.utill;

import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.yts.smartsetting.service.SmartSettingJobService;


public class JobSchedulerStart {
    public static void start(Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        dispatcher.cancelAll();

      /*  Job myJob = dispatcher.newJobBuilder()
                .setService(SmartSettingJobService.class) // the JobService that will be called
                .setTag(SmartSettingJobService.class.getSimpleName())
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
        dispatcher.mustSchedule(myJob);*/

        Job myJob = dispatcher.newJobBuilder()
                .setService(SmartSettingJobService.class) // the JobService that will be called
                .setTag(SmartSettingJobService.class.getSimpleName())        // uniquely identifies the job
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.NOW)
                .build();
        dispatcher.mustSchedule(myJob);
    }
}
