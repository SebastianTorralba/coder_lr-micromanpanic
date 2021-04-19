/***********************************************************************
 * Module:  UsuarioPerfil.java
 * Author:  Sebastian
 * Purpose: Defines the Class UsuarioPerfil
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid cdbf94de-b819-45d4-a124-45c276958d8d */
public class UsuarioPerfil extends DalusObject {
	/** @pdOid 2cea1dbb-7f7c-4a12-ada8-13c643896cc7 */
	private java.lang.String nomPerfil;

	/** @pdRoleInfo migr=no name=Usuarios assc=usuarioPefiles mult=0..1 side=A */
	public Usuario usuarios;

	/** @pdOid d2f48ace-2717-4c06-b047-87e9e4c4da1a */
	public java.lang.String getNomPerfil() {
		return nomPerfil;
	}

	/**
	 * @param newNomPerfil
	 * @pdOid 06eb18f0-2d0c-4ce5-9bd5-1fb0ca09fbd7
	 */
	public void setNomPerfil(java.lang.String newNomPerfil) {
		nomPerfil = newNomPerfil;
	}

	/** @pdGenerated default parent getter */
	public Usuario getUsuarios() {
		return usuarios;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newUsuarios
	 */
	public void setUsuarios(Usuario newUsuarios) {
		if (this.usuarios == null || !this.usuarios.equals(newUsuarios)) {
			if (this.usuarios != null) {
				Usuario oldUsuarios = this.usuarios;
				this.usuarios = null;
				oldUsuarios.removeUsuarioPerfil(this);
			}
			if (newUsuarios != null) {
				this.usuarios = newUsuarios;
				this.usuarios.addUsuarioPerfil(this);
			}
		}
	}

}