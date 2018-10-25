package com.yts.tsbible.bindingadapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.yts.tsbible.GlideApp;
import com.yts.tsbible.R;
import com.yts.tsbible.data.model.History;

import androidx.databinding.BindingAdapter;

public class ImageBindingAdapter {
    @BindingAdapter("setImage")
    public static void setImage(ImageView view, Drawable drawable) {
        GlideApp.with(view.getContext()).load(drawable).override(view.getMeasuredWidth(), view.getMeasuredHeight()).thumbnail(0.1f).centerCrop().into(view);
    }

    @BindingAdapter("setCircleImage")
    public static void setCircleImage(ImageView view, Drawable drawable) {
        GlideApp.with(view.getContext()).load(drawable).override(view.getMeasuredWidth(), view.getMeasuredHeight()).thumbnail(0.1f).circleCrop().into(view);
    }

    @BindingAdapter("setImage")
    public static void setImage(ImageView view, Integer drawableId) {
        GlideApp.with(view.getContext()).load(drawableId).override(view.getMeasuredWidth(), view.getMeasuredHeight()).thumbnail(0.1f).centerCrop().into(view);
    }

    @BindingAdapter("setCircleImage")
    public static void setCircleImage(ImageView view, Integer drawableId) {
        GlideApp.with(view.getContext()).load(drawableId).override(view.getMeasuredWidth(), view.getMeasuredHeight()).thumbnail(0.1f).circleCrop().into(view);
    }

    @BindingAdapter("setImage")
    public static void setImage(ImageView view, Object drawable) {
        if (drawable instanceof History) {
            return;
        }
        GlideApp.with(view.getContext()).load(drawable).override(view.getMeasuredWidth(), view.getMeasuredHeight()).thumbnail(0.1f).centerCrop().into(view);
    }

    @BindingAdapter("setCircleImage")
    public static void setCircleImage(ImageView view, Object drawable) {
        GlideApp.with(view.getContext()).load(drawable).override(view.getMeasuredWidth(), view.getMeasuredHeight()).thumbnail(0.1f).circleCrop().into(view);
    }

    @BindingAdapter("setImage")
    public static void setImage(ImageView view, History history) {
        if (history.getImagePath() != null && history.getImagePath().length() > 0) {
            view.setVisibility(View.VISIBLE);
            setImage(view, history.getImagePath());
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("setCircleImage")
    public static void setCircleImage(ImageView view, History history) {
        if (history.isHighLighter()) {
            setCircleImage(view, R.drawable.img_highlighter_background);
        } else if (history.isBookMark()) {
            setCircleImage(view, R.drawable.img_bookmark_background);
        } else {
            setCircleImage(view, R.drawable.img_make_background);
        }
    }

    @BindingAdapter("setHistoryImage")
    public static void setHistoryImage(ImageView view, History history) {

    }


    @BindingAdapter("setBookMark")
    public static void setBookMark(ImageView view, boolean bookMark) {
        if (bookMark) {
            view.setVisibility(View.VISIBLE);
            GlideApp.with(view.getContext()).load(R.drawable.ic_turned_in_black_24dp).into(view);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
