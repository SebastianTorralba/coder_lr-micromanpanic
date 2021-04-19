package ar.com.twoboot.microman.util;

import java.sql.Timestamp;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GpsLog {
	public static final int LATITUD = 1;
	public static final int LONGITUD = 1;

	private mylocationlistener ll;
	private LocationManager lm;
	public GpsStatus gpsStatus;
	private Context context;
	private Double latitud;
	private Double longitud;
	private Activity activity;
	public Double getLatitud() {
		return latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public GpsLog(Context context,Activity activity) {
		this.context = context;
		lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		ll = new mylocationlistener();
		this.activity=activity;
		// lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, ll);
	}

	private class mylocationlistener implements LocationListener,
			GpsStatus.Listener {

		public void onLocationChanged(Location location) {
			Date today = new Date();
			Timestamp currentTimeStamp = new Timestamp(today.getTime());
			if (location != null) {
				Log.d("LOCATION CHANGED", location.getLatitude() + "");
				Log.d("LOCATION CHANGED", location.getLongitude() + "");
				String str = "\n CurrentLocation: " + "\n Latitude: "
						+ location.getLatitude() + "\n Longitude: "
						+ location.getLongitude() + "\n Accuracy: "
						+ location.getAccuracy() + "\n CurrentTimeStamp "
						+ currentTimeStamp + "\n Altitude: "
						+ location.getAltitude() + "\n Bearing"
						+ location.getBearing() + "\n Speed"
						+ location.getSpeed() + "\n ";
				// Toast.makeText(context, str, Toast.LENGTH_LONG).show();
				latitud = location.getLatitude();
				longitud = location.getLongitude();
			}
		}

		public void onProviderDisabled(String provider) {
			Toast.makeText(context, "Error Servicio GPS desconectado",
					Toast.LENGTH_LONG).show();
			Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			
			activity.startActivityForResult(callGPSSettingIntent, 0);
		}

		public void onProviderEnabled(String provider) {
			Toast.makeText(context, "Servicio GPS establecido", Toast.LENGTH_LONG)
					.show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			//Toast.makeText(context, "Navegando...", Toast.LENGTH_LONG)	.show();
		}

		@Override
		public void onGpsStatusChanged(int event) {
			Log.d("GpsListener", "Status changed to " + event);
			switch (event) {
			case GpsStatus.GPS_EVENT_STARTED:

				break;
			case GpsStatus.GPS_EVENT_STOPPED:

				break;
			// GPS_EVENT_SATELLITE_STATUS will be called frequently
			// all satellites in use will invoke it, don't rely on it alone
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				// this is *very* chatty, only very advanced use cases should
				// need this (avoid it if you don't need it)
				gpsStatus = lm.getGpsStatus(gpsStatus);
				StringBuilder sb = new StringBuilder();
				for (GpsSatellite sat : gpsStatus.getSatellites()) {
					sb.append("Satellite N:" + sat.getPrn() + " AZ:"
							+ sat.getAzimuth() + " EL:" + sat.getElevation()
							+ " S/N:" + sat.getSnr() + "\n");
				}
				Log.d("SARASA", sb.toString());
				Toast.makeText(context, sb, Toast.LENGTH_LONG);

				break;
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				// gpsEvents.setText("GPS_EVENT_FIRST_FIX");
				break;
			}
		}

	}
}
