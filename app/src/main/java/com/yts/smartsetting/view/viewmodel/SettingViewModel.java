package com.yts.smartsetting.view.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.yts.smartsetting.callback.SettingCallback;
import com.yts.smartsetting.data.TSLiveData;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;
import com.yts.smartsetting.view.ui.dialog.AppSelectDialog;

public class SettingViewModel extends BaseViewModel {
    public SettingCallback settingCallback;
    private TSLiveData<String> mKind = new TSLiveData<>();

    public TSLiveData<Boolean> isEnable = new TSLiveData<>(false);
    public TSLiveData<String> appName = new TSLiveData<>();

    public void setSettingCallback(SettingCallback settingCallback) {
        this.settingCallback = settingCallback;
    }

    @Override
    public void setInterstitialAd(Context context) {
        super.setInterstitialAd(context);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startAppSelect();
                interstitialAd.loadAd(adRequest);
            }
        });
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
            if (baseCallback != null && mKind != null && mKind.getValue() != null) {
                baseCallback.saveEnable(mKind.getValue(), isEnable);
            }
        }
    }

    public void startAppSelect() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            if (settingCallback != null) {
                settingCallback.startFragment(AppSelectDialog.newInstance(mKind.getValue()));
            }
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
