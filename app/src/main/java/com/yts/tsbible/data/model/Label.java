package com.yts.tsbible.data.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Label extends RealmObject {
    private String name;
    private RealmList<Chapter> chapterList;
}
