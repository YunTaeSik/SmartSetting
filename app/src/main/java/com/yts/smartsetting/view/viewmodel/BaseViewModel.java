package com.yts.smartsetting.view.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.data.realm.RealmService;
import com.yts.smartsetting.utill.SendBroadcast;
import com.yts.smartsetting.utill.ToastMake;

import io.realm.Realm;

public class BaseViewModel extends ViewModel {
    public BaseCallback baseCallback;
    private long mLastClickTime = 0;

    public boolean clickTimeCheck() {
        if (System.currentTimeMillis() - mLastClickTime < 700) {
            return true;
        }
        mLastClickTime = System.currentTimeMillis();
        return false;
    }

    public void setBaseCallback(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
    }

    public void close() {
        if (baseCallback != null) {
            baseCallback.close();
        }
    }

    public void toast(String text) {
        if (baseCallback != null) {
            baseCallback.toast(text);
        }
    }

    public void toast(int id) {
        if (baseCallback != null) {
            baseCallback.toast(id);
        }
    }

    public void save(Location location) {
        if (clickTimeCheck()) {
            return;
        }
        RealmService.saveLocation(location);
        if (baseCallback != null) {
            baseCallback.save(location);
        }
    }

    public void deleteLocation(Location location) {
        if (baseCallback != null) {
            baseCallback.deleteLocation(location);
        }
    }

    public void saveEnable(String kind, boolean enable) {
        if (baseCallback != null) {
            baseCallback.saveEnable(kind, enable);
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
