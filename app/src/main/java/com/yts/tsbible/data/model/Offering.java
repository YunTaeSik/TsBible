package com.yts.tsbible.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Offering extends RealmObject {
    @PrimaryKey
    private long date;

    private String name;
    private long money; //낸 금액
    private long amount; //총액

    public Offering() {

    }

    public Offering(long date) {
        this.date = date;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
