package ar.com.twoboot.panico.dominio;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.Transaccionable;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.objetos.Sistema;

public class OnSistema extends OnNegocio implements Transaccionable {
	private Sistema sistema;

	public Sistema extraer() {
		return sistema;
	}

	public OnSistema(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("sistema");
	}

	public Boolean verificarVersion(String version) {
		String selectQuery = "select version from sistema";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			if (cursor.getString(0).equals(version)) {
				cursor.close();
				return true;
			} else {
				cursor.close();
				return false;
			}
		} else {
			cursor.close();
			return false;
		}
	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("version", sistema.getVersion());
			valores.put("fecha_actual", Util.formatearFecha(new Date()));
			rtn = getTransaccion().baseDatos.insertOrThrow(getNombreTabla(), null, getValores());
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i(Util.APP, getNombreTabla() + "Insertar" + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			getTransaccion().baseDatos.endTransaction();
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e(Util.APP, e.getMessage());
		}
		return (int) rtn;

	}

	@Override
	public int validar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int actualizar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void eliminarTodo(Context context) {
	}

	@Override
	public void eliminarTodo() {
		// TODO Auto-generated method stub
	}

}
