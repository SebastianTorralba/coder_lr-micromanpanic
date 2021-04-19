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
import ar.com.twoboot.panico.objetos.Cuestionario;
import ar.com.twoboot.panico.objetos.CuestionarioPreguntas;
import ar.com.twoboot.panico.objetos.Respuesta;

public class OnCuestionarioPreguntas extends OnNegocio implements IListable, Transaccionable {
	private ArrayList<CuestionarioPreguntas> cuestionarioPreguntasLista;
	private CuestionarioPreguntas cuestionarioPregunta;

	public ArrayList<CuestionarioPreguntas> extraer(Cuestionario cuestionario) {
		String selectQuery = "select * from cuestionario_preguntas where id_cuestionario=" + ""
				+ cuestionario.getIdCuestionario();
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		cuestionarioPreguntasLista = new ArrayList<CuestionarioPreguntas>();
		if (cursor.moveToFirst()) {
			do {
				CuestionarioPreguntas cp = new CuestionarioPreguntas();
				cp.setPregunta(new OnPregunta(getTransaccion()).extraer(cursor.getInt(1)));
				if (cp.getPregunta().getTipoRepuesta().equals("COMBOBOX")) {
					cp.setRespuesta(new OnRespuesta(getTransaccion()).extraer(cursor.getString(2)));
				} else {
					cp.setRespuesta(new Respuesta(cursor.getString(2)));
				}
				cp.setExtension(cursor.getString(3));
				cp.setCuestionario(cuestionario);
				cuestionarioPreguntasLista.add(cp);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return cuestionarioPreguntasLista;
	}

	public OnCuestionarioPreguntas(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("Cuestionario_preguntas");
	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("id_cuestionario", cuestionarioPregunta.getCuestionario().getIdCuestionario());
			valores.put("id_pregunta", cuestionarioPregunta.getPregunta().getIdPregunta());
			valores.put("cod_respuesta", cuestionarioPregunta.getRespuesta().getCodRespuesta());
			valores.put("extension", cuestionarioPregunta.getExtension());
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
		valores.put("cod_respuesta", cuestionarioPregunta.getRespuesta().getCodRespuesta());
		valores.put("extension", cuestionarioPregunta.getExtension());
		whereString = "id_cuestionario=" + cuestionarioPregunta.getCuestionario().getIdCuestionario()
				+ " and id_pregunta=" + cuestionarioPregunta.getPregunta().getIdPregunta();
		rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores, whereString, null);
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
		whereString = "id_cuestionario=" + cuestionarioPregunta.getCuestionario().getIdCuestionario()
				+ " and id_pregunta=" + cuestionarioPregunta.getPregunta().getIdPregunta();
		rtn = getTransaccion().baseDatos.delete(getNombreTabla(), whereString, null);
		Log.i("Microman", getNombreTabla() + "Elminar" + rtn);
		return rtn;
	}

	public int eliminar(Cuestionario cuestionario) {
		int rtn = 0;
		whereString = "id_cuestionario=" + cuestionario.getIdCuestionario();
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

	public CuestionarioPreguntas getCuestionarioPregunta() {
		return cuestionarioPregunta;
	}

	public void setCuestionarioPregunta(CuestionarioPreguntas cuestionarioPregunta) {
		this.cuestionarioPregunta = cuestionarioPregunta;
	}

	public void eliminarTodo(Context context) {
	}

	@Override
	public void eliminarTodo() {
		int rtn = 0;
		rtn = getTransaccion().baseDatos.delete(getNombreTabla(), null, null);
		Log.i("Microman", getNombreTabla() + "Elminar" + rtn);

	}

}
