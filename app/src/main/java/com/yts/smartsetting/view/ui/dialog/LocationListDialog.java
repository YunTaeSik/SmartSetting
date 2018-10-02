package com.yts.smartsetting.view.ui.dialog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yts.smartsetting.BaseActivity;
import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.databinding.LocationListBinding;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.view.ui.adapter.LocationAdapter;
import com.yts.smartsetting.view.viewmodel.LocationListViewModel;

import java.util.ArrayList;

public class LocationListDialog extends DialogFragment {
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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ViewModelProviders.of(this).get(LocationListViewModel.class);
        model.setBaseCallback((BaseCallback) getActivity());
        model.setInterstitialAd(getContext());
        if (getActivity() instanceof BaseActivity) {
            model.initData(getActivity(), ((BaseActivity) getActivity()).compositeDisposable);
        }
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        observe();

        if (getActivity() != null) {
            getActivity().registerReceiver(broadcastReceiver, getIntentFilter());
        }
    }

    private void observe() {
        model.mLocationList.observe(this, new Observer<ArrayList<Object>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Object> objects) {
                if (binding.listItem.getAdapter() != null && binding.listItem.getAdapter() instanceof LocationAdapter) {
                    ((LocationAdapter) binding.listItem.getAdapter()).setItemList(objects);
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        if (getActivity() != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Keys.EDIT_LOCATION)) {
                    if (model != null) {
                        if (getActivity() instanceof BaseActivity) {
                            model.initData(getActivity(), ((BaseActivity) getActivity()).compositeDisposable);
                        }
                    }
                }
            }
        }
    };

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.EDIT_LOCATION);
        return intentFilter;
    }


}
