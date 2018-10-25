package com.yts.tsbible.data.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Chapter extends RealmObject {
    private String name;
    private RealmList<Paragraph> paragraphList;
}
