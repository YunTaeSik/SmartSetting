package com.yts.smartsetting.view.bindingAdapter;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yts.smartsetting.view.ui.adapter.AppInfoAdapter;
import com.yts.smartsetting.view.ui.adapter.LocationAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterBindingAdapter {
    @BindingAdapter({"setAppSelectAdapter", "setKind"})
    public static void setAppSelectAdapter(RecyclerView view, List<Object> itemList, String kind) {
        Context context = view.getContext();
        RecyclerView.Adapter adapter = view.getAdapter();
        if (adapter != null && adapter instanceof AppInfoAdapter) {
            adapter.notifyDataSetChanged();
            //  ((HomeListAdapter) adapter).setItemList(itemList);
        } else {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            AppInfoAdapter appInfoAdapter = new AppInfoAdapter(context, itemList, kind);
            appInfoAdapter.setHasStableIds(true);
            view.setLayoutManager(manager);
            view.setAdapter(appInfoAdapter);
        }
    }

    @BindingAdapter({"setLocationAdapter"})
    public static void setLocationAdapter(RecyclerView view, ArrayList<Object> itemList) {
        Context context = view.getContext();
        RecyclerView.Adapter adapter = view.getAdapter();
        if (adapter != null && adapter instanceof LocationAdapter) {
            adapter.notifyDataSetChanged();
        } else {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            LocationAdapter appInfoAdapter = new LocationAdapter(context, itemList);
            appInfoAdapter.setHasStableIds(true);
            view.setLayoutManager(manager);
            view.setAdapter(appInfoAdapter);
        }
    }
}
