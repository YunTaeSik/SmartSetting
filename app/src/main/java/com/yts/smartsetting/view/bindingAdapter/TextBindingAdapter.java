package com.yts.smartsetting.view.bindingAdapter;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.yts.smartsetting.R;

public class TextBindingAdapter {
    @BindingAdapter({"setLaunchAppText"})
    public static void setLaunchAppText(TextView view, String text) {
        if (text != null && text.length() > 0) {
            view.setText(view.getContext().getString(R.string.launch_the_app, text));
        }

    }
}
