package com.yts.tsbible.bindingadapter;

import android.util.Log;
import android.widget.ProgressBar;

import com.yts.tsbible.data.model.Goal;

import androidx.databinding.BindingAdapter;

public class ProgressBindingAdapter {
    @BindingAdapter({"setGoalProgress"})
    public static void setGoalProgress(ProgressBar view, Goal goal) {
        double readTime = goal != null ? goal.getReadTime() != null ? Double.parseDouble(goal.getReadTime()) : 0 : 0;
        double goalTime = (goal != null ? goal.getGoal() != null ? Double.parseDouble(goal.getGoal()) : 0 : 0) * 60 * 1000;

        double progress = 0;
        if (goalTime > 0) {
            progress = (readTime / goalTime) * (100.0);
        }
        Log.e("progress", String.valueOf(progress));
        view.setProgress((int) progress);
    }
}
