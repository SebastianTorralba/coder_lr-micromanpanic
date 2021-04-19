package ar.com.twoboot.panico;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import ar.com.twoboot.microman.AbmFragment;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.sincronizacion.ISync;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.R.id;
import ar.com.twoboot.panico.dominio.OnCuestionario;
import ar.com.twoboot.panico.items.ItemCuestionario;
import ar.com.twoboot.panico.objetos.Cuestionario;

public class ListadoCuestionarioActivity extends AbmFragment implements ISync {
	private Button bEnviar;
	private ListView lvCuestionarios;
	private ISync sincro;
	private AlertDialog adMensaje;
	private Context contextDialog = null;
	private Transaccion mTrans;
	private OnCuestionario oCuestionario;
	private ArrayList<Cuestionario> itemsCuestionarios;
	private Iterator<Cuestionario> iteradorCuestionarios;
	private Cuestionario cuestionario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listado_cuestionario);
		mTrans = MicroMan.mTrans;
		contextDialog = this;
		sincro = this;
		oCuestionario = new OnCuestionario(mTrans);
		lvCuestionarios = (ListView) findViewById(id.lvCuestionarios);
		itemsCuestionarios = obtenerCuestionarios();
		lvCuestionarios.setAdapter(new ItemCuestionario(this, itemsCuestionarios));
		bEnviar = (Button) findViewById(id.bEnviar);
		bEnviar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adActualizar = new AlertDialog.Builder(contextDialog);
				adActualizar.setTitle("Enviar Datos al Servidor");
				adActualizar.setMessage("Estas seguro?");
				adActualizar.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						if (MicroMan.detectorConexion.hayConexion()) {
							ejecutarSync();
						} else {
							bEnviar.setError("No hay Internet");
							mostrarAdvertencia("No hay Internet");
						}
					}
				});
				adActualizar.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
				adActualizar.show();
			}
		});
	}

	private ArrayList<Cuestionario> obtenerCuestionarios() {
		ArrayList<Cuestionario> items = new ArrayList(oCuestionario.getListado());
		iteradorCuestionarios = items.iterator();
		return items;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void ejecutarSync() {
		try {
			cuestionario = iteradorCuestionarios.next();
			oCuestionario.setCuestionario(cuestionario);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void ejecucionCorrecta() {
		cuestionario.setEstado(2);
		oCuestionario.actualizarEstado();
		itemsCuestionarios = obtenerCuestionarios();
		lvCuestionarios.setAdapter(new ItemCuestionario(this, itemsCuestionarios));
		AlertDialog.Builder adCuestionarioRemoto = new AlertDialog.Builder(contextDialog);
		adCuestionarioRemoto.setTitle(Util.TIPO_APP);
		String mensaje = "Numero: " + cuestionario.getIdCuestionario();
		adCuestionarioRemoto.setMessage(mensaje);
		adCuestionarioRemoto.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				if (iteradorCuestionarios.hasNext()) {
					ejecutarSync();
				}
			}
		});
		adCuestionarioRemoto.show();

	}

	@Override
	public void ejecucionIncorrecta() {
		Toast.makeText(contextDialog, "Error de Conexion", Toast.LENGTH_LONG);
	}
}
