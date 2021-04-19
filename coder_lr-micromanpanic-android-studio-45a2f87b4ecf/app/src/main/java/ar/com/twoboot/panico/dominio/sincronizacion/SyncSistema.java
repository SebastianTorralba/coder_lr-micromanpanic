package ar.com.twoboot.panico.dominio.sincronizacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.dominio.OnSistema;
import ar.com.twoboot.panico.objetos.Configuracion;
import ar.com.twoboot.panico.objetos.Sistema;

public class SyncSistema extends Sincronizador {
	public SyncSistema(Configuracion configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla("sistema");
		sistemaActual = new OnSistema(mTrans).extraer();
		// TODO Auto-generated constructor stub
	}

	private Sistema sistemaActual;
	private Sistema sistemaDescarga;

	@Override
	public boolean importar() {
		try {
			resultado = true;
			String sqlQuery = "SELECT * FROM sistema";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			mTrans.baseDatos.beginTransaction();
			while (rs.next()) {
				Log.i(Util.APP, String.valueOf(rs.getInt(1)));
				sistemaDescarga = new Sistema();
				sistemaDescarga.setVersion(rs.getString(1));
				if (sistemaActual.equals(sistemaDescarga)) {
					valores.put("version", rs.getString(1));
					valores.put("fecha_actual", Util.formatearFecha(rs.getDate(2)));
					setTotalRegistros(1);
					Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
					Log.i(Util.APP, valores.toString());
					// actualizarSqlServer();
					rtn = mTrans.baseDatos.insert(getmNombreTabla(), null, valores);
					if (rtn > 0) {
						mTrans.baseDatos.setTransactionSuccessful();
					}
					mTrans.baseDatos.endTransaction();
				} else {

					mTrans.baseDatos.endTransaction();
					// aca lanzar aviso de Actualizacion;
					resultado = false;
				}
				Log.i(Util.APP, "INSERTANDO " + rtn);
			}
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		}
		return resultado;
	}

}
