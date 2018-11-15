package com.yts.smartsetting.view.viewmodel;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdListener;
import com.yts.smartsetting.R;
import com.yts.smartsetting.data.TSLiveData;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.data.model.PlaceData;
import com.yts.smartsetting.utill.Keys;

public class LocationViewModel extends BaseViewModel {
    public TSLiveData<Location> location = new TSLiveData<>(new Location());
    public TSLiveData<Boolean> isSelectLocation = new TSLiveData<>(false);

    public void setLocation(Location location) {
        if (location != null) {
            isSelectLocation.setValue(true);
            this.location.setValue(location);
        }
    }

    @Override
    public void setInterstitialAd(Context context) {
        super.setInterstitialAd(context);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if (location != null && location.getValue() != null) {
                    startLocationDialog(location.getValue());
                }
                interstitialAd.loadAd(adRequest);
            }
        });
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

    public void toggleSoundMode(View view) {
        int id = view.getId();
        Location lo = location.getValue();

        if (lo != null) {
            if (id == R.id.btn_arrive_sound_mode) {
                int soundMode = lo.getArriveSoundMode();
                soundMode = (soundMode + 1) % 4;
                lo.setArriveSoundMode(soundMode);
            } else if (id == R.id.btn_leave_sound_mode) {
                int soundMode = lo.getLeaveSoundMode();
                soundMode = (soundMode + 1) % 4;
                lo.setLeaveSoundMode(soundMode);
            }
            location.setValue(lo);
        }
    }

    public void startLocationDialog() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            if (location != null && location.getValue() != null) {
                startLocationDialog(location.getValue());
            }
        }
    }

    public boolean deleteLongClick() {
        deleteLocation(location.getValue());
        return false;
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
                    Location loc = location.getValue();
                    loc.setLocation(placeData);
                    location.setValue(loc);
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
