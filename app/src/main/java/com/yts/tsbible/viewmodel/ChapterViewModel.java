package com.yts.tsbible.viewmodel;

import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.utills.SendBroadcast;

public class ChapterViewModel extends BaseViewModel {
    public TSLiveData<String> mChapter = new TSLiveData<>("");

    public void setChapter(String label) {
        mChapter.setValue(label);
    }

    public void clickChapter(View view) {
        SendBroadcast.editChapter(view.getContext(), mChapter.getValue());
    }

}
