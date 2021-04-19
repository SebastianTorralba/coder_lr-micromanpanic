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
import ar.com.twoboot.panico.objetos.CuestionarioModelo;

public class OnCuestionarioModelo extends OnNegocio implements IListable, Transaccionable {
	private CuestionarioModelo cuestionarioModelo;
	private ArrayList<CuestionarioModelo> cuestionarioModeloLista = null;

	public ArrayList<CuestionarioModelo> extraer() {
		String selectQuery = "select * from cuestionario_modelo ";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		cuestionarioModeloLista = new ArrayList<CuestionarioModelo>();
		if (cursor.moveToFirst()) {
			do {
				CuestionarioModelo cm = new CuestionarioModelo();
				cm.setSecuencia(Util.quitarNull(cursor.getInt(0)));
				cm.setPreguntas(new OnPregunta(getTransaccion()).extraer(Util.quitarNull(cursor.getInt(1))));
				cuestionarioModeloLista.add(cm);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return cuestionarioModeloLista;
	}

	public OnCuestionarioModelo(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("CuestionarioModelo");
	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("secuencia", cuestionarioModelo.getSecuencia());
			valores.put("id_pregunta", cuestionarioModelo.getPreguntas().getIdPregunta());
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
		Log.i("MicroMan", getNombreTabla() + "Actualizar" + rtn);
		return rtn;
	}

	@Override
	public int eliminar() {
		int rtn = 0;
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
