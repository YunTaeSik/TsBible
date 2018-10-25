package com.yts.tsbible.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.interactor.SentenceEditCallback;
import com.yts.tsbible.utills.SendBroadcast;

public class SentenceViewModel extends BaseViewModel {
    public TSLiveData<String> mSentence = new TSLiveData<>("");
    private SentenceEditCallback sentenceEditCallback;

    public void setSentenceEditCallback(SentenceEditCallback sentenceEditCallback) {
        this.sentenceEditCallback = sentenceEditCallback;
    }

    public void setSentence(String sentence) {
        mSentence.setValue(sentence);
    }

    public void setSentence(CharSequence sentence) {
        mSentence.setValue(sentence.toString());
    }

    public void close() {
        if (sentenceEditCallback != null) {
            sentenceEditCallback.close();
        }
    }

    public void editSentence(View view) {
        Context context = view.getContext();
        SendBroadcast.editSentence(context, mSentence.getValue());
        close();
    }
}
