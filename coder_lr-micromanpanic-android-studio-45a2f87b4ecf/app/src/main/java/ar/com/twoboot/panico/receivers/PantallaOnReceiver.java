package ar.com.twoboot.panico.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import ar.com.twoboot.microman.util.CheckSeguridad;
import ar.com.twoboot.panico.LockActivity;

public class PantallaOnReceiver extends BroadcastReceiver {

	public static boolean LLAMADA = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(Intent.ACTION_SCREEN_ON) && CheckSeguridad.sihay(context) && !LLAMADA) {
			SharedPreferences prefs = context.getSharedPreferences("configuracion", Context.MODE_PRIVATE);
			Boolean mostrarenbloqueo = prefs.getBoolean("mbloqueo", true);
			if (mostrarenbloqueo) {
				Intent closeDialogs = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
				context.sendBroadcast(closeDialogs);
				Intent lockscreen = new Intent(context.getApplicationContext(), LockActivity.class);
				lockscreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
				context.startActivity(lockscreen);
			}
		}
	}

}