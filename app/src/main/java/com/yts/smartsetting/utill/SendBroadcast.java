package com.yts.smartsetting.utill;

import android.content.Context;
import android.content.Intent;

public class SendBroadcast {

    public static void earEdit(Context context) {
        Intent send = new Intent(Keys.EDIT_EAR);
        context.sendBroadcast(send);
    }
}