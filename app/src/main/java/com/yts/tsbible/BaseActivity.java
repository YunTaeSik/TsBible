package com.yts.tsbible;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.ads.AdRequest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

public class BaseActivity extends AppCompatActivity {

    protected AdRequest adRequest;
    protected Realm realm;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adRequest = new AdRequest.Builder().addTestDevice(getString(R.string.test_device)).addTestDevice("12BFF7609B86B194323D90FCB8C3BFD7").build();
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
    protected void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        if (realm != null) {
            realm.close();
        }
        super.onDestroy();
    }

    public void close(View view) {
        onBackPressed();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
