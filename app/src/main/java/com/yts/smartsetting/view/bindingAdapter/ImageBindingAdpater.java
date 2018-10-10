package com.yts.smartsetting.view.bindingAdapter;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.yts.smartsetting.GlideApp;

public class ImageBindingAdpater {
    @BindingAdapter({"setImage"})
    public static void setImage(final ImageView view, Drawable drawable) {
        final Context context = view.getContext();
        if (drawable != null) {
        //    view.setImageDrawable(drawable);

           GlideApp.with(context).load(drawable).into(view);
        }
    }

}
