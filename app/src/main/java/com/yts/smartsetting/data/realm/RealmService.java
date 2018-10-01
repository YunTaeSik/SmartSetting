package com.yts.smartsetting.data.realm;

import com.yts.smartsetting.data.model.Location;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class RealmService {
    public static Realm realm = Realm.getDefaultInstance();

    public static void saveLocation(Location location) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(location);
        realm.commitTransaction();
    }

    public static List<Location> getLocationList() {
        return realm.copyFromRealm(realm.where(Location.class).findAll().sort("date", Sort.DESCENDING));
    }
}
