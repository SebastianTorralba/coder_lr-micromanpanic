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

public class SyncRespuestas extends Sincronizador {

	public SyncRespuestas(Configuracion configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla("respuestas");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean importar() {
		Boolean resultado = false;
		try {
			resultado = true;
			String sqlQuery = "SELECT * FROM respuestas";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			contarRegistros(rs);
			rtn = mTrans.baseDatos.delete(getmNombreTabla(), null, null);
			while (rs.next()) {
				valores.put("cod_respuesta", rs.getString(1));
				valores.put("respuesta", rs.getString(2));
				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
				Log.i(Util.APP, valores.toString());
				rtn = mTrans.baseDatos.insert(getmNombreTabla(), null, valores);
				if (rtn > 0) {
					setEstadoEjecucion(EJECUCION_CORRECTA);
					resultado = true;
				} else {
					setEstadoEjecucion(EJECUCION_INCORRECTA);
					resultado = false;
				}
				if (!resultado) {
					setEstadoEjecucion(EJECUCION_INCORRECTA);
					return false;
				}
				publishProgress(actualizarRegistrosImportacion());
				Log.i(Util.APP, "INSERTANDO " + rtn);
			}
			stmt.close();
		} catch (SQLException e) {
			setEstadoEjecucion(EJECUCION_INCORRECTA);
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		}
		return resultado;
	}

}
