/***********************************************************************
 * Module:  OnBarrio.java
 * Author:  Torralba
 * Purpose: Defines the Class OnBarrio
 ***********************************************************************/

package ar.com.twoboot.panico.dominio;

import java.util.ArrayList;
import java.util.Date;

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
import ar.com.twoboot.panico.objetos.Localidad;

/** @pdOid 8c65c1f4-e9cf-4239-9695-c3624650a591 */
public class OnBarrio extends OnNegocio implements Transaccionable, IListable {
	public OnBarrio(Transaccion transaccion) {
		super(transaccion);
		setNombreTabla("Barrios");
	}

	/** @pdOid cd81488a-4996-405b-ae19-5379b5a67e63 */
	private Barrio barrio;

	@Override
	public ArrayList<DalusObject> getLista() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<?> getListado() {
		ArrayList<Barrio> barrios = new ArrayList<Barrio>();
		String selectQuery = "select id_barrio AS _id, nombre from barrios where id_localidad=" + "100100"
				+ " order by 2";

		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Barrio b = new Barrio();
				b.setIdBarrio(cursor.getInt(0));
				b.setNombre(cursor.getString(1));
				barrios.add(b);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return barrios;
	}

	public ArrayList<Barrio> getLista(Localidad l) {
		ArrayList<Barrio> barrios = new ArrayList<Barrio>();
		String selectQuery = "select id_barrio AS _id, nombre from barrios where id_localidad=" + l.getIdLocalidad()
				+ " order by 2";

		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Barrio b = new Barrio();
				b.setIdBarrio(cursor.getInt(0));
				b.setNombre(cursor.getString(1));
				barrios.add(b);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return barrios;
	}

	@Override
	public int insertar() {
		long rtn = 0;

		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("id_barrio", barrio.getIdBarrio());
			valores.put("nombre", barrio.getNombre());
			valores.put("estado", barrio.getEstado());
			valores.put("id_localidad", barrio.getLocalidades().getIdLocalidad());
			valores.put("id_usuario", barrio.getIdUsuario());
			valores.put("fecha_carga", Util.formatearFecha(new Date()));
			valores.put("cant_manzanas", barrio.getIdBarrio());
			valores.put("zona", barrio.getZona());
			rtn = getTransaccion().baseDatos.insertOrThrow(getNombreTabla(), null, getValores());

			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "INSERTANDO " + rtn);
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
		valores.put("nombre", barrio.getNombre());
		valores.put("estado", barrio.getEstado());
		valores.put("id_localidad", barrio.getLocalidades().getIdLocalidad());
		valores.put("id_usuario", barrio.getIdUsuario());
		valores.put("fecha_carga", Util.formatearFecha(new Date()));
		valores.put("cant_manzanas", barrio.getIdBarrio());
		valores.put("zona", barrio.getZona());
		valores.put("fecha_carga", Util.formatearFecha(new Date()));
		whereString = "id_barrio=" + barrio.getIdBarrio();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Retorno" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
		whereString = "id_barrio=" + barrio.getIdBarrio();

		rtn = getTransaccion().baseDatos.delete(getNombreTabla(), whereString, null);
		Log.i("DALUS UPDATE", getNombreTabla() + "Retorno" + rtn);
		return rtn;
	}

	public Barrio extraer(Integer IdBarrio) {
		// TODO Auto-generated method stub
		String selectQuery = "select * from barrios where id_barrio=" + "" + IdBarrio;
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			this.barrio = new Barrio();

			barrio.setIdBarrio(Util.quitarNull(cursor.getInt(0)));
			barrio.setNombre(Util.quitarNull(cursor.getString(1)));
		}
		cursor.close();

		return barrio;
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