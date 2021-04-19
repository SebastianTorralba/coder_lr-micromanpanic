package ar.com.twoboot.panico.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import ar.com.twoboot.panico.servicios.BootServicio;

public class RServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
			Intent i = new Intent(context, BootServicio.class);
			context.startService(i);
	}

}
