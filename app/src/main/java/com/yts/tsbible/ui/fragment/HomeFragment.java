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
import com.yts.tsbible.databinding.HomeBinding;
import com.yts.tsbible.ui.adapter.HomeAdapter;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.viewmodel.HomeViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class HomeFragment extends BaseFragment {
    private HomeBinding binding;
    public HomeViewModel model;


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        model.initData(realm, mCompositeDisposable);

        observe();
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
        model.mHomeList.observe(this, new Observer<List<Object>>() {
            @Override
            public void onChanged(List<Object> objects) {
                RecyclerView view = binding.listHome;
                HomeAdapter adapter = (HomeAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setHomeList(objects);
                } else {
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    adapter = new HomeAdapter(objects);
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
                if (action.equals(SendBroadcast.FINISH_SAVE_IMAGE) || action.equals(SendBroadcast.FINISH_SAVE_HIGHLIGHTER)
                        || action.equals(SendBroadcast.FINISH_SAVE_BOOKMARK)) {
                    if (model != null) {
                        model.getHomeList();
                    }
                } else if (action.equals(SendBroadcast.SAVE_OFFERING) || action.equals(SendBroadcast.DELETE_OFFERING) || action.equals(SendBroadcast.SAVE_GOAL)) {
                    if (model != null) {
                        model.changeHomeHeader();
                    }
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.FINISH_SAVE_IMAGE);
        intentFilter.addAction(SendBroadcast.FINISH_SAVE_HIGHLIGHTER);
        intentFilter.addAction(SendBroadcast.FINISH_SAVE_BOOKMARK);
        intentFilter.addAction(SendBroadcast.SAVE_OFFERING);
        intentFilter.addAction(SendBroadcast.DELETE_OFFERING);
        intentFilter.addAction(SendBroadcast.SAVE_GOAL);
        return intentFilter;
    }
}
