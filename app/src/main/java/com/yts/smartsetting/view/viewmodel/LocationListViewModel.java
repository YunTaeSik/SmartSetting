package com.yts.smartsetting.view.viewmodel;


import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.yts.smartsetting.data.TSLiveData;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.data.realm.RealmService;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

public class LocationListViewModel extends BaseViewModel {
    public TSLiveData<Boolean> isEnable = new TSLiveData<>(false);
    public TSLiveData<ArrayList<Object>> mLocationList = new TSLiveData<>(new ArrayList<Object>());

    public void initData(Context context) {
        isEnable.setValue(SharedPrefsUtils.getBooleanPreference(context, Keys.LOCATION_ENABLE));
        List<Location> locationList = RealmService.getLocationList();
        if (locationList != null) {
            mLocationList.getValue().addAll(locationList);
        }
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
}
