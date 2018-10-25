package com.yts.tsbible.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private String id;

    private long refreshTime;
    private long offeringAmount;
    private Bible todayBible;
    private Goal goal;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public long getOfferingAmount() {
        return offeringAmount;
    }

    public void setOfferingAmount(long offeringAmount) {
        this.offeringAmount = offeringAmount;
    }

    public Bible getTodayBible() {
        return todayBible;
    }

    public void setTodayBible(Bible todayBible) {
        this.todayBible = todayBible;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
