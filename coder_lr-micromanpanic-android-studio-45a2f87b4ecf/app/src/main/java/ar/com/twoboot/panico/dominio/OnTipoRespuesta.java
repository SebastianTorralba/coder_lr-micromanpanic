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
import ar.com.twoboot.panico.objetos.TipoRespuesta;

public class OnTipoRespuesta extends OnNegocio implements IListable, Transaccionable {
	private TipoRespuesta tipoRespuesta;

	public TipoRespuesta extraer(String codTipoRespuesta) {
		String selectQuery = "select * from tipo_Repuesta where cod_tipo_respuesta='" + codTipoRespuesta + "'";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			this.tipoRespuesta = new TipoRespuesta();
			tipoRespuesta.setCodTipoRespuesta(Util.quitarNull(cursor.getString(0)));
			tipoRespuesta.setTipoRespuesta(Util.quitarNull(cursor.getString(1)));
		}
		cursor.close();
		return tipoRespuesta;
	}

	public OnTipoRespuesta(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("tipo_repuesta");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("cod_tipo_Respuesta", tipoRespuesta.getCodTipoRespuesta());
			valores.put("tipo_Respuesta", tipoRespuesta.getTipoRespuesta());
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
		valores.put("tipo_Respuesta", tipoRespuesta.getTipoRespuesta());
		whereString = "cod_tipo_Respuesta='" + tipoRespuesta.getCodTipoRespuesta() + "'";
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
		whereString = "cod_tipo_Respuesta='" + tipoRespuesta.getCodTipoRespuesta() + "'";
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

	public void eliminarTodo(Context context) {
	}

	@Override
	public void eliminarTodo() {
		// TODO Auto-generated method stub
	}

}
