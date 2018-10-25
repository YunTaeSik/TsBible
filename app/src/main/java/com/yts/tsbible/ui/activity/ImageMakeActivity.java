package com.yts.tsbible.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import com.yalantis.ucrop.UCrop;
import com.yts.tsbible.BaseActivity;
import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.databinding.ImageMakeBinding;
import com.yts.tsbible.ui.adapter.ImageMakeSelectAdapter;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.RequestCode;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.utills.ShowIntent;
import com.yts.tsbible.viewmodel.ImageMakeViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ImageMakeActivity extends BaseActivity {
    private ImageMakeBinding binding;
    private ImageMakeViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bible bible = getIntent().getParcelableExtra(Keys.BIBLE);
        String subTitle = getIntent().getStringExtra(Keys.SUBTITLE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_make);
        model = ViewModelProviders.of(this).get(ImageMakeViewModel.class);
        binding.setModel(model);
        model.setAdRequest(adRequest);
        model.initData(realm, mCompositeDisposable, bible, subTitle);
        model.setImageView(binding.cardImage);
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
        model.mImageIdList.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> ids) {
                RecyclerView view = binding.listBackground;
                ImageMakeSelectAdapter adapter = (ImageMakeSelectAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setImageIdList(ids);
                } else {
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    adapter = new ImageMakeSelectAdapter(ids);
                    adapter.setHasStableIds(true);
                    view.setLayoutManager(manager);
                    view.setNestedScrollingEnabled(false);
                    view.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCode.IMAGE_SELECT) {
                ShowIntent.imageCroup(this, data);
            } else if (requestCode == RequestCode.IMAGE_CROP) {
                Uri uri = UCrop.getOutput(data);
                if (uri != null) {
                    String path = uri.getPath();
                    model.setSelectBackground(path);
                }
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(SendBroadcast.EDIT_MAKE_IMAGE_BACKGROUND)) { //기본 배경화면
                    int drawableId = intent.getIntExtra(Keys.DRAWABLE_ID, R.drawable.img_bible_background_01);
                    model.setSelectBackground(drawableId);
                } else if (action.equals(SendBroadcast.ADD_SENTENCE)) {
                    model.addText(intent.getStringExtra(Keys.SENTENCE));
                    model.addSubText(String.valueOf(intent.getIntExtra(Keys.POSITION, 0) + 1));
                } else if (action.equals(SendBroadcast.EDIT_SENTENCE)) {
                    model.setText(intent.getStringExtra(Keys.SENTENCE));
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.EDIT_MAKE_IMAGE_BACKGROUND);
        intentFilter.addAction(SendBroadcast.ADD_SENTENCE);
        intentFilter.addAction(SendBroadcast.EDIT_SENTENCE);
        return intentFilter;
    }

}
