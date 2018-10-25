package com.yts.tsbible.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.yts.tsbible.BaseActivity;
import com.yts.tsbible.R;
import com.yts.tsbible.databinding.OfferingListBinding;
import com.yts.tsbible.ui.adapter.OfferingAdapter;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.viewmodel.OfferingListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OfferingListActivity extends BaseActivity {
    private OfferingListBinding binding;
    private OfferingListViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_offering_list);
        model = ViewModelProviders.of(this).get(OfferingListViewModel.class);
        model.setAdRequest(adRequest);
        binding.setModel(model);
        binding.setLifecycleOwner(this);
        model.initData(realm, mCompositeDisposable);
        observe();
        registerReceiver(broadcastReceiver, getIntentFilter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void observe() {
        binding.listOffering.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isScrollDown = dy > 0;
                if (isScrollDown) {
                    binding.btnAdd.hide();
                } else {
                    binding.btnAdd.show();
                }
            }
        });

        model.mOfferingList.observe(this, new Observer<List<Object>>() {
            @Override
            public void onChanged(List<Object> offerings) {
                RecyclerView view = binding.listOffering;
                OfferingAdapter adapter = (OfferingAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setOfferingList(offerings);
                } else {
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    adapter = new OfferingAdapter(offerings);
                    adapter.setHasStableIds(true);
                    view.setLayoutManager(manager);
                    view.setNestedScrollingEnabled(false);
                    view.setAdapter(adapter);
                }
            }
        });
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(SendBroadcast.SAVE_OFFERING) || action.equals(SendBroadcast.DELETE_OFFERING)) { //기본 배경화면
                    if (model != null) {
                        model.getOfferingList();
                    }
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.SAVE_OFFERING);
        intentFilter.addAction(SendBroadcast.DELETE_OFFERING);
        return intentFilter;
    }
}