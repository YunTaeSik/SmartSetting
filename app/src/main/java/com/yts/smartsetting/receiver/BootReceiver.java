package com.yts.smartsetting.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yts.smartsetting.utill.ServiceUtil;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ServiceUtil.start(context);
    }
}
