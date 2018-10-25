package com.yts.tsbible.bindingadapter;

import android.content.Context;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Offering;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

public class ChartBindingAdapter {
    @BindingAdapter({"setPieChartView", "setVisible"})
    public static void setPieChartView(PieChart view, List<Object> data, Boolean isVisible) {
        boolean visible = isVisible != null ? isVisible : false;
        if (visible) {
            Context context = view.getContext();
            HashMap<String, Long> pieHashMap = new HashMap<>();
            for (Object item : data) {
                if (item instanceof Offering) {
                    Offering offering = (Offering) item;
                    if (offering.getMoney() > 0) {
                        long value;
                        if (pieHashMap.containsKey(offering.getName())) {
                            value = pieHashMap.get(offering.getName()) + offering.getMoney();
                        } else {
                            value = offering.getMoney();
                        }
                        pieHashMap.put(offering.getName(), value);
                    }
                }
            }

            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            for (String key : pieHashMap.keySet()) {
                pieEntries.add(new PieEntry(pieHashMap.get(key), key));
            }

            if (pieEntries.size() > 0) {
                view.setVisibility(View.VISIBLE);

                PieDataSet pieDataSet = new PieDataSet(pieEntries, context.getString(R.string.offering));
                pieDataSet.setSliceSpace(1.5f);
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

                PieData pieData = new PieData(pieDataSet);
                pieData.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

                        return decimalFormat.format(value) + " 원";
                    }
                });
                pieData.setValueTextSize(13);
                pieData.setValueTextColor(ContextCompat.getColor(context, R.color.white));

                view.setData(pieData);
                view.setDrawHoleEnabled(false);
                view.animateY(1000, Easing.EasingOption.EaseInOutQuad);
                view.getLegend().setEnabled(false);
                view.setDescription(null);

            } else {
                view.setVisibility(View.GONE);
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
