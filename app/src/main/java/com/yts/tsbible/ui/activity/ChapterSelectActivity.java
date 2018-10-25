package com.yts.tsbible.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.yts.tsbible.BaseActivity;
import com.yts.tsbible.R;
import com.yts.tsbible.databinding.ChapterListBinding;
import com.yts.tsbible.databinding.LabelListBinding;
import com.yts.tsbible.ui.adapter.ChapterSelectAdapter;
import com.yts.tsbible.ui.adapter.LabelSelectAdapter;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.viewmodel.ChapterListViewModel;
import com.yts.tsbible.viewmodel.LabelListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChapterSelectActivity extends BaseActivity {
    private ChapterListBinding binding;
    private ChapterListViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_chapter_select);
        model = ViewModelProviders.of(this).get(ChapterListViewModel.class);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        String label = getIntent().getStringExtra(Keys.LABEL);

        model.initData(label);
        observe();

        registerReceiver(broadcastReceiver, getIntentFilter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void observe() {
        model.mChapterList.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                RecyclerView view = binding.listChapter;
                ChapterSelectAdapter adapter = (ChapterSelectAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setChapterList(strings);
                } else {
                    GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 5);
                    adapter = new ChapterSelectAdapter(strings);
                    adapter.setHasStableIds(true);
                    view.setLayoutManager(manager);
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
                if (action.equals(SendBroadcast.EDIT_CHAPTER)) {
                    finish();
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.EDIT_CHAPTER);
        return intentFilter;
    }
}
