package com.yts.tsbible.bindingadapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

public class VisibleBindingAdapter {
    @BindingAdapter({"setVisible"})
    public static void setVisible(LinearLayout layout, Boolean isVisible) {
        boolean visible = isVisible != null ? isVisible : false;
        if (visible) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"setVisible"})
    public static void setVisible(View view, Boolean isVisible) {
        boolean visible = isVisible != null ? isVisible : false;
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
