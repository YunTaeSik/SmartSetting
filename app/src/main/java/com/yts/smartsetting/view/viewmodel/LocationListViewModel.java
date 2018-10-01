package com.yts.smartsetting.view.viewmodel;


import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.yts.smartsetting.data.TSLiveData;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;

public class LocationListViewModel extends BaseViewModel {
    public TSLiveData<Boolean> isEnable = new TSLiveData<>(false);

    public void initData(Context context) {
        isEnable.setValue(SharedPrefsUtils.getBooleanPreference(context, Keys.LOCATION_ENABLE));
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
