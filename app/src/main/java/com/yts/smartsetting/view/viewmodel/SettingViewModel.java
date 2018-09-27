package com.yts.smartsetting.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.yts.smartsetting.callback.SettingCallback;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;
import com.yts.smartsetting.view.ui.dialog.AppSelectDialog;

public class SettingViewModel extends BaseViewModel {
    public SettingCallback settingCallback;
    private MutableLiveData<String> mKind = new MutableLiveData<>();

    public MutableLiveData<Boolean> isEnable = new MutableLiveData<>();
    public MutableLiveData<String> appName = new MutableLiveData<>();

    public void setSettingCallback(SettingCallback settingCallback) {
        this.settingCallback = settingCallback;
    }

    public void setKind(Context context, String kind) {
        mKind.setValue(kind);
        if (kind.equals(Keys.EAR)) {
            isEnable.setValue(SharedPrefsUtils.getBooleanPreference(context, Keys.EAR_ENABLE));
            appName.setValue(SharedPrefsUtils.getStringPreference(context, Keys.EAR_NAME));
        } else if (kind.equals(Keys.BLUE_TOOTH)) {
            isEnable.setValue(SharedPrefsUtils.getBooleanPreference(context, Keys.BLUE_TOOTH_ENABLE));
            appName.setValue(SharedPrefsUtils.getStringPreference(context, Keys.BLUE_TOOTH_NAME));
        }
    }

    public void enable(View view) {
        if (view instanceof SwitchCompat) {
            SwitchCompat switchCompat = (SwitchCompat) view;
            boolean isEnable = switchCompat.isChecked();
            if (settingCallback != null && mKind != null && mKind.getValue() != null) {
                settingCallback.saveEnable(mKind.getValue(), isEnable);
            }
        }
    }

    public void startAppSelect() {
        if (settingCallback != null) {
            settingCallback.startFragment(AppSelectDialog.newInstance(mKind.getValue()));
        }
    }

    //브로드캐스트
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && mKind != null && mKind.getValue() != null) {
                String kind = mKind.getValue();
                if (action.equals(Keys.EDIT_EAR) && kind.equals(Keys.EAR)) {
                    String name = SharedPrefsUtils.getStringPreference(context, Keys.EAR_NAME);
                    appName.setValue(name);
                } else if (action.equals(Keys.EDIT_BLUE_TOOTH) && kind.equals(Keys.BLUE_TOOTH)) {
                    String name = SharedPrefsUtils.getStringPreference(context, Keys.BLUE_TOOTH_NAME);
                    appName.setValue(name);
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
