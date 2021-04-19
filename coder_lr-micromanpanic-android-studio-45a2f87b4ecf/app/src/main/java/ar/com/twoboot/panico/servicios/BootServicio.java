package ar.com.twoboot.panico.servicios;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import ar.com.twoboot.panico.receivers.PantallaOffReceiver;
import ar.com.twoboot.panico.receivers.PantallaOnReceiver;

public class BootServicio extends Service {
	private BroadcastReceiver screenOn = new PantallaOnReceiver();
	private BroadcastReceiver screenOff = new PantallaOffReceiver();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IntentFilter screenStateFilter = new IntentFilter();
		screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
		this.registerReceiver(screenOn, screenStateFilter);
		IntentFilter screenStateFilterOff = new IntentFilter();
		screenStateFilterOff.addAction(Intent.ACTION_SCREEN_OFF);
		this.registerReceiver(screenOff, screenStateFilter);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sendBroadcast(new Intent("NOMEKILLMP"));
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}