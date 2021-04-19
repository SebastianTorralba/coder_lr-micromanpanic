package ar.com.twoboot.microman.dominio.sincronizacion;

import java.sql.Connection;

public class OnSync {
	protected Connection mConexion = null;

	public OnSync(Connection conexion) {
		mConexion = conexion;
	}

}
