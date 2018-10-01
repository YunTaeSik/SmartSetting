package com.yts.smartsetting.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.callback.MainCallback;
import com.yts.smartsetting.data.TSLiveData;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;

public class MainViewModel extends BaseViewModel {
    private MainCallback mainCallback;

    public TSLiveData<String> earName = new TSLiveData<>();
    public TSLiveData<String> earPackageName = new TSLiveData<>();

    public TSLiveData<String> blueToothName = new TSLiveData<>();
    public TSLiveData<String> blueToothPackageName = new TSLiveData<>();

    public void setMainCallback(BaseCallback baseCallback, MainCallback mainCallback) {
        this.baseCallback = baseCallback;
        this.mainCallback = mainCallback;
    }

    public void initData(Context context) {
        setEar(context);
        setBlueTooth(context);
    }

    public void setEar(Context context) {
        String name = SharedPrefsUtils.getStringPreference(context, Keys.EAR_NAME);
        String packageName = SharedPrefsUtils.getStringPreference(context, Keys.EAR_PACKAGENAME);
        earName.setValue(name);
        earPackageName.setValue(packageName);
    }

    public void setBlueTooth(Context context) {
        String name = SharedPrefsUtils.getStringPreference(context, Keys.BLUE_TOOTH_NAME);
        String packageName = SharedPrefsUtils.getStringPreference(context, Keys.BLUE_TOOTH_PACKAGENAME);
        blueToothName.setValue(name);
        blueToothPackageName.setValue(packageName);
    }

    public void start(String kind) {
        if (mainCallback != null) {
            mainCallback.start(kind);
        }
    }

    //브로드캐스트
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Keys.EDIT_EAR) || action.equals(Keys.EDIT_BLUE_TOOTH)) {
                    initData(context);
                }
            }

        }
    };

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.EDIT_EAR);
        intentFilter.addAction(Keys.EDIT_BLUE_TOOTH);
        return intentFilter;
    }


}
