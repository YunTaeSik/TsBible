package com.yts.tsbible.bindingadapter;

import android.widget.SeekBar;

import androidx.databinding.BindingAdapter;

public class SeekBarBindingAdapter {
    @BindingAdapter({"setProgress"})
    public static void setProgress(SeekBar view, Integer size) {

        view.setProgress(size != null ? size : 8);
    }
}
