package ar.com.twoboot.microman.dominio;

import java.sql.Date;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import ar.com.twoboot.microman.objetos.DalusObject;

public class OnNegocio {
	public static final int ACCION_ELIMINAR = 9;
	public static final int ACCION_MODIFICAR = 2;
	public static final int ACCION_NUEVO = 1;
	public static final int ACCION_VER = 3;
	private ArrayList<?> listado;
	private String nombreTabla;
	protected Transaccion transaccion;
	protected ContentValues valores;
	protected String whereString;
	protected ProgressDialog pDialog;
	private Context context;
	
	public OnNegocio(Transaccion paramTransaccion) {
		this.transaccion = paramTransaccion;
		
		valores=new ContentValues();
		
	}

	public Integer quitarNull(Integer dato) {
		if (dato == null) {
			dato = 0;
		}
		return dato;
	}

	public String quitarNull(String dato) {
		if (dato == null) {
			dato = "";
		}
		return dato;
	}

	public Double quitarNull(Double dato) {
		if (dato == null) {
			dato = 0.0;
		}
		return dato;
	}

	public Date quitarNull(Date dato) {
		if (dato == null) {
			dato = new Date(1900, 1, 1);
		}
		return dato;
	}

	public ArrayList<?> getListado() {
		return this.listado;
	}

	public String getNombreTabla() {
		return this.nombreTabla;
	}

	public Transaccion getTransaccion() {
		return this.transaccion;
	}

	public ContentValues getValores() {
		return this.valores;
	}

	public void setListado(ArrayList<?> paramArrayList) {
		this.listado = paramArrayList;
	}

	public void setNombreTabla(String paramString) {
		this.nombreTabla = paramString;
	}

	public void setTransaccion(Transaccion paramTransaccion) {
		this.transaccion = paramTransaccion;
	}

	public void setValores(ContentValues paramContentValues) {
		this.valores = paramContentValues;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public static abstract interface IAccion {
		public abstract int accionEliminar();

		public abstract int accionModificar();

		public abstract int accionNuevo();

		public abstract int accionVer();

		public abstract DalusObject consolidar();
	}
}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.dominio.OnNegocio JD-Core Version: 0.6.0
 */