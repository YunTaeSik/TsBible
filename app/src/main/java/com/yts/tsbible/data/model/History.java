package com.yts.tsbible.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class History extends RealmObject {
    @PrimaryKey
    private long date;

    private String imagePath;
    private String title;
    private String content;
    private boolean isHighLighter;
    private boolean isBookMark;
    private String label;
    private String chapter;
    private String paragraph; // 절

    public History() {

    }

    public History(long date, String imagePath, String title, String content, String label, String chapter, String paragraph) {
        this.date = date;
        this.imagePath = imagePath;
        this.title = title;
        this.content = content;
        this.label = label;
        this.chapter = chapter;
        this.paragraph = paragraph;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHighLighter() {
        return isHighLighter;
    }

    public void setHighLighter(boolean highLighter) {
        isHighLighter = highLighter;
    }

    public boolean isBookMark() {
        return isBookMark;
    }

    public void setBookMark(boolean bookMark) {
        isBookMark = bookMark;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }
}
