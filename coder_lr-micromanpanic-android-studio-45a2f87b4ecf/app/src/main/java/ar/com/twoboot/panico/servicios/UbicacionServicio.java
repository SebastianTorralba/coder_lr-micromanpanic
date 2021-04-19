package ar.com.twoboot.panico.servicios;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

public class UbicacionServicio extends Service implements LocationListener {
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
	private IBinder uBinder = new UbicacionBinder();
	private LocationManager locationManager;
	private Location location;
	private boolean isGPS = false;
	private boolean isNetwork = false;
	private boolean canGetLocation = true;

	@Override
	public IBinder onBind(Intent intent) {
		return uBinder;
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public Location getUbicacion() {
		return location;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (!isGPS && !isNetwork) {
			mostrarPrenderGPS();
			getUltimaLoc();
		} else {
			getLoc();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public void mostrarPrenderGPS() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("GPS");
		alertDialog.setMessage("Prender GPS?");
		alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		});
		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alertDialog.show();
	}

	private void getUltimaLoc() {
		try {
			Criteria criteria = new Criteria();
			String provider = locationManager.getBestProvider(criteria, false);
			location = locationManager.getLastKnownLocation(provider);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	private void getLoc() {
		try {
			if (canGetLocation) {
				if (isGPS) {
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					}
				} else if (isNetwork) {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
				} else {
					location.setLatitude(0);
					location.setLongitude(0);
				}
			} else {
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
		isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		getLoc();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	public class UbicacionBinder extends Binder {
		public UbicacionServicio getService() {
			return UbicacionServicio.this;
		}
	}

}
