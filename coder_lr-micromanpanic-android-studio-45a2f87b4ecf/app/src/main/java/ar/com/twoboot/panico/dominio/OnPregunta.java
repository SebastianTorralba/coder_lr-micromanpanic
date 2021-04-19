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
import ar.com.twoboot.panico.objetos.Pregunta;
import ar.com.twoboot.panico.objetos.Respuesta;

public class OnPregunta extends OnNegocio implements IListable, Transaccionable {
	// ver como resulver para las respuestas posibles
	private Pregunta pregunta = null;

	private ArrayList<Respuesta> extraerRespuestasPosibles() {
		String selectQuery = "select cod_respuesta from pregunta_respuestas where id_pregunta="
				+ pregunta.getIdPregunta();
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Respuesta rta = new Respuesta();
				rta = (new OnRespuesta(getTransaccion()).extraer(Util.quitarNull(cursor.getString(0))));
				pregunta.addrespuestasPregunta(rta);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return (ArrayList<Respuesta>) pregunta.getrespuestasPregunta();
	}

	public Pregunta extraer(Integer idPregunta) {
		String selectQuery = "select * from preguntas where id_pregunta=" + idPregunta;
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			this.pregunta = new Pregunta();
			pregunta.setIdPregunta(Util.quitarNull(cursor.getInt(0)));
			pregunta.setPregunta(Util.quitarNull(cursor.getString(1)));
			pregunta.setCategorias(new OnCategoria(getTransaccion()).extraer(Util.quitarNull(cursor.getString(2))));
			pregunta.setTipoRepuesta(
					new OnTipoRespuesta(getTransaccion()).extraer(Util.quitarNull(cursor.getString(3))));
			pregunta.setRespuestaDefecto(
					new OnRespuesta(getTransaccion()).extraer(Util.quitarNull(cursor.getString(4))));
			pregunta.setEsExtendible(Util.quitarNull(cursor.getInt(5)));
			extraerRespuestasPosibles();
			// ver como extraer respuestas de la pregunta;
		}
		cursor.close();
		return pregunta;
	}

	public OnPregunta(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("Preguntas");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("id_pregunta", pregunta.getIdPregunta());
			valores.put("pregunta", pregunta.getPregunta());
			valores.put("cod_categoria", pregunta.getCategorias().getCodCategoria());
			valores.put("cod_tipo_respuesta", pregunta.getTipoRepuesta().getCodTipoRespuesta());
			valores.put("cod_respuesta_defecto", pregunta.getRespuestaDefecto().getCodRespuesta());
			valores.put("es_extensible", pregunta.getEsExtendible());
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
		valores.put("pregunta", pregunta.getPregunta());
		valores.put("cod_categoria", pregunta.getCategorias().getCodCategoria());
		valores.put("cod_tipo_respuesta", pregunta.getTipoRepuesta().getCodTipoRespuesta());
		valores.put("cod_respuesta_defecto", pregunta.getRespuestaDefecto().getCodRespuesta());
		valores.put("es_extensible", pregunta.getEsExtendible());
		whereString = "id_pregunta=" + pregunta.getIdPregunta();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
		whereString = "id_pregunta=" + pregunta.getIdPregunta();
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

	@Override
	public void eliminarTodo(Context context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void eliminarTodo() {
		// TODO Auto-generated method stub
	}

}
