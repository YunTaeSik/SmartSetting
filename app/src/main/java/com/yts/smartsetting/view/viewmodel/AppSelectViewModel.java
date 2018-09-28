package com.yts.smartsetting.view.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.yts.smartsetting.utill.Keys;

import java.util.ArrayList;
import java.util.List;

public class AppSelectViewModel extends BaseViewModel {
    private PackageManager packageManager;

    public MutableLiveData<String> mKind = new MutableLiveData<>();
    public MutableLiveData<List<Object>> mItemList = new MutableLiveData<>();

    public void setPackageManager(PackageManager packageManager) {
        this.packageManager = packageManager;
    }

    public void setKind(String kind) {
        mKind.setValue(kind);
    }

    public void getResolveInfoList(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<Object> itemList = new ArrayList<>();

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(mainIntent, 0);
        if (resolveInfos != null && resolveInfos.size() > 0) {
            itemList.addAll(resolveInfos);
        } else {
            itemList.add(Keys.EMPTY);
        }

        mItemList.setValue(itemList);
    }


}
