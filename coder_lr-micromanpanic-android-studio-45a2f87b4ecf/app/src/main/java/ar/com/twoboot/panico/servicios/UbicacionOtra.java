package ar.com.twoboot.panico.servicios;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
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
import ar.com.twoboot.microman.widget.BaseWidget;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.R;
import ar.com.twoboot.panico.dominio.OnConfiguracion;
import ar.com.twoboot.panico.dominio.OnEvento;
import ar.com.twoboot.panico.dominio.OnUsuario;
import ar.com.twoboot.panico.helpers.HttpRest;
import ar.com.twoboot.panico.objetos.Alerta;
import ar.com.twoboot.panico.objetos.Evento;
import ar.com.twoboot.panico.objetos.Usuario;

public class UbicacionOtra extends Service {
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 500;
    private static final float LOCATION_DISTANCE = 5f;
    private NotificationManager notificationManager;
    private final int precision = 25;
    private int notif_id;
    private Usuario usuario;
    private NotificationCompat.Builder notificacion;
    private String obteniendo_gps, esperando_senial, lat, lon, preci, mala_senial;
    private Handler handler;
    private Runnable mensaje_ok, mensaje_error;

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        private void actualizarUbicacion(Location l) {
            updatewidget(getBaseContext(), "Obteniendo Ubicacion...");
            notificacion.setContentText("");
            notificacion.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(preci + ": " + l.getAccuracy() + "mts\n" + lat + ": " + l.getLatitude() +
                            "\n" + lon + ": " + l.getLongitude())
            );
            notificationManager.notify(notif_id, notificacion.build());
            if (l.getAccuracy() <= precision) {
                updatewidget(getBaseContext(), "Enviando Alerta...");
                Toast.makeText(getBaseContext(), "Enviando Alerta...", Toast.LENGTH_SHORT).show();
                mLastLocation.set(l);
                SmsNotificacion smsNotificacion = new SmsNotificacion();
                SmsMensaje mensaje = new SmsMensaje();
                Alerta alerta = new Alerta(mLastLocation, Alerta.DEFAULT, usuario);
                mensaje.setCuerpo(alerta.toString());
                //smsNotificacion.sendSms(mensaje);
                enviarAlerta();
            } else {
                notificacion.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(preci + ": " + l.getAccuracy() + "mts\n" + lat + ": " + l.getLatitude() + "\n" + lon + ": " + l.getLongitude() +
                                mala_senial)
                );
                notificationManager.notify(notif_id, notificacion.build());
            }
        }

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                actualizarUbicacion(location);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            if (mLocationManager != null) {
                mLocationManager.removeUpdates(this);
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            if (mLocationManager != null) {
                try {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                            mLocationListeners[1]);
                } catch (SecurityException | IllegalArgumentException ex) {
                }
                try {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                            mLocationListeners[0]);
                } catch (SecurityException | IllegalArgumentException ex) {
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        private void enviarAlerta() {
            Thread proceso = new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpRest clienterest = new HttpRest();
                    JSONObject cuerpo = new JSONObject();
                    String resp = "";
                    if (mLastLocation != null && mLastLocation.getLatitude() != 0 && mLastLocation.getLongitude() != 0 && mLastLocation.getAccuracy() != 0) {
                        try {
                            Evento evento = new Evento();
                            evento.setFecha(new Date());
                            evento.setLat(mLastLocation.getLatitude());
                            evento.setLon(mLastLocation.getLongitude());
                            evento.setPrecision(mLastLocation.getAccuracy());
                            evento.setTipo(Alerta.DEFAULT);
                            evento.setUsuario(usuario);
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
                                runEnproceso(mensaje_ok);
                            }
                        } catch (JSONException e) {
                            runEnproceso(mensaje_error);
                        } catch (IOException e) {
                            runEnproceso(mensaje_error);
                        }
                    }
                }
            });
            proceso.start();
        }
    }

    private void updatewidget(Context c, String texto) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(c);
        RemoteViews remoteViews = new RemoteViews(c.getPackageName(), R.layout.widget);
        ComponentName thisWidget = new ComponentName(c, BaseWidget.class);
        remoteViews.setTextViewText(R.id.txt_estado_widget, texto);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    private void cerrar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
            stopSelf();
        } else {
            stopSelf();
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void notificar() {
        //Bitmap icono = BitmapFactory.decodeResource(getResources(),
        //        R.drawable.satellite_variant);
        notif_id = (int) System.currentTimeMillis();
        long[] vibrar = {0, 100, 200, 300, 100, 100};
        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        notificacion = new NotificationCompat.Builder(getBaseContext(), "ar.com.twoboot.panico");
        notificacion.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
        notificacion.setPriority(NotificationCompat.PRIORITY_MAX);
        notificacion.setCategory(NotificationCompat.CATEGORY_ALARM);
        notificacion.setContentInfo("GPS");
        notificacion.setVibrate(vibrar);
        notificacion.setSound(sonido);
        notificacion.setTicker("PANIC ALARM");
        //notificacion.setLargeIcon(icono);
        notificacion.setContentTitle(obteniendo_gps);
        notificacion.setContentText(esperando_senial);
        notificacion.setSmallIcon(R.drawable.ico_sat_icono);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificacion.setColor(Color.RED);
        }
        notificacion.setOnlyAlertOnce(true);
        notificacion.setAutoCancel(true);
        notificacion.setOngoing(true);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ar.com.twoboot.panico",
                    "AYUDASERVICIO",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(notif_id, notificacion.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        obtenerStrings();
        notificar();
        return START_STICKY;
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
    public void onCreate() {
        initializeLocationManager();
        handler = new Handler();
        mensaje_ok = new Runnable() {
            @Override
            public void run() {
                updatewidget(getBaseContext(), "Alerta enviada!");
                Toast.makeText(getBaseContext(), "Alerta enviada!", Toast.LENGTH_LONG).show();
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updatewidget(getBaseContext(), "");
                    }
                },800);
            }
        };
        mensaje_error = new Runnable() {
            @Override
            public void run() {
                updatewidget(getBaseContext(), "Alerta no enviada!");
                Toast.makeText(getBaseContext(), "Alerta no enviada!", Toast.LENGTH_LONG).show();
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updatewidget(getBaseContext(), "");
                    }
                },800);
            }
        };
        MicroMan.mTrans = new Transaccion(getApplicationContext());
        MicroMan.mTrans.conectarDB();
        MicroMan.configuracion = new OnConfiguracion(MicroMan.mTrans).extraer();
        OnUsuario oUsuario = new OnUsuario(MicroMan.mTrans);
        usuario = oUsuario.comprobarAutoLogin();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (SecurityException | IllegalArgumentException ex) {
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException | IllegalArgumentException ex) {
        }
    }

    private void runEnproceso(Runnable runnable) {
        handler.postDelayed(runnable, 5000);
        cerrar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                }
            }
        }
        notificationManager.cancel(notif_id);
        cerrar();
    }

    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
