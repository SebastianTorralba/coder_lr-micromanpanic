package ar.com.twoboot.panico.servicios;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.util.SmsMensaje;
import ar.com.twoboot.microman.util.SmsNotificacion;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.R;
import ar.com.twoboot.panico.dominio.OnConfiguracion;
import ar.com.twoboot.panico.dominio.OnEvento;
import ar.com.twoboot.panico.dominio.OnUsuario;
import ar.com.twoboot.panico.helpers.HttpRest;
import ar.com.twoboot.panico.objetos.Alerta;
import ar.com.twoboot.panico.objetos.Evento;
import ar.com.twoboot.panico.objetos.Usuario;

public class UbicacionServicioWidget extends Service {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private LocationManager locationManager;
    LocationListener locationListener;
    private Location location;
    private boolean isGPS = false;
    private boolean isNetwork = false;
    private boolean canGetLocation = true;
    private Usuario usuario;
    private Context contexto;
    public static boolean enviado = false;
    private NotificationCompat.Builder notification;
    private boolean error = false;
    private NotificationManager notificationManager;
    int notif_id;
    private String obteniendo_gps, esperando_senial, lat, lon, preci, mala_senial;

    @Override
    public IBinder onBind(Intent intent) {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        if (UbicacionServicioWidget.enviado) {
            Toast.makeText(getApplicationContext(), "Alerta Enviada", Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    private void obtenerStrings() {
        obteniendo_gps = getBaseContext().getString(R.string.Subicacion_obteniendo_gps);
        esperando_senial = getBaseContext().getString(R.string.Subicacion_esperando_senial);
        lat = getBaseContext().getString(R.string.Subicacion_lat);
        lon = getBaseContext().getString(R.string.Subicacion_long);
        preci = getBaseContext().getString(R.string.Subicacion_precsicion);
        mala_senial = getBaseContext().getString(R.string.Subicacion_malasenial);
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
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        if (!error) {
            Toast.makeText(this, "Alerta Enviada", Toast.LENGTH_SHORT).show();
        }
    }

    public Location getUbicacion() {
        return location;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        contexto = this;
        obtenerStrings();
        if (!isGPS && !isNetwork) {
            getUltimaLoc();
        } else {
            getLoc();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void notificar() {
        //Bitmap icono = BitmapFactory.decodeResource(getResources(),
        //        R.drawable.satellite_variant);
        notif_id = (int) System.currentTimeMillis();
        long[] vibrar = {0, 100, 200, 300, 100, 100};
        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        notification = new NotificationCompat.Builder(getBaseContext(), "ar.com.twoboot.panico");
        notification.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
        notification.setPriority(NotificationCompat.PRIORITY_MAX);
        notification.setCategory(NotificationCompat.CATEGORY_ALARM);
        notification.setContentInfo("GPS");
        notification.setVibrate(vibrar);
        notification.setSound(sonido);
        notification.setTicker("PANIC");
        //notificacion.setLargeIcon(icono);
        notification.setContentTitle(obteniendo_gps);
        notification.setContentText(esperando_senial);
        notification.setSmallIcon(R.drawable.ico_sat_icono);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.setColor(Color.RED);
        }
        notification.setAutoCancel(true);
        notification.setOngoing(true);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ar.com.twoboot.panico",
                    "AYUDASERVICIO",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(0121, notification.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        notificar();
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
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                } else if (isNetwork) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground();
        } else {
            startForeground(1, new Notification());
        }
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        MicroMan.mTrans = new Transaccion(getApplicationContext());
        MicroMan.mTrans.conectarDB();
        MicroMan.configuracion = new OnConfiguracion(MicroMan.mTrans).extraer();
        OnUsuario oUsuario = new OnUsuario(MicroMan.mTrans);
        usuario = oUsuario.comprobarAutoLogin();
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Toast.makeText(contexto, "Enviando Alerta...", Toast.LENGTH_SHORT).show();
                new POSTTaskW().execute(usuario);
            }

            public void onProviderDisabled(String provider) {
                if (locationManager != null) {
                    locationManager.removeUpdates(this);
                }
            }

            public void onProviderEnabled(String provider) {
                getLoc();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };
    }

    private class POSTTaskW extends AsyncTask<Usuario, Void, String> {
        @Override
        protected String doInBackground(Usuario... usuarios) {
            HttpRest clienterest = new HttpRest();
            JSONObject cuerpo = new JSONObject();
            String resp = "";
            if (location != null && location.getLatitude() != 0 && location.getLongitude() != 0 && location.getAccuracy() != 0) {
                try {
                    Evento evento = new Evento();
                    evento.setFecha(new Date());
                    evento.setLat(location.getLatitude());
                    evento.setLon(location.getLongitude());
                    evento.setPrecision(location.getAccuracy());
                    evento.setTipo(Alerta.DEFAULT);
                    evento.setUsuario(usuarios[0]);
                    cuerpo.put("gpsLat", evento.getLat());
                    cuerpo.put("gpsLon", evento.getLon());
                    cuerpo.put("gpsPres", evento.getPrecision());
                    JSONObject jsonusuario = new JSONObject();
                    jsonusuario.put("clave", evento.getUsuario().getClave());
                    jsonusuario.put("id", evento.getUsuario().getIdUsuario());
                    jsonusuario.put("claveMd5", Util.getMD5(evento.getUsuario().getClave()));
                    jsonusuario.put("email", evento.getUsuario().getUsuario());
                    jsonusuario.put("username", evento.getUsuario().getUsuario());
                    jsonusuario.put("estado", 1);
                    cuerpo.put("usuario", jsonusuario);
                    JSONObject jsonequipo = new JSONObject();
                    jsonequipo.put("id", 1);
                    cuerpo.put("equipo", jsonequipo);
                    MediaType tipoform = MediaType.parse("application/json; charset=utf-8");
                    RequestBody formulario = RequestBody.create(tipoform, cuerpo.toString());
                    Request request = new Request.Builder()
                            .url(MicroMan.configuracion.getRestserverUrl() + "usuarios/eventos/create").post(formulario).build();
                    Response respuestahttp = clienterest.getClientehttp().newCall(request).execute();
                    resp = respuestahttp.body().string();
                    JSONObject jsonresp = new JSONObject(resp);
                    int codresp = jsonresp.getInt("status");
                    if (codresp == 200) {
                        OnEvento oEvento = new OnEvento(MicroMan.mTrans);
                        oEvento.setEvento(evento);
                        oEvento.insertar();
                        UbicacionServicioWidget.enviado = true;
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(true);
                stopSelf();
            } else {
                stopSelf();
            }
        }
    }

}
