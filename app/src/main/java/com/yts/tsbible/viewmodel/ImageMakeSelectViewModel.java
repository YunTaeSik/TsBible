package com.yts.tsbible.viewmodel;

import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.utills.ShowIntent;


public class ImageMakeSelectViewModel extends BaseViewModel {
    public TSLiveData<Integer> mDrawableId = new TSLiveData<>();
    public int mPosition = 0;

    public void setDrawableId(Integer drawableId) {
        mDrawableId.setValue(drawableId);
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public void onItemClick(View view) {
        if (mDrawableId != null && mDrawableId.getValue() != null) {
            if (mPosition != 0) {
                SendBroadcast.editMakeImageBackground(view.getContext(), mDrawableId.getValue());
            } else {
                ShowIntent.imageSelect(view.getContext());
            }
        }
    }
}
