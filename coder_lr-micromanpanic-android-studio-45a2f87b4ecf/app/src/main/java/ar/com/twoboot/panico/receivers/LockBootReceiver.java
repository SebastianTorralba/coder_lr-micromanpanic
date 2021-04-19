package ar.com.twoboot.panico.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import ar.com.twoboot.microman.util.ServicioTool;
import ar.com.twoboot.panico.servicios.BootServicio;

public class LockBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (!ServicioTool.estaVivo(context, BootServicio.class)) {
			Intent i = new Intent(context, BootServicio.class);
			context.startService(i);
		}
	}
}
