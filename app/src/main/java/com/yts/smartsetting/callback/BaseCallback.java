package com.yts.smartsetting.callback;

import android.content.pm.ResolveInfo;

import com.yts.smartsetting.data.model.Location;

public interface BaseCallback {
    void close();

    void toast(String text);

    void toast(int textId);

    void save(String kind, ResolveInfo resolveInfo);

    void save(Location location);

    void startLocationListDialog();

    void startLocationDialog(Location location);

    void startSelectLocation();
}
