package ar.com.twoboot.panico.dominio;

import java.util.ArrayList;

import android.content.Context;
import ar.com.twoboot.microman.dominio.IListable;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.Transaccionable;
import ar.com.twoboot.microman.objetos.DalusObject;

public class OnUsuarioPerfil extends OnNegocio implements IListable, Transaccionable {

	public OnUsuarioPerfil(Transaccion paramTransaccion) {
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

	public void eliminarTodo(Context context) {
	}

	@Override
	public void eliminarTodo() {
		// TODO Auto-generated method stub
	}

}
