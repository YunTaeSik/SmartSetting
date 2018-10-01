package com.yts.smartsetting.callback;

import android.content.pm.ResolveInfo;

import com.yts.smartsetting.data.model.Location;

public interface BaseCallback {
    void close();

    void save(String kind, ResolveInfo resolveInfo);

    void startLocationListDialog();

    void startLocationDialog(Location location);
}
