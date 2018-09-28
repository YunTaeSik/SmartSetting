package com.yts.smartsetting.view.ui.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
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
import com.yts.smartsetting.databinding.AppSelectBinding;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.view.viewmodel.AppSelectViewModel;

public class AppSelectDialog extends DialogFragment {
    private AppSelectBinding binding;
    private AppSelectViewModel model;

    public static AppSelectDialog newInstance(String key) {
        Bundle args = new Bundle();
        args.putString(Keys.KIND, key);
        AppSelectDialog fragment = new AppSelectDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_app_select, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ViewModelProviders.of(this).get(AppSelectViewModel.class);
        model.setBaseCallback((BaseCallback) getActivity());
        model.setPackageManager(getContext().getPackageManager());
        model.setKind(getArguments().getString(Keys.KIND));
        model.getResolveInfoList(getActivity());
        binding.setModel(model);
        binding.setLifecycleOwner(this);
     //   binding.li.setFilterText();
    }
}

