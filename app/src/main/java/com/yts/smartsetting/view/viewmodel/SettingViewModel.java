package com.yts.smartsetting.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.yts.smartsetting.callback.SettingCallback;
import com.yts.smartsetting.view.ui.dialog.AppSelectDialog;

public class SettingViewModel extends BaseViewModel {
    public SettingCallback settingCallback;
    private MutableLiveData<String> mKind = new MutableLiveData<>();


    public void setSettingCallback(SettingCallback settingCallback) {
        this.settingCallback = settingCallback;
    }

    public void setKind(String kind) {
        mKind.setValue(kind);
    }

    public void startAppSelect() {
        if (settingCallback != null) {
            settingCallback.startFragment(AppSelectDialog.newInstance(mKind.getValue()));
        }

    }

}
