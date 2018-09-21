package com.yts.smartsetting.view.ui.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.databinding.AppInfoBinding;
import com.yts.smartsetting.databinding.EmptyAppBinding;
import com.yts.smartsetting.view.viewmodel.AppInfoViewModel;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter {
    private int EMPTY_TYPE = 0;
    private int APP_TYPE = 1;

    private List<Object> mItemList;
    private String mKind;

    public AppInfoAdapter(List<Object> itemList, String kind) {
        mItemList = itemList;
        mKind = kind;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItemList.get(position);
        if (item instanceof ResolveInfo) {
            return APP_TYPE;
        }
        return EMPTY_TYPE;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == APP_TYPE) {
            AppInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_app_info, viewGroup, false);
            return new AppInfoViewHolder(binding);
        }
        EmptyAppBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_empty_app, viewGroup, false);
        return new EmptyAppViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == APP_TYPE) {
            AppInfoViewHolder holder = (AppInfoViewHolder) viewHolder;
            Context context = holder.itemView.getContext();
            ResolveInfo resolveInfo = (ResolveInfo) mItemList.get(position);
            AppInfoViewModel model = new AppInfoViewModel();
            model.setKind(mKind);
            model.setBaseCallback((BaseCallback) context);
            model.setResolveInfo(context.getPackageManager(), resolveInfo);
            holder.setViewModel(model);
        }

    }

    @Override
    public int getItemCount() {
        if (mItemList != null) {
            return mItemList.size();
        }
        return 0;
    }

    private class EmptyAppViewHolder extends RecyclerView.ViewHolder {
        private EmptyAppBinding binding;

        public EmptyAppViewHolder(@NonNull EmptyAppBinding binding) {
            super(binding.getRoot());
        }
    }

    private class AppInfoViewHolder extends RecyclerView.ViewHolder {
        private AppInfoBinding binding;

        public AppInfoViewHolder(@NonNull AppInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(AppInfoViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}
