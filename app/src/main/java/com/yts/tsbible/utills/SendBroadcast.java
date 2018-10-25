package com.yts.tsbible.utills;

import android.content.Context;
import android.content.Intent;

public class SendBroadcast {
    public static final String EDIT_LABEL = "editLabel";
    public static final String EDIT_CHAPTER = "editChapter";
    public static final String EDIT_BIBLE_SETTING_VISIBLE = "editBibleSettingVisible";
    public static final String EDIT_BIBLE_SENTENCE_SETTING_VISIBLE = "editBibleSentenceSettingVisible";
    public static final String EDIT_MAKE_IMAGE_BACKGROUND = "editMakeImageBackground";

    public static final String ADD_SENTENCE = "addSentence";
    public static final String EDIT_SENTENCE = "editSentence";

    public static final String FINISH_SAVE_IMAGE = "finishSaveImage";
    public static final String SHARED_SAVE_IMAGE = "sharedSaveImage";
    public static final String FINISH_SAVE_HIGHLIGHTER = "finishSaveHighlighter";
    public static final String FINISH_SAVE_BOOKMARK = "finishSaveBookMark";

    public static final String MOVE_BIBLE_LIST = "moveBibleList";
    public static final String MOVE_BIBLE_LIST_POSITION = "moveBibleListPosition";

    public static final String SAVE_OFFERING = "saveOffering";
    public static final String DELETE_OFFERING = "deleteOffering";

    public static final String SAVE_GOAL = "saveGoal";

    public static void editLabel(Context context, String label) {
        Intent send = new Intent(EDIT_LABEL);
        send.putExtra(Keys.LABEL, label);
        context.sendBroadcast(send);
    }

    public static void editChapter(Context context, String chapter) {
        Intent send = new Intent(EDIT_CHAPTER);
        send.putExtra(Keys.CHAPTER, chapter);
        context.sendBroadcast(send);
    }

    public static void editBibleSettingVisible(Context context) {
        Intent send = new Intent(EDIT_BIBLE_SETTING_VISIBLE);
        context.sendBroadcast(send);
    }

    public static void editBibleSentenceSettingVisible(Context context, int position) {
        Intent send = new Intent(EDIT_BIBLE_SENTENCE_SETTING_VISIBLE);
        send.putExtra(Keys.POSITION, position);
        context.sendBroadcast(send);
    }

    public static void addSentence(Context context, String sentence, int position) {
        Intent send = new Intent(ADD_SENTENCE);
        send.putExtra(Keys.SENTENCE, sentence);
        send.putExtra(Keys.POSITION, position);
        context.sendBroadcast(send);
    }

    public static void editSentence(Context context, String sentence) {
        Intent send = new Intent(EDIT_SENTENCE);
        send.putExtra(Keys.SENTENCE, sentence);
        context.sendBroadcast(send);
    }

    public static void editMakeImageBackground(Context context, int drawableId) {
        Intent send = new Intent(EDIT_MAKE_IMAGE_BACKGROUND);
        send.putExtra(Keys.DRAWABLE_ID, drawableId);
        context.sendBroadcast(send);
    }

    public static void finishSaveImage(Context context, String filePath) {
        Intent send = new Intent(FINISH_SAVE_IMAGE);
        send.putExtra(Keys.FILE_PATH, filePath);
        context.sendBroadcast(send);
    }

    public static void sharedSaveImage(Context context, String filePath) {
        Intent send = new Intent(SHARED_SAVE_IMAGE);
        send.putExtra(Keys.FILE_PATH, filePath);
        context.sendBroadcast(send);
    }

    public static void finishSaveHighlighter(Context context) {
        Intent send = new Intent(FINISH_SAVE_HIGHLIGHTER);
        context.sendBroadcast(send);
    }

    public static void finishSaveBookMark(Context context) {
        Intent send = new Intent(FINISH_SAVE_BOOKMARK);
        context.sendBroadcast(send);
    }

    public static void moveBibleList(Context context, String label, String chapter, int position) {
        Intent send = new Intent(MOVE_BIBLE_LIST);
        send.putExtra(Keys.LABEL, label);
        send.putExtra(Keys.CHAPTER, chapter);
        send.putExtra(Keys.POSITION, position);
        context.sendBroadcast(send);
    }

    public static void moveBibleListPosition(Context context, int position) {
        Intent send = new Intent(MOVE_BIBLE_LIST_POSITION);
        send.putExtra(Keys.POSITION, position);
        context.sendBroadcast(send);
    }

    public static void saveOffering(Context context) {
        Intent send = new Intent(SAVE_OFFERING);
        context.sendBroadcast(send);
    }

    public static void deleteOffering(Context context) {
        Intent send = new Intent(DELETE_OFFERING);
        context.sendBroadcast(send);
    }

    public static void saveGoal(Context context) {
        Intent send = new Intent(SAVE_GOAL);
        context.sendBroadcast(send);
    }

}
