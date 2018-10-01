package com.yts.smartsetting.utill;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.places.Place;
import com.yts.smartsetting.data.model.PlaceData;

public class SendBroadcast {

    public static void earEdit(Context context) {
        Intent send = new Intent(Keys.EDIT_EAR);
        context.sendBroadcast(send);
    }

    public static void blueToothEdit(Context context) {
        Intent send = new Intent(Keys.EDIT_BLUE_TOOTH);
        context.sendBroadcast(send);
    }


    public static void place(Context context, String action, Place place) {
        if (place != null) {
            String name = NullFilter.check(place.getName().toString());
            String address = "";
            if (place.getAddress() != null) {
                address = place.getAddress().toString();
            }
            PlaceData placeData = new PlaceData(name, address, place.getLatLng());
            Intent send = new Intent(action);
            send.putExtra(Keys.PLACE, placeData);
            context.sendBroadcast(send);
        }
    }
}
