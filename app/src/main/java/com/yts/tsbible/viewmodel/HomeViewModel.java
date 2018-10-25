package com.yts.tsbible.viewmodel;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.User;
import com.yts.tsbible.data.realm.RealmService;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;

public class HomeViewModel extends BaseViewModel {
    public TSLiveData<List<Object>> mHomeList = new TSLiveData<>();

    public void initData(Realm realm, CompositeDisposable compositeDisposable) {
        this.realm = realm;
        this.compositeDisposable = compositeDisposable;
        getHomeList();
    }

    public void changeHomeHeader() {
        try {
            List<Object> homeList = mHomeList.getValue();
            User user = RealmService.getUserList(realm).get(0);
            homeList.set(0, user);
            mHomeList.setValue(homeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHomeList() {
        compositeDisposable.add(RealmService.getHomeList(realm).subscribe(new Consumer<List<Object>>() {
            @Override
            public void accept(List<Object> objects) throws Exception {
                mHomeList.setValue(objects);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
    }
}
