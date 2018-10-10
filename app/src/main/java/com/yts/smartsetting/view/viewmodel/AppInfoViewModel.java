package com.yts.smartsetting.view.viewmodel;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import com.yts.smartsetting.data.TSLiveData;

public class AppInfoViewModel extends BaseViewModel {
    private ResolveInfo resolveInfo;

    private String kind;
    public TSLiveData<String> appPackageName = new TSLiveData<>();
    public TSLiveData<String> appName = new TSLiveData<>();
    public TSLiveData<Drawable> icon = new TSLiveData<>();

    public AppInfoViewModel() {

    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setResolveInfo(PackageManager packageManager, ResolveInfo resolveInfo) {
        this.resolveInfo = resolveInfo;
        appName.setValue(resolveInfo.activityInfo.loadLabel(packageManager).toString());
        icon.setValue(resolveInfo.activityInfo.loadIcon(packageManager));
    }

    public void saveApp() {
        if (baseCallback != null) {
            baseCallback.save(kind, resolveInfo);
            close();
        }
    }
}
