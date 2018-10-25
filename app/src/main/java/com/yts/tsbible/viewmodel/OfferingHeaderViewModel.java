package com.yts.tsbible.viewmodel;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.User;
import com.yts.tsbible.interactor.OfferingCallback;

import java.util.List;

public class OfferingHeaderViewModel extends BaseViewModel {
    public TSLiveData<User> mUser = new TSLiveData<>();
    public TSLiveData<List<Object>> mData = new TSLiveData<>();
    public TSLiveData<Boolean> mChartVisible = new TSLiveData<>();

    private OfferingCallback offeringCallback;

    public void setUser(User user) {
        mUser.setValue(user);
    }

    public void setData(List<Object> data) {
        mData.setValue(data);
    }

    public void setChartVisible(boolean chartVisible) {
        mChartVisible.setValue(chartVisible);
    }

    public void setOfferingCallback(OfferingCallback offeringCallback) {
        this.offeringCallback = offeringCallback;
    }

    public void search(CharSequence charSequence) {
        if (offeringCallback != null) {
            offeringCallback.search(charSequence);
        }
    }

    public void changeChartVisible() {
        if (offeringCallback != null) {
            offeringCallback.changeChartVisible();
        }
    }

}
