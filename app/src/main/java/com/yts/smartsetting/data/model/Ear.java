package com.yts.smartsetting.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ear extends RealmObject {
    @PrimaryKey
    public String packageName;

    public String name;
}
