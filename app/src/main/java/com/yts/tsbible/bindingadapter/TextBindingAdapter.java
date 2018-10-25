package com.yts.tsbible.bindingadapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.data.model.Goal;
import com.yts.tsbible.utills.DateFormat;

import java.text.DecimalFormat;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

public class TextBindingAdapter {
    @BindingAdapter({"setSelection"})
    public static void setSelection(EditText view, String text) {
        try {
            view.setText(text);
            view.setSelection(view.getText().length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"setChapterText"})
    public static void setChapterText(TextView view, String chapter) {
        String text = chapter + " " + view.getContext().getString(R.string.chapter);
        view.setText(text);
    }

    @BindingAdapter({"setParagraphText"})
    public static void setParagraphText(TextView view, String paragraph) {
        String text = paragraph + ". ";
        view.setText(text);
    }

    @BindingAdapter({"setTextSizeText"})
    public static void setTextSizeText(TextView view, Integer textSize) {
        int size = textSize != null ? textSize : 8;
        String text = (size + 8) + "sp";
        view.setText(text);
    }

    @BindingAdapter({"setTextSize"})
    public static void setTextSize(TextView view, Integer textSize) {
        int size = textSize != null ? textSize : 16;
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    @BindingAdapter({"setTextHighLight", "setSentence"})
    public static void setTextHighLight(TextView view, boolean highLight, String sentence) {
        if (highLight) {
            SpannableString spannable = new SpannableString(sentence);
            spannable.setSpan(new BackgroundColorSpan(ContextCompat.getColor(view.getContext(), R.color.highLight)), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.setText(spannable);
        } else {
            view.setText(sentence);
        }
      /*  if (isBookMark) {
            spannable = new SpannableString(spannable + " ");
            spannable.setSpan(new ImageSpan(view.getContext(), R.drawable.ic_turned_in_black_16dp), spannable.length() - 1, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }*/
    }

    @BindingAdapter({"setTimeText"})
    public static void setTimeText(TextView view, long date) {
        view.setText(DateFormat.getDate(date, DateFormat.FULL_FORMAT));
    }

    @BindingAdapter({"setTimeText", "setTimePattern"})
    public static void setTimeText(TextView view, long date, String pattern) {
        view.setText(DateFormat.getDate(date, pattern));
    }

    @BindingAdapter({"setContentText"})
    public static void setContentText(TextView view, String content) {
        if (content != null && content.length() > 0) {
            view.setVisibility(View.VISIBLE);
            view.setText(content);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"setTodayVerseText"})
    public static void setTodayVerseText(TextView view, Bible bible) {
        if (bible == null) {
            return;
        }
        Context context = view.getContext();
        String text = bible.getLabel() + " " + bible.getChapter() + context.getString(R.string.chapter) + " " + bible.getParagraph() + context.getString(R.string.sentence);
        view.setText(text);
    }

    @BindingAdapter({"setAmountText"})
    public static void setAmountText(TextView view, long amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String text = decimalFormat.format(amount) + " " + view.getContext().getString(R.string.a_monetary_unit);
        view.setText(text);
    }

    @BindingAdapter({"setMoneyText"})
    public static void setMoneyText(TextView view, long amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String text = "+" + decimalFormat.format(amount) + " " + view.getContext().getString(R.string.a_monetary_unit);
        view.setText(text);
    }

    @BindingAdapter({"setAmountText"})
    public static void setAmountText(TextView view, String amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String text = decimalFormat.format(Long.parseLong(amount)) + " " + view.getContext().getString(R.string.a_monetary_unit);
        view.setText(text);
    }

    @BindingAdapter({"setChartVisibleText"})
    public static void setChartVisibleText(TextView view, Boolean isVisible) {
        boolean visible = isVisible != null ? isVisible : false;
        Context context = view.getContext();
        String text = visible ? context.getString(R.string.collapse_chart) : context.getString(R.string.expand_chart);
        view.setText(text);
    }

    @BindingAdapter({"setTotalSuccessText"})
    public static void setTotalSuccessText(TextView view, Goal goal) {
        Context context = view.getContext();
        long total = goal != null ? goal.getTotalSuccessesNumber() : 0;
        String text = context.getString(R.string.total_success_number, String.valueOf(total));
        view.setText(text);
    }

    @BindingAdapter({"setGoalTitleText"})
    public static void setGoalTitleText(TextView view, Goal goal) {
        Context context = view.getContext();
        double readTime = goal != null ? goal.getReadTime() != null ? Double.parseDouble(goal.getReadTime()) : 0 : 0;
        double goalTime = (goal != null ? goal.getGoal() != null ? Double.parseDouble(goal.getGoal()) : 0 : 0) * 60 * 1000;

        double progress = 0;
        if (goalTime > 0) {
            progress = (readTime / goalTime) * 100.0;
        }

        String text;
        if (goalTime > 0) {
            if (progress >= 100) {
                text = context.getString(R.string.message_goal_success);
            } else {
                text = context.getString(R.string.read_the_bible_day, String.valueOf((int) (goalTime / (60 * 1000))));
            }
        } else {
            text = context.getString(R.string.message_goal_setting);
        }
        view.setText(text);


    }

    @BindingAdapter({"setGoalProgressText"})
    public static void setGoalProgressText(TextView view, Goal goal) {
        double readTime = goal != null ? goal.getReadTime() != null ? Double.parseDouble(goal.getReadTime()) : 0 : 0;
        double goalTime = (goal != null ? goal.getGoal() != null ? Double.parseDouble(goal.getGoal()) : 0 : 0) * 60 * 1000;

        double progress = 0;
        if (goalTime > 0) {
            progress = (readTime / goalTime) * 100.0;
        }
        if (progress >= 100) {
            progress = 100;
        }

        String text = ((int) progress) + "%";
        view.setText(text);
    }
}
