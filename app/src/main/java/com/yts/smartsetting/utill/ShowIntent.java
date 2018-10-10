package com.yts.smartsetting.utill;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.yts.smartsetting.R;


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

    public static void invite(Context context) {
        Intent intent = new AppInviteInvitation.IntentBuilder(context.getString(R.string.shared_title))
                .setMessage(context.getString(R.string.shared_message))
                .setDeepLink(Uri.parse(context.getString(R.string.invitation_deep_link)))
                .setCallToActionText(context.getString(R.string.shared_call_to_action_text))
                .build();
        ((Activity) context).startActivityForResult(intent, RequestCode.invite);
    }


    public static void emailSend(Context context) {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        String[] address = {context.getString(R.string.contact_email)};
        email.putExtra(Intent.EXTRA_EMAIL, address);
        email.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.contact_us));
        try {
            context.startActivity(email);
        } catch (Exception e) {
            e.printStackTrace();
            ToastMake.make(context, context.getString(R.string.error_email));
        }
    }

}
