package com.yts.smartsetting.utill;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class Turn {
    public static void blueTooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            if (enable) {
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                }
            } else {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                }
            }
        }
    }

    public static void wifi(Context context, boolean enable) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            if (enable) {
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }
            } else {
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }
            }
        }
    }

    public static void soundMode(Context context, int soundMode) {
        AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            if (soundMode == 1) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            } else if (soundMode == 2) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            } else if (soundMode == 3) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }
    }
}
