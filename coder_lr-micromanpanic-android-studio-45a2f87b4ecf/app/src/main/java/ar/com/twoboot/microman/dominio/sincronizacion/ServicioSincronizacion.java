package ar.com.twoboot.microman.dominio.sincronizacion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.panico.LoginActivity;

public class ServicioSincronizacion extends Service implements ISync {
	protected ISync sincro;
	private Sincronizador sincronizador;
	private OnNegocio oNegocio;

	public ISync getSincro() {
		return sincro;
	}

	Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ServicioSincronizacion(Sincronizador sincronizador,
			OnNegocio oNegocio) {
		super();
		this.sincronizador = sincronizador;
		this.oNegocio = oNegocio;
		this.sincro = this;

	}

	public void notificar(int icon, CharSequence titulo, CharSequence texto,
			Integer id) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		long hora = System.currentTimeMillis();

		Intent notificationIntent = new Intent(context, LoginActivity.class);
		//PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
			//	notificationIntent, 0);

		PendingIntent contentIntent = PendingIntent.getActivity(
			    context,
			    0,
			    new Intent(), // add this
			    PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder notification = new NotificationCompat.Builder(
				context).setSmallIcon(icon)
				.setAutoCancel(true)
				.setContentTitle(titulo)
				.setContentText(texto)
				.setSound(soundUri)
				.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
				.setLights(Color.BLUE, 3000, 3000)
				.setContentIntent(contentIntent);

		nm.notify(id, notification.build());

	}

	public void setSincro(ISync sincro) {
		this.sincro = sincro;
	}

	public OnNegocio getoNegocio() {
		return oNegocio;
	}

	public void setoNegocio(OnNegocio oNegocio) {
		this.oNegocio = oNegocio;
	}

	public ServicioSincronizacion() {
		this.sincro = this;

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ejecutarSync() {

	}

	@Override
	public void ejecucionCorrecta() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejecucionIncorrecta() {
		// TODO Auto-generated method stub

	}

	public Sincronizador getSincronizador() {
		return sincronizador;
	}

	public void setSincronizador(Sincronizador sincronizador) {
		this.sincronizador = sincronizador;
	}

}
