package com.yts.smartsetting.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yts.smartsetting.BaseActivity;
import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.MainCallback;
import com.yts.smartsetting.databinding.MainBinding;
import com.yts.smartsetting.ui.dialog.EarPhoneSettingDialog;
import com.yts.smartsetting.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity implements MainCallback {
    private MainBinding binding;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.setMainCallback(this, this);
        binding.setModel(model);

    }

    @Override
    public void startEarPhone() {
        EarPhoneSettingDialog dialog = EarPhoneSettingDialog.newInstance();
        startFragmentDialog(dialog, android.R.transition.slide_right);
    }

    @Override
    public void startBlueTooth() {

    }
}
