package com.yts.smartsetting.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;

public class ServiceReceiver extends BroadcastReceiver {
    private boolean isFirst = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
            boolean state = intent.getIntExtra("state", 0) == 1;
            String nm = intent.getStringExtra("name");
            int mic = intent.getIntExtra("microphone", -1);
            String all = "nm=" + nm + " mic=" + Integer.toString(mic);
            Log.e("ServiceReceiver", all);
            System.out.print("ServiceReceiver" + all);

            if (state) {
                if (isFirst) {
                    boolean enable = SharedPrefsUtils.getBooleanPreference(context, Keys.EAR_ENABLE);
                    if (enable) {
                        try {
                            String packageName = SharedPrefsUtils.getStringPreference(context, Keys.EAR_PACKAGENAME);
                            Intent pack = context.getPackageManager().getLaunchIntentForPackage(packageName);
                            pack.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(pack);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    isFirst = false;
                }
            } else {
                isFirst = true;
            }
        }
    }
}
