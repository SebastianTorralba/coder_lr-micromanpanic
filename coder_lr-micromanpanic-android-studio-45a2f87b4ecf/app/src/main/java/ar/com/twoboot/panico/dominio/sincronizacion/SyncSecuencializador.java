package ar.com.twoboot.panico.dominio.sincronizacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.objetos.remotos.Secuencializador;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.objetos.Configuracion;

public class SyncSecuencializador extends Sincronizador {
	private Secuencializador secuencializador;

	public Secuencializador getSecuencializador() {
		return secuencializador;
	}

	public void setSecuencializador(Secuencializador secuencializador) {
		this.secuencializador = secuencializador;
	}

	public SyncSecuencializador(Configuracion configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla("Secuencializador");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean importar() {
		Boolean resultado = false;
		try {
			resultado = true;
			String sqlQuery = "SELECT id FROM secuencializadores " + "where secuencializador='"
					+ secuencializador.getSecuencializador() + "'";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			contarRegistros(rs);
			while (rs.next()) {
				Long id = rs.getLong(1);
				id++;
				PreparedStatement stmtUpdate = mConexion
						.prepareStatement("update secuencializadores set id=?" + " where secuencializador=?");
				stmtUpdate.setLong(1, id);
				stmtUpdate.setString(2, secuencializador.getSecuencializador());
				rtn = stmtUpdate.executeUpdate();
				stmtUpdate.close();
				if (rtn > 0) {
					setEstadoEjecucion(EJECUCION_DEFAULT);
					secuencializador.setId(id);
					resultado = true;
				} else {
					secuencializador.setId((long) 0);
					setEstadoEjecucion(EJECUCION_INCORRECTA);
					resultado = false;
				}
				if (!resultado) {
					secuencializador.setId((long) 0);
					setEstadoEjecucion(EJECUCION_INCORRECTA);
					return false;
				}
				publishProgress(actualizarRegistrosImportacion());
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
