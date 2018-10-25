package com.yts.tsbible.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Bible extends RealmObject implements Parcelable {
    @PrimaryKey
    private String idx;
    private String cate;
    private String testament; //구약/신약

    private String label; //창세기
    private int book;// 창세기의 순번 = 1

    private String chapter; // 장
    private String paragraph; // 절

    private String sentence;
    private boolean isHighlight;
    private boolean isBookMark;

    public Bible() {
    }

    public Bible(String idx, String cate, String label, int book, String testament, String chapter, String paragraph, String sentence) {
        this.idx = idx;
        this.cate = cate;
        this.label = label;
        this.book = book;
        this.testament = testament;
        this.chapter = chapter;
        this.paragraph = paragraph;
        this.sentence = sentence;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }

    public String getTestament() {
        return testament;
    }

    public void setTestament(String testament) {
        this.testament = testament;
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

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setHighlight(boolean highlight) {
        isHighlight = highlight;
    }

    public boolean isBookMark() {
        return isBookMark;
    }

    public void setBookMark(boolean bookMark) {
        isBookMark = bookMark;
    }


    public static final String[] cates = {"구약", "신약"};
    public static final String[] books = {
            "창세기", "출애굽기", "레위기", "민수기", "신명기",
            "여호수아", "사사기", "룻기", "사무엘상", "사무엘하",
            "열왕기상", "열왕기하", "역대상", "역대하", "에스라",
            "느헤미야", "에스더", "욥기", "시편", "잠언",
            "전도서", "아가", "이사야", "예레미야", "예레미야애가",
            "에스겔", "다니엘", "호세아", "요엘", "아모스",
            "오바댜", "요나", "미가", "나훔", "하박국",
            "스바냐", "학개", "스가랴", "말라기", "마태복음",
            "마가복음", "누가복음", "요한복음", "사도행전", "로마서",
            "고린도전서", "고린도후서", "갈라디아서", "에베소서", "빌립보서",
            "골로새서", "데살로니가전서", "데살로니가후서", "디모데전서", "디모데후서",
            "디도서", "빌레몬서", "히브리서", "야고보서", "베드로전서",
            "베드로후서", "요한1서", "요한2서", "요한3서", "유다서",
            "요한계시록"};
    public static final String[] chapters = {
            "50", "40", "27", "36", "34",
            "24", "21", "4", "31", "24",
            "22", "25", "29", "36", "10",
            "13", "10", "42", "150", "31",
            "12", "8", "66", "52", "5",
            "48", "12", "14", "3", "9",
            "1", "4", "7", "3", "3",
            "3", "2", "14", "4", "28",
            "16", "24", "21", "28", "16",
            "16", "13", "6", "6", "4",
            "4", "5", "3", "6", "4",
            "3", "1", "13", "5", "5",
            "3", "5", "1", "1", "1",
            "22"}; //~39 구약 40~ 신약

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idx);
        dest.writeString(this.cate);
        dest.writeString(this.testament);
        dest.writeString(this.label);
        dest.writeInt(this.book);
        dest.writeString(this.chapter);
        dest.writeString(this.paragraph);
        dest.writeString(this.sentence);
        dest.writeByte(this.isHighlight ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isBookMark ? (byte) 1 : (byte) 0);
    }

    protected Bible(Parcel in) {
        this.idx = in.readString();
        this.cate = in.readString();
        this.testament = in.readString();
        this.label = in.readString();
        this.book = in.readInt();
        this.chapter = in.readString();
        this.paragraph = in.readString();
        this.sentence = in.readString();
        this.isHighlight = in.readByte() != 0;
        this.isBookMark = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Bible> CREATOR = new Parcelable.Creator<Bible>() {
        @Override
        public Bible createFromParcel(Parcel source) {
            return new Bible(source);
        }

        @Override
        public Bible[] newArray(int size) {
            return new Bible[size];
        }
    };
}
