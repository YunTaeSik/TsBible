package com.yts.tsbible.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.chip.Chip;
import com.yts.tsbible.BaseActivity;
import com.yts.tsbible.R;
import com.yts.tsbible.databinding.OfferingAddBinding;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.viewmodel.OfferingViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class OfferingAddActivity extends BaseActivity {
    private OfferingAddBinding binding;
    private OfferingViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_offering_add);
        model = ViewModelProviders.of(this).get(OfferingViewModel.class);
        model.setAdRequest(adRequest);
        model.initOfferingKindList(this, realm);
        binding.setModel(model);
        binding.setLifecycleOwner(this);
        observe();

        registerReceiver(broadcastReceiver, getIntentFilter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


    private void observe() {
        model.mOfferingKindList.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                try {
                    binding.groupChip.removeAllViews();

                    for (int i = 0; i < model.mOfferingKindList.getValue().size(); i++) {
                        String kind = model.mOfferingKindList.getValue().get(i);

                        Chip chip = new Chip(OfferingAddActivity.this, null);
                        chip.setId(i);
                        chip.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.chip_select_text_color));
                        chip.setChipBackgroundColorResource(R.color.chip_select_background);
                        chip.setCheckedIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_check_white_24dp));
                        chip.setCheckedIconVisible(true);
                        chip.setText(kind);
                        chip.setEnabled(true);
                        chip.setCheckable(true);
                        if (i == 0) {
                            chip.setChecked(true);
                        }
                        binding.groupChip.addView(chip);
                    }

                    binding.groupChip.setOnCheckedChangeListener(model.mOfferingKindCheckedChangeListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        model.mOfferingPriceTextList.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                try {
                    binding.groupChipPrice.removeAllViews();

                    for (int i = 0; i < model.mOfferingPriceTextList.getValue().size(); i++) {
                        String price = model.mOfferingPriceTextList.getValue().get(i);

                        Chip chip = new Chip(OfferingAddActivity.this, null);
                        chip.setId(i);
                        chip.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.chip_select_text_color));
                        chip.setChipBackgroundColorResource(R.color.chip_select_background);
                        chip.setText(price);

                        chip.setOnClickListener(model.mAddOfferingPrice);
                        binding.groupChipPrice.addView(chip);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(SendBroadcast.SAVE_OFFERING)) {
                    finish();
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.SAVE_OFFERING);
        return intentFilter;
    }
}
