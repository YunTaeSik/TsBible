package com.yts.tsbible.viewmodel;

import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.History;
import com.yts.tsbible.utills.SendBroadcast;

public class HistoryViewModel extends BaseViewModel {
    public TSLiveData<History> mHistory = new TSLiveData<>();

    public void setHistory(History history) {
        mHistory.setValue(history);
    }

    public void itemClick(View view) {
        History history = mHistory.getValue();
        if (history.isBookMark() || history.isHighLighter()) {
            int position = Integer.parseInt(history.getParagraph()) - 1;
            SendBroadcast.moveBibleList(view.getContext(), history.getLabel(), history.getChapter(), position);
        } else {
            SendBroadcast.sharedSaveImage(view.getContext(), history.getImagePath());
        }

    }
}
