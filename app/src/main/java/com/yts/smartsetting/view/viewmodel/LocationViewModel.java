package com.yts.smartsetting.view.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.android.gms.maps.model.LatLng;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.data.model.PlaceData;
import com.yts.smartsetting.utill.Keys;

public class LocationViewModel extends BaseViewModel {
    public MutableLiveData<Location> location = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSelectLocation = new MutableLiveData<>();


    public void setLocation(Location location) {
        if (location != null) {
            isSelectLocation.setValue(true);
            this.location.setValue(location);
        } else {
            isSelectLocation.setValue(false);
        }
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Keys.SELECT_LOCATION)) {
                    isSelectLocation.setValue(true);
                    PlaceData placeData = intent.getParcelableExtra(Keys.PLACE);
                    Location getLocation = new Location(placeData);
                    location.setValue(getLocation);
                }
            }

        }
    };

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.SELECT_LOCATION);
        return intentFilter;
    }
}
