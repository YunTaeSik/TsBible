package com.yts.tsbible.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.yts.tsbible.R;
import com.yts.tsbible.databinding.SentenceEditBinding;
import com.yts.tsbible.interactor.SentenceEditCallback;
import com.yts.tsbible.viewmodel.SentenceViewModel;

import androidx.databinding.DataBindingUtil;


public class SentenceEditDialog extends Dialog implements SentenceEditCallback {
    private SentenceEditBinding binding;
    private SentenceViewModel model;

    public SentenceEditDialog(Context context, String sentence) {
        super(context);
        setWindowHeight(84);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_sentence_edit, null, false);
        model = new SentenceViewModel();
        model.setSentence(sentence);
        model.setSentenceEditCallback(this);
        binding.setModel(model);
        setContentView(binding.getRoot());
    }

    private void setWindowHeight(int percent) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (int) (screenHeight * percent / 100);
        this.getWindow().setAttributes(params);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }


    @Override
    public void close() {
        dismiss();
    }
}
