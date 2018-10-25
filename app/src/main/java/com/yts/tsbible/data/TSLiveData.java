package com.yts.tsbible.data;

import androidx.lifecycle.MutableLiveData;

public class TSLiveData<T> extends MutableLiveData<T> {

    public TSLiveData() {

    }

    public TSLiveData(T value) {
        if (value != null) {
            setValue(value);
        }
    }

}
