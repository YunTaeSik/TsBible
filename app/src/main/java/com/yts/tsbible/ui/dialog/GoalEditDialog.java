package com.yts.tsbible.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Goal;
import com.yts.tsbible.databinding.GoalEditBinding;
import com.yts.tsbible.interactor.GoalEditCallback;
import com.yts.tsbible.viewmodel.GoalViewModel;

import androidx.databinding.DataBindingUtil;

public class GoalEditDialog extends Dialog implements GoalEditCallback {
    private GoalEditBinding binding;
    private GoalViewModel model;

    public GoalEditDialog(Context context, Goal goal) {
        super(context);
        setWindowHeight(84);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_goal_edit, null, false);
        model = new GoalViewModel();
        model.setGoal(goal);
        model.setGoalEditCallback(this);
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
