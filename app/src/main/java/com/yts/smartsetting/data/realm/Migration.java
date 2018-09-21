package com.yts.smartsetting.data.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {

    //     .removeField("firstName")
    //                    .removeField("lastName");
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
/*        if (oldVersion == 2) {
            RealmObjectSchema coupleSchema = schema.get("Couple");
            coupleSchema.addField("isStartOne", boolean.class);

            schema.create("Anniversary").
                    addField("date", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("title", String.class);
            oldVersion++;
        }
        if (oldVersion == 3) {
            RealmObjectSchema userSchema = schema.get("User");
            userSchema.addField("imageData", byte[].class);

            oldVersion++;
        }*/
    }


}
