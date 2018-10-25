package com.yts.tsbible.viewmodel;

import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.utills.ShowIntent;

public class SettingViewModel extends BaseViewModel {
    public TSLiveData<String> mVersion = new TSLiveData<>();

    public void setVersion(String version) {
        mVersion.setValue(version);
    }

    public void contact(View view) {
        ShowIntent.emailSend(view.getContext());
    }

    public void reviews(View view) {
        ShowIntent.reviews(view.getContext());
    }
}
