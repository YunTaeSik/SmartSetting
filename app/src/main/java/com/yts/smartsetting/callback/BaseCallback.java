package com.yts.smartsetting.callback;

import android.content.pm.ResolveInfo;

public interface BaseCallback {
    void close();

    void save(String kind, ResolveInfo resolveInfo);
}
