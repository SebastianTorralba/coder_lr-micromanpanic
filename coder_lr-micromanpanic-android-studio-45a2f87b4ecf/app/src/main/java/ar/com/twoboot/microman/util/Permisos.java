package ar.com.twoboot.microman.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class Permisos {
    public static final int REQUEST = 0121;
    public static final String[] PERMISSIONS = {android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_CONFIGURATION,
            Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    public static void solicitar(Context contexto) {
        ActivityCompat.requestPermissions((Activity) contexto, PERMISSIONS, REQUEST);
    }

    public static boolean check(Context contexto) {
        boolean hay = true;
        if (Build.VERSION.SDK_INT >= 23) {
            hay = haypermisos(contexto, PERMISSIONS);
        }
        return hay;
    }

    private static boolean haypermisos(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
