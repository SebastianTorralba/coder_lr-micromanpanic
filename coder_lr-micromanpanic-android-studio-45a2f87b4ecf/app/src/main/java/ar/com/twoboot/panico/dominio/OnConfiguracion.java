package ar.com.twoboot.panico.dominio;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.Transaccionable;
import ar.com.twoboot.panico.objetos.Configuracion;

public class OnConfiguracion extends OnNegocio implements Transaccionable {
	public OnConfiguracion(Transaccion transaccion) {
		super(transaccion);
		// TODO Auto-generated constructor stub
	}

	private Configuracion configuracion;

	/**
	 * @return the configuracion
	 */
	public final Configuracion getConfiguracion() {
		return configuracion;
	}

	/**
	 * @param configuracion
	 *            the configuracion to set
	 */
	public final void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	@Override
	public int insertar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int actualizar() {
		valores.put("usuario", configuracion.getUsuario());
		valores.put("password", configuracion.getPassword());
		valores.put("SqlServer_Url", configuracion.getSqlserverUrl());
		valores.put("SqlServer_instancia", configuracion.getSqlserverInstancia());
		valores.put("ftp_url", configuracion.getFtpUrl());
		valores.put("cantidad_cuestionarios", configuracion.getCantidadCuestionarios());
		valores.put("id_cuestionario_app", configuracion.getIdCuestionarioApp());
		valores.put("id_cuestionario_bd", configuracion.getIdCuestionarioBd());
		valores.put("protocolo", configuracion.getProtocolo());
		valores.put("usuario_rest", configuracion.getUsuario_rest());
		valores.put("clave_rest", configuracion.getPassword_rest());
		valores.put("resturl", configuracion.getRestserverUrl());
		return getTransaccion().baseDatos.update("configuracion", valores, null, null);

	}

	@Override
	public int eliminar() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Integer getFormularioSecuencia() {
		Integer secuencia = 0;
		String selectQuery = "select id_formulario_base from configuracion";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i("DALUS BRIDGE", "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			secuencia = cursor.getInt(0);
			secuencia++;
			// configuracion.setIdFormularioBase(secuencia);
		}
		cursor.close();

		valores.put("id_formulario_base", secuencia);

		int rtn = getTransaccion().baseDatos.update("configuracion", valores, null, null);

		return secuencia;
	}

	public Configuracion extraer() {
		configuracion = new Configuracion();

		String selectQuery = "select * from configuracion";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i("DALUS BRIDGE", "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			configuracion.setUsuario(cursor.getString(0));
			configuracion.setPassword(cursor.getString(1));
			configuracion.setSqlserverUrl(cursor.getString(2));
			configuracion.setSqlserverInstancia(cursor.getString(3));
			configuracion.setFtpUrl(cursor.getString(4));
			configuracion.setCantidadCuestionarios(cursor.getInt(5));
			configuracion.setIdCuestionarioApp(cursor.getInt(6));
			configuracion.setIdCuestionarioBd(cursor.getInt(7));
			configuracion.setProtocolo(cursor.getInt(8));
			configuracion.setUsuario_rest(cursor.getString(9));
			configuracion.setPassword_rest(cursor.getString(10));
			configuracion.setRestserverUrl(cursor.getString(11));
		} else {
			return null;
		}
		cursor.close();

		return configuracion;
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
