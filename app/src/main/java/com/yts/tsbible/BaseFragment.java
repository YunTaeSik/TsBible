package com.yts.tsbible;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

public class BaseFragment extends Fragment {
    protected Realm realm;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        if (realm != null) {
            realm.close();
        }
    }
}
