package ar.com.twoboot.panico;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ar.com.twoboot.panico.R;
import ar.com.twoboot.microman.dominio.sincronizacion.ISync;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.panico.dominio.sincronizacion.SyncLocalidades;
import ar.com.twoboot.panico.dominio.sincronizacion.SyncPreguntaRespuestas;
import ar.com.twoboot.panico.dominio.sincronizacion.SyncPreguntas;
import ar.com.twoboot.panico.dominio.sincronizacion.SyncRespuestas;
import ar.com.twoboot.panico.dominio.sincronizacion.SyncTipoRespuesta;

public class ActualizarActivity extends Activity implements ISync {
	private Button bActualizarDatosDomicilio;
	private Button bActualizarSistema;
	private SyncLocalidades syncLocalidades;
	private SyncTipoRespuesta syncTipoRespuesta;
	private SyncRespuestas syncRespuestas;
	private SyncPreguntas syncPreguntas;
	private SyncPreguntaRespuestas syncPreguntaRespuestas;
	private ArrayList<Sincronizador> sincronizadores = new ArrayList<Sincronizador>();
	private Iterator itSincro;
	private AlertDialog adMensaje;
	private Context contextDialog = null;
	private ISync sincro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar);
		sincro = this;
		contextDialog = this;
		bActualizarSistema = (Button) findViewById(R.id.bActualizarSistema);
		bActualizarSistema.setOnClickListener(new OnClickListener() {
			private boolean importar;

			@Override
			public void onClick(View v) {
				importar = true;
				syncTipoRespuesta = new SyncTipoRespuesta(MicroMan.configuracion, contextDialog);
				syncTipoRespuesta.setmObjetoSync(sincro);
				syncTipoRespuesta.setmTrans(MicroMan.mTrans);
				sincronizadores.add(syncTipoRespuesta);
				syncRespuestas = new SyncRespuestas(MicroMan.configuracion, contextDialog);
				syncRespuestas.setmObjetoSync(sincro);
				syncRespuestas.setmTrans(MicroMan.mTrans);
				sincronizadores.add(syncRespuestas);
				syncPreguntas = new SyncPreguntas(MicroMan.configuracion, contextDialog);
				syncPreguntas.setmObjetoSync(sincro);
				syncPreguntas.setmTrans(MicroMan.mTrans);
				sincronizadores.add(syncPreguntas);
				syncPreguntaRespuestas = new SyncPreguntaRespuestas(MicroMan.configuracion, contextDialog);
				syncPreguntaRespuestas.setmObjetoSync(sincro);
				syncPreguntaRespuestas.setmTrans(MicroMan.mTrans);
				sincronizadores.add(syncPreguntaRespuestas);
				itSincro = sincronizadores.iterator();
				AlertDialog.Builder adRuta = new AlertDialog.Builder(contextDialog);
				adRuta.setTitle("Actualizacion");
				adRuta.setMessage("Estas seguro?");
				adRuta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						ejecutarSync();
					}
				});
				adRuta.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
				adRuta.show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void ejecutarSync() {
		try {
			Sincronizador s = (Sincronizador) itSincro.next();
			s.execute(Sincronizador.OP_IMPORTAR);

		} catch (Exception e) {
		}
	}

	@Override
	public void ejecucionCorrecta() {
		adMensaje = new AlertDialog.Builder(this).create();
		adMensaje.setMessage("Ruta Descargada");
		adMensaje.setCancelable(true);
		adMensaje.show();
		adMensaje.dismiss();
		ejecutarSync();
	}

	@Override
	public void ejecucionIncorrecta() {
		adMensaje = new AlertDialog.Builder(this).create();
		adMensaje.setMessage("Error al Descargar");
		adMensaje.setCancelable(true);
		adMensaje.show();
		adMensaje.dismiss();
	}

}
