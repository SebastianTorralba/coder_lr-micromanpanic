package ar.com.twoboot.panico.dominio;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import ar.com.twoboot.microman.dominio.IListable;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.Transaccionable;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.objetos.Categoria;

public class OnCategoria extends OnNegocio implements IListable, Transaccionable {
	private Categoria categoria = null;

	@Override
	public ArrayList<?> getListado() {
		ArrayList<Categoria> categoriasListado = new ArrayList<Categoria>();
		String selectQuery = "select * from categorias ";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Categoria c = new Categoria();
				c.setCodCategoria(Util.quitarNull(cursor.getString(0)));
				c.setCaterogia(Util.quitarNull(cursor.getString(1)));
				categoriasListado.add(c);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return categoriasListado;
	}

	public Categoria extraer(String codCategoria) {
		String selectQuery = "select * from categorias where cod_categoria='" + codCategoria + "'";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			this.categoria = new Categoria();
			categoria.setCodCategoria(Util.quitarNull(cursor.getString(0)));
			categoria.setCaterogia(Util.quitarNull(cursor.getString(1)));
		}
		cursor.close();
		return categoria;
	}

	public OnCategoria(Transaccion paramTransaccion) {
		super(paramTransaccion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int actualizar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertar() {
		// TODO Auto-generated method stub
		return 0;
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
	}

	@Override
	public void eliminarTodo() {
		// TODO Auto-generated method stub
	}

}
