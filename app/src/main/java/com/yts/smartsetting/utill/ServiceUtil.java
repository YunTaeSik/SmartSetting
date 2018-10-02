package com.yts.smartsetting.utill;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.yts.smartsetting.service.Service;

public class ServiceUtil {
    public static void start(Context context) {
        boolean earEnable = SharedPrefsUtils.getBooleanPreference(context, Keys.EAR_ENABLE);
        boolean blueEnable = SharedPrefsUtils.getBooleanPreference(context, Keys.BLUE_TOOTH_ENABLE);
        boolean locationEnable = SharedPrefsUtils.getBooleanPreference(context, Keys.LOCATION_ENABLE);
        if (earEnable || blueEnable || locationEnable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, Service.class));
            } else {
                context.startService(new Intent(context, Service.class));
            }
        } else {
            context.stopService(new Intent(context, Service.class));
        }
    }
}
