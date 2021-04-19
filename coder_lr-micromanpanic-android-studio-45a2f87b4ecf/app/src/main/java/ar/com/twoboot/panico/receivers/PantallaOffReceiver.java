package ar.com.twoboot.panico.receivers;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.WindowManager;
import ar.com.twoboot.microman.util.Util;

public class PantallaOffReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final Context contexto = context;
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			SharedPreferences prefs = context.getSharedPreferences("configuracion", Context.MODE_PRIVATE);
			Boolean gps = prefs.getBoolean("mgpsact", true);
			boolean estadogps = Util.estadoGPS(context);
			if (gps && !estadogps) {
				PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
				PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNjfdhotDimScreen");
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Desea mantener su GPS activado?");
				builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						contexto.startActivity(intent);
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				alert.show();
			}
		}
	}

}