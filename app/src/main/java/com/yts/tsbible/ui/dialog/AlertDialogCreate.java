package com.yts.tsbible.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.yts.tsbible.R;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogCreate {
    private AlertDialog.Builder alertDialog;
    private Context mContext;

    public AlertDialogCreate(Context context) {
        mContext = context;
        alertDialog = new AlertDialog.Builder(context, R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    public void imageShare(DialogInterface.OnClickListener clickListener) {
        try {
            alertDialog.setCancelable(false);
            alertDialog.setTitle(mContext.getString(R.string.image));
            alertDialog.setMessage(mContext.getString(R.string.message_image_share));
            alertDialog.setPositiveButton(R.string.share, clickListener);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveImageMake(DialogInterface.OnClickListener clickListener) {
        try {
            alertDialog.setCancelable(false);
            alertDialog.setTitle(mContext.getString(R.string.image));
            alertDialog.setMessage(mContext.getString(R.string.message_move_image_make));
            alertDialog.setPositiveButton(R.string.move, clickListener);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveBibleList(DialogInterface.OnClickListener clickListener) {
        try {
            alertDialog.setCancelable(false);
            alertDialog.setTitle(mContext.getString(R.string.view));
            alertDialog.setMessage(mContext.getString(R.string.message_move_bible_list));
            alertDialog.setPositiveButton(R.string.move, clickListener);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOffering(DialogInterface.OnClickListener clickListener) {
        try {
            alertDialog.setCancelable(false);
            alertDialog.setTitle(mContext.getString(R.string.delete));
            alertDialog.setMessage(mContext.getString(R.string.message_delete_offering));
            alertDialog.setPositiveButton(R.string.delete, clickListener);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
