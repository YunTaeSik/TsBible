package com.yts.tsbible.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yts.tsbible.R;
import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.data.model.History;
import com.yts.tsbible.data.realm.RealmService;
import com.yts.tsbible.ui.activity.SentenceSelectActivity;
import com.yts.tsbible.ui.dialog.SentenceEditDialog;
import com.yts.tsbible.utills.CreateBitmap;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.SendBroadcast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

public class ImageMakeViewModel extends BaseViewModel {
    public TSLiveData<List<Integer>> mImageIdList = new TSLiveData<>();
    public TSLiveData<Object> mSelectBackground = new TSLiveData<>();

    public TSLiveData<String> mText = new TSLiveData<>("");
    public TSLiveData<String>
            mSubText = new TSLiveData<>("");

    private View mImageView;

    private Bible mBible;

    public void initData(Realm realm, CompositeDisposable compositeDisposable, Bible bible, String subTitle) {
        this.realm = realm;
        this.compositeDisposable = compositeDisposable;

        List<Integer> imageIdList = new ArrayList<>();
        imageIdList.add(R.drawable.image_empty);
        imageIdList.add(R.drawable.img_bible_background_01);
        imageIdList.add(R.drawable.img_bible_background_02);
        imageIdList.add(R.drawable.img_bible_background_03);
        imageIdList.add(R.drawable.img_bible_background_04);
        imageIdList.add(R.drawable.img_bible_background_05);
        imageIdList.add(R.drawable.img_bible_background_06);
        imageIdList.add(R.drawable.img_bible_background_07);

        mImageIdList.setValue(imageIdList);
        mSelectBackground.setValue(R.drawable.img_bible_background_03);
        mSubText.setValue(subTitle);

        if (bible != null) {
            mText.setValue(bible.getSentence());
            mBible = bible;
        }

    }

    public void setImageView(View mImageView) {
        this.mImageView = mImageView;
    }

    public void setText(String text) {
        mText.setValue(text);
    }

    public void addText(String text) {
        mText.setValue(mText.getValue() + "\n" + text);
    }

    public void addSubText(String subText) {
        mSubText.setValue(mSubText.getValue() + ", " + subText);
    }

    public void setSelectBackground(Object image) {
        mSelectBackground.setValue(image);
    }

    public void startSentenceEditDialog(View view) {
        SentenceEditDialog sentenceEditDialog = new SentenceEditDialog(view.getContext(), mText.getValue());
        sentenceEditDialog.show();
    }

    public void startSentenceSelectActivity(View view) {
        Context context = view.getContext();
        Intent sentenceSelect = new Intent(context, SentenceSelectActivity.class);
        sentenceSelect.putExtra(Keys.BIBLE, mBible);
        context.startActivity(sentenceSelect);
    }

    public void save(View view) {
        final Context context = view.getContext();
        final File file = CreateBitmap.create(context, mImageView);
        if (file != null) {
            History history = new History(System.currentTimeMillis(), file.getPath(), context.getString(R.string.title_image_save, mSubText.getValue()), "", mBible.getLabel(), mBible.getChapter(), mBible.getParagraph());
            RealmService.saveHistory(realm, history);
            SendBroadcast.finishSaveImage(context, file.getPath());
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }
    }
}
