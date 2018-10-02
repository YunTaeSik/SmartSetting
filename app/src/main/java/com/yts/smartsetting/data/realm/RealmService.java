package com.yts.smartsetting.data.realm;

import com.yts.smartsetting.data.model.Location;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.Sort;

public class RealmService {
    public static Realm realm = Realm.getDefaultInstance();

    public static void saveLocation(Location location) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(location);
        realm.commitTransaction();
    }

    public static void deleteLocation(Location location) {
        realm.beginTransaction();
        realm.where(Location.class).equalTo("date", location.getDate()).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

   /* public static List<Location> getLocationList() {
        return realm.copyFromRealm(realm.where(Location.class).findAll().sort("date", Sort.DESCENDING));
    }*/

    public static Observable<List<Location>> getLocationList() {
        return Observable.just(realm.copyFromRealm(realm.where(Location.class).findAll().sort("date", Sort.DESCENDING)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
