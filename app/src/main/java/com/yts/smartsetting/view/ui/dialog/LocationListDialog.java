package com.yts.smartsetting.view.ui.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yts.smartsetting.BaseActivity;
import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.databinding.LocationListBinding;
import com.yts.smartsetting.view.viewmodel.LocationListViewModel;

public class LocationListDialog extends DialogFragment  {
    private LocationListBinding binding;
    private LocationListViewModel model;

    public static LocationListDialog newInstance() {
        Bundle args = new Bundle();
        LocationListDialog fragment = new LocationListDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_location_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ViewModelProviders.of(this).get(LocationListViewModel.class);
        model.setBaseCallback((BaseCallback) getActivity());
        binding.setModel(model);
        binding.setLifecycleOwner(this);
    }

}
