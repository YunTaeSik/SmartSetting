package com.yts.smartsetting.view.bindingAdapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yts.smartsetting.view.ui.adapter.AppInfoAdapter;

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
}
