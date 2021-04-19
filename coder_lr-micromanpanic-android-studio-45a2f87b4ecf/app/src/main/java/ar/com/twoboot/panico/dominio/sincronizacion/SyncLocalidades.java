package ar.com.twoboot.panico.dominio.sincronizacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.objetos.Configuracion;

public class SyncLocalidades extends Sincronizador {

	public SyncLocalidades(Configuracion configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla("localidades");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean importar() {
		Boolean resultado = false;
		try {
			resultado = true;
			String sqlQuery = "SELECT * FROM localidades";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			contarRegistros(rs);
			while (rs.next()) {
				Log.i(Util.APP, String.valueOf(rs.getInt(1)));
				valores.put("id_localidad", rs.getInt(1));
				valores.put("localidad", rs.getString(2));
				valores.put("ccoddpto", rs.getString(3));
				valores.put("departamento", rs.getString(4));
				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
				Log.i(Util.APP, valores.toString());
				rtn = mTrans.baseDatos.insert(getmNombreTabla(), null, valores);
				if (rtn > 0) {
					resultado = true;
				} else {
					setEstadoEjecucion(Sincronizador.EJECUCION_INCORRECTA);

					resultado = false;
				}
				if (!resultado) {
					setEstadoEjecucion(Sincronizador.EJECUCION_INCORRECTA);

					return false;
				}
				publishProgress(actualizarRegistrosImportacion());

				Log.i(Util.APP, "INSERTANDO " + rtn);
			}
			stmt.close();
			setEstadoEjecucion(Sincronizador.EJECUCION_CORRECTA);
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
			setEstadoEjecucion(Sincronizador.EJECUCION_INCORRECTA);
		}
		return resultado;
	}

}
