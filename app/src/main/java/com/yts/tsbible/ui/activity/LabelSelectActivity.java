package com.yts.tsbible.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.yts.tsbible.BaseActivity;
import com.yts.tsbible.R;
import com.yts.tsbible.databinding.LabelListBinding;
import com.yts.tsbible.ui.adapter.LabelSelectAdapter;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.viewmodel.LabelListViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LabelSelectActivity extends BaseActivity {
    private LabelListBinding binding;
    private LabelListViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_label_select);
        model = ViewModelProviders.of(this).get(LabelListViewModel.class);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        String selectLabel = getIntent().getStringExtra(Keys.LABEL);
        model.initData(selectLabel);
        observe();

        registerReceiver(broadcastReceiver, getIntentFilter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void observe() {
        model.mLabelList.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                RecyclerView view = binding.listLabel;
                LabelSelectAdapter adapter = (LabelSelectAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setLabelList(strings);
                } else {
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    adapter = new LabelSelectAdapter(strings);
                    adapter.setHasStableIds(true);
                    view.setLayoutManager(manager);
                    view.setAdapter(adapter);
                }
            }
        });
        model.mSelectPosition.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                RecyclerView view = binding.listLabel;
                if (view != null) {
                    try {
                        view.smoothScrollToPosition(integer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(SendBroadcast.EDIT_LABEL)) {
                    finish();
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.EDIT_LABEL);
        return intentFilter;
    }
}
