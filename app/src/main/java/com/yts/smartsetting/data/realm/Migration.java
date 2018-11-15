package com.yts.smartsetting.data.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {

    //     .removeField("firstName")
    //                    .removeField("lastName");
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            RealmObjectSchema locationSchema = schema.get("Location");
            locationSchema.addField("arriveSoundMode", int.class);
            locationSchema.addField("leaveSoundMode", int.class);
            oldVersion++;
        }
    }


}
