package com.yts.tsbible.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Goal extends RealmObject implements Parcelable {
    private String goal;

    private String startReadTime;
    private String endReadTime;

    private String readTime;

    private boolean todaySuccess;
    private long totalSuccessesNumber;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getStartReadTime() {
        return startReadTime;
    }

    public void setStartReadTime(String startReadTime) {
        this.startReadTime = startReadTime;
    }

    public String getEndReadTime() {
        return endReadTime;
    }

    public void setEndReadTime(String endReadTime) {
        this.endReadTime = endReadTime;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public boolean isTodaySuccess() {
        return todaySuccess;
    }

    public void setTodaySuccess(boolean todaySuccess) {
        this.todaySuccess = todaySuccess;
    }

    public long getTotalSuccessesNumber() {
        return totalSuccessesNumber;
    }

    public void setTotalSuccessesNumber(long totalSuccessesNumber) {
        this.totalSuccessesNumber = totalSuccessesNumber;
    }


    public Goal() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goal);
        dest.writeString(this.startReadTime);
        dest.writeString(this.endReadTime);
        dest.writeString(this.readTime);
        dest.writeByte(this.todaySuccess ? (byte) 1 : (byte) 0);
        dest.writeLong(this.totalSuccessesNumber);
    }

    protected Goal(Parcel in) {
        this.goal = in.readString();
        this.startReadTime = in.readString();
        this.endReadTime = in.readString();
        this.readTime = in.readString();
        this.todaySuccess = in.readByte() != 0;
        this.totalSuccessesNumber = in.readLong();
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel source) {
            return new Goal(source);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };
}
