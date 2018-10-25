package com.yts.tsbible.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yts.tsbible.BaseFragment;
import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.ui.adapter.BibleAdapter;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.NullFilter;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.utills.SharedPrefsUtils;
import com.yts.tsbible.viewmodel.BibleListViewModel;
import com.yts.tsbible.databinding.BibleListBinding;


import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BibleListFragment extends BaseFragment {
    private BibleListBinding binding;
    public BibleListViewModel model;

    public static BibleListFragment newInstance() {
        Bundle args = new Bundle();
        BibleListFragment fragment = new BibleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bible_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(BibleListViewModel.class);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        int textSize = SharedPrefsUtils.getIntegerPreference(getContext(), Keys.TEXT_SIZE, 8);
        String lastLabel = SharedPrefsUtils.getStringPreference(getContext(), Keys.LAST_LABEL, Bible.books[0]);
        String lastChapter = SharedPrefsUtils.getStringPreference(getContext(), Keys.LAST_CHAPTER, "1");
        boolean isBlueLight = SharedPrefsUtils.getBooleanPreference(getContext(), Keys.IS_BLUE_LIGHT, false);

        model.initData(realm, mCompositeDisposable, textSize, lastLabel, lastChapter, isBlueLight);

        observe();
    }

    @Override
    public void onPause() {
        SharedPrefsUtils.setIntegerPreference(getContext(), Keys.TEXT_SIZE, NullFilter.check(model.mTextSize.getValue()));
        SharedPrefsUtils.setStringPreference(getContext(), Keys.LAST_LABEL, model.mLabel.getValue());
        SharedPrefsUtils.setStringPreference(getContext(), Keys.LAST_CHAPTER, model.mChapter.getValue());
        SharedPrefsUtils.setBooleanPreference(getContext(), Keys.IS_BLUE_LIGHT, NullFilter.check(model.isBlueLight.getValue()));
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (getActivity() != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        super.onDestroyView();
    }

    private void observe() {
        if (getActivity() != null) {
            getActivity().registerReceiver(broadcastReceiver, getIntentFilter());
        }
        /**
         * BibleList 어뎁터
         */
        model.mBibleList.observe(this, new Observer<ArrayList<Bible>>() {
            @Override
            public void onChanged(ArrayList<Bible> bibles) {
                RecyclerView view = binding.listBible;
                BibleAdapter adapter = (BibleAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setBibleList(bibles);
                } else {
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    adapter = new BibleAdapter(bibles);
                    adapter.setHasStableIds(true);
                    view.setLayoutManager(manager);
                    view.setAdapter(adapter);
                }
            }
        });
        model.mChapter.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    RecyclerView view = binding.listBible;
                    view.smoothScrollToPosition(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        model.mLabel.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    RecyclerView view = binding.listBible;
                    view.smoothScrollToPosition(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        model.mTextSize.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                RecyclerView view = binding.listBible;
                BibleAdapter adapter = (BibleAdapter) view.getAdapter();
                if (adapter != null) {
                    if (model != null && model.mTextSize != null && model.mTextSize.getValue() != null) {
                        adapter.setTextSize(model.mTextSize.getValue());
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
                    String label = intent.getStringExtra(Keys.LABEL);
                    if (model != null && realm != null && mCompositeDisposable != null) {
                        model.changeLabel(label);
                    }
                } else if (action.equals(SendBroadcast.EDIT_CHAPTER)) {
                    String chapter = intent.getStringExtra(Keys.CHAPTER);
                    if (model != null && realm != null && mCompositeDisposable != null) {
                        model.changeChapter(chapter);
                    }
                } else if (action.equals(SendBroadcast.EDIT_BIBLE_SETTING_VISIBLE)) {
                    if (model != null) {
                        model.changeSettingVisible();
                    }
                } else if (action.equals(SendBroadcast.EDIT_BIBLE_SENTENCE_SETTING_VISIBLE)) {
                    if (model != null) {
                        int selectSentencePosition = intent.getIntExtra(Keys.POSITION, 0);
                        model.changeSentenceSettingVisible(selectSentencePosition);
                        model.changeSelectSentencePosition(selectSentencePosition);
                    }
                } else if (action.equals(SendBroadcast.FINISH_SAVE_IMAGE)) {
                    final String filePath = intent.getStringExtra(Keys.FILE_PATH);
                    SendBroadcast.sharedSaveImage(getContext(), filePath);
                } else if (action.equals(SendBroadcast.MOVE_BIBLE_LIST_POSITION)) {
                    int position = intent.getIntExtra(Keys.POSITION, 0);
                    try {
                        RecyclerView view = binding.listBible;
                        view.smoothScrollToPosition(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.EDIT_LABEL);
        intentFilter.addAction(SendBroadcast.EDIT_CHAPTER);
        intentFilter.addAction(SendBroadcast.EDIT_BIBLE_SETTING_VISIBLE);
        intentFilter.addAction(SendBroadcast.EDIT_BIBLE_SENTENCE_SETTING_VISIBLE);
        intentFilter.addAction(SendBroadcast.FINISH_SAVE_IMAGE);
        intentFilter.addAction(SendBroadcast.MOVE_BIBLE_LIST_POSITION);
        return intentFilter;
    }
}
