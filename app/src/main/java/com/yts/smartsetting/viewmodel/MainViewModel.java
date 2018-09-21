package com.yts.smartsetting.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.callback.MainCallback;

public class MainViewModel extends BaseViewModel {
    private MainCallback mainCallback;


    public void setMainCallback(BaseCallback baseCallback, MainCallback mainCallback) {
        this.baseCallback = baseCallback;
        this.mainCallback = mainCallback;
    }

    public void startEarPhoneDialog() {
        if (mainCallback != null) {
            mainCallback.startEarPhone();
        }
    }

    public void startBlueTooth() {
        if (mainCallback != null) {
            mainCallback.startBlueTooth();
        }
    }
}
