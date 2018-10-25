package com.yts.tsbible.viewmodel;

import android.content.Context;
import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.utills.NullFilter;
import com.yts.tsbible.utills.SendBroadcast;

public class BibleViewModel extends BaseViewModel {
    private int position;
    public TSLiveData<Boolean> isAddSentence = new TSLiveData<>(false);

    public TSLiveData<String> mPositionText = new TSLiveData<>("1 ");
    public TSLiveData<Bible> mBible = new TSLiveData<>();

    public TSLiveData<Integer> mSize = new TSLiveData<>(8);


    public void setBible(Bible bible) {
        mBible.setValue(bible);
    }

    public void setPosition(int position) {
        this.position = position;
        mPositionText.setValue((position + 1) + ". ");
    }

    public void setIsSentence(boolean addSentence) {
        isAddSentence.setValue(addSentence);
    }

    public void setSize(Integer size) {
        this.mSize.setValue(size + 8);
    }

    /**
     * 클릭
     */

    public void itemClick(View view) {
        if (isAddSentence.getValue() != null && isAddSentence.getValue()) {
            Context context = view.getContext();
            SendBroadcast.addSentence(context, NullFilter.check(mBible.getValue().getSentence()), position);
        } else {
            Context context = view.getContext();
            SendBroadcast.editBibleSentenceSettingVisible(context, position);
        }
    }
}
