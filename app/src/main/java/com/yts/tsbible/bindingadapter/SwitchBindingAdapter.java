package com.yts.tsbible.bindingadapter;

import android.widget.Switch;

import androidx.databinding.BindingAdapter;

public class SwitchBindingAdapter {
    @BindingAdapter({"setChecked"})
    public static void setChecked(Switch view, Boolean isBlueLight) {
        boolean checked = isBlueLight != null ? isBlueLight : false;
        view.setChecked(checked);

    }
}
