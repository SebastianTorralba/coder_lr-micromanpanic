package ar.com.twoboot.panico;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.util.DetectorConexion;

import ar.com.twoboot.panico.dominio.OnConfiguracion;
import ar.com.twoboot.panico.dominio.OnUsuario;
import ar.com.twoboot.panico.objetos.Configuracion;
import ar.com.twoboot.panico.objetos.Cuestionario;

public class MicroMan extends AppCompatActivity {
	public static final String PARAM_ID_CUESTIONARIO_ACTIVO = "id_cuestionario_activo";

	public static final String PARAM_CUESTIONARIO_ACTIVO = "cuestionario_activa";
	public static final String PARAM_USUARIO = "usuario_activo";
	public static final String PARAM_ESTADO = "estado";
	public static boolean registrado = false;
	private ListView list;
	private TextView tvBaseDatos;
	public static Transaccion mTrans;
	public static Cuestionario cuestionarioActivo;
	public static Configuracion configuracion;
	public static String imei = "";
	public static Boolean estaLogueado = false;
	public static int idUsuario;
	public static DetectorConexion detectorConexion;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// checkRutaActiva();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		if (savedInstanceState != null) {
			// setRutaActiva(((Ruta) savedInstanceState
			// .getSerializable(PARAM_RUTA_ACTIVA)));
			// checkRutaActiva();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		// outState.putSerializable(PARAM_RUTA_ACTIVA, cuestionarioActivo);
	}

	public static Cuestionario getCuestionarioActivo() {
		return cuestionarioActivo;
	}

	public static void setCuestionarioActivo(Cuestionario rutaActiva) {
		MicroMan.cuestionarioActivo = rutaActiva;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mTrans = new Transaccion(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_microman);
		// TelephonyManager mngr = (TelephonyManager)
		// getSystemService(Context.TELEPHONY_SERVICE);
		// imei = mngr.getDeviceId();
		// mTrans.conectarDB();
		// Intent intent = new Intent(getApplicationContext(), PanicoActivity.class);
		// startActivity(intent);
		list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				String item = (String) list.getAdapter().getItem(position);
				if (item.equals("Ver eventos enviados")) {
					Intent intent = new Intent(getApplicationContext(), EventosEnviadosActivity.class);
					startActivity(intent);
				} else if (item.equals("Configuración")) {
					Intent intent = new Intent(getApplicationContext(), ConfiguracionActivity.class);
					startActivity(intent);
				} else if (item.equals("Teléfonos Útiles")) {
					Intent intent = new Intent(getApplicationContext(), TelUtilesActivity.class);
					startActivity(intent);
				} else if (item.equals("Cerrar sesión")) {
					OnUsuario onUsuario = new OnUsuario(mTrans);
					onUsuario.resetEstado();
					System.exit(0);
				} else if (item.equals("Salir")) {
					finishAffinity();
				}
			}
		});

	}

	public void actualizar() {
		configuracion = new OnConfiguracion(mTrans).extraer();
	}

	private boolean estaRegistrado() {
		registrado = OnUsuario.verificarRegistracion(mTrans);
		return registrado;
	}

	@Override
	public void onBackPressed() {
		// Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		// intent.putExtra("enabled", false);
		// sendBroadcast(intent);
		// finish();
        finishAffinity();
	}

}
