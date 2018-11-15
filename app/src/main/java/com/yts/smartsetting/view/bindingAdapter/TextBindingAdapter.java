package com.yts.smartsetting.view.bindingAdapter;

import android.content.Context;

import androidx.databinding.BindingAdapter;

import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
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

    @BindingAdapter({"setSoundMode"})
    public static void setSoundMode(TextView view, int mode) {
        Context context = view.getContext();
        String text = context.getString(R.string.sound_mode)+" : ";
        if (mode == 0) {
            text += context.getString(R.string.no_change);
        } else if (mode == 1) {
            text += context.getString(R.string.sound);
        } else if (mode == 2) {
            text += context.getString(R.string.vibration);
        } else if (mode == 3) {
            text += context.getString(R.string.silent);
        }
        view.setText(text);
    }

    @BindingAdapter({"setSoundMode"})
    public static void setSoundMode(MaterialButton view, int mode) {
        Context context = view.getContext();
        String text = "";
        if (mode == 0) {
            text = context.getString(R.string.no_change);
        } else if (mode == 1) {
            text = context.getString(R.string.sound);
        } else if (mode == 2) {
            text = context.getString(R.string.vibration);
        } else if (mode == 3) {
            text = context.getString(R.string.silent);
        }
        view.setText(text);
    }
}
