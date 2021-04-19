package ar.com.twoboot.panico.dominio;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.dominio.IListable;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.Transaccionable;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.objetos.Respuesta;

public class OnRespuesta extends OnNegocio implements IListable, Transaccionable {
	private Respuesta respuesta;

	public OnRespuesta(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("Respuestas");
	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("cod_respuesta", respuesta.getCodRespuesta());
			valores.put("respuesta", respuesta.getRespuesta());
			rtn = getTransaccion().baseDatos.insertOrThrow(getNombreTabla(), null, getValores());
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", getNombreTabla() + "Insertar" + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			getTransaccion().baseDatos.endTransaction();
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;
	}

	@Override
	public int actualizar() {
		int rtn = 0;
		valores.put("respuesta", respuesta.getRespuesta());
		whereString = "cod_respuesta='" + respuesta.getCodRespuesta() + "'";
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
		whereString = "cod_respuesta='" + respuesta.getCodRespuesta() + "'";
		rtn = getTransaccion().baseDatos.delete(getNombreTabla(), whereString, null);
		Log.i("Microman", getNombreTabla() + "Elminar" + rtn);
		return rtn;
	}

	@Override
	public int validar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<DalusObject> getLista() {
		// TODO Auto-generated method stub
		return null;
	}

	public Respuesta extraer(String codRespuesta) {
		String selectQuery = "select * from respuestas where cod_respuesta='" + codRespuesta + "'";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			this.respuesta = new Respuesta();

			respuesta.setCodRespuesta(Util.quitarNull(cursor.getString(0)));
			respuesta.setRespuesta(Util.quitarNull(cursor.getString(1)));
		}
		cursor.close();
		return respuesta;
	}

	public void eliminarTodo(Context context) {
	}

	@Override
	public void eliminarTodo() {
		// TODO Auto-generated method stub
	}

}
