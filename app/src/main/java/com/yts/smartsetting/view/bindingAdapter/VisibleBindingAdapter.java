package com.yts.smartsetting.view.bindingAdapter;

import androidx.databinding.BindingAdapter;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;

import com.yts.smartsetting.utill.NullFilter;

public class VisibleBindingAdapter {

    @BindingAdapter({"setVisible"})
    public static void setVisible(AppCompatTextView view, Boolean visible) {
        int visibility = NullFilter.check(visible) ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }
}
