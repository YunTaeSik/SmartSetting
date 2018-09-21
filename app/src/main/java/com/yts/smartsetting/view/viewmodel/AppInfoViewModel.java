package com.yts.smartsetting.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import com.yts.smartsetting.utill.SharedPrefsUtils;

public class AppInfoViewModel extends BaseViewModel {
    private ResolveInfo resolveInfo;

    private String kind;
    public MutableLiveData<String> appPackageName = new MutableLiveData<>();
    public MutableLiveData<String> appName = new MutableLiveData<>();
    public MutableLiveData<Drawable> icon = new MutableLiveData<>();

    public AppInfoViewModel() {

    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setResolveInfo(PackageManager packageManager, ResolveInfo resolveInfo) {
        this.resolveInfo = resolveInfo;
        appName.setValue(resolveInfo.activityInfo.loadLabel(packageManager).toString());
        icon.setValue(resolveInfo.activityInfo.loadIcon(packageManager));
        // resolveInfo.activityInfo.packageName;
    }

    public void saveApp() {
        if (baseCallback != null) {
            baseCallback.save(kind, resolveInfo);
            close();
        }
        //    SharedPrefsUtils.setStringPreference()
    }
}
