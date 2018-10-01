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

import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.databinding.LocationBinding;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.view.viewmodel.LocationViewModel;

public class LocationDialog extends DialogFragment {
    private Location mLocation;
    private LocationBinding binding;
    private LocationViewModel model;

    public static LocationDialog newInstance(Location location) {
        Bundle args = new Bundle();
        args.putParcelable(Keys.LOCATION, location);
        LocationDialog fragment = new LocationDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_location, container, false);
        if (getArguments() != null) {
            mLocation = getArguments().getParcelable(Keys.LOCATION);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ViewModelProviders.of(this).get(LocationViewModel.class);
        model.setBaseCallback((BaseCallback) getActivity());
        model.setLocation(mLocation);
        binding.setModel(model);
        binding.setLifecycleOwner(this);
    }
}
