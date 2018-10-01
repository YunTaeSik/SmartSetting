package com.yts.smartsetting.view.bindingAdapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
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
