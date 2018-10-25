package com.yts.tsbible.viewmodel;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Bible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LabelListViewModel extends BaseViewModel {
    public TSLiveData<Integer> mSelectPosition = new TSLiveData<>(0);

    public TSLiveData<List<String>> mFullLabelList = new TSLiveData<>();
    public TSLiveData<List<String>> mLabelList = new TSLiveData<>();

    public void initData(String selectLabel) {
        List<String> labelList = Arrays.asList(Bible.books);
        mFullLabelList.setValue(labelList);
        mLabelList.setValue(labelList);

        try {
            mSelectPosition.setValue(mFullLabelList.getValue().indexOf(selectLabel));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void search(CharSequence charSequence) {
        if (mFullLabelList != null && mFullLabelList.getValue() != null) {
            List<String> labelList = new ArrayList<>();
            for (String label : mFullLabelList.getValue()) {
                if (label.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    labelList.add(label);
                }
            }
            mLabelList.setValue(labelList);
        }
    }
}

