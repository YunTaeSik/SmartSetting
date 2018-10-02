package com.yts.smartsetting.view.viewmodel;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.yts.smartsetting.data.TSLiveData;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.data.model.PlaceData;
import com.yts.smartsetting.data.realm.RealmService;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class LocationListViewModel extends BaseViewModel {
    public TSLiveData<Boolean> isEnable = new TSLiveData<>(false);
    public TSLiveData<ArrayList<Object>> mLocationList = new TSLiveData<>(new ArrayList<Object>());

    public void initData(Context context, CompositeDisposable compositeDisposable) {
        isEnable.setValue(SharedPrefsUtils.getBooleanPreference(context, Keys.LOCATION_ENABLE));

        compositeDisposable.add(RealmService.getLocationList().subscribe(new Consumer<List<Location>>() {
            @Override
            public void accept(List<Location> locationList) throws Exception {
                if (locationList != null) {
                    ArrayList<Object> objectList = new ArrayList<>();
                    objectList.addAll(locationList);
                    mLocationList.setValue(objectList);
                }
            }
        }));
    }

    @Override
    public void setInterstitialAd(Context context) {
        super.setInterstitialAd(context);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startLocationDialog();
                interstitialAd.loadAd(adRequest);
            }
        });
    }

    public void enable(View view) {
        if (view instanceof SwitchCompat) {
            SwitchCompat switchCompat = (SwitchCompat) view;
            boolean isEnable = switchCompat.isChecked();
            if (baseCallback != null) {
                baseCallback.saveEnable(Keys.LOCATION, isEnable);
            }
        }
    }

    public void startAdLocationDialog() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            startLocationDialog();
        }

    }


}
