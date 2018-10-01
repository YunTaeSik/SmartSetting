package com.yts.smartsetting.view.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.databinding.AppInfoBinding;
import com.yts.smartsetting.databinding.EmptyAppBinding;
import com.yts.smartsetting.databinding.LocationItemBinding;
import com.yts.smartsetting.view.viewmodel.AppInfoViewModel;
import com.yts.smartsetting.view.viewmodel.LocationViewModel;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter {
    private int LOCATION_TYPE = 1;

    private Context mContext;
    private List<Object> mItemList;

    public LocationAdapter(Context context, List<Object> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public long getItemId(int position) {
        Object item = mItemList.get(position);
        if (item instanceof Location) {
            Location location = (Location) item;
            return location.getDate().hashCode();
        }
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItemList.get(position);
        if (item instanceof Location) {
            return LOCATION_TYPE;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == LOCATION_TYPE) {
            LocationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_location, viewGroup, false);
            return new LocationViewHolder(binding);
        } else {
            LocationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_location, viewGroup, false);
            return new LocationViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == LOCATION_TYPE) {
            Object item = mItemList.get(position);
            if (item instanceof Location) {
                LocationViewHolder holder = (LocationViewHolder) viewHolder;
                Context context = holder.itemView.getContext();

                Location location = (Location) item;
                LocationViewModel model = new LocationViewModel();
                model.setLocation(location);
                model.setBaseCallback((BaseCallback) context);
                holder.setViewModel(model);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (mItemList != null) {
            return mItemList.size();
        }
        return 0;
    }

    private class LocationViewHolder extends RecyclerView.ViewHolder {
        private LocationItemBinding binding;

        public LocationViewHolder(@NonNull LocationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(LocationViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}
