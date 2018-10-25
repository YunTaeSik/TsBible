package com.yts.tsbible.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.realm.RealmService;
import com.yts.tsbible.ui.activity.OfferingAddActivity;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;

public class OfferingListViewModel extends BaseViewModel {
    public TSLiveData<List<Object>> mOfferingList = new TSLiveData<>();

    public void initData(Realm realm, CompositeDisposable compositeDisposable) {
        this.realm = realm;
        this.compositeDisposable = compositeDisposable;
        getOfferingList();
    }

    public void getOfferingList() {
        compositeDisposable.add(RealmService.getOfferingList(realm).subscribe(new Consumer<List<Object>>() {
            @Override
            public void accept(List<Object> objects) throws Exception {
                mOfferingList.setValue(objects);
            }
        }));
    }

    public void startOfferingAdd(View view) {
        Context context = view.getContext();
        Intent offeringAdd = new Intent(context, OfferingAddActivity.class);
        context.startActivity(offeringAdd);
    }
}
