package com.yts.tsbible.data.model;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Testament extends RealmObject {

    private String name;

    private RealmList<Label> labelList;
}
