package ar.com.twoboot.panico.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.Transaccionable;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.objetos.Usuario;

public class OnUsuario extends OnNegocio implements Transaccionable {
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public OnUsuario(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("Usuarios");
	}

	public Usuario extraer(Integer idUsuario) {
		String selectQuery = "select * from usuarios where id_usuario=" + idUsuario;
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			usuario = new Usuario();
			usuario.setIdUsuario(cursor.getInt(0));
			usuario.setUsuario(cursor.getString(1));
			usuario.setClave(cursor.getString(2));
			usuario.setImei(cursor.getString(3));
			usuario.setApellidoNombre(cursor.getString(4));
		} else {
			return null;
		}
		cursor.close();
		return usuario;
	}

	public Usuario extraer(String usuario, String clave) {
		String selectQuery = "select * from usuarios where usuario='" + usuario + "'" + " and clave='" + clave + "'";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			this.usuario = new Usuario();
			this.usuario.setIdUsuario(cursor.getInt(0));
			this.usuario.setUsuario(cursor.getString(1));
			this.usuario.setClave(cursor.getString(2));
			this.usuario.setImei(cursor.getString(3));
			this.usuario.setApellidoNombre(cursor.getString(4));
		} else {
			return null;
		}
		cursor.close();
		return this.usuario;
	}

	public boolean autorizarUsuario(String usuario, String clave) {
		String selectQuery = "select id_Usuario from usuarios where usuario='" + usuario + "'" + " and clave='" + clave
				+ "'";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			if (cursor.getInt(0) > 0) {
				extraer(cursor.getInt(0));
				return true;
			}
		} else {
			return false;
		}
		cursor.close();
		return false;
	}

	public boolean confirmarContrasenia(String clave, String claveConfirmacion) {
		if (clave.equals(claveConfirmacion)) {
			return true;
		}
		return false;
	}

	public Usuario comprobarAutoLogin() {
		String selectQuery = "select * from usuarios where estado=1";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		Usuario usuarioSesion;
		if (cursor.moveToFirst()) {
			usuarioSesion = new Usuario();
			usuarioSesion.setIdUsuario(cursor.getInt(0));
			usuarioSesion.setUsuario(cursor.getString(1));
			usuarioSesion.setClave(cursor.getString(2));
			usuarioSesion.setImei(cursor.getString(3));
			usuarioSesion.setApellidoNombre(cursor.getString(4));

		} else {
			return null;
		}
		cursor.close();

		return usuarioSesion;
	}

	public int resetEstado() {
		int rtn = 0;
		valores = new ContentValues();
		valores.put("estado", 0);
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, null, null);
		Log.i(Util.APP, getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	public int actualizarEstado() {
		int rtn = 0;
		valores = new ContentValues();
		valores.put("estado", usuario.getEstado());
		whereString = "usuario='" + usuario.getUsuario() + "'";
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i(Util.APP, getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	public int actualizarEstado(int estado) {
		int rtn = 0;
		valores = new ContentValues();
		valores.put("estado", estado);
		whereString = "id_usuario=" + usuario.getIdUsuario();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i(Util.APP, getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int insertar() {
		long rtn = 0;

		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("id_usuario", usuario.getIdUsuario());
			valores.put("usuario", usuario.getUsuario());
			valores.put("clave", usuario.getClave());
			valores.put("imei", usuario.getImei());
			valores.put("apellido_nombre", usuario.getApellidoNombre());
			valores.put("estado", usuario.getEstado());
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
	public int actualizar() {
		int rtn = 0;
		valores.put("usuario", usuario.getUsuario());
		valores.put("clave", usuario.getClave());
		valores.put("imei", usuario.getImei());
		valores.put("apellido_nombre", usuario.getApellidoNombre());
		valores.put("estado", usuario.getEstado());
		whereString = "id_usuario=" + usuario.getIdUsuario();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i(Util.APP, getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
		whereString = "id_usuario=" + usuario.getIdUsuario();
		rtn = getTransaccion().baseDatos.delete(getNombreTabla(), whereString, null);
		Log.i(Util.APP, getNombreTabla() + "Elminar" + rtn);
		return rtn;
	}

	@Override
	public int validar() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void eliminarTodo(Context context) {
	}

	public static boolean verificarRegistracion(Transaccion transaccion) {
		String selectQuery = "select id_Usuario from usuarios where id_usuario>1";
		Cursor cursor = transaccion.baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			if (cursor.getInt(0) > 0) {
				return true;
			}
		} else {
			return false;
		}
		cursor.close();
		return false;
	}

	@Override
	public void eliminarTodo() {
		// TODO Auto-generated method stub
	}

}
