package com.yts.smartsetting.utill;

import android.content.Context;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.places.ui.PlacePicker;



public class ShowIntent {
    public static void location(Context context, int requestCode) {
        if (context instanceof AppCompatActivity) {
            try {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                ((AppCompatActivity) context).startActivityForResult(builder.build((AppCompatActivity) context), requestCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
