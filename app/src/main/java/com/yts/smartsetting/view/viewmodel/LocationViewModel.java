package com.yts.smartsetting.view.viewmodel;


import android.arch.lifecycle.MutableLiveData;

import com.yts.smartsetting.data.model.Location;

public class LocationViewModel extends BaseViewModel {
    public MutableLiveData<Location> location = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSelectLocation = new MutableLiveData<>();


    public void setLocation(Location location) {
        if (location != null) {
            this.location.setValue(location);
        }else{
            isSelectLocation.setValue(false);
        }
    }
}
