package com.yts.smartsetting.view.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ToggleButton;

import com.google.android.gms.maps.model.LatLng;
import com.yts.smartsetting.R;
import com.yts.smartsetting.data.TSLiveData;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.data.model.PlaceData;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.ToastMake;

public class LocationViewModel extends BaseViewModel {
    public TSLiveData<Location> location = new TSLiveData<>(new Location());
    public TSLiveData<Boolean> isSelectLocation = new TSLiveData<>(false);


    public void setLocation(Location location) {
        if (location != null) {
            isSelectLocation.setValue(true);
            this.location.setValue(location);
        }
    }

    public void toggle(View view) {
        int id = view.getId();
        if (view instanceof ToggleButton) {
            ToggleButton button = (ToggleButton) view;
            boolean check = button.isChecked();
            if (id == R.id.toggle_arrive_bluetooth) {
                location.getValue().setArriveBlueTooth(check);
            } else if (id == R.id.toggle_arrive_wifi) {
                location.getValue().setArriveWifi(check);
            } else if (id == R.id.toggle_leave_bluetooth) {
                location.getValue().setLeaveBlueTooth(check);
            } else if (id == R.id.toggle_leave_wifi) {
                location.getValue().setLeaveWifi(check);
            }
        }

    }

    public void startLocationDialog() {
        if (location.getValue() != null) {
            startLocationDialog(new Location(location.getValue()));
        }
    }

    public void saveLocation() {
        Location location = this.location.getValue();
        if (location == null) {
            toast(R.string.hint_location);
            return;
        }

        if (!location.isValidity()) {
            toast(R.string.hint_location);
            return;
        }
        save(location);
        close();
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
