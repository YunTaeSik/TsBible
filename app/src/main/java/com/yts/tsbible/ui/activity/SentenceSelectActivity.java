package com.yts.tsbible.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.yts.tsbible.BaseActivity;
import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.databinding.SentenceListBinding;
import com.yts.tsbible.ui.adapter.BibleAdapter;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.utills.SharedPrefsUtils;
import com.yts.tsbible.viewmodel.SentenceListViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SentenceSelectActivity extends BaseActivity {
    private SentenceListBinding binding;
    private SentenceListViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sentence_select);
        model = ViewModelProviders.of(this).get(SentenceListViewModel.class);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        Bible bible = getIntent().getParcelableExtra(Keys.BIBLE);
        if (bible != null) {
            String title = bible.getLabel() + " " + bible.getChapter() + getString(R.string.chapter);
            model.setTitle(title);
        }
        model.getBibleList(realm, mCompositeDisposable, bible);
        observe();
        registerReceiver(broadcastReceiver, getIntentFilter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void observe() {
        model.mBibleList.observe(this, new Observer<ArrayList<Bible>>() {
            @Override
            public void onChanged(ArrayList<Bible> bibles) {
                RecyclerView view = binding.listSentence;
                BibleAdapter adapter = (BibleAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setBibleList(bibles);
                } else {
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    adapter = new BibleAdapter(bibles);
                    adapter.setHasStableIds(true);
                    adapter.setAddSentence(true);
                    adapter.setTextSize(SharedPrefsUtils.getIntegerPreference(getApplicationContext(), Keys.TEXT_SIZE, 8));
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
                if (action.equals(SendBroadcast.ADD_SENTENCE)) {
                    finish();
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.ADD_SENTENCE);
        return intentFilter;
    }
}
