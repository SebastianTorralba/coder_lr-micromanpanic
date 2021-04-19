/***********************************************************************
 * Module:  OnLocalidad.java
 * Author:  Torralba
 * Purpose: Defines the Class OnLocalidad
 ***********************************************************************/

package ar.com.twoboot.panico.dominio;

import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;
import ar.com.twoboot.microman.dominio.IListable;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.objetos.Localidad;

/** @pdOid 7baf1f7c-033c-47fd-9d4f-162f64699ebe */
public class OnLocalidad extends OnNegocio implements IListable {
	public OnLocalidad(Transaccion transaccion) {
		super(transaccion);
		localidad = new Localidad();
		setNombreTabla("Localidades");
		// TODO Auto-generated constructor stub
	}

	/** @pdOid cbe6e632-d25b-495c-803c-70b978ab541c */
	private Localidad localidad;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.com.twoboot.dalus.dominio.OnNegocio#getListado()
	 */
	@Override
	public ArrayList<?> getListado() {
		ArrayList<DalusObject> localidades = new ArrayList<DalusObject>();
		String selectQuery = "select * from Localidades";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Localidad l = new Localidad();
				l.setIdLocalidad(cursor.getInt(0));
				l.setLocalidad(cursor.getString(1));
				localidades.add(l);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return localidades;

	}

	@Override
	public ArrayList<DalusObject> getLista() {
		ArrayList<DalusObject> localidades = new ArrayList<DalusObject>();
		String selectQuery = "select * from Localidades";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Localidad l = new Localidad();
				l.setIdLocalidad(cursor.getInt(0));
				l.setLocalidad(cursor.getString(1));
				localidades.add(l);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return localidades;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Localidad extraer(Integer idLocalidad) {
		String selectQuery = "select * from Localidades where id_localidad=" + idLocalidad;
		Log.i("DALUS LOC", selectQuery);
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			localidad.setIdLocalidad(Util.quitarNull(cursor.getInt(0)));
			localidad.setLocalidad(Util.quitarNull(cursor.getString(1)));
			localidad.setCcoddpto(Util.quitarNull(cursor.getString(2)));
			localidad.setDepartamento(Util.quitarNull(cursor.getString(3)));
		}
		return localidad;
	}

}