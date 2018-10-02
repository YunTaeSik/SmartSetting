package com.yts.smartsetting.utill;

import android.location.Location;

public class Distance {
    public static boolean get(Location currentLocation, com.yts.smartsetting.data.model.Location location) {
        try {
            Location pointLocation = new Location("point");
            pointLocation.setLatitude(Double.parseDouble(location.getLatitude()));
            pointLocation.setLongitude(Double.parseDouble(location.getLongitude()));

            float distance = currentLocation.distanceTo(pointLocation);
            if (distance <= Keys.DISTANCE_METER) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
