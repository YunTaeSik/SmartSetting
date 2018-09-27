package com.yts.smartsetting.view.ui.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.yts.smartsetting.BaseActivity;
import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.callback.SettingCallback;
import com.yts.smartsetting.databinding.AppSelectSettingBinding;
import com.yts.smartsetting.receiver.ServiceReceiver;
import com.yts.smartsetting.service.SmartSettingJobService;
import com.yts.smartsetting.utill.JobSchedulerStart;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SharedPrefsUtils;
import com.yts.smartsetting.view.viewmodel.SettingViewModel;

public class AppSelectSettingDialog extends DialogFragment implements SettingCallback {
    private AppSelectSettingBinding binding;
    private SettingViewModel model;

    public static AppSelectSettingDialog newInstance(String kind) {
        Bundle args = new Bundle();
        args.putString(Keys.KIND, kind);
        AppSelectSettingDialog fragment = new AppSelectSettingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_app_select_setting, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ViewModelProviders.of(this).get(SettingViewModel.class);
        model.setBaseCallback((BaseCallback) getActivity());
        model.setSettingCallback(this);
        model.setKind(getActivity(), getArguments().getString(Keys.KIND));
        binding.setModel(model);
        binding.setLifecycleOwner(this);
        if (getActivity() != null) {
            getActivity().registerReceiver(model.getBroadcastReceiver(), model.getIntentFilter());
        }
    }

    @Override
    public void onDestroyView() {
        if (getActivity() != null && model != null) {
            getActivity().unregisterReceiver(model.getBroadcastReceiver());
        }
        super.onDestroyView();
    }

    @Override
    public void saveEnable(String kind, boolean enable) {
        if (kind.equals(Keys.EAR)) {
            //  JobSchedulerStart.start(getActivity());
            SharedPrefsUtils.setBooleanPreference(getActivity(), Keys.EAR_ENABLE, enable);
        } else if (kind.equals(Keys.BLUE_TOOTH)) {
            //JobSchedulerStart.start(getActivity());
            SharedPrefsUtils.setBooleanPreference(getActivity(), Keys.BLUE_TOOTH_ENABLE, enable);
        }
    }

    @Override
    public void startFragment(DialogFragment fragment) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addFragmentDialog(fragment, android.R.transition.slide_right);
        }
    }
}
