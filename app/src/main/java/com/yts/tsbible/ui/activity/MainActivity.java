package com.yts.tsbible.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yts.tsbible.BaseActivity;
import com.yts.tsbible.R;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.data.model.User;
import com.yts.tsbible.data.realm.RealmService;
import com.yts.tsbible.databinding.MainViewBinding;
import com.yts.tsbible.ui.dialog.AlertDialogCreate;
import com.yts.tsbible.ui.fragment.BibleListFragment;
import com.yts.tsbible.ui.fragment.HomeFragment;
import com.yts.tsbible.ui.fragment.SettingFragment;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.NullFilter;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.utills.SharedPrefsUtils;
import com.yts.tsbible.utills.ShowIntent;
import com.yts.tsbible.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private String[] tags = {Keys.BIBLE, Keys.HOME, Keys.SETTING};
    private String mCurrentFragmentTag = Keys.HOME;

    private MainViewBinding binding;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.setAdRequest(adRequest);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        setSupportActionBar(binding.bottomAppBar);
        setFragments();

        binding.btnFloating.setOnClickListener(this);

        registerReceiver(broadcastReceiver, getIntentFilter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeReadBible();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseReadBible();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        drawableAnimation(binding.btnFloating.getDrawable());
        if (mCurrentFragmentTag.equals(tags[0])) {
            SendBroadcast.editBibleSettingVisible(this);
        } else if (mCurrentFragmentTag.equals(tags[1])) {
            List<User> userList = RealmService.getUserList(realm);
            User user = userList.get(0);

            Bible bible = user.getTodayBible();

            String subTitle = NullFilter.check(bible.getLabel()) + " " + NullFilter.check(bible.getChapter()) + "-" + (NullFilter.check(bible.getParagraph()));

            Intent imageMake = new Intent(this, ImageMakeActivity.class);
            imageMake.putExtra(Keys.BIBLE, bible);
            imageMake.putExtra(Keys.SUBTITLE, subTitle);
            startActivity(imageMake);
        } else if (mCurrentFragmentTag.equals(tags[2])) {
            ShowIntent.invite(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_bible:
                binding.btnFloating.setImageResource(R.drawable.ic_settings_anim);
                drawableAnimation(binding.btnFloating.getDrawable());
                moveFragment(tags[0]);
                break;
            case R.id.app_bar_home:
                binding.btnFloating.setImageResource(R.drawable.ic_create_anim);
                drawableAnimation(binding.btnFloating.getDrawable());
                moveFragment(tags[1]);
                break;
            case R.id.app_bar_settings:
                binding.btnFloating.setImageResource(R.drawable.ic_share_anim);
                drawableAnimation(binding.btnFloating.getDrawable());
                moveFragment(tags[2]);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void drawableAnimation(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (drawable instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                animatedVectorDrawable.start();
            }
        } else {
            if (drawable instanceof AnimatedVectorDrawableCompat) {
                AnimatedVectorDrawableCompat animatedVectorDrawableCompat = (AnimatedVectorDrawableCompat) drawable;
                animatedVectorDrawableCompat.start();
            }
        }
    }

    private void setFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(BibleListFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(SettingFragment.newInstance());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            fragmentTransaction.add(R.id.container, fragment, tags[i]);
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.show(fragments.get(1));
        fragmentTransaction.commit();
    }

    private void moveFragment(String tag) {
        if (mCurrentFragmentTag.equals(tag)) {
            return;
        }

        mCurrentFragmentTag = tag;
        moveBible();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentManager.getFragments()) {
            String fTag = fragment.getTag();
            if (fTag != null) {
                if (fTag.equals(tag)) {
                    fragmentTransaction.show(fragment);
                } else {
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        fragmentTransaction.commit();
    }

    public void moveBible() {
        if (mCurrentFragmentTag.equals(tags[0])) {
            startReadBible();
        }
        if (!mCurrentFragmentTag.equals(tags[0])) {
            finishReadBible();
        }
    }

    public void resumeReadBible() {
        if (mCurrentFragmentTag.equals(tags[0])) {
            startReadBible();
        }
    }

    public void pauseReadBible() {
        if (mCurrentFragmentTag.equals(tags[0])) {
            finishReadBible();
        }
    }

    private void startReadBible() {
        RealmService.startReadBible(realm);
    }

    private void finishReadBible() {
        RealmService.stopReadBible(realm);
    }


    @Override
    public void onBackPressed() {
        boolean backPressed = true;
        if (mCurrentFragmentTag.equals(tags[0])) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment instanceof BibleListFragment) {
                    BibleListFragment bibleListFragment = (BibleListFragment) fragment;
                    if (bibleListFragment.model != null) {
                        if (bibleListFragment.model.onlySentenceSettingClose()) {
                            backPressed = false;
                            break;
                        } else if (bibleListFragment.model.onlySettingClose()) {
                            backPressed = false;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        if (backPressed) {
            super.onBackPressed();
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(SendBroadcast.SHARED_SAVE_IMAGE)) {
                    final String filePath = intent.getStringExtra(Keys.FILE_PATH);
                    new AlertDialogCreate(context).imageShare(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ShowIntent.shareImage(MainActivity.this, filePath);
                            dialogInterface.dismiss();
                        }
                    });
                } else if (action.equals(SendBroadcast.MOVE_BIBLE_LIST)) {
                    final String label = intent.getStringExtra(Keys.LABEL);
                    final String chapter = intent.getStringExtra(Keys.CHAPTER);
                    final int position = intent.getIntExtra(Keys.POSITION, 0);
                    new AlertDialogCreate(context).moveBibleList(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            SharedPrefsUtils.setStringPreference(getApplicationContext(), Keys.LAST_LABEL, label);
                            SharedPrefsUtils.setStringPreference(getApplicationContext(), Keys.LAST_CHAPTER, chapter);
                            moveFragment(tags[0]);
                            SendBroadcast.moveBibleListPosition(getApplicationContext(), position);
                        }
                    });
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadcast.SHARED_SAVE_IMAGE);
        intentFilter.addAction(SendBroadcast.MOVE_BIBLE_LIST);
        return intentFilter;
    }
}
