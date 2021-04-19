/***********************************************************************
 * Module:  OnCallejero.java
 * Author:  Torralba
 * Purpose: Defines the Class OnCallejero
 ***********************************************************************/

package ar.com.twoboot.panico.dominio;

import java.util.Date;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.Transaccionable;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.objetos.Callejero;

/** @pdOid 5c8f1ddf-4ecc-4968-b02a-0cfe60fbef01 */
public class OnCallejero extends OnNegocio implements Transaccionable {
	private Callejero callejero;

	public OnCallejero(Transaccion transaccion) {
		super(transaccion);
		setNombreTabla("Callejeros");
	}

	public Callejero getCallejero() {
		return callejero;
	}

	public void setCallejero(Callejero callejero) {
		this.callejero = callejero;
	}

	@Override
	public int insertar() {
		long rtn = 0;

		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("id_calle", callejero.getCalle().getIdCalle());
			valores.put("id_barrio", callejero.getBarrio().getIdBarrio());
			valores.put("estado", callejero.getEstado());
			valores.put("id_usuario", callejero.getIdUsuario());
			valores.put("fecha_carga", Util.formatearFecha(new Date()));
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
		valores.put("estado", callejero.getEstado());
		valores.put("id_usuario", callejero.getIdUsuario());
		valores.put("fecha_carga", Util.formatearFecha(new Date()));
		whereString = "id_calle=" + callejero.getCalle().getIdCalle() + "and id_barrio="
				+ callejero.getBarrio().getIdBarrio();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
		whereString = "id_calle=" + callejero.getCalle().getIdCalle() + "and id_barrio="
				+ callejero.getBarrio().getIdBarrio();
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
	public void eliminarTodo(Context context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void eliminarTodo() {
		// TODO Auto-generated method stub
	}
}