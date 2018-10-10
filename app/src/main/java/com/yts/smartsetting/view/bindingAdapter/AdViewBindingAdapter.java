package com.yts.smartsetting.view.bindingAdapter;

import androidx.databinding.BindingAdapter;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdViewBindingAdapter {
    @BindingAdapter({"setBanner"})
    public static void setBanner(AdView view, AdRequest adRequest) {
        if (adRequest != null) {
            view.loadAd(adRequest);
        }

    }
}
