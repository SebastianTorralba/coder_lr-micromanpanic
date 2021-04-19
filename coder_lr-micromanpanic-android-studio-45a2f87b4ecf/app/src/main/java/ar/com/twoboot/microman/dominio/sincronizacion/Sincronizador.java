package ar.com.twoboot.microman.dominio.sincronizacion;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.dominio.OnConfiguracion;
import ar.com.twoboot.panico.objetos.Configuracion;

/** @pdOid 7277f107-79ac-4e88-861e-96cb828db136 */
public class Sincronizador extends AsyncTask<Integer, Integer, Integer> {
	/** @pdOid 8b6afcbb-8597-4818-bd44-93398afaa5dc */
	protected Connection mConexion = null;
	protected Transaccion mTrans;
	private WeakReference<Context> mContext;
	protected Configuracion configuracion;
	private String mNombreTabla;
	private ISync mObjetoSync;
	public final static Integer EJECUCION_DEFAULT = 0;
	public final static Integer EJECUCION_CORRECTA = 1;
	public final static Integer EJECUCION_INCORRECTA = 2;
	private boolean estado = true;
	private Integer estadoEjecucion = EJECUCION_INCORRECTA;
	private String mensaje;

	public String getEstadoTransmision() {
		return estadoTransmision;
	}

	public void setEstadoTransmision(String estadoTransmision) {
		this.estadoTransmision = estadoTransmision;
	}

	private ArrayList<DalusObject> lista;

	public ArrayList<DalusObject> getLista() {
		return lista;
	}

	public void setLista(ArrayList<DalusObject> lista) {
		this.lista = lista;
	}

	protected boolean resultado;

	/**
	 * @return the mObjetoSync
	 */
	public final ISync getmObjetoSync() {
		return mObjetoSync;
	}

	/**
	 * @param mObjetoSync
	 *            the mObjetoSync to set
	 */
	public final void setmObjetoSync(ISync mObjetoSync) {
		this.mObjetoSync = mObjetoSync;
	}

	/**
	 * @return the mNombreTabla
	 */
	public final String getmNombreTabla() {
		return mNombreTabla;
	}

	/**
	 * @param mNombreTabla
	 *            the mNombreTabla to set
	 */
	public final void setmNombreTabla(String mNombreTabla) {
		this.mNombreTabla = mNombreTabla;
	}

	protected Integer mtotalRegistros = 0;
	protected Integer mProgreso = 0;
	protected Integer mRegistroActual = 0;
	private ProgressDialog pDialog;
	public static final Integer OP_TESTCONEXION = 1;
	public final static Integer OP_IMPORTAR = 2;
	public final static Integer OP_EXPORTAR = 3;
	public final static Integer OP_LISTAR = 4;

	protected long rtn;
	protected Integer registros = 0;
	protected String estadoTransmision = "";

	public Sincronizador(Configuracion configuracion, Context context) {
		super();
		Log.i(Util.APP, getClass().getSimpleName().toString());
		this.configuracion = configuracion;
		if (context != null) {
			this.mContext = new WeakReference<Context>(context);
		}
		if (mContext != null) {

			pDialog = new ProgressDialog(mContext.get());
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setIndeterminate(true);

		}
	}

	/**
	 * @param params
	 * @pdOid 12e739e9-6d51-40cc-a772-90e2cfbf7415
	 */
	@Override
	protected Integer doInBackground(Integer... params) {
		switch (params[0]) {
		case 1:
			conectarDBRemoto();
			if (estado)
				descontarDBRemoto();
			break;
		case 2:
			// if (!existenFormulariosAExportar()) {

			conectarDBRemoto();
			if (estado) {
				if (importar())
					descontarDBRemoto();
				// }
			}
			break;
		case 3:
			conectarDBRemoto();
			if (estado) {
				exportar();
				descontarDBRemoto();
			}
			break;
		case 4:

			conectarDBRemoto();
			if (estado) {
				getLista(lista);
				descontarDBRemoto();
			}
			break;
		default:
			break;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		if (mContext != null) {

			int progreso = values[0].intValue();
			pDialog.setProgress(progreso);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		if (mContext != null) {
			mRegistroActual = 0;
			pDialog.setCancelable(false);
			pDialog.setMessage("Procesando " + mNombreTabla);
			pDialog.setMax(100);
			pDialog.show();
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		if (mConexion != null) {
			if (mContext != null) {
				if (mContext.get() != null) {
					if ((pDialog != null) && pDialog.isShowing()) {
						pDialog.dismiss();
					}
				}
			}
			if (estadoEjecucion == EJECUCION_DEFAULT) {
				mObjetoSync.ejecutarSync();
			}
			if (estadoEjecucion == EJECUCION_CORRECTA) {
				mObjetoSync.ejecucionCorrecta();
			}
			if (estadoEjecucion == EJECUCION_INCORRECTA) {
				mObjetoSync.ejecucionIncorrecta();
			}
		}
	}

	public void getLista(ArrayList<DalusObject> lista) {
	}

	/** @pdOid ecab30c1-16b0-4222-9eaf-bfd20b564be7 */
	public Connection conectarDBRemoto() {

		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				Log.i(Util.APP, "Driver no encotrado");
			}
			if(configuracion==null){
				MicroMan.mTrans = new Transaccion(getmContext());
				MicroMan.mTrans.conectarDB();			
				MicroMan.configuracion = new OnConfiguracion(MicroMan.mTrans).extraer();
				
			}
			Log.i(Util.APP, "INICIALIZACION");
			String connString = "jdbc:mysql://"
					+ configuracion.getSqlserverUrl() + "/zonda";
			// + configuracion.getBaseDatos() + "";
			/*
			 * if (configuracion.getSqlserverInstancia() != null) { connString
			 * += "instance=" + configuracion.getSqlserverInstancia() + ";"; }
			 */Log.i(Util.APP, connString);
			mConexion = DriverManager.getConnection(connString, "zonda",
					"Z0nd4");
			Log.i(Util.APP, "CONECTADO");
		} catch (SQLException e) {
			Log.e(Util.APP, "Conexion error: " + e.getMessage());
			mostrarAdvertencia("Conexion error: " + e.getMessage());
			estado = false;
		}
		return mConexion;
	}

	public boolean importar() {
		return true;
	}

	public void exportar() {

	}

	public boolean resetTablaLocal() {
		boolean resultado = false;
		mTrans.baseDatos.delete(getmNombreTabla(), null, null);
		return resultado;
	}

	public boolean resetTablaServidor() {
		boolean resultado = false;
		try {
			String query = "";// "DELETE FROM " + getmNombreTabla() +
								// " WHERE id_formulario in (SELECT id_formulario FROM formulario WHERE id_agente_sanitario = "+
								// idAS +")";
			Log.i(Util.APP, "Query: " + query);
			PreparedStatement ps = mConexion.prepareStatement(query);

			resultado = ps.execute();
			Log.i(Util.APP, "Eliminando registros tabla "
					+ getmNombreTabla());
		} catch (SQLException e) {
			Log.e(Util.APP, "Conexion error: " + e.getMessage());
			mostrarAdvertencia("Conexion error: " + e.getMessage());

		}

		return resultado;
	}

	public void descontarDBRemoto() {
		try {
			if (!(mConexion == null)) {
				mConexion.close();
				Log.i(Util.APP, "DESCONECTADO");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the mTrans
	 * 
	 * @pdOid bccba401-83c1-4000-a088-22df010cacf3
	 */
	public final Transaccion getmTrans() {
		return mTrans;
	}

	/**
	 * @param mTrans
	 *            the mTrans to set
	 * @pdOid 62f8b1b1-7d48-44d6-8141-e8763f0b5475
	 */
	public final void setmTrans(Transaccion mTrans) {
		this.mTrans = mTrans;
	}

	public Configuracion getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	public void contarRegistros(ResultSet rs) {
		try {
			if (rs.last()) {
				mtotalRegistros = rs.getRow();
				if (mtotalRegistros == 0)
					mtotalRegistros = 100;
				rs.beforeFirst();
			}
			setTotalRegistros(mtotalRegistros);
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
		}
	}

	public Integer actualizarRegistrosImportacion() {
		return mRegistroActual++;
		// return ((mRegistroActual * 100) / mtotalRegistros);
	}

	public Integer actualizarRegistrosExportacion() {
		return mRegistroActual++;
		// return ((mRegistroActual * 100) / mtotalRegistros);
	}

	public void contarRegistrosSqlLite(Cursor cursor) {
		mtotalRegistros = cursor.getCount();
	}

	public void setTotalRegistros(Integer mtotalRegistros) {
		this.mtotalRegistros = mtotalRegistros;
		if (pDialog != null) {
			pDialog.setMax(this.mtotalRegistros);
		}
	}

	public Integer getEstadoEjecucion() {
		return estadoEjecucion;
	}

	public void setEstadoEjecucion(Integer estadoEjecucion) {
		this.estadoEjecucion = estadoEjecucion;
	}

	protected void mostrarAdvertencia(String mensaje) {
		if (mContext != null) {
			Toast.makeText(mContext.get(), mensaje, Toast.LENGTH_LONG);
			// AlertDialog.Builder advertencia = new
			// AlertDialog.Builder(mContext);
			//
			// advertencia.setTitle("Advertencia");
			// advertencia.setMessage(mensaje);
			// advertencia.setCancelable(true);
			// advertencia.show();
			//

		}
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Context getmContext() {
		return mContext.get();
	}

}
