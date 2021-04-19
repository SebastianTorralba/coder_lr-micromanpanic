package ar.com.twoboot.panico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.twoboot.microman.AbmFragment;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.sincronizacion.ISync;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.remotos.Secuencializador;
import ar.com.twoboot.microman.util.CoderLog;
import ar.com.twoboot.microman.util.DetectorConexion;
import ar.com.twoboot.microman.util.GpsLog;
import ar.com.twoboot.microman.util.SmsMensaje;
import ar.com.twoboot.microman.util.SmsNotificacion;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.R.id;
import ar.com.twoboot.panico.dominio.FactoryPreguntas;
import ar.com.twoboot.panico.dominio.OnCategoria;
import ar.com.twoboot.panico.dominio.OnCuestionario;
import ar.com.twoboot.panico.dominio.OnCuestionarioModelo;
import ar.com.twoboot.panico.objetos.Categoria;
import ar.com.twoboot.panico.objetos.Cuestionario;
import ar.com.twoboot.panico.objetos.CuestionarioModelo;
import ar.com.twoboot.panico.objetos.Pregunta;

public class PanicoActivity extends AbmFragment implements ISync {
	private Secuencializador secuencializador = new Secuencializador(Util.TIPO_APP);
	private Cuestionario cuestionario;
	private OnCuestionarioModelo oCuestionarioModelo;
	private OnCuestionario oCuestionario;
	private GpsLog gps;
	private Button bGps;
	private TextView tvGpsLatitud;
	private TextView tvGpsLongitud;
	private ImageView ivCabecera;
	private Button bFoto;
	private Bitmap mImageBitmap;
	private boolean falloCamara;
	private Uri outputFileUri;
	private ArrayList<CuestionarioModelo> cuestionarioModelos;
	private ISync sincro;
	private File imagenArchivo;
	private int estadoCuestionario;
	private Button bVerFoto;
	private View llPreguntas;
	private FactoryPreguntas factoryPreguntas;
	private final String codCategoria = "APPPANIC";
	private Categoria categoria;
	private Button bEnviar;

	public PanicoActivity(int accion, DalusObject objeto) {
		super(accion, objeto);
		cuestionario = (Cuestionario) objeto;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void popular() {
		if (cuestionario != null) {
			tvGpsLatitud.setText(String.valueOf(cuestionario.getGpsLatitud()));
			tvGpsLongitud.setText(String.valueOf(cuestionario.getGpsLongitud()));
		}
	}

	public PanicoActivity() {
		super();
		cuestionario = new Cuestionario();
		mTrans = MicroMan.mTrans;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mTrans = MicroMan.mTrans;
		// Show the Up button in the action bar.
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("cuestionario")) {
				cuestionario = (Cuestionario) savedInstanceState.getSerializable("cuestionario");
				popular();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==R.id.im_volver) {
			this.finish();
		}
		return true;
	}

	private void inicializarSecuencializador() {
		if (accion == OnNegocio.ACCION_NUEVO) {
		}
		estadoCuestionario = OnCuestionario.ESTADO_DB_LOCAL_CON_CONEXION;
	}

	@Override
	public Boolean ejecutarSincronizador() {
		int rtn = 0;
		existeConexionInternet = MicroMan.detectorConexion.hayConexion();
		if (existeConexionInternet) {
			cuestionario.setEstado(OnCuestionario.ESTADO_DB_LOCAL_SIN_CONEXION);
			CoderLog.log(cuestionario, "Envidador a Servidor", "OK");
			mostrarAdvertenciaToast("Enviado a Servidor");

		} else {
			cuestionario.setIdCuestionario(oCuestionario.getSiguienteId());
			grabar(cuestionario);
			String mensaje = "Se Grabo Localmente envielo cuando tenga Internet, desde el menu Listado";
			mostrarAdvertencia(Util.TIPO_APP.toUpperCase(), mensaje);
		}
		// Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		// intent.putExtra("enabled", false);
		// sendBroadcast(intent);

		this.finish();
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
		return true;
	}

	@Override
	public void ejecutarSync() {
		// TODO Auto-generated method stub
	}

	@Override
	public void ejecucionCorrecta() {
	}

	@Override
	public void ejecucionIncorrecta() {
		CoderLog.log(cuestionario, "Error en Servidor", "OK");
		mostrarAdvertenciaToast("Error al enviar Intente nuevamente");
	}

	@Override
	protected void inicializacion() {
		setContentView(R.layout.activity_panico);
		Bundle bundle = getIntent().getExtras();
		ivCabecera = (ImageView) findViewById(id.ivCabecera);
		// accion = bundle.getInt("accion");
		categoria = new OnCategoria(mTrans).extraer(codCategoria);
		oCuestionario = new OnCuestionario(mTrans);
		oCuestionario.setCuestionario(cuestionario);
		oCuestionarioModelo = new OnCuestionarioModelo(mTrans);
		titulo = "Panico";
		sincro = this;
		usaSincronizador = true;
		MicroMan.detectorConexion = new DetectorConexion(this);
		existeConexionInternet = MicroMan.detectorConexion.hayConexion();
		if (existeConexionInternet) {
			inicializarSecuencializador();
		} else {
			estadoCuestionario = OnCuestionario.ESTADO_DB_LOCAL_SIN_CONEXION;
			cuestionario.setIdCuestionario(oCuestionario.getSiguienteId());
		}
		cuestionarioModelos = oCuestionarioModelo.extraer();
		gps = new GpsLog(this, this);
		ivCabecera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		bGps = (Button) findViewById(id.bGps);
		bGps.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				coordenadasGps();
			}
		});

		tvGpsLatitud = (TextView) findViewById(id.tvGpsLatitud);
		tvGpsLongitud = (TextView) findViewById(id.tvGpsLongitud);
		bFoto = (Button) findViewById(id.bFoto);
		bFoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tomarFoto();
			}
		});
		bVerFoto = (Button) findViewById(id.bVerFoto);
		bVerFoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mImageBitmap != null) {
					mostrarImagen(mImageBitmap);
				} else {
					mostrarAdvertencia("No hay Foto");
				}
			}
		});
		bEnviar = (Button) findViewById(id.bEnviar);
		bEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				aceptar();
			}
		});
		llPreguntas = (LinearLayout) findViewById(id.llPreguntas);
		generarPreguntasCategoria(categoria);
		coordenadasGps();
	}

	private void generarPreguntasCategoria(Categoria categoria) {
		factoryPreguntas = new FactoryPreguntas(this, llPreguntas);
		for (CuestionarioModelo cuestionarioModelo : cuestionarioModelos) {
			Pregunta p = cuestionarioModelo.getPreguntas();
			if (p.getCategorias().equals(categoria)) {
				factoryPreguntas.generarPreguntas(p);
			}
		}
	}

	@Override
	public DalusObject consolidar() {
		cuestionario.setIdUsuario(MicroMan.idUsuario);
		if (tvGpsLatitud.getText().length() > 0) {
			cuestionario.setGpsLatitud(Double.valueOf(tvGpsLatitud.getText().toString()));
		}
		if (tvGpsLongitud.getText().length() > 0) {
			cuestionario.setGpsLongitud(Double.valueOf(tvGpsLongitud.getText().toString()));
		}
		if (mImageBitmap != null) {
			cuestionario.setFotoArchivo(imagenArchivo.getPath());
		}
		factoryPreguntas.consolidarRespuestasCuestionario(cuestionario);
		return super.consolidar();
	}

	@Override
	public boolean validar() {
		Boolean resultado = true;
		Boolean cargoCoordenadas = false;
		Boolean cargoDomicilio = false;
		if (cuestionario.getGpsLatitud() != 0 && cuestionario.getGpsLongitud() != 0) {
			cargoCoordenadas = true;
		}
		if (!cargoCoordenadas) {
			mostrarAdvertencia("Advertencia", "Falta Georeferencia");
		}
		/*
		 * if (!falloCamara) { if (mImageBitmap == null) {
		 * mostrarAdvertenciaToast("Debe tomar Fotografia"); return false; }
		 * 
		 * }
		 */
		if (existeConexionInternet) {
			// if (secuencializador != null) {
			// if (!(secuencializador.getId() > 0)) {
			// mostrarAdvertenciaToast("Secuencializador sin Datos");
			// return false;
			// }
			// } else {
			// mostrarAdvertenciaToast("Reiniciando Secuencializador Intente Nuevamente");
			// inicializarSecuencializador();
			// return false;
			// }
		}
		return resultado;
	}

	@Override
	public int grabar(DalusObject objeto) {
		int rtn = 0;
		CoderLog.log(cuestionario, "Grabando", "Inicio");
		// if (existeConexionInternet) {
		// cuestionario.setIdCuestionario(secuencializador.getId().intValue());
		// }
		cuestionario.setEstado(estadoCuestionario);
		cuestionario.setFechaCarga(new Date());
		if (accion == OnNegocio.ACCION_NUEVO) {
			// Extraer de Servidor Numero de Reclamo
			rtn = oCuestionario.insertar();
		}
		if (rtn > 0) {
			CoderLog.log(cuestionario, "Grabando", "OK");
			mostrarAdvertenciaToast("Datos Grabados");
		} else {
			CoderLog.log(cuestionario, "Grabando", "Error");
			CoderLog.log(cuestionario, "Grabando", "Codigo:" + cuestionario.getIdCuestionario());
			// oCuestionario.reset();
			// CoderLog.log(cuestionario, oCuestionario.getSqlError());
			// mostrarAdvertencia("Error al Grabar: " + oHito.getSqlError());
		}
		return 0;
	}

	public void coordenadasGps() {
		Double latitude = gps.getLatitud();
		Double longitude = gps.getLongitud();
		if (latitude != null) {
			tvGpsLatitud.setText(latitude.toString());
		} else {
			Toast.makeText(this, "Intente de Nuevo", Toast.LENGTH_LONG);
		}
		if (longitude != null) {

			tvGpsLongitud.setText(longitude.toString());
		} else {
			Toast.makeText(this, "Intente de Nuevo", Toast.LENGTH_LONG);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			try {
				Matrix matrix = new Matrix();
				matrix.setRotate(90);
				mImageBitmap = Media.getBitmap(this.getContentResolver(), Uri.fromFile(imagenArchivo));
				mImageBitmap = Bitmap.createScaledBitmap(mImageBitmap, 800, 600, true);
				mImageBitmap = Bitmap.createBitmap(mImageBitmap, 0, 0, mImageBitmap.getWidth(),
						mImageBitmap.getHeight(), matrix, true);
				mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(imagenArchivo));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			falloCamara = false;
			CoderLog.log(cuestionario, "Camara", "Fallo");
		} else {
			CoderLog.log(cuestionario, "Camara", "Fallo");
			falloCamara = true;
		}

	}

	private File getTempFile(Context context) {
		// it will return /sdcard/image.tmp
		final File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
		if (!path.exists()) {
			path.mkdir();
		}
		return new File(path, oCuestionario.generarNombreFoto());

	}

	private void tomarFoto() {
		/*
		 * Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		 * imagenArchivo = getTempFile(this); outputFileUri =
		 * Uri.fromFile(imagenArchivo);
		 * takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		 * startActivityForResult(takePictureIntent, 1);
		 */
		SmsNotificacion smsNotificacion = new SmsNotificacion();
		SmsMensaje mensaje = new SmsMensaje();
		mensaje.setCuerpo("AUXILIO");
		smsNotificacion.sendSms(mensaje);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (cuestionario != null) {
			outState.putSerializable("cuestionario", cuestionario);
		}
	}
}
