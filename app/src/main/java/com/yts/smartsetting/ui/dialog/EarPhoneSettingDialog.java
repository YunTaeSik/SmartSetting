package com.yts.smartsetting.ui.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.databinding.EarPhoneSettingBinding;
import com.yts.smartsetting.viewmodel.SettingViewModel;

public class EarPhoneSettingDialog extends DialogFragment {
    private EarPhoneSettingBinding binding;
    private SettingViewModel model;

    public static EarPhoneSettingDialog newInstance() {
        Bundle args = new Bundle();
        EarPhoneSettingDialog fragment = new EarPhoneSettingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_earphone_setting, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ViewModelProviders.of(this).get(SettingViewModel.class);
        model.setBaseCallback((BaseCallback) getActivity());
        binding.setModel(model);
    }
}
