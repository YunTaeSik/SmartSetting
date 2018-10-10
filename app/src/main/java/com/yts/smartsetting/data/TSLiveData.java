package com.yts.smartsetting.data;

import androidx.lifecycle.MutableLiveData;

public class TSLiveData<T> extends MutableLiveData<T> {

    public TSLiveData() {

    }

    public TSLiveData(T value) {
        setValue(value);
    }

    public void notifyObserver() {
        //    getValue() = getValue();
    }

}
