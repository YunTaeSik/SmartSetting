package com.yts.smartsetting.view.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.data.model.Location;

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

    public void startLocationListDialog() {
        if (baseCallback != null) {
            baseCallback.startLocationListDialog();
        }
    }

    public void startLocationDialog() {
        if (baseCallback != null) {
            baseCallback.startLocationDialog(null);
        }
    }

    public void startLocationDialog(Location location) {
        if (baseCallback != null) {
            baseCallback.startLocationDialog(location);
        }
    }

    public void startSelectLocation() {
        if (baseCallback != null) {
            baseCallback.startSelectLocation();
        }
    }
}
