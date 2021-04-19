/***********************************************************************
 * Module:  OnCalle.java
 * Author:  Torralba
 * Purpose: Defines the Class OnCalle
 ***********************************************************************/

package ar.com.twoboot.panico.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import ar.com.twoboot.panico.objetos.Barrio;
import ar.com.twoboot.panico.objetos.Calle;

/** @pdOid 2ed46a53-cd45-4c72-93b9-f70825b9e321 */
public class OnCalle extends OnNegocio implements Transaccionable, IListable {
	public OnCalle(Transaccion transaccion) {
		super(transaccion);
		setNombreTabla("calles");
	}

	/** @pdOid dc3b4e82-7b6e-4235-ba32-4b68f2652d14 */
	private Calle calle;

	@Override
	public ArrayList<DalusObject> getLista() {
		// TODO Auto-generated method stub
		return null;
	}

	public Calle getCalle() {
		return calle;
	}

	public void setCalle(Calle calle) {
		this.calle = calle;
	}

	public List<Calle> getLista(Barrio b) {
		ArrayList<Calle> calles = new ArrayList<Calle>();
		String selectQuery = "select c.id_calle AS _id, nombre "
				+ "from calles c, callejeros ca where c.id_calle=ca.id_calle " + "and ca.id_calle=" + b.getIdBarrio()
				+ " order by 2";

		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Calle c = new Calle();
				c.setIdCalle(cursor.getInt(0));
				c.setNombre(cursor.getString(1));
				calles.add(c);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return calles;

	}

	public Calle extraer(Integer idCalle) {
		String selectQuery = "select id_calle,nombre " + "from calles where id_calle=" + idCalle;
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			this.calle = new Calle();
			calle.setIdCalle(Util.quitarNull(cursor.getInt(0)));
			calle.setNombre(Util.quitarNull(cursor.getString(1)));
		}
		cursor.close();
		return calle;

	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("id_calle", calle.getIdCalle());
			valores.put("nombre", calle.getNombre());
			valores.put("estado", calle.getEstado());
			valores.put("id_usuario", calle.getIdUsuario());
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
		valores.put("nombre", calle.getNombre());
		valores.put("estado", calle.getEstado());
		valores.put("id_usuario", calle.getIdUsuario());
		valores.put("fecha_carga", Util.formatearFecha(new Date()));
		whereString = "id_calle=" + calle.getIdCalle();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
		whereString = "id_calle=" + calle.getIdCalle();
		rtn = getTransaccion().baseDatos.delete(getNombreTabla(), whereString, null);
		Log.i("Microman", getNombreTabla() + "Elminar" + rtn);
		return rtn;
	}

	@Override
	public int validar() {
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