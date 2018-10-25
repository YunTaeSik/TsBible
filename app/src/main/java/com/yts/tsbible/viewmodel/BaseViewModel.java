package com.yts.tsbible.viewmodel;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.ads.AdRequest;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.ui.activity.ImageMakeActivity;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.NullFilter;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

public class BaseViewModel extends ViewModel {
    public AdRequest mAdRequest;
    protected Realm realm;
    protected CompositeDisposable compositeDisposable;

    public void setRealm(Realm realm) {
        this.realm = realm;

    }

    public void setAdRequest(AdRequest mAdRequest) {
        this.mAdRequest = mAdRequest;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public void startImageMake(Context context, Bible bible) {
        if (bible != null) {
            String subTitle = NullFilter.check(bible.getLabel()) + " " + NullFilter.check(bible.getChapter()) + "-" + (NullFilter.check(bible.getParagraph()));

            Intent imageMake = new Intent(context, ImageMakeActivity.class);
            imageMake.putExtra(Keys.BIBLE, bible);
            imageMake.putExtra(Keys.SUBTITLE, subTitle);
            context.startActivity(imageMake);
        }
    }
}
