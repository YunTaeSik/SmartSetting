package com.yts.smartsetting.data.realm;

import com.yts.smartsetting.data.model.Location;

import io.realm.Realm;

public class RealmService {
    public static Realm realm = Realm.getDefaultInstance();

    public static void saveLocation(Location location) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(location);
        realm.commitTransaction();
    }
}
