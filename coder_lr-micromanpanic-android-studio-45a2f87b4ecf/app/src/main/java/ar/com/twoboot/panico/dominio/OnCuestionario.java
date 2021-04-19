package ar.com.twoboot.panico.dominio;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.content.ContentValues;
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
import ar.com.twoboot.panico.objetos.Cuestionario;
import ar.com.twoboot.panico.objetos.CuestionarioPreguntas;

public class OnCuestionario extends OnNegocio implements Transaccionable, IListable {
	public static final int ESTADO_TRANSMITIDO = 2;
	public static final int ESTADO_DB_LOCAL_CON_CONEXION = 1;
	public static final int ESTADO_DB_LOCAL_SIN_CONEXION = 0;

	public OnCuestionario(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("cuestionario");
	}

	public long getSiguienteId() {
		long id = 0;
		String selectQuery = "select max(id_cuestionario) from cuestionario where estado in (0,1)";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			id = cursor.getLong(0);
			id++;
		}
		cursor.close();
		return id;
	}

	private Cuestionario cuestionario = null;

	public Cuestionario extraer(Integer idCuestionario) {
		String selectQuery = "select * from cuestionario where id_cuestionario=" + "" + idCuestionario;
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			this.cuestionario = new Cuestionario();
			cuestionario.setIdCuestionario(Util.quitarNull(cursor.getInt(0)));
			cuestionario.setBarrios(new OnBarrio(getTransaccion()).extraer(Util.quitarNull(cursor.getInt(1))));
			cuestionario.setCalles(new OnCalle(getTransaccion()).extraer(Util.quitarNull(cursor.getInt(2))));
			cuestionario.setAltura(Util.quitarNull(cursor.getString(3)));
			cuestionario.setGpsLatitud(Util.quitarNull(cursor.getDouble(4)));
			cuestionario.setGpsLongitud(Util.quitarNull(cursor.getDouble(5)));
			cuestionario.setFotoArchivo(Util.quitarNull(cursor.getString(6)));
			cuestionario.setEstado(Util.quitarNull(cursor.getInt(7)));
			cuestionario.setIdUsuario(Util.quitarNull(cursor.getInt(8)));
			cuestionario.setFechaCarga(Util.fomtaearFecha(Util.quitarNull(cursor.getString(9))));
			cuestionario.setBarrios(new OnBarrio(getTransaccion()).extraer(cursor.getInt(1)));
			cuestionario.setCalles(new OnCalle(getTransaccion()).extraer(cursor.getInt(2)));
			new OnCuestionarioPreguntas(getTransaccion()).extraer(cuestionario);
			// Falta extraer la Foto
		}
		cursor.close();
		return cuestionario;
	}

	@Override
	public ArrayList<DalusObject> getLista() {
		ArrayList<DalusObject> cuestionarios = new ArrayList<DalusObject>();
		String selectQuery = "select * from cuestionario";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Cuestionario c = new Cuestionario();
				c.setIdCuestionario(Util.quitarNull(cursor.getInt(0)));
				c.setBarrios(new OnBarrio(getTransaccion()).extraer(Util.quitarNull(cursor.getInt(1))));
				c.setCalles(new OnCalle(getTransaccion()).extraer(Util.quitarNull(cursor.getInt(2))));
				c.setAltura(Util.quitarNull(cursor.getString(3)));
				c.setGpsLatitud(Util.quitarNull(cursor.getDouble(4)));
				c.setGpsLongitud(Util.quitarNull(cursor.getDouble(5)));
				c.setFotoArchivo(Util.quitarNull(cursor.getString(6)));
				c.setEstado(Util.quitarNull(cursor.getInt(7)));
				c.setIdUsuario(Util.quitarNull(cursor.getInt(8)));
				c.setFechaCarga(Util.fomtaearFecha(Util.quitarNull(cursor.getString(9))));
				c.setBarrios(new OnBarrio(getTransaccion()).extraer(cursor.getInt(1)));
				c.setCalles(new OnCalle(getTransaccion()).extraer(cursor.getInt(2)));
				c.setCuestionarioPreguntas(new OnCuestionarioPreguntas(getTransaccion()).extraer(c));
				cuestionarios.add(c);
			} while (cursor.moveToNext());
			// Falta extraer la Foto
		}
		cursor.close();
		return cuestionarios;
	}

	@Override
	public ArrayList<?> getListado() {
		ArrayList<Cuestionario> cuestionarios = new ArrayList<Cuestionario>();
		String selectQuery = "select * from cuestionario where estado in (0,1)";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Cuestionario c = new Cuestionario();
				c.setIdCuestionario(Util.quitarNull(cursor.getInt(0)));
				c.setBarrios(new OnBarrio(getTransaccion()).extraer(Util.quitarNull(cursor.getInt(1))));
				c.setCalles(new OnCalle(getTransaccion()).extraer(Util.quitarNull(cursor.getInt(2))));
				c.setAltura(Util.quitarNull(cursor.getString(3)));
				c.setGpsLatitud(Util.quitarNull(cursor.getDouble(4)));
				c.setGpsLongitud(Util.quitarNull(cursor.getDouble(5)));
				c.setFotoArchivo(Util.quitarNull(cursor.getString(6)));
				c.setEstado(Util.quitarNull(cursor.getInt(7)));
				c.setIdUsuario(Util.quitarNull(cursor.getInt(8)));
				c.setFechaCarga(Util.fomtaearFecha(Util.quitarNull(cursor.getString(9))));
				c.setBarrios(new OnBarrio(getTransaccion()).extraer(cursor.getInt(1)));
				c.setCalles(new OnCalle(getTransaccion()).extraer(cursor.getInt(2)));
				c.setCuestionarioPreguntas(new OnCuestionarioPreguntas(getTransaccion()).extraer(c));
				cuestionarios.add(c);
			} while (cursor.moveToNext());
			// Falta extraer la Foto
		}
		cursor.close();
		return cuestionarios;
	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			// aca ver como obtener el numero
			valores.put("id_cuestionario", cuestionario.getIdCuestionario());
			if (cuestionario.getBarrios() != null) {
				valores.put("id_barrio", cuestionario.getBarrios().getIdBarrio());
			}
			if (cuestionario.getCalles() != null) {

				valores.put("id_calle", cuestionario.getCalles().getIdCalle());
			}
			valores.put("altura", cuestionario.getAltura());
			valores.put("gpslatitud", cuestionario.getGpsLatitud());
			valores.put("gpslongitud", cuestionario.getGpsLatitud());
			valores.put("foto_Archivo", cuestionario.getFotoArchivo());
			valores.put("estado", cuestionario.getEstado());
			valores.put("id_usuario", cuestionario.getIdUsuario());
			valores.put("fecha_carga", Util.formatearFecha(new Date()));
			rtn = getTransaccion().baseDatos.insertOrThrow(getNombreTabla(), null, getValores());
			if (rtn > 0) {
				OnCuestionarioPreguntas onCuestionarioPreguntas = new OnCuestionarioPreguntas(getTransaccion());
				Iterator<CuestionarioPreguntas> cp = cuestionario.getCuestionarioPreguntas().iterator();
				while (cp.hasNext()) {
					CuestionarioPreguntas cuestionarioPregunta = (CuestionarioPreguntas) cp.next();
					onCuestionarioPreguntas.setCuestionarioPregunta(cuestionarioPregunta);
					onCuestionarioPreguntas.insertar();
				}
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
		valores.put("id_barrio", cuestionario.getBarrios().getIdBarrio());
		valores.put("id_calle", cuestionario.getCalles().getIdCalle());
		valores.put("altura", cuestionario.getAltura());
		valores.put("gpslatitud", cuestionario.getGpsLatitud());
		valores.put("gpslongitud", cuestionario.getGpsLatitud());
		valores.put("foto_archivo", cuestionario.getFotoArchivo());
		valores.put("estado", cuestionario.getEstado());
		valores.put("id_usuario", cuestionario.getIdUsuario());
		valores.put("fecha_carga", Util.formatearFecha(new Date()));
		whereString = "id_cuestionario=" + cuestionario.getIdCuestionario();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	public int actualizarEstado() {
		int rtn = 0;
		valores = new ContentValues();
		valores.put("estado", cuestionario.getEstado());
		whereString = "id_cuestionario=" + cuestionario.getIdCuestionario();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	public int actualizarIdRemoto(long id_cuestionario_remoto) {
		int rtn = 0;
		valores = new ContentValues();
		valores.put("id_cuestionario", id_cuestionario_remoto);
		whereString = "id_cuestionario=" + cuestionario.getIdCuestionario();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		if (rtn > 0) {
			rtn = getTransaccion().baseDatos.update("cuestionario_preguntas", valores, whereString, null);
			Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		}
		cuestionario.setIdCuestionario(id_cuestionario_remoto);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;

		OnCuestionarioPreguntas oCuestionarioPreguntas = new OnCuestionarioPreguntas(getTransaccion());
		rtn = oCuestionarioPreguntas.eliminar(cuestionario);
		if (rtn > 0) {
			whereString = "id_cuestionario=" + cuestionario.getIdCuestionario();
			rtn = getTransaccion().baseDatos.delete(getNombreTabla(), whereString, null);
			Log.i("Microman", getNombreTabla() + "Elminar" + rtn);
		}
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
		int rtn = 0;
		rtn = getTransaccion().baseDatos.delete(getNombreTabla(), null, null);
		Log.i("Microman", getNombreTabla() + "Elminar" + rtn);
		OnCuestionarioPreguntas onCuestionarioPreguntas = new OnCuestionarioPreguntas(getTransaccion());
		onCuestionarioPreguntas.eliminarTodo();
	}

	public File cargarArchivoFoto() {
		File imagenArchivo = null;
		String fotoArchivo = cuestionario.getFotoArchivo();
		if (fotoArchivo != null && fotoArchivo.length() > 0) {
			imagenArchivo = new File(fotoArchivo);
		}
		return imagenArchivo;
	}

	public Boolean borrarArchivoFoto(String archivo) {
		Boolean rtn = false;
		File file = new File(archivo);
		rtn = file.delete();
		return rtn;
	}

	public String generarNombreFoto() {
		String nombreFoto;
		nombreFoto = "Zimg_" + Util.formatearFechaArchivo(new Date()) + ".jpg";
		return nombreFoto;
	}

	public void setCuestionario(Cuestionario cuestionario) {
		// TODO Auto-generated method stub7
		this.cuestionario = cuestionario;
	}
}
