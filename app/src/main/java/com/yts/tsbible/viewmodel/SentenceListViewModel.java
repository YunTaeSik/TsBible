package com.yts.tsbible.viewmodel;


import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Bible;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

public class SentenceListViewModel extends BibleListViewModel {
    public TSLiveData<String> mTitle = new TSLiveData<>();

    public void setTitle(String title) {
        mTitle.setValue(title);
    }

    public void getBibleList(Realm realm, CompositeDisposable compositeDisposable, Bible bible) {
        if (bible != null) {
            super.getBibleList(realm, compositeDisposable, bible.getLabel(), bible.getChapter());
        } else {
            super.getBibleList(realm, compositeDisposable);
        }
    }
}
