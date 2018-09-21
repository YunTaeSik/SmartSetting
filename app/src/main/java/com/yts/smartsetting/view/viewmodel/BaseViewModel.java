package com.yts.smartsetting.view.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.yts.smartsetting.callback.BaseCallback;

public class BaseViewModel extends ViewModel {
    public BaseCallback baseCallback;

    public void setBaseCallback(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
    }

    public void close() {
        if (baseCallback != null) {
            baseCallback.close();
        }
    }
}
