package com.yts.tsbible.bindingadapter;

import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import androidx.databinding.BindingAdapter;

public class AdViewBindingAdapter {
    @BindingAdapter({"setBanner"})
    public static void setBanner(AdView view, AdRequest adRequest) {
        view.setVisibility(View.VISIBLE);
        view.loadAd(adRequest);
    }
}
