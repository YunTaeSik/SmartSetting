package com.yts.smartsetting.utill;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.yts.smartsetting.receiver.ServiceReceiver;

public class Register {
 /*   public static void plug(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        context.getApplicationContext().registerReceiver(new ServiceReceiver(), intentFilter);
    }*/

    public static void plug(Context context, BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
  //      intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        context.getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
    }
}
