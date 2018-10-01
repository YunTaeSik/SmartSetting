package com.yts.smartsetting.data;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

public class TSLiveData<T> extends MutableLiveData<T> {

    public TSLiveData() {

    }

    public TSLiveData(T value) {
        setValue(value);
    }

}
