package com.yts.tsbible.utills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.yalantis.ucrop.UCrop;
import com.yts.tsbible.BuildConfig;
import com.yts.tsbible.R;

import java.io.File;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

public class ShowIntent {
    public static void imageSelect(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            ((Activity) context).startActivityForResult(intent, RequestCode.IMAGE_SELECT);
        }
    }

    public static void imageCroup(Activity context, Intent data) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(context, R.color.white));
        options.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.colorAccent));
        options.setActiveWidgetColor(ContextCompat.getColor(context, R.color.colorAccent));
        options.setToolbarTitle(context.getString(R.string.edit_image));

        String fileName = String.valueOf(System.currentTimeMillis()) + ".png";
        UCrop.of(data.getData(), Uri.fromFile(new File(context.getCacheDir(), fileName)))
                .withOptions(options)
                .start(context, RequestCode.IMAGE_CROP);
    }

    public static void shareImage(Context context, String filePath) {
        try {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, new File(filePath)));
            Intent chooser = Intent.createChooser(intent, context.getString(R.string.share));
            context.startActivity(chooser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void invite(Context context) {
        Intent intent = new AppInviteInvitation.IntentBuilder(context.getString(R.string.shared_title))
                .setMessage(context.getString(R.string.shared_message))
                .setDeepLink(Uri.parse("https://sn3d4.app.goo.gl/TSBIBLE"))
                .setCallToActionText(context.getString(R.string.shared_call_to_action_text))
                .build();
        ((Activity) context).startActivityForResult(intent, RequestCode.invite);
    }

    public static void reviews(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.yts.tsbible"));
            context.startActivity(intent);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
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
