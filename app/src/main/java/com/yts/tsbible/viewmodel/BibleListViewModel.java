package com.yts.tsbible.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;


import com.yts.tsbible.R;
import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.data.model.History;
import com.yts.tsbible.data.realm.RealmService;
import com.yts.tsbible.ui.activity.ChapterSelectActivity;
import com.yts.tsbible.ui.activity.LabelSelectActivity;
import com.yts.tsbible.utills.Keys;
import com.yts.tsbible.utills.SendBroadcast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.realm.Realm;

public class BibleListViewModel extends BaseViewModel {
    public TSLiveData<String> mLabel = new TSLiveData<>(Bible.books[0]);
    public TSLiveData<String> mChapter = new TSLiveData<>("1");

    public TSLiveData<ArrayList<Bible>> mBibleList = new TSLiveData<>(new ArrayList<Bible>());

    public TSLiveData<Boolean> isSettingVisible = new TSLiveData<>(false);

    public TSLiveData<Integer> mTextSize = new TSLiveData<>(8);
    public TSLiveData<Boolean> isBlueLight = new TSLiveData<>(false);

    public TSLiveData<Boolean> isSentenceSettingVisible = new TSLiveData<>(false);

    public TSLiveData<Integer> mSelectSentencePosition = new TSLiveData<>(0);
    public TSLiveData<Bible> mSelectBible = new TSLiveData<>();


    public void initData(Realm realm, CompositeDisposable compositeDisposable, int textSize, String label, String chapter, boolean isBlueLight) {
        this.realm = realm;
        this.compositeDisposable = compositeDisposable;


        mTextSize.setValue(textSize);
        mLabel.setValue(label);
        mChapter.setValue(chapter);
        this.isBlueLight.setValue(isBlueLight);
        getBibleList(realm, compositeDisposable);
    }

    public void changeSettingVisible() {
        if (isSettingVisible != null && isSettingVisible.getValue() != null) {
            isSettingVisible.setValue(!isSettingVisible.getValue());
        }
    }

    public boolean onlySettingClose() {
        if (isSettingVisible != null && isSettingVisible.getValue() != null && isSettingVisible.getValue()) {
            isSettingVisible.setValue(!isSettingVisible.getValue());
            return true;
        }
        return false;
    }

    public void changeSentenceSettingVisible() {
        if (isSentenceSettingVisible != null && isSentenceSettingVisible.getValue() != null) {
            isSentenceSettingVisible.setValue(!isSentenceSettingVisible.getValue());
        }
    }

    public void changeSentenceSettingVisible(int position) {
        boolean visible = this.isSentenceSettingVisible.getValue();
        if (!visible) {
            isSentenceSettingVisible.setValue(true);
        } else {
            if (mSelectSentencePosition.getValue() == position) {
                isSentenceSettingVisible.setValue(false);
            }
        }
    }

    public boolean onlySentenceSettingClose() {
        if (isSentenceSettingVisible != null && isSentenceSettingVisible.getValue() != null && isSentenceSettingVisible.getValue()) {
            isSentenceSettingVisible.setValue(!isSentenceSettingVisible.getValue());
            return true;
        }
        return false;
    }

    protected void getBibleList(Realm realm, CompositeDisposable compositeDisposable) {
        compositeDisposable.add(RealmService.getBibleList(realm, mLabel.getValue(), mChapter.getValue()).subscribe(new Consumer<List<Bible>>() {
            @Override
            public void accept(List<Bible> bibles) throws Exception {
                if (bibles != null) {
                    ArrayList<Bible> bibleArrayList = new ArrayList<>();
                    bibleArrayList.addAll(bibles);
                    mBibleList.setValue(bibleArrayList);
                }
            }
        }));
    }

    protected void getBibleList(Realm realm, CompositeDisposable compositeDisposable, String label, String chapte) {
        compositeDisposable.add(RealmService.getBibleList(realm, label, chapte).subscribe(new Consumer<List<Bible>>() {
            @Override
            public void accept(List<Bible> bibles) throws Exception {
                if (bibles != null) {
                    ArrayList<Bible> bibleArrayList = new ArrayList<>();
                    bibleArrayList.addAll(bibles);
                    mBibleList.setValue(bibleArrayList);
                }
            }
        }));
    }


    public void changeLabel(String label) {
        mLabel.setValue(label);
        mChapter.setValue("1");
        compositeDisposable.add(RealmService.getBibleList(realm, mLabel.getValue(), mChapter.getValue()).subscribe(new Consumer<List<Bible>>() {
            @Override
            public void accept(List<Bible> bibles) throws Exception {
                if (bibles != null) {
                    ArrayList<Bible> bibleArrayList = new ArrayList<>();
                    bibleArrayList.addAll(bibles);
                    mBibleList.setValue(bibleArrayList);
                }
            }
        }));
    }

    public void changeChapter(String chapter) {
        mChapter.setValue(chapter);
        compositeDisposable.add(RealmService.getBibleList(realm, mLabel.getValue(), mChapter.getValue()).subscribe(new Consumer<List<Bible>>() {
            @Override
            public void accept(List<Bible> bibles) throws Exception {
                if (bibles != null) {
                    ArrayList<Bible> bibleArrayList = new ArrayList<>();
                    bibleArrayList.addAll(bibles);
                    mBibleList.setValue(bibleArrayList);
                }
            }
        }));
    }

    public void changeBlueLight() {
        if (isBlueLight != null && isBlueLight.getValue() != null) {
            isBlueLight.setValue(!isBlueLight.getValue());
        }
    }

    public void changeTextSize(int progress) {
        mTextSize.setValue(progress);
    }


    public void changeSelectSentencePosition(int position) {
        mSelectSentencePosition.setValue(position);
        try {
            ArrayList<Bible> bibles = mBibleList.getValue();
            int selectSentencePosition = mSelectSentencePosition.getValue();
            Bible bible = bibles.get(selectSentencePosition);
            mSelectBible.setValue(bible);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 클릭이벤트
     */

    public void left() {
        List<String> labelList = Arrays.asList(Bible.books);
        int labelPosition = labelList.indexOf(mLabel.getValue());


        String label = mLabel.getValue();
        int chapter = Integer.parseInt(mChapter.getValue());

        if (chapter <= 1) {
            int newLabelPosition = labelPosition > 0 ? labelPosition - 1 : Bible.books.length - 1;
            label = Bible.books[newLabelPosition];

            List<String> lastChapterList = Arrays.asList(Bible.chapters);
            chapter = Integer.parseInt(lastChapterList.get(newLabelPosition));
        } else {
            chapter = chapter - 1;
        }
        mLabel.setValue(label);
        changeChapter(String.valueOf(chapter));
    }

    public void right() {
        List<String> labelList = Arrays.asList(Bible.books);
        int labelPosition = labelList.indexOf(mLabel.getValue());

        List<String> lastChapterList = Arrays.asList(Bible.chapters);

        String label = mLabel.getValue();

        int chapter = Integer.parseInt(mChapter.getValue());
        int lastChapter = Integer.parseInt(lastChapterList.get(labelPosition));

        if (chapter >= lastChapter) {

            int newLabelPosition = labelPosition >= Bible.books.length - 1 ? 0 : labelPosition + 1;

            label = Bible.books[newLabelPosition];

            chapter = 1;
        } else {
            chapter = chapter + 1;
        }

        mLabel.setValue(label);
        changeChapter(String.valueOf(chapter));
    }

    public void startLabelSelect(View view) {
        Context context = view.getContext();
        Intent labelSelect = new Intent(context, LabelSelectActivity.class);
        labelSelect.putExtra(Keys.LABEL, mLabel.getValue());
        context.startActivity(labelSelect);
    }

    public void startChapterSelect(View view) {
        Context context = view.getContext();
        Intent chapterSelect = new Intent(context, ChapterSelectActivity.class);
        chapterSelect.putExtra(Keys.LABEL, mLabel.getValue());
        context.startActivity(chapterSelect);
    }

    public void startImageMake(View view) {
        Bible bible = mSelectBible.getValue();
        startImageMake(view.getContext(), bible);
    }

    public void highLight(View view) {
        try {
            Context context = view.getContext();
            ArrayList<Bible> bibles = mBibleList.getValue();
            Bible bible = mSelectBible.getValue();
            bible.setHighlight(!bible.isHighlight());
            RealmService.saveBible(realm, bible);

            if (bible.isHighlight()) {
                String subText = bible.getLabel() + " " + bible.getChapter() + context.getString(R.string.chapter) + " " + bible.getParagraph() + context.getString(R.string.sentence);
                History history = new History(System.currentTimeMillis(), "", context.getString(R.string.title_highlighter_save, subText), bible.getSentence(), bible.getLabel(), bible.getChapter(), bible.getParagraph());
                history.setHighLighter(true);
                RealmService.saveHistory(realm, history);
                SendBroadcast.finishSaveHighlighter(context);
            }


            bibles.set(mSelectSentencePosition.getValue(), bible);
            mBibleList.setValue(bibles);
            mSelectBible.setValue(bible);
            changeSentenceSettingVisible();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bookMark(View view) {
        try {
            Context context = view.getContext();
            ArrayList<Bible> bibles = mBibleList.getValue();
            int selectSentencePosition = mSelectSentencePosition.getValue();

            Bible bible = mSelectBible.getValue();
            bible.setBookMark(!bible.isBookMark());
            RealmService.saveBible(realm, bible);

            if (bible.isBookMark()) {
                String subText = bible.getLabel() + " " + bible.getChapter() + context.getString(R.string.chapter) + " " + bible.getParagraph() + context.getString(R.string.sentence);
                History history = new History(System.currentTimeMillis(), "", context.getString(R.string.title_bookmark_save, subText), bible.getSentence(), bible.getLabel(), bible.getChapter(), bible.getParagraph());
                history.setBookMark(true);
                RealmService.saveHistory(realm, history);
                SendBroadcast.finishSaveBookMark(context);

            }


            bibles.set(selectSentencePosition, bible);
            mBibleList.setValue(bibles);
            mSelectBible.setValue(bible);

            changeSentenceSettingVisible();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
