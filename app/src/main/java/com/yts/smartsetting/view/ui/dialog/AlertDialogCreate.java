package com.yts.smartsetting.view.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yts.smartsetting.R;


public class AlertDialogCreate {
    private static volatile AlertDialogCreate singletonInstance = null;
    private AlertDialog.Builder alertDialog;
    private Context mContext;

    public static AlertDialogCreate getInstance(Context context) {
        if (singletonInstance == null) {
            synchronized (AlertDialogCreate.class) {
                if (singletonInstance == null) {
                    singletonInstance = new AlertDialogCreate(context);
                }
            }
        }
        return singletonInstance;
    }

    private AlertDialogCreate(Context context) {
        mContext = context;
        alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    public void deleteLocation(DialogInterface.OnClickListener clickListener) {
        try {
            alertDialog.setCancelable(true);
            alertDialog.setTitle(mContext.getString(R.string.location));
            alertDialog.setMessage(mContext.getString(R.string.delete_location));
            alertDialog.setPositiveButton(mContext.getString(R.string.ok), clickListener);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
