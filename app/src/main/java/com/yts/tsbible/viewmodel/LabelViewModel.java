package com.yts.tsbible.viewmodel;

import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.utills.SendBroadcast;

public class LabelViewModel extends BaseViewModel {
    public TSLiveData<String> mLabel = new TSLiveData<>("");

    public void setLabel(String label) {
        mLabel.setValue(label);
    }

    public void clickLabel(View view) {
        SendBroadcast.editLabel(view.getContext(), mLabel.getValue());
    }

}
