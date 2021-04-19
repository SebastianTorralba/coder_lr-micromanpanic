package ar.com.twoboot.microman.util;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class CheckSeguridad {

	public static boolean sihay(Context context) {
		return isPatternSet(context) || isPassOrPinSet(context);
	}

	@SuppressLint("NewApi")
	private static boolean isPatternSet(Context context) {
		boolean esta = false;
		if (Build.VERSION.SDK_INT >= 23) {
			KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
			esta = keyguardManager.isDeviceSecure();
		} else {
			ContentResolver cr = context.getContentResolver();
			try {
				Settings.Secure.getInt(cr, Settings.Secure.LOCK_PATTERN_ENABLED);
				esta = true;
			} catch (Settings.SettingNotFoundException e) {
				esta = false;
			}
		}
		return esta;
	}

	private static boolean isPassOrPinSet(Context context) {
		KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		return keyguardManager.isKeyguardSecure();
	}

}
