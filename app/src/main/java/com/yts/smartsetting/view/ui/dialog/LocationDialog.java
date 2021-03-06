package com.yts.smartsetting.view.ui.dialog;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
        if (getActivity() != null) {
            getActivity().registerReceiver(model.getBroadcastReceiver(), model.getIntentFilter());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null && binding != null && binding.getModel() != null) {
            getActivity().unregisterReceiver(binding.getModel().getBroadcastReceiver());
        }
    }
}
