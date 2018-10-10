package com.yts.smartsetting.view.bindingAdapter;

import androidx.databinding.BindingAdapter;
import androidx.appcompat.widget.SwitchCompat;

import android.widget.ToggleButton;

import com.yts.smartsetting.utill.NullFilter;

public class CheckBindingAdapter {
    @BindingAdapter({"setCheck"})
    public static void setCheck(SwitchCompat view, Boolean check) {
        view.setChecked(NullFilter.check(check));
    }

    @BindingAdapter({"setCheck"})
    public static void setCheck(ToggleButton view, Boolean check) {
        view.setChecked(NullFilter.check(check));
    }
}
