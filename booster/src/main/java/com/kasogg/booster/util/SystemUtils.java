package com.kasogg.booster.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Author: KasoGG
 * Date:   2015-09-08 16:19
 */
public class SystemUtils {

    public static void dialPhoneNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
