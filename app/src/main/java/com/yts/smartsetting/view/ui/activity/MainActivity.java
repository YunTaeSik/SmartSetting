package com.yts.smartsetting.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yts.smartsetting.BaseActivity;
import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.MainCallback;
import com.yts.smartsetting.databinding.MainBinding;
import com.yts.smartsetting.utill.ServiceUtil;
import com.yts.smartsetting.view.ui.dialog.AppSelectSettingDialog;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.view.ui.dialog.LocationListDialog;
import com.yts.smartsetting.view.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity implements MainCallback {
    private MainBinding binding;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceUtil.start(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.setMainCallback(this, this);
        model.initData(this);
        binding.setModel(model);
        binding.setLifecycleOwner(this); //필수

        registerReceiver(model.getBroadcastReceiver(), model.getIntentFilter());

    }

    @Override
    public void start(String kind) {
        if (kind.equals(Keys.EAR) || kind.equals(Keys.BLUE_TOOTH)) {
            AppSelectSettingDialog dialog = AppSelectSettingDialog.newInstance(kind);
            startFragmentDialog(dialog, android.R.transition.slide_right);
        }
    }

    @Override
    protected void onDestroy() {
        if (model != null) {
            unregisterReceiver(model.getBroadcastReceiver());
        }
        super.onDestroy();
    }
}
