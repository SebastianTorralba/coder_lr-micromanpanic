package ar.com.twoboot.panico;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import com.ncorti.slidetoact.SlideToActView;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.util.SmsMensaje;
import ar.com.twoboot.microman.util.SmsNotificacion;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.dominio.OnConfiguracion;
import ar.com.twoboot.panico.dominio.OnEvento;
import ar.com.twoboot.panico.dominio.OnUsuario;
import ar.com.twoboot.panico.helpers.HttpRest;
import ar.com.twoboot.panico.objetos.Alerta;
import ar.com.twoboot.panico.objetos.Evento;
import ar.com.twoboot.panico.objetos.Usuario;
import ar.com.twoboot.panico.servicios.BootServicio;
import ar.com.twoboot.panico.servicios.UbicacionServicio;
import ar.com.twoboot.panico.servicios.UbicacionServicio.UbicacionBinder;

public class LockActivity extends Activity {
    private Context contexto;
    private UbicacionServicio ubicacionService;
    boolean uServiceBound = false;
    private Button boton;
    private TextView estadogps;
    private boolean enviando;
    private int segundos;
    private SlideToActView lock_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contexto = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_lock);
        boton = (Button) findViewById(R.id.btn_help);
        estadogps = (TextView) findViewById(R.id.txtestadogps);
        enviando = false;
        SharedPreferences prefs = getSharedPreferences("configuracion", Context.MODE_PRIVATE);
        segundos = prefs.getInt("sbloqueo", 5);
        startService(new Intent(contexto, BootServicio.class));
        lock_btn = findViewById(R.id.slideunlock);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (Util.estadoGPS(this)) {
            estadogps.setText("GPS ACTIVADO");
        } else {
            estadogps.setText("GPS DESACTIVADO");
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!enviando) {
                    parar();
                }
            }
        }, segundos * 1000);
        MicroMan.mTrans = new Transaccion(getApplicationContext());
        MicroMan.mTrans.conectarDB();
        MicroMan.configuracion = new OnConfiguracion(MicroMan.mTrans).extraer();
        lock_btn.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NotNull SlideToActView slideToActView) {
                slideToActView.setLocked(true);
                boton.setEnabled(true);
                slideToActView.setVisibility(View.GONE);
            }
        });
        lock_btn.setOnSlideResetListener(new SlideToActView.OnSlideResetListener() {
            @Override
            public void onSlideReset(@NotNull SlideToActView slideToActView) {
                slideToActView.setLocked(false);
                boton.setEnabled(false);
            }
        });
    }

    public void pedirAyuda(View v) throws InterruptedException {
        if (Util.estadoGPS(this)) {
            enviaralerta();
        } else {
            Toast.makeText(getApplicationContext(), "Activar GPS", Toast.LENGTH_SHORT).show();
        }
    }

    private void enviaralerta() {
        boton.setEnabled(false);
        OnUsuario oUsuario = new OnUsuario(MicroMan.mTrans);
        final Usuario usuario = oUsuario.comprobarAutoLogin();
        if (usuario == null) {
            Toast.makeText(contexto, "Usted no esta logueado!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intentubicacion = new Intent(this, UbicacionServicio.class);
        startService(intentubicacion);
        bindService(intentubicacion, mServiceConnection, Context.BIND_AUTO_CREATE);
        Toast.makeText(contexto, "Pidiendo Ayuda...", Toast.LENGTH_SHORT).show();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (uServiceBound && ubicacionService.getUbicacion() != null && !enviando) {
                    SmsNotificacion smsNotificacion = new SmsNotificacion();
                    SmsMensaje mensaje = new SmsMensaje();
                    Alerta alerta = new Alerta(ubicacionService.getUbicacion(), Alerta.DEFAULT, usuario);
                    mensaje.setCuerpo(alerta.toString());
                    enviando = true;
                    //smsNotificacion.sendSms(mensaje);
                    new POSTTask().execute(usuario);
                }
            }
        }, 0, 3000);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (uServiceBound) {
            pararubicacion();
        }
        parar();
    }

    private void pararubicacion() {
        unbindService(mServiceConnection);
        Intent intentparar = new Intent(contexto, UbicacionServicio.class);
        stopService(intentparar);
    }

    private void parar() {
        stopService(new Intent(contexto, BootServicio.class));
        finish();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            uServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            UbicacionBinder uBinder = (UbicacionBinder) service;
            ubicacionService = uBinder.getService();
            uServiceBound = true;
        }
    };

    private class POSTTask extends AsyncTask<Usuario, Void, String> {
        @Override
        protected String doInBackground(Usuario... usuarios) {
            HttpRest clienterest = new HttpRest();
            JSONObject cuerpo = new JSONObject();
            String resp = "";
            try {
                Evento evento = new Evento();
                evento.setFecha(new Date());
                evento.setLat(ubicacionService.getUbicacion().getLatitude());
                evento.setLon(ubicacionService.getUbicacion().getLongitude());
                evento.setPrecision(ubicacionService.getUbicacion().getAccuracy());
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
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {
            pararubicacion();
            parar();
        }
    }
}
