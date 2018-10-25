package com.yts.tsbible.viewmodel;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Bible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChapterListViewModel extends BaseViewModel {
    public TSLiveData<List<String>> mFullChapterList = new TSLiveData<>();
    public TSLiveData<List<String>> mChapterList = new TSLiveData<>();

    public void initData(String label) {
        List<String> labelList = Arrays.asList(Bible.books);
        int labelPosition = labelList.indexOf(label);

        List<String> chapterList = new ArrayList<>();
        for (int i = 1; i < Integer.parseInt(Bible.chapters[labelPosition]) + 1; i++) {
            chapterList.add(String.valueOf(i));
        }
        mFullChapterList.setValue(chapterList);
        mChapterList.setValue(chapterList);
    }

    public void search(CharSequence charSequence) {
        if (mFullChapterList != null && mFullChapterList.getValue() != null) {
            List<String> chapterList = new ArrayList<>();
            for (String label : mFullChapterList.getValue()) {
                if (label.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    chapterList.add(label);
                }
            }
            mChapterList.setValue(chapterList);
        }
    }
}

