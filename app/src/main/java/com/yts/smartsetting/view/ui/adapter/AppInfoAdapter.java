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
import android.widget.Filter;
import android.widget.Filterable;

import com.yts.smartsetting.R;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.databinding.AppInfoBinding;
import com.yts.smartsetting.databinding.EmptyAppBinding;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.view.viewmodel.AppInfoViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter implements Filterable {
    private int EMPTY_TYPE = 0;
    private int APP_TYPE = 1;

    private Context mContext;
    private List<Object> mItemList;
    private List<Object> mItemListFilter;
    private String mKind;

    public AppInfoAdapter(Context context, List<Object> itemList, String kind) {
        mContext = context;
        mItemList = itemList;
        mItemListFilter = itemList;
        mKind = kind;
    }

    @Override
    public long getItemId(int position) {
        Object item = mItemList.get(position);
        if (item instanceof String) {
            return item.toString().hashCode();
        } else if (item instanceof ResolveInfo) {
            ResolveInfo resolveInfo = (ResolveInfo) item;
            return resolveInfo.activityInfo.packageName.hashCode();
        } else {
            return position;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItemList.get(position);
        if (item instanceof String) {
            return EMPTY_TYPE;
        } else if (item instanceof ResolveInfo) {
            return APP_TYPE;
        } else {
            return EMPTY_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == APP_TYPE) {
            AppInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_app_info, viewGroup, false);
            return new AppInfoViewHolder(binding);
        } else {
            EmptyAppBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_empty_app, viewGroup, false);
            return new EmptyAppViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == APP_TYPE) {
            AppInfoViewHolder holder = (AppInfoViewHolder) viewHolder;
            Context context = holder.itemView.getContext();
            if (mItemListFilter.get(position) instanceof ResolveInfo) {
                ResolveInfo resolveInfo = (ResolveInfo) mItemListFilter.get(position);
                AppInfoViewModel model = new AppInfoViewModel();
                model.setKind(mKind);
                model.setBaseCallback((BaseCallback) context);
                model.setResolveInfo(context.getPackageManager(), resolveInfo);
                holder.setViewModel(model);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (mItemListFilter != null) {
            return mItemListFilter.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Object> filterList = new ArrayList<>();

                String search = charSequence.toString();
                if (search.isEmpty()) {
                    filterList = mItemList;
                } else {
                    for (Object o : mItemList) {
                        if (o instanceof ResolveInfo) {
                            ResolveInfo resolveInfo = (ResolveInfo) o;
                            String label = resolveInfo.activityInfo.loadLabel(mContext.getPackageManager()).toString();
                            if (label.toLowerCase().contains(search.toLowerCase())) {
                                filterList.add(resolveInfo);
                            }
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mItemListFilter = (List<Object>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
