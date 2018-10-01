package com.yts.smartsetting.view.bindingAdapter;

import android.content.Context;
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

    @BindingAdapter({"setTurnBluetoothText"})
    public static void setTurnBluetoothText(TextView view, boolean enable) {
        Context context = view.getContext();
        String turn = enable ? context.getString(R.string.on) : context.getString(R.string.off);
        view.setText(view.getContext().getString(R.string.bluetooth_turn, turn));
    }

    @BindingAdapter({"setTurnWifiText"})
    public static void setTurnWifiText(TextView view, boolean enable) {
        Context context = view.getContext();
        String turn = enable ? context.getString(R.string.on) : context.getString(R.string.off);
        view.setText(view.getContext().getString(R.string.wifi_turn, turn));
    }
}
