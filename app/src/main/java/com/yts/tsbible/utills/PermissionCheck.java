package com.yts.tsbible.utills;

import android.Manifest;
import android.content.Context;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

public class PermissionCheck {

    public static void writeCheck(Context context, PermissionListener permissionListener) {
        TedPermission.with(context)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }
}
