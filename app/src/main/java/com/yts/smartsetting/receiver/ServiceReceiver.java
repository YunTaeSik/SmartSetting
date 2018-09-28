package com.yts.smartsetting.receiver;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;

public class ServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
            boolean state = intent.getIntExtra("state", 0) == 1;
            if (state) {
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
            }
        } else if (/*action.equals(BluetoothDevice.ACTION_ACL_CONNECTED) ||*/ action.equals(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, -1);
            if (state == BluetoothHeadset.STATE_AUDIO_CONNECTED) {
                boolean enable = SharedPrefsUtils.getBooleanPreference(context, Keys.BLUE_TOOTH_ENABLE);
                if (enable) {
                    try {
                        String packageName = SharedPrefsUtils.getStringPreference(context, Keys.BLUE_TOOTH_PACKAGENAME);
                        Intent pack = context.getPackageManager().getLaunchIntentForPackage(packageName);
                        pack.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(pack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
