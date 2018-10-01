package com.yts.smartsetting.view.bindingAdapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.yts.smartsetting.utill.NullFilter;

public class CheckBindingAdapter {
    @BindingAdapter({"setCheck"})
    public static void setCheck(SwitchCompat view, Boolean check) {
        view.setChecked(NullFilter.check(check));
    }
}
